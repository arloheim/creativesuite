package dev.danae.creativesuite.plugin;

import dev.danae.commons.messages.MessageManager;
import dev.danae.creativesuite.model.Alias;
import dev.danae.creativesuite.model.AliasMap;
import dev.danae.creativesuite.model.Charmap;
import dev.danae.creativesuite.model.Hotbar;
import dev.danae.creativesuite.model.HotbarMap;
import dev.danae.creativesuite.model.Manager;
import java.util.Map;
import net.md_5.bungee.api.chat.BaseComponent;
import org.bukkit.NamespacedKey;


public class CreativeSuiteManager extends CreativeSuitePluginComponent implements Manager
{
  // The message manager
  private final MessageManager messageManager;

  // The configuration map of the defined aliases
  private final AliasMap aliases;

  // The configuration map of the defined hotbars
  private final HotbarMap hotbars;

  // The charmap
  private Charmap charmap;


  // Constructor
  public CreativeSuiteManager(CreativeSuitePlugin plugin)
  {
    super(plugin);

    this.messageManager = plugin;
    this.aliases = new AliasMap(plugin, "aliases.yml");
    this.hotbars = new HotbarMap(plugin, "hotbars.yml");
    this.charmap = new Charmap(plugin, "charmap.yml");
  }


  // Return the message with the specified name
  public String getMessage(String name)
  {
    return this.messageManager.getMessage(name);
  }

  // Format the message with the specified name and arguments
  public BaseComponent[] formatMessage(String name, Map<String, Object> args)
  {
    return this.messageManager.formatMessage(name, args);
  }


  // Return the defined aliases
  @Override
  public Map<NamespacedKey, Alias> getDefinedAliases()
  {
    return this.aliases;
  }

  // Get an alias
  @Override
  public Alias getAlias(NamespacedKey key)
  {
    return this.aliases.getOrDefault(key, null);
  }

  // Set an alias
  @Override
  public void setAlias(NamespacedKey key, Alias alias)
  {
    this.aliases.put(key, alias);
  }

  // Remove an alias
  @Override
  public void removeAlias(NamespacedKey key)
  {
    this.aliases.remove(key);
  }

  // Return the defined hotbars
  @Override
  public Map<NamespacedKey, Hotbar> getDefinedHotbars()
  {
    return this.hotbars;
  }

  // Get a hotbar
  @Override
  public Hotbar getHotbar(NamespacedKey key)
  {
    return this.hotbars.getOrDefault(key, null);
  }

  // Set a hotbar'
  @Override
  public void setHotbar(NamespacedKey key, Hotbar hotbar)
  {
    this.hotbars.put(key, hotbar);
  }

  // Remove a hotbar
  @Override
  public void removeHotbar(NamespacedKey key)
  {
    this.hotbars.remove(key);
  }

  // Return the charmap
  @Override
  public Charmap getCharmap()
  {
    return this.charmap;
  }

  // Add code points to the charmap
  @Override
  public void addToCharmap(String codePoints)
  {
    this.charmap.addCodePoints(codePoints);
  }

  // Remove code points from the charmap
  @Override
  public void removeFromCharmap(String codePoints)
  {
    this.charmap.removeCodePoints(codePoints);
  }
}
