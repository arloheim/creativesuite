package dev.danae.creativesuite.plugin.commands.tools;

import dev.danae.commons.Locations;
import dev.danae.commons.commands.CommandContext;
import dev.danae.commons.commands.CommandException;
import dev.danae.commons.commands.CommandUsageException;
import dev.danae.commons.parser.ParserException;
import dev.danae.creativesuite.model.Manager;
import dev.danae.creativesuite.plugin.commands.ManagerCommand;
import dev.danae.creativesuite.util.Toggle;
import java.util.List;
import org.bukkit.GameMode;


public class ToolsNightVisionCommand extends ManagerCommand
{
  // Constructor
  public ToolsNightVisionCommand(Manager manager)
  {
    super(manager, "creativesuite.tools.nightvision");
  }
    
  
  // Handle the command
  @Override
  public void handle(CommandContext context) throws CommandException, CommandUsageException
  {     
    // Assert that the command sender is a player and in creative mode
    var player = context.assertSenderIsPlayer(GameMode.CREATIVE);

    // Validate the number of arguments
    if (!context.hasAtLeastArgumentsCount(1))
      throw new CommandUsageException();
    
    // Create a scanner for the arguments
    var scanner = context.getArgumentsScanner();
      
    // Parse the arguments
    var toggle = scanner.nextEnum(Toggle.class, "on, off, or toggle", Toggle.TOGGLE);

    var targetPlayer = scanner.nextPlayer(player);
    if (targetPlayer != player)
      context.assertSenderHasPermissions("creativesuite.tools.nightvision.others");

    // Update the night vision effect of the player
    this.getManager().updateNightVision(targetPlayer, toggle);
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
