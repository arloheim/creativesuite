package dev.danae.creativesuite.plugin;

import dev.danae.creativesuite.model.Alias;
import dev.danae.creativesuite.model.Hotbar;
import dev.danae.creativesuite.model.Manager;
import dev.danae.creativesuite.plugin.commands.admin.AdminReloadCommand;
import dev.danae.creativesuite.plugin.commands.admin.AdminVersionCommand;
import dev.danae.creativesuite.plugin.commands.alias.AliasListCommand;
import dev.danae.creativesuite.plugin.commands.alias.AliasRemoveCommand;
import dev.danae.creativesuite.plugin.commands.alias.AliasRunCommand;
import dev.danae.creativesuite.plugin.commands.alias.AliasSaveCommand;
import dev.danae.creativesuite.plugin.commands.charmap.CharmapAddCommand;
import dev.danae.creativesuite.plugin.commands.charmap.CharmapListCommand;
import dev.danae.creativesuite.plugin.commands.charmap.CharmapRemoveCommand;
import dev.danae.creativesuite.plugin.commands.hotbar.HotbarListCommand;
import dev.danae.creativesuite.plugin.commands.hotbar.HotbarLoadCommand;
import dev.danae.creativesuite.plugin.commands.hotbar.HotbarRemoveCommand;
import dev.danae.creativesuite.plugin.commands.hotbar.HotbarSaveCommand;
import dev.danae.creativesuite.plugin.listeners.SignMaterialListener;
import dev.danae.creativesuite.plugin.migrations.v1_1_1.ConfigurationSerializableMigration;
import dev.danae.creativesuite.util.commands.CommandGroup;
import java.util.logging.Level;
import org.bukkit.Bukkit;
import org.bukkit.configuration.serialization.ConfigurationSerialization;
import org.bukkit.plugin.java.JavaPlugin;


public class CreativeSuitePlugin extends JavaPlugin
{
  // The manager of the plugin
  private CreativeSuiteManager manager;

  // The sign material listener of the plugin
  private SignMaterialListener signMaterialListener;
  
  
  // Return the manager of the plugin
  public Manager getManager()
  {
    return this.manager;
  }
  

  // Load the plugin
  @Override
  public void onLoad()
  {
    // Register serializable classes for the configuration API
    ConfigurationSerialization.registerClass(Alias.class);
    ConfigurationSerialization.registerClass(Hotbar.class);

    // Load the plugin
    this.loadPlugin();
  }
  
  // Enable the plugin
  @Override
  public void onEnable()
  {    
    // Create the components
    this.manager = new CreativeSuiteManager(this);
    this.signMaterialListener = new SignMaterialListener(this);

    // Set the listeners
    Bukkit.getPluginManager().registerEvents(this.signMaterialListener, this);

    // Set the command handlers  
    new CommandGroup()
      .registerSubcommand("reload", new AdminReloadCommand(this.manager, this))
      .registerSubcommand("version", new AdminVersionCommand(this.manager, this))
      .publishCommandHandler(this, this.getCommand("creativesuite"));

    new CommandGroup()
      .registerSubcommand("list", new AliasListCommand(this.manager))
      .registerSubcommand("overwrite", new AliasSaveCommand(this.manager, true))
      .registerSubcommand("remove", new AliasRemoveCommand(this.manager))
      .registerSubcommand("save", new AliasSaveCommand(this.manager, false))
      .registerSubcommand("run", new AliasRunCommand(this.manager))
      .publishCommandHandler(this, this.getCommand("alias"));
      
    new AliasRunCommand(this.manager)
      .publishCommandHandler(this, this.getCommand("run"));
    
    new CommandGroup()
      .registerSubcommand("list", new HotbarListCommand(this.manager))
      .registerSubcommand("load", new HotbarLoadCommand(this.manager))
      .registerSubcommand("overwrite", new HotbarSaveCommand(this.manager, true))
      .registerSubcommand("remove", new HotbarRemoveCommand(this.manager))
      .registerSubcommand("save", new HotbarSaveCommand(this.manager, false))
      .publishCommandHandler(this, this.getCommand("hotbar"));

    new CommandGroup()
      .registerSubcommand("add", new CharmapAddCommand(this.manager))
      .registerSubcommand("remove", new CharmapRemoveCommand(this.manager))
      .registerEmptySubcommand(new CharmapListCommand(this.manager))
      .publishCommandHandler(this, this.getCommand("charmap"));
  }


  // Load the plugin
  public void loadPlugin()
  {
    // Execute migrations
    this.executeMigrations();

    // Load the configuration
    this.loadConfiguration();
      
  }

  // Execute migrations
  public void executeMigrations()
  {
    this.getLogger().log(Level.INFO, "Checking for migrations to execute...");

    // Execute v1.1.1 migrations
    new ConfigurationSerializableMigration(this).migrate();
  }

  // Load the configuration
  public void loadConfiguration()
  {
    // Save the default configuration
    this.saveDefaultConfig();
    this.reloadConfig();
  }
}