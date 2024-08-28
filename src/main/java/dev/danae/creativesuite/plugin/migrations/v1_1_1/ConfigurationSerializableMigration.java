package dev.danae.creativesuite.plugin.migrations.v1_1_1;

import com.google.common.io.Files;
import dev.danae.creativesuite.model.alias.Alias;
import dev.danae.creativesuite.model.hotbar.Hotbar;
import dev.danae.creativesuite.plugin.CreativeSuitePlugin;
import dev.danae.creativesuite.plugin.migrations.Migration;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.configuration.serialization.ConfigurationSerialization;


public class ConfigurationSerializableMigration extends Migration
{
  // The file where the aliases are stored
  private final File aliasesFile;

  // The backup file to move the aliases file to
  private final File aliasesBackupFile;

  // The file where the hotbars are stored
  private final File hotbarsFile;

  // The backup file to move the hotbars file to
  private final File hotbarsBackupFile;


  // Constructor
  public ConfigurationSerializableMigration(CreativeSuitePlugin plugin) 
  {
    super(plugin);

    this.aliasesFile = new File(plugin.getDataFolder(), "aliases.yml");
    this.aliasesBackupFile = new File(plugin.getDataFolder(), "aliases.yml.bak");
    this.hotbarsFile = new File(plugin.getDataFolder(), "hotbars.yml");
    this.hotbarsBackupFile = new File(plugin.getDataFolder(), "hotbars.yml.bak");
  }


  // Return the version the migration applies to
  @Override
  protected String getVersion()
  {
    return "1.1.1";
  }

  // Return if the prerequisites for the migrations are met
  @Override
  protected boolean canMigrate()
  {
    // Return if one of the data files exists
    return (this.aliasesFile.exists() && !this.aliasesBackupFile.exists()) || (this.hotbarsFile.exists() && !this.hotbarsBackupFile.exists());
  }

  // Actually execute the migration
  @Override
  protected void doMigrate()
  {
    try
    {
      this.getPlugin().getLogger().log(Level.INFO, "[v{0}: {1}] Migrating configuration serializables...", new Object[] { this.getVersion(), ConfigurationSerializableMigration.class.getSimpleName()});

      // Register aliases for the configuration serializables
      ConfigurationSerialization.registerClass(Alias.class, "dev.danae.gregocommands.model.alias.Alias");
      ConfigurationSerialization.registerClass(Hotbar.class, "dev.danae.gregocommands.model.hotbar.Hotbar");

      // Handle the aliases files
      if (this.aliasesFile.exists())
      {
        // Copy the file to the backup file
        Files.copy(this.aliasesFile, this.aliasesBackupFile);

        // Load and save the alias configuration
        var aliasesConfig = new YamlConfiguration();
        aliasesConfig.load(this.aliasesFile);
        aliasesConfig.save(this.aliasesFile);
      }

      // Handle the hotbars files
      if (this.hotbarsFile.exists())
      {
        // Copy the file to the backup file
        Files.copy(this.hotbarsFile, this.hotbarsBackupFile);

        // Load and save the alias configuration
        var aliasesConfig = new YamlConfiguration();
        aliasesConfig.load(this.hotbarsFile);
        aliasesConfig.save(this.hotbarsFile);
      }
    }
    catch (IOException | InvalidConfigurationException ex)
    {
      this.getPlugin().getLogger().log(Level.SEVERE, "[v{0}: {1}] {2}", new Object[] { this.getVersion(), ConfigurationSerializableMigration.class.getSimpleName(), ex.getMessage()});
    }
  }
}
