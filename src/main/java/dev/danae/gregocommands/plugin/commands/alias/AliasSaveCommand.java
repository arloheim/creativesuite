package dev.danae.gregocommands.plugin.commands.alias;

import dev.danae.gregocommands.model.alias.Alias;
import dev.danae.gregocommands.model.hotbar.Hotbar;
import dev.danae.gregocommands.plugin.Formatter;
import dev.danae.gregocommands.plugin.GregoCommandsPlugin;
import dev.danae.gregocommands.plugin.commands.CommandContext;
import dev.danae.gregocommands.plugin.commands.CommandException;
import dev.danae.gregocommands.plugin.commands.CommandUsageException;
import dev.danae.gregocommands.plugin.commands.PluginCommand;
import dev.danae.gregocommands.util.parser.ParserException;
import java.util.List;
import org.bukkit.GameMode;


public class AliasSaveCommand extends PluginCommand
{
  // Boolean that indicates if aliases automatically get overwritten
  private final boolean overwriteAliases;

  // Constructor
  public AliasSaveCommand(GregoCommandsPlugin plugin, boolean overwriteAliases)
  {
    super(plugin, "gregocommands.alias.save");

    this.overwriteAliases = overwriteAliases;
  }
    
  
  // Handle the command
  @Override
  public void handle(CommandContext context) throws CommandException, CommandUsageException
  {     
    var aliases = this.getPlugin().getDefinedAliases();

    try
    {
      // Validate the number of arguments
      if (!context.hasAtLeastArgumentsCount(2))
        throw new CommandUsageException();
      
      // Create a scanner for the arguments
      var scanner = context.getArgumentsScanner();
      
      // Parse the arguments
      var key = scanner.nextKey();
      var command = scanner.rest("command");
      var existingKey = aliases.containsKey(key);

      // Check if we can overwrite and existing hotbar
      if (!this.overwriteAliases && existingKey)
      {
        // Send a message about the otherwise overwritten alias
        context.sendMessage(Formatter.formatAliasOverwriteMessage(key, command));
      }
      else
      {
        // Save the alias
        aliases.put(key, new Alias(command));

        // Send a message about the saved alias
        context.sendMessage(Formatter.formatAliasSavedMessage(key, command, existingKey));
      }
    }
    catch (ParserException ex)
    {
      throw new CommandException(ex.getMessage(), ex);
    }
  }
  
  // Handle tab completion of the command
  @Override
  public List<String> handleTabCompletion(CommandContext context)
  {
    if (context.hasArgumentsCount(1))
      return this.handleAliasTabCompletion(context.getArgument(0));
    else
      return List.of();
  }
}
