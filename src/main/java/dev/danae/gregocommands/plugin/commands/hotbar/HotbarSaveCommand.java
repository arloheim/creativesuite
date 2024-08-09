package dev.danae.gregocommands.plugin.commands.hotbar;

import dev.danae.gregocommands.plugin.commands.PluginComponentCommand;
import dev.danae.gregocommands.plugin.components.hotbar.Hotbar;
import dev.danae.gregocommands.plugin.components.hotbar.HotbarComponent;
import dev.danae.gregocommands.util.parser.ParserException;
import dev.danae.gregocommands.util.commands.CommandContext;
import dev.danae.gregocommands.util.commands.CommandException;
import dev.danae.gregocommands.util.commands.CommandUsageException;
import java.util.List;
import org.bukkit.GameMode;


public class HotbarSaveCommand extends PluginComponentCommand<HotbarComponent>
{
  // Boolean that indicates if hotbars automatically get overwritten
  private final boolean overwriteHotbars;

  // Constructor
  public HotbarSaveCommand(HotbarComponent component, boolean overwriteHotbars)
  {
    super(component, "gregocommands.hotbar.save");

    this.overwriteHotbars = overwriteHotbars;
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
      var key = scanner.nextKey();
      var existingKey = this.getComponent().getHotbars().containsKey(key);

      // Check if we can overwrite and existing hotbar
      if (!this.overwriteHotbars && existingKey)
      {
        // Send a message about the otherwise overwritten hotbar
        context.sendMessage(this.getComponent().formatHotbarOverwriteMessage(key));
      }
      else
      {
        // Save the hotbar
        this.getComponent().getHotbars().put(key, new Hotbar(player.getInventory()));

        // Send a message about the saved hotbar
        context.sendMessage(this.getComponent().formatHotbarSavedMessage(key, existingKey));
      }
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
