package dev.danae.gregocommands.plugin.commands.alias;

import dev.danae.gregocommands.plugin.commands.PluginComponentCommand;
import dev.danae.gregocommands.plugin.components.alias.AliasComponent;
import dev.danae.gregocommands.util.parser.ParserException;
import dev.danae.gregocommands.util.commands.CommandContext;
import dev.danae.gregocommands.util.commands.CommandException;
import dev.danae.gregocommands.util.commands.CommandUsageException;
import java.util.List;


public class AliasRunCommand extends PluginComponentCommand<AliasComponent>
{
  // Constructor
  public AliasRunCommand(AliasComponent component)
  {
    super(component, "gregocommands.alias.run");
  }
    
  
  // Handle the command
  @Override
  public void handle(CommandContext context) throws CommandException, CommandUsageException
  {
    try
    {
      // Validate the number of arguments
      if (!context.hasArgumentsCount(1))
        throw new CommandUsageException();
      
      // Create a scanner for the arguments
      var scanner = context.getArgumentsScanner();
      
      // Parse the arguments
      var key = scanner.nextKey();
      var existingKey = this.getComponent().getAliases().containsKey(key);
      if (!existingKey)
        throw new CommandException(String.format("Alias %s does not exist", key.toString()));

      // Dispatch the command of the alias
      var alias = this.getComponent().getAliases().get(key);
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
      return this.getComponent().handleAliasTabCompletion(context.getArgument(0));
    else
      return List.of();
  }
}
