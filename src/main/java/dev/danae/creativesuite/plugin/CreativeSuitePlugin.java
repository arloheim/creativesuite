package dev.danae.creativesuite.plugin;

import dev.danae.creativesuite.model.Manager;
import dev.danae.creativesuite.model.alias.Alias;
import dev.danae.creativesuite.model.hotbar.Hotbar;
import dev.danae.creativesuite.plugin.commands.admin.AdminReloadCommand;
import dev.danae.creativesuite.plugin.commands.admin.AdminVersionCommand;
import dev.danae.creativesuite.plugin.commands.alias.AliasInspectCommand;
import dev.danae.creativesuite.plugin.commands.alias.AliasListCommand;
import dev.danae.creativesuite.plugin.commands.alias.AliasRemoveCommand;
import dev.danae.creativesuite.plugin.commands.alias.AliasRenameCommand;
import dev.danae.creativesuite.plugin.commands.alias.AliasRunCommand;
import dev.danae.creativesuite.plugin.commands.alias.AliasSaveCommand;
import dev.danae.creativesuite.plugin.commands.charmap.CharmapAddCommand;
import dev.danae.creativesuite.plugin.commands.charmap.CharmapListCommand;
import dev.danae.creativesuite.plugin.commands.charmap.CharmapRemoveCommand;
import dev.danae.creativesuite.plugin.commands.hotbar.HotbarListCommand;
import dev.danae.creativesuite.plugin.commands.hotbar.HotbarLoadCommand;
import dev.danae.creativesuite.plugin.commands.hotbar.HotbarRemoveCommand;
import dev.danae.creativesuite.plugin.commands.hotbar.HotbarRenameCommand;
import dev.danae.creativesuite.plugin.commands.hotbar.HotbarSaveCommand;
import dev.danae.creativesuite.plugin.commands.tools.ToolsClearFillCommand;
import dev.danae.creativesuite.plugin.commands.tools.ToolsDropCommand;
import dev.danae.creativesuite.plugin.commands.tools.ToolsNightVisionCommand;
import dev.danae.creativesuite.plugin.listeners.SignMaterialListener;
import dev.danae.creativesuite.plugin.migrations.v1_1_1.ConfigurationSerializableMigration;
import dev.danae.commons.commands.CommandGroup;
import dev.danae.commons.messages.MessageManager;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.configuration.serialization.ConfigurationSerialization;
import org.bukkit.plugin.java.JavaPlugin;


public class CreativeSuitePlugin extends JavaPlugin implements MessageManager
{
  // The manager of the plugin
  private CreativeSuiteManager manager;

  // The sign material listener of the plugin
  private SignMaterialListener signMaterialListener;


  // The options of the plugin
  private final CreativeSuitePluginOptions options = new CreativeSuitePluginOptions();

  // The map of the defined messages
  private Map<String, String> messages = new HashMap<>();
  
  
  // Return the manager of the plugin
  public Manager getManager()
  {
    return this.manager;
  }

  // Return the message with the specified name
  public String getMessage(String name)
  {
    return this.messages.get(name);
  }
  

  // Load the plugin
  @Override
  public void onLoad()
  {
    // Register serializable classes for the configuration API
    ConfigurationSerialization.registerClass(Alias.class);
    ConfigurationSerialization.registerClass(Hotbar.class);

    // Load the plugin
    this.loadPlugin();
  }
  
