package dev.danae.gregocommands.plugin;

import dev.danae.gregocommands.model.alias.Alias;
import dev.danae.gregocommands.model.charmap.Charmap;
import dev.danae.gregocommands.model.hotbar.Hotbar;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.hover.content.Text;
import org.bukkit.NamespacedKey;


public class Formatter
{  
  // Return a sorted stream of the specified namespaced keys 
  private static Stream<NamespacedKey> sortKeys(Collection<NamespacedKey> keys)
  {
    return keys.stream()
      .sorted((a, b) -> a.toString().compareToIgnoreCase(b.toString()));
  }

  // Return a sorted and grouped stream of the specified namespaced keys 
  private static Stream<Map.Entry<String, List<NamespacedKey>>> groupKeys(Collection<NamespacedKey> keys)
  {
    return sortKeys(keys)
      .collect(Collectors.groupingBy(key -> key.getNamespace())).entrySet().stream()
      .sorted((a, b) -> a.getKey().compareToIgnoreCase(b.getKey()));
  }


  // Truncate a string to the specified length and append the specified ellipsis string
  public static String truncate(String string, int length, String ellipsisString)
  {
    if (string.length() > length)
      return string.substring(0, length - ellipsisString.length());
    else
      return string;
  }

  // Truncate a string to the specified length
  public static String truncate(String string, int length)
  {
    return truncate(string, length, "...");
  }


  // Format a hotbar list message
  public static BaseComponent[] formatHotbarListMessage(Map<NamespacedKey, Hotbar> hotbars)
  {
    var builder = new ComponentBuilder(String.format("%d hotbars are defined", hotbars.size()));
    for (var e : groupKeys(hotbars.keySet()).toList())
    {
      builder
        .append("\n", ComponentBuilder.FormatRetention.NONE)
        .append(e.getKey().toString(), ComponentBuilder.FormatRetention.NONE).color(ChatColor.BLUE)
        .append(": ", ComponentBuilder.FormatRetention.NONE);

      var first = true;
      for (var key : e.getValue())
      {
        var loadCommand =  String.format("/hotbar load %s", key.toString());

        if (first)
          first = false;
        else
          builder.append(", ", ComponentBuilder.FormatRetention.NONE);

        builder
          .append(key.getKey(), ComponentBuilder.FormatRetention.NONE).color(ChatColor.GREEN).underlined(true)
            .event(new ClickEvent(ClickEvent.Action.SUGGEST_COMMAND, loadCommand));
      }
    }

    return builder.create();
  }

  // Format a hotbar loaded message
  public static BaseComponent[] formatHotbarLoadedMessage(NamespacedKey key)
  {
    return new ComponentBuilder()
      .append("The hotbar ", ComponentBuilder.FormatRetention.NONE)
      .append(key.toString(), ComponentBuilder.FormatRetention.NONE).color(ChatColor.GREEN)
      .append(" has been loaded", ComponentBuilder.FormatRetention.NONE)
      .create();
  }

  // Format a hotbar saved message
  public static BaseComponent[] formatHotbarSavedMessage(NamespacedKey key, boolean existingKey)
  {
    return new ComponentBuilder()
      .append("The hotbar ", ComponentBuilder.FormatRetention.NONE)
      .append(key.toString(), ComponentBuilder.FormatRetention.NONE).color(ChatColor.GREEN)
      .append(" has been ", ComponentBuilder.FormatRetention.NONE)
      .append(existingKey ? "overwritten" : "saved", ComponentBuilder.FormatRetention.NONE)
      .create();
  }

  // Format a hotbar overwrite message
  public static BaseComponent[] formatHotbarOverwriteMessage(NamespacedKey key)
  {
    var overwriteCommand = String.format("/hotbar overwrite %s", key.toString());

    return new ComponentBuilder()
      .append("The hotbar ", ComponentBuilder.FormatRetention.NONE)
      .append(key.toString(), ComponentBuilder.FormatRetention.NONE).color(ChatColor.GREEN)
      .append(" already exists. Use ", ComponentBuilder.FormatRetention.NONE)
      .append(overwriteCommand, ComponentBuilder.FormatRetention.NONE).underlined(true)
        .event(new ClickEvent(ClickEvent.Action.SUGGEST_COMMAND, overwriteCommand))
      .append(" to overwrite the hotbar", ComponentBuilder.FormatRetention.NONE)
      .create();
  }
  
