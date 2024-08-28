package dev.danae.creativesuite.plugin.commands.hotbar;

import dev.danae.creativesuite.model.Manager;
import dev.danae.creativesuite.plugin.commands.ManagerCommand;
import dev.danae.creativesuite.util.parser.ParserException;
import dev.danae.creativesuite.util.commands.CommandContext;
import dev.danae.creativesuite.util.commands.CommandException;
import dev.danae.creativesuite.util.commands.CommandUsageException;
import java.util.List;


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
      var newKey = scanner.nextNamespacedKey();
      var hotbar = this.getManager().getHotbar(key);

      // Check if the hotbar exists
      if (hotbar != null)
      {
        // Send a message about the otherwise overwritten alias
        context.sendMessage(HotbarFormatter.formatHotbarRenameOverwriteMessage(key, newKey));
      }
      else
      {
        // Save the new hotbar and remove the old hotbar
        this.getManager().setHotbar(newKey, hotbar);
        this.getManager().removeHotbar(key);

        // Send a message about the renamed hotbar
        context.sendMessage(HotbarFormatter.formatHotbarRenamedMessage(key, newKey));
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
