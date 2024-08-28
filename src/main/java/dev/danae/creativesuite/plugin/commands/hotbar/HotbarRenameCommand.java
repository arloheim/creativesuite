package dev.danae.creativesuite.plugin.commands.hotbar;

import dev.danae.commons.commands.CommandContext;
import dev.danae.commons.commands.CommandException;
import dev.danae.commons.commands.CommandUsageException;
import dev.danae.commons.parser.ParserException;
import dev.danae.creativesuite.model.Manager;
import dev.danae.creativesuite.plugin.commands.ManagerCommand;
import java.util.List;
import java.util.Map;


public class HotbarRenameCommand extends ManagerCommand
{
  // Constructor
  public HotbarRenameCommand(Manager manager)
  {
    super(manager, "creativesuite.hotbar.rename");
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

      var hotbar = this.getManager().getHotbar(key);
      if (hotbar == null)
        throw new CommandException(this.formatMessage("hotbar-not-found", Map.of("name", key)));

      var destinationHotbar = this.getManager().getHotbar(destination);

      // Check if the destination hotbar exists
      if (destinationHotbar != null)
      {
        // Send a message about the otherwise overwritten alias
        context.sendMessage(this.formatMessage("hotbar-cannt-rename", Map.of("name", key, "destination", destination)));
      }
      else
      {
        // Save the new hotbar and remove the old hotbar
        this.getManager().setHotbar(destination, hotbar);
        this.getManager().removeHotbar(key);

        // Send a message about the renamed hotbar
        context.sendMessage(this.formatMessage("hotbar-renamed", Map.of("name", key)));
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
    if (context.hasArgumentsCount(2))
      return this.handleHotbarTabCompletion(context.getArgument(1));
    else if (context.hasArgumentsCount(1))
      return this.handleHotbarTabCompletion(context.getArgument(0));
    else
      return List.of();
  }
}
