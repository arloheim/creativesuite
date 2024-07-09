package dev.danae.gregocommands.plugin.data;

import dev.danae.gregocommands.plugin.GregoCommandsPlugin;
import java.io.File;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.serialization.ConfigurationSerializable;


public class DataMap<K, V extends ConfigurationSerializable> extends Data implements Map<K, V>
{
  // The type of the elements in the map
  private final Class<V> clazz;

  // The key type of the map
  private final DataMapKeyType<K> keyType;
  
  // The backing map
  private final Map<K, V> map = new HashMap<>();
  
  
  // Constructor
  public DataMap(GregoCommandsPlugin plugin, File file, Class<V> clazz, DataMapKeyType<K> keyType)
  {
    super(plugin, file);

    this.clazz = clazz;
    this.keyType = keyType;

    this.load();
  }
  
  
  // Return the keys in the map
  @Override
  public Set<K> keySet()
  {
    return this.map.keySet();
  }
  
  // Return the values in the map
  @Override
  public Collection<V> values()
  {
    return this.map.values();
  }
  
  // Return the entries in the map
  @Override
  public Set<Map.Entry<K, V>> entrySet()
  {
    return this.map.entrySet();
  }
  
  // Return the size of the map
  @Override
  public int size()
  {
    return this.map.size();
  }

  // Return if the map is empty
  @Override
  public boolean isEmpty()
  {
    return this.map.isEmpty();
  }

  // Return if the map contains the specified key
  @Override
  public boolean containsKey(Object key)
  {
    return this.map.containsKey(key);
  }

  // Return if the map contains the specified value
  @Override
  public boolean containsValue(Object value)
  {
    return this.map.containsValue(value);
  }
  
  // Get an item from the map
  @Override
  public V get(Object key)
  {
    return this.map.get(key);
  }
  
  // Put an item into the map
  @Override
  public V put(K key, V value)
  {
    V original = this.map.put(key, value);
    this.save();
    return original;
  }
  
  // Put all items in the specified map into the map
  @Override
  public void putAll(Map<? extends K, ? extends V> m)
  {
    this.map.putAll(m);
    this.save();
  }
  
  // Remove an item from the map
  @Override
  public V remove(Object key)
  {
    V original = this.map.remove(key);
    this.save();
    return original;
  }
  
  // Clear the map
  @Override
  public void clear()
  {
    this.map.clear();
    this.save();
  }
  
  
  // Serialize the configuration
  @Override
  protected void serialize(ConfigurationSection config)
  {
    for (var e : this.map.entrySet())
      config.set(this.keyType.toString(e.getKey()), e.getValue());
  }

  // Deserialize the configuration
  @Override
  protected void deserialize(ConfigurationSection config)
  {
    this.map.clear();
    for (String key : config.getKeys(false))
       this.map.put(this.keyType.toKey(key), config.getSerializable(key, this.clazz));
  }
}
