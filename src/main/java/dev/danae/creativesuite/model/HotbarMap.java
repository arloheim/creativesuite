package dev.danae.creativesuite.model;

import dev.danae.commons.data.DataMap;
import dev.danae.commons.data.DataMapKeyType;
import dev.danae.creativesuite.plugin.CreativeSuitePlugin;
import org.bukkit.NamespacedKey;


public class HotbarMap extends DataMap<NamespacedKey, Hotbar>
{
  // Constructor
  public HotbarMap(CreativeSuitePlugin plugin, String fileName)
  {
    super(plugin, fileName, Hotbar.class, DataMapKeyType.NAMESPACED_KEY);
  }
}
