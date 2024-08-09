package dev.danae.gregocommands.plugin.commands.alias;

import dev.danae.gregocommands.plugin.commands.PluginComponentCommand;
import dev.danae.gregocommands.plugin.components.alias.Alias;
import dev.danae.gregocommands.plugin.components.alias.AliasComponent;
import dev.danae.gregocommands.util.parser.ParserException;
import dev.danae.gregocommands.util.commands.CommandContext;
import dev.danae.gregocommands.util.commands.CommandException;
import dev.danae.gregocommands.util.commands.CommandUsageException;
import java.util.List;


public class AliasSaveCommand extends PluginComponentCommand<AliasComponent>
{
  // Boolean that indicates if aliases automatically get overwritten
  private final boolean overwriteAliases;

  // Constructor
  public AliasSaveCommand(AliasComponent component, boolean overwriteAliases)
  {
    super(component, "gregocommands.alias.save");

    this.overwriteAliases = overwriteAliases;
  }
    
  
  // Handle the command
  @Override
  public void handle(CommandContext context) throws CommandException, CommandUsageException
  {
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
      var existingKey = this.getComponent().getAliases().containsKey(key);

      // Check if we can overwrite and existing hotbar
      if (!this.overwriteAliases && existingKey)
      {
        // Send a message about the otherwise overwritten alias
        context.sendMessage(this.getComponent().formatAliasOverwriteMessage(key, command));
      }
      else
      {
        // Save the alias
        this.getComponent().getAliases().put(key, new Alias(command));

        // Send a message about the saved alias
        context.sendMessage(this.getComponent().formatAliasSavedMessage(key, command, existingKey));
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
      return this.getComponent().handleAliasTabCompletion(context.getArgument(0));
    else
      return List.of();
  }
}
