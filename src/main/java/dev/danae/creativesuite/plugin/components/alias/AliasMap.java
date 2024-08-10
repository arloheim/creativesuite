package dev.danae.gregocommands.plugin.components.alias;

import dev.danae.gregocommands.plugin.CreativeSuitePlugin;
import dev.danae.gregocommands.util.data.DataMap;
import dev.danae.gregocommands.util.data.DataMapKeyType;
import org.bukkit.NamespacedKey;


public class AliasMap extends DataMap<NamespacedKey, Alias>
{
  // Constructor
  public AliasMap(CreativeSuitePlugin plugin, String fileName)
  {
    super(plugin, fileName, Alias.class, DataMapKeyType.NAMESPACED_KEY);
  }
}
