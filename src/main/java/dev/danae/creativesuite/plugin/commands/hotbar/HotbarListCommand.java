package dev.danae.creativesuite.plugin.commands.hotbar;

import dev.danae.creativesuite.model.Hotbar;
import dev.danae.creativesuite.model.Manager;
import dev.danae.creativesuite.plugin.commands.ManagerCommand;
import dev.danae.commons.commands.CommandContext;
import dev.danae.commons.commands.CommandException;
import dev.danae.commons.commands.CommandUsageException;
import java.util.List;


public class HotbarListCommand extends ManagerCommand
{
  // Constructor
  public HotbarListCommand(Manager manager)
  {
    super(manager, "creativesuite.hotbar.list");
  }
    
  
  // Handle the command
  @Override
  public void handle(CommandContext context) throws CommandException, CommandUsageException
  {
    // Validate the number of arguments
    if (!context.hasArgumentsCount(0))
      throw new CommandUsageException();
    
    // Send a message listing the hotbars
    context.sendMessage(HotbarFormatter.formatHotbarListMessage(this.getManager().getDefinedHotbars()));
  }

  // Handle tab completion of the command
  @Override
  public List<String> handleTabCompletion(CommandContext context)
  {
    return List.of();
  }
}
