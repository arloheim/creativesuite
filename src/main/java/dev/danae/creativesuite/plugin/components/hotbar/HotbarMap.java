package dev.danae.creativesuite.plugin.components.hotbar;

import dev.danae.creativesuite.plugin.CreativeSuitePlugin;
import dev.danae.creativesuite.util.data.DataMap;
import dev.danae.creativesuite.util.data.DataMapKeyType;
import org.bukkit.NamespacedKey;


public class HotbarMap extends DataMap<NamespacedKey, Hotbar>
{
  // Constructor
  public HotbarMap(CreativeSuitePlugin plugin, String fileName)
  {
    super(plugin, fileName, Hotbar.class, DataMapKeyType.NAMESPACED_KEY);
  }
}
