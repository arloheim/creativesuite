package dev.danae.creativesuite.plugin.commands.alias;

import dev.danae.creativesuite.model.Alias;
import dev.danae.creativesuite.util.NamespacedKeys;
import java.util.Map;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import org.bukkit.NamespacedKey;


public class AliasFormatter
{
  // Format an alias list message
  public static BaseComponent[] formatAliasListMessage(Map<NamespacedKey, Alias> aliases)
  {
    return new ComponentBuilder(String.format("%d aliases are defined", aliases.size()))
      .append(NamespacedKeys.createGroupedKeysMessage(aliases.keySet(), 
        key -> new ClickEvent(ClickEvent.Action.SUGGEST_COMMAND, String.format("/alias run %s", key.toString()))), ComponentBuilder.FormatRetention.NONE)
      .create();
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
      .append(" already exists. Click ", ComponentBuilder.FormatRetention.NONE)
      .append("here", ComponentBuilder.FormatRetention.NONE).underlined(true)
        .event(new ClickEvent(ClickEvent.Action.SUGGEST_COMMAND, overwriteCommand))
      .append(" to overwrite the alias", ComponentBuilder.FormatRetention.NONE)
      .create();
  }
  
  // Format an alias renamed message
  public static BaseComponent[] formatAliasRenamedMessage(NamespacedKey key, NamespacedKey newKey)
  {
    return new ComponentBuilder()
      .append("The alias ", ComponentBuilder.FormatRetention.NONE)
      .append(key.toString(), ComponentBuilder.FormatRetention.NONE).color(ChatColor.GREEN)
      .append(" has been renamed to ", ComponentBuilder.FormatRetention.NONE)
      .append(newKey.toString(), ComponentBuilder.FormatRetention.NONE).color(ChatColor.GREEN)
      .create();
  }

  // Format an alias rename overwrite message
  public static BaseComponent[] formatAliasRenameOverwriteMessage(NamespacedKey key, NamespacedKey newKey)
  {
    return new ComponentBuilder()
      .append("The alias ", ComponentBuilder.FormatRetention.NONE)
      .append(key.toString(), ComponentBuilder.FormatRetention.NONE).color(ChatColor.GREEN)
      .append(" cannot be renamed, because the alias ", ComponentBuilder.FormatRetention.NONE)
      .append(newKey.toString(), ComponentBuilder.FormatRetention.NONE).color(ChatColor.GREEN)
      .append(" already exists", ComponentBuilder.FormatRetention.NONE)
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
