package dev.danae.gregocommands.plugin.commands.charmap;

import dev.danae.gregocommands.plugin.commands.PluginComponentCommand;
import dev.danae.gregocommands.plugin.components.charmap.CharmapComponent;
import dev.danae.gregocommands.util.parser.ParserException;
import dev.danae.gregocommands.util.commands.CommandContext;
import dev.danae.gregocommands.util.commands.CommandException;
import dev.danae.gregocommands.util.commands.CommandUsageException;
import java.util.List;


public class CharmapAddCommand extends PluginComponentCommand<CharmapComponent>
{
  // Constructor
  public CharmapAddCommand(CharmapComponent component)
  {
    super(component, "gregocommands.charmap.add");
  }
    
  
  // Handle the command
  @Override
  public void handle(CommandContext context) throws CommandException, CommandUsageException
  {     
    try
    {
      // Validate the number of arguments
      if (!context.hasArgumentsCount(1))
        throw new CommandUsageException();
      
      // Create a scanner for the arguments
      var scanner = context.getArgumentsScanner();
      
      // Parse the arguments
      var codePoints = scanner.next();

      // Add the code points to the charmap
      this.getComponent().getCharmap().addCodePoints(codePoints);

      // Send a message about the added code points
      context.sendMessage(this.getComponent().formatCharmapAddedMessage(codePoints));
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
