package dev.danae.creativesuite.plugin;

import dev.danae.creativesuite.model.ManagerComponent;


public abstract class CreativeSuitePluginComponent extends ManagerComponent
{
  // The plugin of the component
  private final CreativeSuitePlugin plugin;
  
  
  // Constructor
  public CreativeSuitePluginComponent(CreativeSuitePlugin plugin)
  {
    super(plugin.getManager());

    this.plugin = plugin;
  }
  
  
  // Return the plugin of the component
  public CreativeSuitePlugin getPlugin()
  {
    return this.plugin;
  }
}
