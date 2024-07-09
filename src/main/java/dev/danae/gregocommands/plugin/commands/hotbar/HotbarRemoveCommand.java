package dev.danae.gregocommands.plugin.commands.hotbar;

import dev.danae.gregocommands.plugin.Formatter;
import dev.danae.gregocommands.plugin.GregoCommandsPlugin;
import dev.danae.gregocommands.plugin.commands.CommandContext;
import dev.danae.gregocommands.plugin.commands.CommandException;
import dev.danae.gregocommands.plugin.commands.CommandUsageException;
import dev.danae.gregocommands.plugin.commands.PluginCommand;
import dev.danae.gregocommands.util.parser.ParserException;
import java.util.List;


public class HotbarRemoveCommand extends PluginCommand
{
  // Constructor
  public HotbarRemoveCommand(GregoCommandsPlugin plugin)
  {
    super(plugin, "gregocommands.hotbar.remove");
  }
    
  
  // Handle the command
  @Override
  public void handle(CommandContext context) throws CommandException, CommandUsageException
  {     
    var hotbars = this.getPlugin().getDefinedHotbars();

    try
    {
      // Validate the number of arguments
      if (!context.hasArgumentsCount(1))
        throw new CommandUsageException();
      
      // Create a scanner for the arguments
      var scanner = context.getArgumentsScanner();
      
      // Parse the arguments
      var key = scanner.nextKey();
      var existingKey = hotbars.containsKey(key);
      if (!existingKey)
        throw new CommandException(String.format("Hotbar %s does not exist", key.toString()));

      // Remove the hotbar
      hotbars.remove(key);

      // Send a message about the removed hotbar
      context.sendMessage(Formatter.formatHotbarRemovedMessage(key));
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
      return this.handleHotbarTabCompletion(context.getArgument(0));
    else
      return List.of();
  }
}
