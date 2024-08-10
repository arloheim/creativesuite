package dev.danae.creativesuite.plugin.commands.hotbar;

import dev.danae.creativesuite.plugin.commands.PluginComponentCommand;
import dev.danae.creativesuite.plugin.components.commands.HotbarComponent;
import dev.danae.creativesuite.util.commands.CommandContext;
import dev.danae.creativesuite.util.commands.CommandException;
import dev.danae.creativesuite.util.commands.CommandUsageException;
import java.util.List;


public class HotbarListCommand extends PluginComponentCommand<HotbarComponent>
{
  // Constructor
  public HotbarListCommand(HotbarComponent component)
  {
    super(component, "creativesuite.hotbar.list");
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
