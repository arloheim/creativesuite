package dev.danae.gregocommands.plugin;

import dev.danae.gregocommands.model.alias.Alias;
import dev.danae.gregocommands.model.charmap.Charmap;
import dev.danae.gregocommands.model.hotbar.Hotbar;
import dev.danae.gregocommands.plugin.commands.CommandGroup;
import dev.danae.gregocommands.plugin.commands.admin.AdminReloadCommand;
import dev.danae.gregocommands.plugin.commands.admin.AdminVersionCommand;
import dev.danae.gregocommands.plugin.commands.alias.AliasListCommand;
import dev.danae.gregocommands.plugin.commands.alias.AliasRemoveCommand;
import dev.danae.gregocommands.plugin.commands.alias.AliasRunCommand;
import dev.danae.gregocommands.plugin.commands.alias.AliasSaveCommand;
import dev.danae.gregocommands.plugin.commands.charmap.CharmapAddCommand;
import dev.danae.gregocommands.plugin.commands.charmap.CharmapListCommand;
import dev.danae.gregocommands.plugin.commands.charmap.CharmapRemoveCommand;
import dev.danae.gregocommands.plugin.commands.hotbar.HotbarListCommand;
import dev.danae.gregocommands.plugin.commands.hotbar.HotbarLoadCommand;
import dev.danae.gregocommands.plugin.commands.hotbar.HotbarRemoveCommand;
import dev.danae.gregocommands.plugin.commands.hotbar.HotbarSaveCommand;
import dev.danae.gregocommands.plugin.data.DataMap;
import dev.danae.gregocommands.plugin.data.DataMapKeyType;
import java.io.File;
import java.util.Map;
import java.util.logging.Level;
import org.bukkit.NamespacedKey;
import org.bukkit.configuration.serialization.ConfigurationSerialization;
import org.bukkit.plugin.java.JavaPlugin;


public class GregoCommandsPlugin extends JavaPlugin
{
  // The charmap
  private Charmap charmap;

  // The configuration map of the defined hotbars
  private DataMap<NamespacedKey, Hotbar> hotbars;

  // The configuration map of the defined aliases
  private DataMap<NamespacedKey, Alias> aliases;


  // Return the charmap
  public Charmap getCharmap()
  {
    return charmap;
  }

  // Return the defined hotbars
  public Map<NamespacedKey, Hotbar> getDefinedHotbars()
  {
    return this.hotbars;
  }
  
  // Return the defined aliases
  public Map<NamespacedKey, Alias> getDefinedAliases()
  {
    return this.aliases;
  }
  

  // Load the plugin
  @Override
  public void onLoad()
  {
    // Register serializable classes for the configuration API
    ConfigurationSerialization.registerClass(Hotbar.class);

    // Load the plugin
    this.loadPlugin();
  }
  
  // Enable the plugin
  @Override
  public void onEnable()
  {    
    // Create the components
    this.charmap = new Charmap(this, new File(this.getDataFolder(), "charmap.yml"));
    this.hotbars = new DataMap<>(this, new File(this.getDataFolder(), "hotbars.yml"), Hotbar.class, DataMapKeyType.NAMESPACED_KEY);
    this.aliases = new DataMap<>(this, new File(this.getDataFolder(), "aliases.yml"), Alias.class, DataMapKeyType.NAMESPACED_KEY);

    // Set the command handlers    
    new CommandGroup()
      .registerSubcommand("reload", new AdminReloadCommand(this))
      .registerSubcommand("version", new AdminVersionCommand(this))
      .publishCommandHandler(this, this.getCommand("gregocommands"));

    new CommandGroup()
      .registerSubcommand("list", new HotbarListCommand(this))
      .registerSubcommand("load", new HotbarLoadCommand(this))
      .registerSubcommand("overwrite", new HotbarSaveCommand(this, true))
      .registerSubcommand("remove", new HotbarRemoveCommand(this))
      .registerSubcommand("save", new HotbarSaveCommand(this, false))
      .publishCommandHandler(this, this.getCommand("hotbar"));
      
    new CommandGroup()
      .registerSubcommand("add", new CharmapAddCommand(this))
      .registerSubcommand("remove", new CharmapRemoveCommand(this))
      .registerEmptySubcommand(new CharmapListCommand(this))
      .publishCommandHandler(this, this.getCommand("charmap"));

    new CommandGroup()
      .registerSubcommand("list", new AliasListCommand(this))
      .registerSubcommand("overwrite", new AliasSaveCommand(this, true))
      .registerSubcommand("remove", new AliasRemoveCommand(this))
      .registerSubcommand("save", new AliasSaveCommand(this, false))
      .registerSubcommand("run", new AliasRunCommand(this))
      .publishCommandHandler(this, this.getCommand("alias"));

    new AliasRunCommand(this)
      .publishCommandHandler(this, this.getCommand("run"));
  }


  // Load the plugin
  public void loadPlugin()
  {
    // Load the configuration
    this.loadConfiguration();
  }
  
  // Load the configuration
  public void loadConfiguration()
  {
    this.getLogger().log(Level.INFO, "Loading configuration...");

    // Save the default configuration
    this.saveDefaultConfig();
    this.reloadConfig();
  }
}