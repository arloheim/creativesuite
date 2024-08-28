package dev.danae.creativesuite.plugin.commands.charmap;

import dev.danae.creativesuite.model.Manager;
import dev.danae.creativesuite.plugin.commands.ManagerCommand;
import dev.danae.commons.commands.CommandContext;
import dev.danae.commons.commands.CommandException;
import dev.danae.commons.commands.CommandUsageException;
import dev.danae.commons.messages.MessageFunction;
import java.util.List;


public class CharmapListCommand extends ManagerCommand
{
  // Constants for the page size and columns
  public static int COLUMNS = 16;


  // Constructor
  public CharmapListCommand(Manager manager)
  {
    super(manager, "creativesuite.charmap.list");
  }
    
  
  // Handle the command
  @Override
  public void handle(CommandContext context) throws CommandException, CommandUsageException
  {
    // Validate the number of arguments
    if (!context.hasArgumentsCount(0))
      throw new CommandUsageException();
    
    // Send a message listing the hotbars
    context.sendMessage(CharmapFormatter.formatCharmapListMessage(this.getManager().getCharmap(), COLUMNS));
  }

  // Handle tab completion of the command
  @Override
  public List<String> handleTabCompletion(CommandContext context)
  {
    return List.of();
  }
}
