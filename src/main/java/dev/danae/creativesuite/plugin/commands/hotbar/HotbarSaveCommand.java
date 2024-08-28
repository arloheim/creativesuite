package dev.danae.creativesuite.plugin.commands.hotbar;

import dev.danae.commons.commands.CommandContext;
import dev.danae.commons.commands.CommandException;
import dev.danae.commons.commands.CommandUsageException;
import dev.danae.commons.messages.MessageFunction;
import dev.danae.commons.parser.ParserException;
import dev.danae.creativesuite.model.Hotbar;
import dev.danae.creativesuite.model.Manager;
import dev.danae.creativesuite.plugin.commands.ManagerCommand;
import java.util.List;
import java.util.Map;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.hover.content.Text;
import org.bukkit.GameMode;
import org.bukkit.NamespacedKey;


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

      // Check if we can overwrite and existing hotbar
      if (!this.overwriteHotbars && hotbar != null)
      {
        // Send a message about the otherwise overwritten hotbar
        context.sendMessage(this.formatMessage("hotbar-cannot-save", Map.of("key", key, "overwrite", this.getOverwriteComponent(context, key))));
      }
      else
      {
        // Save the hotbar
        this.getManager().setHotbar(key, new Hotbar(player.getInventory()));

        // Send a message about the saved hotbar
        context.sendMessage(this.formatMessage(hotbar != null ? "hotbar-overwritten" : "hotbar-saved", Map.of("key", key)));
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


  // Create an overwrite command chat component
  private MessageFunction getOverwriteComponent(CommandContext context, NamespacedKey key)
  {
    var overwriteCommand = String.format("/%s overwrite %s", context.getCommand().getName(), key.toString());

    return (String content) -> {
      return new ComponentBuilder()
        .append(content, ComponentBuilder.FormatRetention.NONE).underlined(true)
          .event(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new Text(overwriteCommand)))
          .event(new ClickEvent(ClickEvent.Action.SUGGEST_COMMAND, overwriteCommand))
        .create();
    };
  }
}
