package dev.danae.creativesuite.plugin.commands;

import dev.danae.creativesuite.plugin.CreativeSuitePlugin;
import dev.danae.creativesuite.util.commands.Command;


public abstract class PluginCommand extends Command
{    
  // The plugin of the command
  private final CreativeSuitePlugin plugin;
  
  
  // Constructor
  public PluginCommand(CreativeSuitePlugin plugin, String... permissions)
  {
    super(permissions);
    
    this.plugin = plugin;
  }
  
  // Constructor without permissions
  public PluginCommand(CreativeSuitePlugin plugin)
  {    
    this.plugin = plugin;
  }
  
  
  // Return the plugin of the command
  protected CreativeSuitePlugin getPlugin()
  {
    return this.plugin;
  }
}
