package dev.danae.creativesuite.plugin.commands.hotbar;

import dev.danae.creativesuite.model.Hotbar;
import dev.danae.creativesuite.model.Manager;
import dev.danae.creativesuite.plugin.commands.ManagerCommand;
import dev.danae.creativesuite.util.parser.ParserException;
import dev.danae.creativesuite.util.commands.CommandContext;
import dev.danae.creativesuite.util.commands.CommandException;
import dev.danae.creativesuite.util.commands.CommandUsageException;
import java.util.List;
import org.bukkit.GameMode;


public class HotbarSaveCommand extends ManagerCommand
{
  // Boolean that indicates if hotbars automatically get overwritten
  private final boolean overwriteHotbars;

  // Constructor
  public HotbarSaveCommand(Manager manager, boolean overwriteHotbars)
  {
    super(manager, "creativesuite.hotbar.save");

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
      var key = scanner.nextNamespacedKey();
      var hotbar = this.getManager().getHotbar(key);

      // Check if we can overwrite and existing hotbar
      if (!this.overwriteHotbars && hotbar != null)
      {
        // Send a message about the otherwise overwritten hotbar
        context.sendMessage(HotbarFormatter.formatHotbarOverwriteMessage(key));
      }
      else
      {
        // Save the hotbar
        this.getManager().setHotbar(key, new Hotbar(player.getInventory()));

        // Send a message about the saved hotbar
        context.sendMessage(HotbarFormatter.formatHotbarSavedMessage(key, hotbar != null));
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
