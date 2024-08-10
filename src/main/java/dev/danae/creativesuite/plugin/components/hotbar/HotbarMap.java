package dev.danae.gregocommands.plugin.components.hotbar;

import dev.danae.gregocommands.plugin.CreativeSuitePlugin;
import dev.danae.gregocommands.util.data.DataMap;
import dev.danae.gregocommands.util.data.DataMapKeyType;
import org.bukkit.NamespacedKey;


public class HotbarMap extends DataMap<NamespacedKey, Hotbar>
{
  // Constructor
  public HotbarMap(CreativeSuitePlugin plugin, String fileName)
  {
    super(plugin, fileName, Hotbar.class, DataMapKeyType.NAMESPACED_KEY);
  }
}
