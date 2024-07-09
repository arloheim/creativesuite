package dev.danae.gregocommands.plugin.commands.charmap;

import dev.danae.gregocommands.plugin.Formatter;
import dev.danae.gregocommands.plugin.GregoCommandsPlugin;
import dev.danae.gregocommands.plugin.commands.CommandContext;
import dev.danae.gregocommands.plugin.commands.CommandException;
import dev.danae.gregocommands.plugin.commands.CommandUsageException;
import dev.danae.gregocommands.plugin.commands.PluginCommand;
import java.util.List;


public class CharmapListCommand extends PluginCommand
{
  // Constants for the page size and columns
  public static int COLUMNS = 16;


  // Constructor
  public CharmapListCommand(GregoCommandsPlugin plugin)
  {
    super(plugin, "gregocommands.charmap.list");
  }
    
  
  // Handle the command
  @Override
  public void handle(CommandContext context) throws CommandException, CommandUsageException
  {    
    var charmap = this.getPlugin().getCharmap();

    // Validate the number of arguments
    if (!context.hasArgumentsCount(0))
      throw new CommandUsageException();
    
    // Send a message listing the hotbars
    context.sendMessage(Formatter.formatCharmapListMessage(charmap, COLUMNS));
  }

  // Handle tab completion of the command
  @Override
  public List<String> handleTabCompletion(CommandContext context)
  {
    return List.of();
  }
}
