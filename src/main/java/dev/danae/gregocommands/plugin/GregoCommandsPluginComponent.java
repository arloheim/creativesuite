package dev.danae.gregocommands.plugin;

import dev.danae.gregocommands.util.commands.Command;
import org.bukkit.event.Listener;


public abstract class GregoCommandsPluginComponent implements Listener
{
  // The plugin of the component
  private final GregoCommandsPlugin plugin;
  
  
  // Constructor
  public GregoCommandsPluginComponent(GregoCommandsPlugin plugin)
  {    
    this.plugin = plugin;
  }
  
  
  // Return the plugin of the component
  public GregoCommandsPlugin getPlugin()
  {
    return this.plugin;
  }


  // Load the component
  public void loadComponent()
  {
  }

  // Enable the component
  public void enableComponent()
  {
    // Register the listener
    this.plugin.getServer().getPluginManager().registerEvents(this, this.plugin);
  }


  // Register the specified command handler
  protected void registerCommandHandler(String name, Command command)
  {
    command.publishCommandHandler(this.getPlugin(), this.getPlugin().getCommand(name));
  }
}
