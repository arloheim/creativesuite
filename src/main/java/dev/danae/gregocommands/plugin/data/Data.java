package dev.danae.gregocommands.plugin.data;

import dev.danae.gregocommands.plugin.GregoCommandsPlugin;
import dev.danae.gregocommands.plugin.GregoCommandsPluginComponent;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.YamlConfiguration;


public abstract class Data extends GregoCommandsPluginComponent
{
  // The file where the data is stored
  private final File file;
  
  
  // Constructor
  public Data(GregoCommandsPlugin plugin, File file)
  {
    super(plugin);
    
    this.file = file;
  }


  // Serialize the configuration
  protected abstract void serialize(ConfigurationSection config);
  
  // Deserialize the configuration
  protected abstract void deserialize(ConfigurationSection config);
  
  
  // Load the map from the file
  protected void load()
  {
    try
    {
      if (!this.file.exists())
        this.file.createNewFile();
      
      var config = new YamlConfiguration();
      config.load(this.file);

      this.deserialize(config);
    }
    catch (IOException | InvalidConfigurationException ex)
    {
      this.getPlugin().getLogger().log(Level.WARNING, "Could not load the configuration", ex);
    }
  }
  
  // Save the map to the file
  protected void save()
  {
    try
    {
      var config = new YamlConfiguration();
      this.serialize(config);
      
      config.save(this.file);
    }
    catch (IOException ex)
    {
      this.getPlugin().getLogger().log(Level.WARNING, "Could not save the configuration", ex);
    }
  }
}
