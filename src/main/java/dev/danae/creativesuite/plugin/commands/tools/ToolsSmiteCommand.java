package dev.danae.creativesuite.plugin.commands.tools;

import dev.danae.commons.Locations;
import dev.danae.commons.commands.CommandContext;
import dev.danae.commons.commands.CommandException;
import dev.danae.commons.commands.CommandUsageException;
import dev.danae.commons.parser.ParserException;
import dev.danae.creativesuite.model.Manager;
import dev.danae.creativesuite.plugin.commands.ManagerCommand;
import java.util.List;
import org.bukkit.GameMode;


public class ToolsSmiteCommand extends ManagerCommand
{
  // Constructor
  public ToolsSmiteCommand(Manager manager)
  {
    super(manager, "creativesuite.tools.smite");
  }
    
  
  // Handle the command
  @Override
  public void handle(CommandContext context) throws CommandException, CommandUsageException
  {     
    try
    {
      // Assert that the command sender is a player and in creative mode
      var player = context.assertSenderIsPlayer(GameMode.CREATIVE);

      // Validate the number of arguments
      if (!context.hasAtLeastArgumentsCount(1))
        throw new CommandUsageException();
      
      // Create a scanner for the arguments
      var scanner = context.getArgumentsScanner();
      
      // Parse the arguments
      var location = scanner.nextLocation(player.getLocation());

      // Execute a lightning effect at the location
      this.getManager().smite(location);
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
    if (context.hasAtLeastArgumentsCount(1))
      return Locations.handleTabCompletion(context, 0);
    else
      return List.of();
  }
}
