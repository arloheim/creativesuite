package dev.danae.gregocommands.plugin;

import dev.danae.gregocommands.model.Alias;
import dev.danae.gregocommands.model.Hotbar;
import dev.danae.gregocommands.plugin.commands.admin.AdminReloadCommand;
import dev.danae.gregocommands.plugin.commands.admin.AdminVersionCommand;
import dev.danae.gregocommands.plugin.components.AliasComponent;
import dev.danae.gregocommands.plugin.components.CharmapComponent;
import dev.danae.gregocommands.plugin.components.HotbarComponent;
import dev.danae.gregocommands.util.commands.Command;
import dev.danae.gregocommands.util.commands.CommandGroup;
import java.util.List;
import java.util.logging.Level;
import org.bukkit.configuration.serialization.ConfigurationSerialization;
import org.bukkit.plugin.java.JavaPlugin;


public class GregoCommandsPlugin extends JavaPlugin
{
  // List of the components of the plugin
  private List<GregoCommandsPluginComponent> components = List.of(
    new AliasComponent(this),
    new CharmapComponent(this),
    new HotbarComponent(this)
  );
  

  // Load the plugin
  @Override
  public void onLoad()
  {
    // Register serializable classes for the configuration API
    ConfigurationSerialization.registerClass(Alias.class);
    ConfigurationSerialization.registerClass(Hotbar.class);

    // Load the plugin
    this.loadPlugin();

    // Load the components
    for (var component : components)
      component.loadComponent();
  }
  
  // Enable the plugin
  @Override
  public void onEnable()
  {    
    // Register the command handlers    
    new CommandGroup()
      .registerSubcommand("reload", new AdminReloadCommand(this))
      .registerSubcommand("version", new AdminVersionCommand(this))
      .publishCommandHandler(this, this.getCommand("gregocommands"));

    // Enable the components
    for (var component : components)
      component.enableComponent();
  }


  // Load the plugin
  public void loadPlugin()
  {
    // Save the default configuration and reload
    this.saveDefaultConfig();
    this.reloadConfig();
  }
}