package dev.danae.gregocommands.plugin.commands.alias;

import dev.danae.gregocommands.plugin.GregoCommandsPlugin;
import dev.danae.gregocommands.plugin.commands.CommandContext;
import dev.danae.gregocommands.plugin.commands.CommandException;
import dev.danae.gregocommands.plugin.commands.CommandUsageException;
import dev.danae.gregocommands.plugin.commands.PluginCommand;
import dev.danae.gregocommands.util.parser.ParserException;
import java.util.List;


public class AliasRunCommand extends PluginCommand
{
  // Constructor
  public AliasRunCommand(GregoCommandsPlugin plugin)
  {
    super(plugin, "gregocommands.alias.run");
  }
    
  
  // Handle the command
  @Override
  public void handle(CommandContext context) throws CommandException, CommandUsageException
  {    
    var aliases = this.getPlugin().getDefinedAliases();

    try
    {
      // Validate the number of arguments
      if (!context.hasArgumentsCount(1))
        throw new CommandUsageException();
      
      // Create a scanner for the arguments
      var scanner = context.getArgumentsScanner();
      
      // Parse the arguments
      var key = scanner.nextKey();
      var existingKey = aliases.containsKey(key);
      if (!existingKey)
        throw new CommandException(String.format("Alias %s does not exist", key.toString()));

      // Dispatch the command of the alias
      var alias = aliases.get(key);
      alias.dispatchCommand(context.getSender());
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
