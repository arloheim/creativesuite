package dev.danae.creativesuite.plugin.commands.hotbar;

import dev.danae.creativesuite.plugin.commands.PluginComponentCommand;
import dev.danae.creativesuite.plugin.components.commands.HotbarComponent;
import dev.danae.creativesuite.util.parser.ParserException;
import dev.danae.creativesuite.util.commands.CommandContext;
import dev.danae.creativesuite.util.commands.CommandException;
import dev.danae.creativesuite.util.commands.CommandUsageException;
import java.util.List;
import org.bukkit.GameMode;


public class HotbarLoadCommand extends PluginComponentCommand<HotbarComponent>
{
  // Constructor
  public HotbarLoadCommand(HotbarComponent component)
  {
    super(component, "creativesuite.hotbar.load");
  }
    
  
  // Handle the command
  @Override
  public void handle(CommandContext context) throws CommandException, CommandUsageException
  {
    try
    {
      // Assert that the command sender is a player and in creative mode
      var player = context.assertSenderIsPlayer();
      if (player.getGameMode() != GameMode.CREATIVE)
        throw new CommandException("This command can only be executed while in creative mode");

      // Validate the number of arguments
      if (!context.hasArgumentsCount(1))
        throw new CommandUsageException();
      
      // Create a scanner for the arguments
      var scanner = context.getArgumentsScanner();
      
      // Parse the arguments
      var key = scanner.nextNamespacedKey();
      var existingKey = this.getComponent().getHotbars().containsKey(key);
      if (!existingKey)
        throw new CommandException(String.format("Hotbar %s does not exist", key.toString()));

      // Load the hotbar
      var hotbar = this.getComponent().getHotbars().get(key);
      hotbar.applyTo(player.getInventory());

      // Send a message about the loaded hotbar
      context.sendMessage(this.getComponent().formatHotbarLoadedMessage(key));
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
      return this.getComponent().handleHotbarTabCompletion(context.getArgument(0));
    else
      return List.of();
  }
}
