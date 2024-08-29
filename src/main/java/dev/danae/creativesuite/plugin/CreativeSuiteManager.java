package dev.danae.creativesuite.plugin;

import dev.danae.commons.messages.MessageManager;
import dev.danae.creativesuite.model.Manager;
import dev.danae.creativesuite.model.alias.Alias;
import dev.danae.creativesuite.model.alias.AliasMap;
import dev.danae.creativesuite.model.charmap.Charmap;
import dev.danae.creativesuite.model.hotbar.Hotbar;
import dev.danae.creativesuite.model.hotbar.HotbarMap;
import dev.danae.creativesuite.util.Toggle;
import java.util.Map;
import net.md_5.bungee.api.chat.BaseComponent;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;


public class CreativeSuiteManager extends CreativeSuitePluginComponent implements Manager
{
  // The options for the manager
  private final CreativeSuitePluginOptions options;

  // The message manager
  private final MessageManager messageManager;

  // The configuration map of the defined aliases
  private final AliasMap aliases;

  // The configuration map of the defined hotbars
  private final HotbarMap hotbars;

  // The charmap
  private Charmap charmap;


  // Constructor
  public CreativeSuiteManager(CreativeSuitePlugin plugin, CreativeSuitePluginOptions options)
  {
    super(plugin);

    this.options = options;

    this.messageManager = plugin;
    this.aliases = new AliasMap(plugin, "aliases.yml");
    this.hotbars = new HotbarMap(plugin, "hotbars.yml");
    this.charmap = new Charmap(plugin, "charmap.yml");
  }


  // Load the data of the manager
  public void loadData()
  {
    this.aliases.load();
    this.hotbars.load();
    this.charmap.load();
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

  // Clear the inventory of a player and fill it with a hotbar
  @Override
  public void clearInventory(Player player)
  {
    // Get the player's inventory
    var inventory = player.getInventory();
    
    // Clear the inventory of the player
    inventory.clear();

    // Apply the hotbar, if applicable
    if (this.options.getClearfillToolHotbar() != null)
    {
      var key = NamespacedKey.fromString(this.options.getClearfillToolHotbar());
      if (key != null)
      {
        var hotbar = this.getHotbar(key);
        if (hotbar != null)
          hotbar.applyTo(inventory);
      }
    }

    // Equip the elytra, if applicable
    if (this.options.isClearfillToolElytraAdded())
    {
      var elytra = new ItemStack(Material.ELYTRA, 1);
      elytra.editMeta(meta -> meta.setUnbreakable(true));
      inventory.setChestplate(elytra);
    }
  }

  // Update the night vision effect of a player
  @Override
  public boolean updateNightVision(Player player, Toggle toggle)
  {
    // Convert the toggle to a boolean
    var hasNightVision = player.hasPotionEffect(PotionEffectType.NIGHT_VISION);
    var enabled = toggle.toBoolean(hasNightVision);

    // Remove the night vision potion effect
    player.removePotionEffect(PotionEffectType.NIGHT_VISION);

    // Check if the effect should enable and the player has no effect
    if (enabled)
    {
      // Add a new night vision potion effect
      var effect = new PotionEffect(PotionEffectType.NIGHT_VISION, PotionEffect.INFINITE_DURATION, 1, false, false, false);
      player.addPotionEffect(effect);
    }

    // Return the new enabled state
    return enabled;
  }

  // Drop a gravity-affected block at a location
  @Override
  public void drop(Material material, Location location)
  {
    // Get the drop location
    var dropLocation = location.clone();
    while (dropLocation.getBlock().getRelative(BlockFace.UP).getType().isAir() && dropLocation.distance(location) <= this.options.getDropToolRelativeHeight())
      dropLocation = dropLocation.getBlock().getRelative(BlockFace.UP).getLocation();
    
    // Spawn a block at the drop location
    dropLocation.getBlock().setType(material);
  }

  // Execute a lightning effect at a location
  @Override
  public void smite(Location location)
  {
    // Strike lightning at the specified location
    location.getWorld().strikeLightning(location);
  }
}
