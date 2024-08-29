package dev.danae.creativesuite.plugin.commands.tools;

import dev.danae.commons.Players;
import dev.danae.commons.commands.CommandContext;
import dev.danae.commons.commands.CommandException;
import dev.danae.commons.commands.CommandUsageException;
import dev.danae.creativesuite.model.Manager;
import dev.danae.creativesuite.plugin.commands.ManagerCommand;
import dev.danae.creativesuite.util.Toggle;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;
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
    
    // Create a scanner for the arguments
    var scanner = context.getArgumentsScanner();
      
    // Parse the arguments
    var toggle = scanner.nextEnum(Toggle.class, "on, off, or toggle", Toggle.TOGGLE);

    var targetPlayer = scanner.nextPlayer(player);
    if (targetPlayer != player)
      context.assertSenderHasPermissions("creativesuite.tools.nightvision.others");

    // Update the night vision effect of the player
    var enabled = this.getManager().updateNightVision(targetPlayer, toggle);

    // Send a message about the executed command
    context.sendMessage(this.formatMessage(enabled ? "nightvision-turned-on" : "nightvision-turned-off", Map.of("player", targetPlayer.getName())));
  }

  // Handle tab completion of the command
  @Override
  public List<String> handleTabCompletion(CommandContext context)
  {
    if (context.hasAtLeastArgumentsCount(2))
      return Players.names().toList();
    else if (context.hasArgumentsCount(1))
      return Stream.concat(Players.names(), Stream.of("off", "on", "toggle")).toList();
    else
      return List.of();
  }
}
