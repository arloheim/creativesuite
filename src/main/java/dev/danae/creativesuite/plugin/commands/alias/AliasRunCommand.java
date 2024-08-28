package dev.danae.creativesuite.plugin.commands.alias;

import dev.danae.commons.commands.CommandContext;
import dev.danae.commons.commands.CommandException;
import dev.danae.commons.commands.CommandUsageException;
import dev.danae.commons.parser.ParserException;
import dev.danae.creativesuite.model.Manager;
import dev.danae.creativesuite.plugin.commands.ManagerCommand;
import java.util.List;


public class AliasRunCommand extends ManagerCommand
{
  // Constructor
  public AliasRunCommand(Manager manager)
  {
    super(manager, "creativesuite.alias.run");
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
      var key = scanner.nextNamespacedKey();
      
      var alias = this.getManager().getAlias(key);
      if (alias == null)
        throw new CommandException(String.format("Alias %s does not exist", key.toString()));

      // Dispatch the command of the alias
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
