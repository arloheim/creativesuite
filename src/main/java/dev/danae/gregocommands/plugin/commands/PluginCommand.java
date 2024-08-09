package dev.danae.gregocommands.plugin.commands;

import dev.danae.gregocommands.plugin.GregoCommandsPlugin;
import dev.danae.gregocommands.util.commands.Command;


public abstract class PluginCommand extends Command
{    
  // The plugin of the command
  private final GregoCommandsPlugin plugin;
  
  
  // Constructor
  public PluginCommand(GregoCommandsPlugin plugin, String... permissions)
  {
    super(permissions);
    
    this.plugin = plugin;
  }
  
  // Constructor without permissions
  public PluginCommand(GregoCommandsPlugin plugin)
  {    
    this.plugin = plugin;
  }
  
  
  // Return the plugin of the command
  protected GregoCommandsPlugin getPlugin()
  {
    return this.plugin;
  }
}
