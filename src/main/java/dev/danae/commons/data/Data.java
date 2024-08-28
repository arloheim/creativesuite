package dev.danae.commons.data;

import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;


public abstract class Data
{
  // The plugin of the data
  private final Plugin plugin;

  // The file where the data is stored
  private final File file;
  
  
  // Constructor
  public Data(Plugin plugin, File file)
  {
    this.plugin = plugin;
    this.file = file;
  }

  // Constructor for a file in the data folder of the plugin
  public Data(Plugin plugin, String fileName)
  {
    this(plugin, new File(plugin.getDataFolder(), fileName));
  }


  // Serialize the configuration
  protected abstract void serialize(ConfigurationSection config);
  
  // Deserialize the configuration
  protected abstract void deserialize(ConfigurationSection config);
  
  
  // Load the map from the file
  public void load()
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
      this.plugin.getLogger().log(Level.WARNING, "Could not load the configuration", ex);
    }
  }
  
  // Save the map to the file
  public void save()
  {
    try
    {
      var config = new YamlConfiguration();
      this.serialize(config);
      
      config.save(this.file);
    }
    catch (IOException ex)
    {
      this.plugin.getLogger().log(Level.WARNING, "Could not save the configuration", ex);
    }
  }
}
