package dev.danae.gregocommands.plugin;


public abstract class GregoCommandsPluginComponent
{
  // The plugin of the component
  private final GregoCommandsPlugin plugin;
  
  
  // Constructor
  public GregoCommandsPluginComponent(GregoCommandsPlugin plugin)
  {    
    this.plugin = plugin;
  }
  
  
  // Return the plugin of the component
  protected GregoCommandsPlugin getPlugin()
  {
    return this.plugin;
  }
}
