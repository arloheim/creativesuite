package dev.danae.gregocommands.plugin.commands.hotbar;

import dev.danae.gregocommands.model.hotbar.Hotbar;
import dev.danae.gregocommands.plugin.Formatter;
import dev.danae.gregocommands.plugin.GregoCommandsPlugin;
import dev.danae.gregocommands.plugin.commands.CommandContext;
import dev.danae.gregocommands.plugin.commands.CommandException;
import dev.danae.gregocommands.plugin.commands.CommandUsageException;
import dev.danae.gregocommands.plugin.commands.PluginCommand;
import dev.danae.gregocommands.util.parser.ParserException;
import java.util.List;
import org.bukkit.GameMode;


public class HotbarSaveCommand extends PluginCommand
{
  // Boolean that indicates if hotbars automatically get overwritten
  private final boolean overwriteHotbars;

  // Constructor
  public HotbarSaveCommand(GregoCommandsPlugin plugin, boolean overwriteHotbars)
  {
    super(plugin, "gregocommands.hotbar.save");

    this.overwriteHotbars = overwriteHotbars;
  }
    
  
  // Handle the command
  @Override
  public void handle(CommandContext context) throws CommandException, CommandUsageException
  {     
    var hotbars = this.getPlugin().getDefinedHotbars();

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
      var existingKey = hotbars.containsKey(key);

      // Check if we can overwrite and existing hotbar
      if (!this.overwriteHotbars && existingKey)
      {
        // Send a message about the otherwise overwritten hotbar
        context.sendMessage(Formatter.formatHotbarOverwriteMessage(key));
      }
      else
      {
        // Save the hotbar
        hotbars.put(key, new Hotbar(player.getInventory()));

        // Send a message about the saved hotbar
        context.sendMessage(Formatter.formatHotbarSavedMessage(key, existingKey));
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
      return this.handleHotbarTabCompletion(context.getArgument(0));
    else
      return List.of();
  }
}
