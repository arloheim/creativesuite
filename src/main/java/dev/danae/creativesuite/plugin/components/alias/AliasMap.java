package dev.danae.creativesuite.plugin.components.alias;

import dev.danae.creativesuite.plugin.CreativeSuitePlugin;
import dev.danae.creativesuite.util.data.DataMap;
import dev.danae.creativesuite.util.data.DataMapKeyType;
import org.bukkit.NamespacedKey;


public class AliasMap extends DataMap<NamespacedKey, Alias>
{
  // Constructor
  public AliasMap(CreativeSuitePlugin plugin, String fileName)
  {
    super(plugin, fileName, Alias.class, DataMapKeyType.NAMESPACED_KEY);
  }
}
