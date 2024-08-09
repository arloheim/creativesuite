package dev.danae.gregocommands.plugin.commands.charmap;

import dev.danae.gregocommands.plugin.commands.PluginComponentCommand;
import dev.danae.gregocommands.plugin.components.charmap.CharmapComponent;
import dev.danae.gregocommands.util.commands.CommandContext;
import dev.danae.gregocommands.util.commands.CommandException;
import dev.danae.gregocommands.util.commands.CommandUsageException;
import java.util.List;


public class CharmapListCommand extends PluginComponentCommand<CharmapComponent>
{
  // Constants for the page size and columns
  public static int COLUMNS = 16;


  // Constructor
  public CharmapListCommand(CharmapComponent component)
  {
    super(component, "gregocommands.charmap.list");
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
