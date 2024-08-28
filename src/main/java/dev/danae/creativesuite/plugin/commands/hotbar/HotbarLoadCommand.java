package dev.danae.creativesuite.plugin.commands.hotbar;

import dev.danae.commons.commands.CommandContext;
import dev.danae.commons.commands.CommandException;
import dev.danae.commons.commands.CommandUsageException;
import dev.danae.commons.parser.ParserException;
import dev.danae.creativesuite.model.Manager;
import dev.danae.creativesuite.plugin.commands.ManagerCommand;
import java.util.List;
import java.util.Map;
import org.bukkit.GameMode;


public class HotbarLoadCommand extends ManagerCommand
{
  // Constructor
  public HotbarLoadCommand(Manager manager)
  {
    super(manager, "creativesuite.hotbar.load");
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
      if (!context.hasArgumentsCount(1))
        throw new CommandUsageException();
      
      // Create a scanner for the arguments
      var scanner = context.getArgumentsScanner();
      
      // Parse the arguments
      var key = scanner.nextNamespacedKey();
      
      var hotbar = this.getManager().getHotbar(key);
      if (hotbar == null)
        throw new CommandException(this.formatMessage("hotbar-not-found", Map.of("key", key)));

      // Load the hotbar
      hotbar.applyTo(player.getInventory());

      // Send a message about the loaded hotbar
      context.sendMessage(this.formatMessage("hotbar-loaded", Map.of("key", key)));
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
