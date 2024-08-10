package dev.danae.creativesuite.plugin;

import dev.danae.creativesuite.plugin.commands.admin.AdminReloadCommand;
import dev.danae.creativesuite.plugin.commands.admin.AdminVersionCommand;
import dev.danae.creativesuite.plugin.components.alias.Alias;
import dev.danae.creativesuite.plugin.components.alias.AliasComponent;
import dev.danae.creativesuite.plugin.components.charmap.CharmapComponent;
import dev.danae.creativesuite.plugin.components.hotbar.Hotbar;
import dev.danae.creativesuite.plugin.components.hotbar.HotbarComponent;
import dev.danae.creativesuite.plugin.components.signmaterial.SignMaterialComponent;
import dev.danae.creativesuite.util.commands.CommandGroup;
import java.util.List;
import org.bukkit.configuration.serialization.ConfigurationSerialization;
import org.bukkit.plugin.java.JavaPlugin;


public class CreativeSuitePlugin extends JavaPlugin
{
  // List of the components of the plugin
  private List<CreativeSuitePluginComponent> components = List.of(
    new AliasComponent(this),
    new CharmapComponent(this),
    new HotbarComponent(this),
    new SignMaterialComponent(this)
  );
  

  // Load the plugin
  @Override
  public void onLoad()
  {
    // Register serializable classes for the configuration API
    ConfigurationSerialization.registerClass(Alias.class);
    ConfigurationSerialization.registerClass(Hotbar.class);

    // Register aliases for serializable classes for the configuration API for backwards compatibility
    ConfigurationSerialization.registerClass(Alias.class, "dev.danae.gregocommands.model.alias.Alias");
    ConfigurationSerialization.registerClass(Hotbar.class, "dev.danae.gregocommands.model.hotbar.Hotbar");

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
    // Register the commands    
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