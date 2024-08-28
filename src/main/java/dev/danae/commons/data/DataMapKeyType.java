package dev.danae.commons.data;

import org.bukkit.NamespacedKey;


public interface DataMapKeyType<K>
{
  // Static key type instances
  public static DataMapKeyType<String> STRING = new StringKeyType();
  public static DataMapKeyType<NamespacedKey> NAMESPACED_KEY = new NamespacedKeyKeyType();


  // Convert the specified key to its string representation
  public String toString(K key);

  // Convert the specified string to its key representation
  public K toKey(String string);


  
  // Class that defines a configuration map key type for a code tag
  public static class StringKeyType implements DataMapKeyType<String>
  {
    // Convert the specified key to its string representation
    public String toString(String key)
    {
      return key;
    }

    // Convert the specified string to its key representation
    public String toKey(String string)
    {
      return string;
    }
  }


  // Class that defines a configuration map key type for a namespaced key
  public static class NamespacedKeyKeyType implements DataMapKeyType<NamespacedKey>
  {
    // Convert the specified key to its string representation
    public String toString(NamespacedKey key)
    {
      return key.toString();
    }

    // Convert the specified string to its key representation
    public NamespacedKey toKey(String string)
    {
      return NamespacedKey.fromString(string);
    }
  }
}
