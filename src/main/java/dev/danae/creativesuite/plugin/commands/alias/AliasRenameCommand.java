package dev.danae.creativesuite.plugin.commands.alias;

import dev.danae.commons.commands.CommandContext;
import dev.danae.commons.commands.CommandException;
import dev.danae.commons.commands.CommandUsageException;
import dev.danae.commons.parser.ParserException;
import dev.danae.creativesuite.model.Manager;
import dev.danae.creativesuite.plugin.commands.ManagerCommand;
import java.util.List;


public class AliasRenameCommand extends ManagerCommand
{
  // Constructor
  public AliasRenameCommand(Manager manager)
  {
    super(manager, "creativesuite.alias.rename");
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
      var key = scanner.nextNamespacedKey();
      var newKey = scanner.nextNamespacedKey();
      var alias = this.getManager().getAlias(key);

      // Check if the alias exists
      if (alias != null)
      {
        // Send a message about the otherwise overwritten alias
        context.sendMessage(AliasFormatter.formatAliasRenameOverwriteMessage(key, newKey));
      }
      else
      {
        // Save the new alias and remove the old alias
        this.getManager().setAlias(newKey, alias);
        this.getManager().removeAlias(key);

        // Send a message about the renamed alias
        context.sendMessage(AliasFormatter.formatAliasRenamedMessage(key, newKey));
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
