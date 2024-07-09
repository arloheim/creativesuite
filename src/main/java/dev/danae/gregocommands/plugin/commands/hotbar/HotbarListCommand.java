package dev.danae.gregocommands.plugin.commands.hotbar;

import dev.danae.gregocommands.plugin.Formatter;
import dev.danae.gregocommands.plugin.GregoCommandsPlugin;
import dev.danae.gregocommands.plugin.commands.CommandContext;
import dev.danae.gregocommands.plugin.commands.CommandException;
import dev.danae.gregocommands.plugin.commands.CommandUsageException;
import dev.danae.gregocommands.plugin.commands.PluginCommand;
import java.util.List;


public class HotbarListCommand extends PluginCommand
{
  // Constant for the page size
  public static int PAGE_SIZE = 20;


  // Constructor
  public HotbarListCommand(GregoCommandsPlugin plugin)
  {
    super(plugin, "gregocommands.hotbar.list");
  }
    
  
  // Handle the command
  @Override
  public void handle(CommandContext context) throws CommandException, CommandUsageException
  {    
    var hotbars = this.getPlugin().getDefinedHotbars();

    // Validate the number of arguments
    if (!context.hasArgumentsCount(0))
      throw new CommandUsageException();
    
    // Send a message listing the hotbars
    context.sendMessage(Formatter.formatHotbarListMessage(hotbars));
  }

  // Handle tab completion of the command
  @Override
  public List<String> handleTabCompletion(CommandContext context)
  {
    return List.of();
  }
}
