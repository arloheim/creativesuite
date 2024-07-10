package dev.danae.gregocommands.plugin.commands;

import dev.danae.gregocommands.plugin.GregoCommandsPlugin;
import java.util.List;


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


  // Handle tab completion of a hotbar argument
  protected List<String> handleHotbarTabCompletion(String arg)
  {
    return this.plugin.getDefinedHotbars().keySet().stream()
      .sorted((a, b) -> a.toString().compareToIgnoreCase(b.toString()))
      .map(key -> key.toString())
      .toList();
  }

  // Handle tab completion of an alias argument
  protected List<String> handleAliasTabCompletion(String arg)
  {
    return this.plugin.getDefinedAliases().keySet().stream()
      .sorted((a, b) -> a.toString().compareToIgnoreCase(b.toString()))
      .map(key -> key.toString())
      .toList();
  }
}
