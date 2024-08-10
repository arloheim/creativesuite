package dev.danae.creativesuite.plugin.components.commands;

import dev.danae.creativesuite.plugin.CreativeSuitePluginComponent;
import dev.danae.creativesuite.model.Hotbar;
import dev.danae.creativesuite.model.HotbarMap;
import dev.danae.creativesuite.plugin.CreativeSuitePlugin;
import dev.danae.creativesuite.plugin.commands.hotbar.HotbarListCommand;
import dev.danae.creativesuite.plugin.commands.hotbar.HotbarLoadCommand;
import dev.danae.creativesuite.plugin.commands.hotbar.HotbarRemoveCommand;
import dev.danae.creativesuite.plugin.commands.hotbar.HotbarSaveCommand;
import dev.danae.creativesuite.util.NamespacedKeys;
import dev.danae.creativesuite.util.TabCompleters;
import dev.danae.creativesuite.util.commands.CommandGroup;
import java.util.List;
import java.util.Map;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import org.bukkit.NamespacedKey;


public class HotbarComponent extends CreativeSuitePluginComponent
{
  // The configuration map of the defined hotbars
  private final HotbarMap hotbars;


  // Constructor
  public HotbarComponent(CreativeSuitePlugin plugin) 
  {
    super(plugin);

    this.hotbars = new HotbarMap(plugin, "hotbars.yml");
  }
  

  // Load the component
  @Override
  public void loadComponent()
  {
    super.loadComponent();

    // Load the data
    this.hotbars.load();
  }

  // Enable the component
  @Override
  public void enableComponent()
  {
    super.enableComponent();

    // Register the commands
    this.registerCommandHandler("hotbar", new CommandGroup()
      .registerSubcommand("list", new HotbarListCommand(this))
      .registerSubcommand("load", new HotbarLoadCommand(this))
      .registerSubcommand("overwrite", new HotbarSaveCommand(this, true))
      .registerSubcommand("remove", new HotbarRemoveCommand(this))
      .registerSubcommand("save", new HotbarSaveCommand(this, false)));
  }
  

  // Return the hotbars
  public Map<NamespacedKey, Hotbar> getHotbars()
  {
    return this.hotbars;
  }

  // Handle tab completion of a hotbar argument
  public List<String> handleHotbarTabCompletion(String arg)
  {
    return TabCompleters.handleSearchTabCompletion(arg, this.hotbars.keySet().stream()
      .sorted((a, b) -> a.toString().compareToIgnoreCase(b.toString()))
      .map(key -> key.toString())
      .toList());
  }

  // Format a hotbar list message
  public BaseComponent[] formatHotbarListMessage()
  {
    return new ComponentBuilder(String.format("%d hotbars are defined", this.hotbars.size()))
      .append(NamespacedKeys.createGroupedKeysMessage(this.hotbars.keySet(), 
        key -> new ClickEvent(ClickEvent.Action.SUGGEST_COMMAND, String.format("/hotbar load %s", key.toString()))))
      .create();
  }

  // Format a hotbar loaded message
  public BaseComponent[] formatHotbarLoadedMessage(NamespacedKey key)
  {
    return new ComponentBuilder()
      .append("The hotbar ", ComponentBuilder.FormatRetention.NONE)
      .append(key.toString(), ComponentBuilder.FormatRetention.NONE).color(ChatColor.GREEN)
      .append(" has been loaded", ComponentBuilder.FormatRetention.NONE)
      .create();
  }

  // Format a hotbar saved message
  public BaseComponent[] formatHotbarSavedMessage(NamespacedKey key, boolean existingKey)
  {
    return new ComponentBuilder()
      .append("The hotbar ", ComponentBuilder.FormatRetention.NONE)
      .append(key.toString(), ComponentBuilder.FormatRetention.NONE).color(ChatColor.GREEN)
      .append(" has been ", ComponentBuilder.FormatRetention.NONE)
      .append(existingKey ? "overwritten" : "saved", ComponentBuilder.FormatRetention.NONE)
      .create();
  }

  // Format a hotbar overwrite message
  public BaseComponent[] formatHotbarOverwriteMessage(NamespacedKey key)
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
  
  // Format a hotbar removed message
  public BaseComponent[] formatHotbarRemovedMessage(NamespacedKey key)
  {
    return new ComponentBuilder()
      .append("The hotbar ", ComponentBuilder.FormatRetention.NONE)
      .append(key.toString(), ComponentBuilder.FormatRetention.NONE).color(ChatColor.GREEN)
      .append(" has been removed", ComponentBuilder.FormatRetention.NONE)
      .create();
  }
}
