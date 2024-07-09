package dev.danae.gregocommands.plugin.commands.charmap;

import dev.danae.gregocommands.plugin.Formatter;
import dev.danae.gregocommands.plugin.GregoCommandsPlugin;
import dev.danae.gregocommands.plugin.commands.CommandContext;
import dev.danae.gregocommands.plugin.commands.CommandException;
import dev.danae.gregocommands.plugin.commands.CommandUsageException;
import dev.danae.gregocommands.plugin.commands.PluginCommand;
import dev.danae.gregocommands.util.parser.ParserException;
import java.util.List;


public class CharmapRemoveCommand extends PluginCommand
{
  // Constructor
  public CharmapRemoveCommand(GregoCommandsPlugin plugin)
  {
    super(plugin, "gregocommands.charmap.remove");
  }
    
  
  // Handle the command
  @Override
  public void handle(CommandContext context) throws CommandException, CommandUsageException
  {
    var charmap = this.getPlugin().getCharmap();

    try
    {
      // Validate the number of arguments
      if (!context.hasArgumentsCount(1))
        throw new CommandUsageException();
      
      // Create a scanner for the arguments
      var scanner = context.getArgumentsScanner();
      
      // Parse the arguments
      var codePoints = scanner.next();

      // Remove the code points from the charmap
      charmap.removeCodePoints(codePoints);

      // Send a message about the added code points
      context.sendMessage(Formatter.formatCharmapRemovedMessage(codePoints));
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
    return List.of();
  }
}