  // Format a hotbar removed message
  public static BaseComponent[] formatHotbarRemovedMessage(NamespacedKey key)
  {
    return new ComponentBuilder()
      .append("The hotbar ", ComponentBuilder.FormatRetention.NONE)
      .append(key.toString(), ComponentBuilder.FormatRetention.NONE).color(ChatColor.GREEN)
      .append(" has been removed", ComponentBuilder.FormatRetention.NONE)
      .create();
  }

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

  // Format an alias list message
  public static BaseComponent[] formatAliasListMessage(Map<NamespacedKey, Alias> aliases)
  {
    var builder = new ComponentBuilder(String.format("%d aliases are defined", aliases.size()));    
    for (var e : groupKeys(aliases.keySet()).toList())
    {
      builder
        .append("\n", ComponentBuilder.FormatRetention.NONE)
        .append(e.getKey().toString(), ComponentBuilder.FormatRetention.NONE).color(ChatColor.BLUE)
        .append(": ", ComponentBuilder.FormatRetention.NONE);

      var first = true;
      for (var key : e.getValue())
      {
        var runCommand =  String.format("/alias run %s", e.getKey().toString());

        if (first)
          first = false;
        else
          builder.append(", ", ComponentBuilder.FormatRetention.NONE);

        builder
          .append(key.getKey(), ComponentBuilder.FormatRetention.NONE).color(ChatColor.GREEN).underlined(true)
            .event(new ClickEvent(ClickEvent.Action.SUGGEST_COMMAND, runCommand));
      }
    }

    return builder.create();
  }

  // Format an alias saved message
  public static BaseComponent[] formatAliasSavedMessage(NamespacedKey key, String command, boolean existingKey)
  {
    return new ComponentBuilder()
      .append("The alias ", ComponentBuilder.FormatRetention.NONE)
      .append(key.toString(), ComponentBuilder.FormatRetention.NONE).color(ChatColor.GREEN)
      .append(" has been ", ComponentBuilder.FormatRetention.NONE)
      .append(existingKey ? "overwritten" : "saved", ComponentBuilder.FormatRetention.NONE)
      .append(" with command ", ComponentBuilder.FormatRetention.NONE)
      .append(command, ComponentBuilder.FormatRetention.NONE).color(ChatColor.GREEN)
      .create();
  }

  // Format an alias overwrite message
  public static BaseComponent[] formatAliasOverwriteMessage(NamespacedKey key, String command)
  {
    var overwriteCommand = String.format("/alias overwrite %s %s", key.toString(), command);

    return new ComponentBuilder()
      .append("The alias ", ComponentBuilder.FormatRetention.NONE)
      .append(key.toString(), ComponentBuilder.FormatRetention.NONE).color(ChatColor.GREEN)
      .append(" already exists. Use ", ComponentBuilder.FormatRetention.NONE)
      .append(overwriteCommand, ComponentBuilder.FormatRetention.NONE).underlined(true)
        .event(new ClickEvent(ClickEvent.Action.SUGGEST_COMMAND, overwriteCommand))
      .append(" to overwrite the alias", ComponentBuilder.FormatRetention.NONE)
      .create();
  }
  
  // Format an alias removed message
  public static BaseComponent[] formatAliasRemovedMessage(NamespacedKey key)
  {
    return new ComponentBuilder()
      .append("The alias ", ComponentBuilder.FormatRetention.NONE)
      .append(key.toString(), ComponentBuilder.FormatRetention.NONE).color(ChatColor.GREEN)
      .append(" has been removed", ComponentBuilder.FormatRetention.NONE)
      .create();
  }
}
