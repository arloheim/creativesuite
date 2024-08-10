package dev.danae.creativesuite.plugin.commands.charmap;

import dev.danae.creativesuite.plugin.commands.PluginComponentCommand;
import dev.danae.creativesuite.plugin.components.commands.CharmapComponent;
import dev.danae.creativesuite.util.commands.CommandContext;
import dev.danae.creativesuite.util.commands.CommandException;
import dev.danae.creativesuite.util.commands.CommandUsageException;
import java.util.List;


public class CharmapListCommand extends PluginComponentCommand<CharmapComponent>
{
  // Constants for the page size and columns
  public static int COLUMNS = 16;


  // Constructor
  public CharmapListCommand(CharmapComponent component)
  {
    super(component, "creativesuite.charmap.list");
  }
    
  
  // Handle the command
  @Override
  public void handle(CommandContext context) throws CommandException, CommandUsageException
  {
    // Validate the number of arguments
    if (!context.hasArgumentsCount(0))
      throw new CommandUsageException();
    
    // Send a message listing the hotbars
    context.sendMessage(this.getComponent().formatCharmapListMessage(COLUMNS));
  }

  // Handle tab completion of the command
  @Override
  public List<String> handleTabCompletion(CommandContext context)
  {
    return List.of();
  }
}
