package dev.danae.creativesuite.model;

import dev.danae.commons.messages.MessageManager;
import dev.danae.creativesuite.model.alias.Alias;
import dev.danae.creativesuite.model.charmap.Charmap;
import dev.danae.creativesuite.model.hotbar.Hotbar;
import dev.danae.creativesuite.util.Toggle;
import java.util.Map;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;


public interface Manager extends MessageManager
{
  // Return the defined aliases
  public Map<NamespacedKey, Alias> getDefinedAliases();

  // Get an alias
  public Alias getAlias(NamespacedKey key);

  // Set an alias
  public void setAlias(NamespacedKey key, Alias alias);

  // Remove an alias
  public void removeAlias(NamespacedKey key);

  // Return the defined hotbars
  public Map<NamespacedKey, Hotbar> getDefinedHotbars();

  // Get a hotbar
  public Hotbar getHotbar(NamespacedKey key);

  // Set a hotbar
  public void setHotbar(NamespacedKey key, Hotbar hotbar);

  // Remove a hotbar
  public void removeHotbar(NamespacedKey key);

  // Return the charmap
  public Charmap getCharmap();

  // Add code points to the charmap
  public void addToCharmap(String codePoints);

  // Remove code points from the charmap
  public void removeFromCharmap(String codePoints);

  // Clear the inventory of a player and fill it with a hotbar
  public void clearInventory(Player player);

  // Update the night vision effect of a player
  public boolean updateNightVision(Player player, Toggle enabled);

  // Drop a gravity-affected block at a location
  public void drop(Material material, Location location);

  // Execute a lightning effect at a location
  public void smite(Location location);
}
