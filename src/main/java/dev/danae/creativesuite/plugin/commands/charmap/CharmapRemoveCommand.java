package dev.danae.creativesuite.plugin.commands.charmap;

import dev.danae.creativesuite.plugin.commands.PluginComponentCommand;
import dev.danae.creativesuite.plugin.components.charmap.CharmapComponent;
import dev.danae.creativesuite.util.parser.ParserException;
import dev.danae.creativesuite.util.commands.CommandContext;
import dev.danae.creativesuite.util.commands.CommandException;
import dev.danae.creativesuite.util.commands.CommandUsageException;
import java.util.List;


public class CharmapRemoveCommand extends PluginComponentCommand<CharmapComponent>
{
  // Constructor
  public CharmapRemoveCommand(CharmapComponent component)
  {
    super(component, "creativesuite.charmap.remove");
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

      // Remove the code points from the charmap
      this.getComponent().getCharmap().removeCodePoints(codePoints);

      // Send a message about the added code points
      context.sendMessage(this.getComponent().formatCharmapRemovedMessage(codePoints));
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
