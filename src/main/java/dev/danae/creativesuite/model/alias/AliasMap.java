package dev.danae.creativesuite.model.alias;

import dev.danae.commons.data.DataMap;
import dev.danae.commons.data.DataMapKeyType;
import dev.danae.creativesuite.plugin.CreativeSuitePlugin;
import org.bukkit.NamespacedKey;


public class AliasMap extends DataMap<NamespacedKey, Alias>
{
  // Constructor
  public AliasMap(CreativeSuitePlugin plugin, String fileName)
  {
    super(plugin, fileName, Alias.class, DataMapKeyType.NAMESPACED_KEY);
  }
}
