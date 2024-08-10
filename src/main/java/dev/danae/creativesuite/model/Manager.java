package dev.danae.creativesuite.model;

import java.util.Map;
import java.util.Optional;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;

public interface Manager 
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
}
