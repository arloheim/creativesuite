package dev.danae.creativesuite.plugin.commands.hotbar;

import dev.danae.creativesuite.model.Hotbar;
import dev.danae.creativesuite.util.NamespacedKeys;
import java.util.Map;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import org.bukkit.NamespacedKey;


public class HotbarFormatter
{
  // Format a hotbar list message
  public static BaseComponent[] formatHotbarListMessage(Map<NamespacedKey, Hotbar> hotbars)
  {
    return new ComponentBuilder(String.format("%d hotbars are defined", hotbars.size()))
      .append(NamespacedKeys.createGroupedKeysMessage(hotbars.keySet(), 
        key -> new ClickEvent(ClickEvent.Action.SUGGEST_COMMAND, String.format("/hotbar load %s", key.toString()))), ComponentBuilder.FormatRetention.NONE)
      .create();
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
    return new ComponentBuilder()
      .append("The hotbar ", ComponentBuilder.FormatRetention.NONE)
      .append(key.toString(), ComponentBuilder.FormatRetention.NONE).color(ChatColor.GREEN)
      .append(" already exists. Click ", ComponentBuilder.FormatRetention.NONE)
      .append("here", ComponentBuilder.FormatRetention.NONE).underlined(true)
        .event(new ClickEvent(ClickEvent.Action.SUGGEST_COMMAND, String.format("/hotbar overwrite %s", key.toString())))
      .append(" to overwrite the hotbar", ComponentBuilder.FormatRetention.NONE)
      .create();
  }
  
  // Format a hotbar renamed message
  public static BaseComponent[] formatHotbarRenamedMessage(NamespacedKey key, NamespacedKey newKey)
  {
    return new ComponentBuilder()
      .append("The hotbar ", ComponentBuilder.FormatRetention.NONE)
      .append(key.toString(), ComponentBuilder.FormatRetention.NONE).color(ChatColor.GREEN)
      .append(" has been renamed to ", ComponentBuilder.FormatRetention.NONE)
      .append(newKey.toString(), ComponentBuilder.FormatRetention.NONE).color(ChatColor.GREEN)
      .create();
  }

  // Format a hotbar rename overwrite message
  public static BaseComponent[] formatHotbarRenameOverwriteMessage(NamespacedKey key, NamespacedKey newKey)
  {
    return new ComponentBuilder()
      .append("The hotbar ", ComponentBuilder.FormatRetention.NONE)
      .append(key.toString(), ComponentBuilder.FormatRetention.NONE).color(ChatColor.GREEN)
      .append(" cannot be renamed, because the hotbar ", ComponentBuilder.FormatRetention.NONE)
      .append(newKey.toString(), ComponentBuilder.FormatRetention.NONE).color(ChatColor.GREEN)
      .append(" already exists", ComponentBuilder.FormatRetention.NONE)
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
}
