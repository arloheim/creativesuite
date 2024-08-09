package dev.danae.gregocommands.plugin.components.hotbar;

import dev.danae.gregocommands.plugin.GregoCommandsPlugin;
import dev.danae.gregocommands.util.data.DataMap;
import dev.danae.gregocommands.util.data.DataMapKeyType;
import org.bukkit.NamespacedKey;


public class HotbarMap extends DataMap<NamespacedKey, Hotbar>
{
  // Constructor
  public HotbarMap(GregoCommandsPlugin plugin, String fileName)
  {
    super(plugin, fileName, Hotbar.class, DataMapKeyType.NAMESPACED_KEY);
  }
}
