package dev.danae.creativesuite.plugin.commands.charmap;

import dev.danae.creativesuite.model.Manager;
import dev.danae.creativesuite.plugin.commands.ManagerCommand;
import dev.danae.commons.commands.CommandContext;
import dev.danae.commons.commands.CommandException;
import dev.danae.commons.commands.CommandUsageException;
import dev.danae.commons.messages.MessageFunction;
import java.util.List;
import java.util.Map;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.hover.content.Text;


public class CharmapListCommand extends ManagerCommand
{
  // Constants for the page size and columns
  public static int COLUMNS = 16;


  // Constructor
  public CharmapListCommand(Manager manager)
  {
    super(manager, "creativesuite.charmap.list");
  }
    
  
  // Handle the command
  @Override
  public void handle(CommandContext context) throws CommandException, CommandUsageException
  {
    // Validate the number of arguments
    if (!context.hasArgumentsCount(0))
      throw new CommandUsageException();
    
    // Send a message listing the hotbars
    context.sendMessage(this.formatMessage("charmap-list", Map.of("count", this.getManager().getCharmap().size(), "charmap", this.createListComponent())));
  }

  // Handle tab completion of the command
  @Override
  public List<String> handleTabCompletion(CommandContext context)
  {
    return List.of();
  }


  // Create a list component
  private MessageFunction createListComponent()
  {
    return (String content) -> {
      var builder = new ComponentBuilder();

      var currentColumn = 0;
      for (var codePoint : this.getManager().getCharmap().getCodePointsAsStrings())
      {
        if (currentColumn == 0)
          builder.append("\n  ");
        currentColumn = (currentColumn + 1) % COLUMNS;

        builder
          .append(codePoint)
            .event(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new Text(this.formatMessage("charmap-copy-to-clipboard", Map.of("char", codePoint)))))
            .event(new ClickEvent(ClickEvent.Action.COPY_TO_CLIPBOARD, codePoint))
          .append(" ");
      }

      return builder.create();
    };
  }
}
