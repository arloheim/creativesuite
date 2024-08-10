package dev.danae.creativesuite.plugin.commands.charmap;

import dev.danae.creativesuite.model.Charmap;
import java.util.stream.Collectors;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.hover.content.Text;


public class CharmapFormatter
{
  // Format a charmap list message
  public static BaseComponent[] formatCharmapListMessage(Charmap charmap, int columns)
  {
    var builder = new ComponentBuilder(String.format("%d characters are defined", charmap.getCodePoints().size()));
    var currentColumn = 0;
    for (var codePoint : charmap.getCodePointsAsStrings())
    {
      if (currentColumn == 0)
        builder.append("\n  ");
      currentColumn = (currentColumn + 1) % columns;

      builder
        .append(codePoint, ComponentBuilder.FormatRetention.NONE).color(ChatColor.GREEN)
          .event(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new Text(String.format("Click to copy \"%s\" to clipboard", codePoint))))
          .event(new ClickEvent(ClickEvent.Action.COPY_TO_CLIPBOARD, codePoint))
        .append(" ", ComponentBuilder.FormatRetention.NONE);
    }

    return builder.create();
  }

  // Format a charmap added message
  public static BaseComponent[] formatCharmapAddedMessage(String codePoints)
  {
    var characters = codePoints.codePoints().boxed()
      .sorted()
      .map(Charmap::codePointToString)
      .collect(Collectors.joining(" "));

    return new ComponentBuilder()
      .append("The characters ", ComponentBuilder.FormatRetention.NONE)
      .append(characters, ComponentBuilder.FormatRetention.NONE).color(ChatColor.GREEN)
      .append(" have been added to the charmap", ComponentBuilder.FormatRetention.NONE)
      .create();
  }

  // Format a charmap removed message
  public static BaseComponent[] formatCharmapRemovedMessage(String codePoints)
  {
    var characters = codePoints.codePoints().boxed()
      .sorted()
      .map(Charmap::codePointToString)
      .collect(Collectors.joining(" "));

    return new ComponentBuilder()
      .append("The characters ", ComponentBuilder.FormatRetention.NONE)
      .append(characters, ComponentBuilder.FormatRetention.NONE).color(ChatColor.GREEN)
      .append(" have been removed from the charmap", ComponentBuilder.FormatRetention.NONE)
      .create();
  }
}