  // Enable the plugin
  @Override
  public void onEnable()
  {    
    // Create the components
    this.manager = new CreativeSuiteManager(this, this.options);
    this.signMaterialListener = new SignMaterialListener(this);

    // Load the data
    this.manager.loadData();

    // Set the listeners
    Bukkit.getPluginManager().registerEvents(this.signMaterialListener, this);

    // Set the command handlers  
    new CommandGroup()
      .registerSubcommand("reload", new AdminReloadCommand(this.manager, this))
      .registerSubcommand("version", new AdminVersionCommand(this.manager, this))
      .publishCommandHandler(this, this.getCommand("creativesuite"));

    new CommandGroup()
      .registerSubcommand("inspect", new AliasInspectCommand(this.manager))
      .registerSubcommand("list", new AliasListCommand(this.manager))
      .registerSubcommand("overwrite", new AliasSaveCommand(this.manager, true))
      .registerSubcommand("remove", new AliasRemoveCommand(this.manager))
      .registerSubcommand("rename", new AliasRenameCommand(this.manager))
      .registerSubcommand("run", new AliasRunCommand(this.manager))
      .registerSubcommand("save", new AliasSaveCommand(this.manager, false))
      .publishCommandHandler(this, this.getCommand("alias"));
    
    new CommandGroup()
      .registerSubcommand("list", new HotbarListCommand(this.manager))
      .registerSubcommand("load", new HotbarLoadCommand(this.manager))
      .registerSubcommand("overwrite", new HotbarSaveCommand(this.manager, true))
      .registerSubcommand("remove", new HotbarRemoveCommand(this.manager))
      .registerSubcommand("rename", new HotbarRenameCommand(this.manager))
      .registerSubcommand("save", new HotbarSaveCommand(this.manager, false))
      .publishCommandHandler(this, this.getCommand("hotbar"));

    new CommandGroup()
      .registerSubcommand("add", new CharmapAddCommand(this.manager))
      .registerSubcommand("remove", new CharmapRemoveCommand(this.manager))
      .registerEmptySubcommand(new CharmapListCommand(this.manager))
      .publishCommandHandler(this, this.getCommand("charmap"));

    new AliasRunCommand(this.manager)
      .publishCommandHandler(this, this.getCommand("run"));

    new ToolsClearFillCommand(this.getManager())
      .publishCommandHandler(this, this.getCommand("clearfill"));

    new ToolsNightVisionCommand(this.getManager())
      .publishCommandHandler(this, this.getCommand("nightvision"));

    new ToolsDropCommand(this.getManager())
      .publishCommandHandler(this, this.getCommand("drop"));

    new ToolsDropCommand(this.getManager(), Material.ANVIL)
      .publishCommandHandler(this, this.getCommand("anvil"));

    new ToolsNightVisionCommand(this.getManager())
      .publishCommandHandler(this, this.getCommand("smite"));
  }


  // Load the plugin
  public void loadPlugin()
  {
    // Execute migrations
    this.executeMigrations();

    // Load the configuration
    this.loadConfiguration();
  }

  // Execute migrations
  public void executeMigrations()
  {
    this.getLogger().log(Level.INFO, "Checking for migrations to execute...");

    // Execute v1.1.1 migrations
    new ConfigurationSerializableMigration(this).migrate();
  }

  // Load the configuration
  public void loadConfiguration()
  {
    // Save the default configuration
    this.saveDefaultConfig();
    this.reloadConfig();

    // Load the clearfill tool configuration
    var clearfillToolConfig = this.getConfig().getConfigurationSection("clearfill-tool");
    if (clearfillToolConfig != null)
    {
      this.options.setClearfillToolHotbar(clearfillToolConfig.getString("hotbar", null));
      this.options.setClearfillToolElytraAdded(clearfillToolConfig.getBoolean("elytra-added", true));
    }

    // Load the drop tool configuration
    var dropToolConfig = this.getConfig().getConfigurationSection("drop-tool");
    if (dropToolConfig != null)
    {
      this.options.setDropToolRelativeHeight(dropToolConfig.getInt("relative-height", 5));
    }

    // Load the messages configuration
    this.messages.clear();

    var messagesConfig = this.getConfig().getConfigurationSection("messages");
    if (messagesConfig != null)
    {
      for (var messageName : messagesConfig.getKeys(false))
      {
        var message = messagesConfig.getString(messageName);
        this.messages.put(messageName, message);
      }
    }
  }
}