package dev.danae.creativesuite.plugin.commands.alias;

import dev.danae.commons.commands.CommandContext;
import dev.danae.commons.commands.CommandException;
import dev.danae.commons.commands.CommandUsageException;
import dev.danae.commons.parser.ParserException;
import dev.danae.creativesuite.model.Manager;
import dev.danae.creativesuite.plugin.commands.ManagerCommand;
import java.util.List;
import java.util.Map;


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
      var destination = scanner.nextNamespacedKey();

      var alias = this.getManager().getAlias(key);
      if (alias == null)
        throw new CommandException(this.formatMessage("alias-not-found", Map.of("key", key)));

      var destinationAlias = this.getManager().getAlias(destination);

      // Check if the destination alias exists
      if (destinationAlias != null)
      {
        // Send a message about the otherwise overwritten alias
        context.sendMessage(this.formatMessage("alias-cannot-rename", Map.of("key", key, "destination", destination)));
      }
      else
      {
        // Save the new alias and remove the old alias
        this.getManager().setAlias(destination, alias);
        this.getManager().removeAlias(key);

        // Send a message about the renamed alias
        context.sendMessage(this.formatMessage("alias-renamed", Map.of("key", key, "destination", destination)));
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
