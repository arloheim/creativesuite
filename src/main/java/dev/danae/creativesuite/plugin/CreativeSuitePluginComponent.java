package dev.danae.gregocommands.plugin;

import dev.danae.gregocommands.util.commands.Command;
import org.bukkit.event.Listener;


public abstract class CreativeSuitePluginComponent implements Listener
{
  // The plugin of the component
  private final CreativeSuitePlugin plugin;
  
  
  // Constructor
  public CreativeSuitePluginComponent(CreativeSuitePlugin plugin)
  {    
    this.plugin = plugin;
  }
  
  
  // Return the plugin of the component
  public CreativeSuitePlugin getPlugin()
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
