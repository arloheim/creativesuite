package dev.danae.gregocommands.plugin.commands.hotbar;

import dev.danae.gregocommands.plugin.commands.PluginComponentCommand;
import dev.danae.gregocommands.plugin.components.hotbar.HotbarComponent;
import dev.danae.gregocommands.util.commands.CommandContext;
import dev.danae.gregocommands.util.commands.CommandException;
import dev.danae.gregocommands.util.commands.CommandUsageException;
import java.util.List;


public class HotbarListCommand extends PluginComponentCommand<HotbarComponent>
{
  // Constant for the page size
  public static int PAGE_SIZE = 20;


  // Constructor
  public HotbarListCommand(HotbarComponent component)
  {
    super(component, "gregocommands.hotbar.list");
  }
    
  
  // Handle the command
  @Override
  public void handle(CommandContext context) throws CommandException, CommandUsageException
  {
    // Validate the number of arguments
    if (!context.hasArgumentsCount(0))
      throw new CommandUsageException();
    
    // Send a message listing the hotbars
    context.sendMessage(this.getComponent().formatHotbarListMessage());
  }

  // Handle tab completion of the command
  @Override
  public List<String> handleTabCompletion(CommandContext context)
  {
    return List.of();
  }
}
