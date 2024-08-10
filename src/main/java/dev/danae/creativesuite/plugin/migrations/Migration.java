package dev.danae.creativesuite.plugin.migrations;

import dev.danae.creativesuite.plugin.CreativeSuitePlugin;
import dev.danae.creativesuite.plugin.CreativeSuitePluginComponent;


public abstract class Migration extends CreativeSuitePluginComponent
{
  // Constructor
  public Migration(CreativeSuitePlugin plugin)
  {
    super(plugin);
  }

  
  // Execute the migration
  public void migrate()
  {
    if (this.canMigrate())
      this.doMigrate();
  }


  // Return the version the migration applies to
  protected abstract String getVersion();
  
  // Return if the prerequisites for the migrations are met
  protected abstract boolean canMigrate();
  
  // Actually execute the migration
  protected abstract void doMigrate();
}
