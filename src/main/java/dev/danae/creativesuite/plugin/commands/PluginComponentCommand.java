package dev.danae.creativesuite.plugin.commands;

import dev.danae.creativesuite.plugin.CreativeSuitePluginComponent;


public abstract class PluginComponentCommand<C extends CreativeSuitePluginComponent> extends PluginCommand
{    
  // The component of the command
  private final C component;
  
  
  // Constructor
  public PluginComponentCommand(C component, String... permissions)
  {
    super(component.getPlugin(), permissions);
    
    this.component = component;
  }
  
  // Constructor without permissions
  public PluginComponentCommand(C component)
  {    
    super(component.getPlugin());

    this.component = component;
  }
  
  
  // Return the component of the command
  protected C getComponent()
  {
    return this.component;
  }
}
