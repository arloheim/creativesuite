package dev.danae.commons.messages;

import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import org.bukkit.NamespacedKey;

public class NamespacedKeyFormatter 
{
  // Class that defines a case-insensitive comparator for namespaced keys
  public static class CaseInsensitiveComparator implements Comparator<NamespacedKey>
  {
    @Override
    public int compare(NamespacedKey a, NamespacedKey b) 
    {
      return a.toString().compareToIgnoreCase(b.toString());
    }
  }


  // Comparators for namespaced keys
  public static final Comparator<NamespacedKey> CASE_INSENSITIVE_ORDER = new CaseInsensitiveComparator();


  // Return a sorted stream of the specified namespaced keys 
  public static Stream<NamespacedKey> sortKeys(Collection<NamespacedKey> keys)
  {
    return keys.stream()
      .sorted(CASE_INSENSITIVE_ORDER);
  }

  // Return a sorted and grouped stream of the specified namespaced keys 
  public static Stream<Map.Entry<String, List<NamespacedKey>>> groupKeys(Collection<NamespacedKey> keys)
  {
    return sortKeys(keys)
      .collect(Collectors.groupingBy(key -> key.getNamespace())).entrySet().stream()
      .sorted((a, b) -> a.getKey().compareToIgnoreCase(b.getKey()));
  }


  // Format a collection of sorted namespaced keys to an array of chat components
  public static <T> BaseComponent[] formatSortedKeys(Collection<NamespacedKey> keys, MessageFunction keyFunc)
  {
    // Create a new chat component builder
    var builder = new ComponentBuilder();

    // Iterate over the sorted keys
    for (var key : sortKeys(keys).toList())
    {
      // Append a newline
      builder.append("\n", ComponentBuilder.FormatRetention.NONE);

      // Append the key processed through the message function
      builder.append(keyFunc.apply(key.toString()), ComponentBuilder.FormatRetention.NONE);
    }

    // Return the built chat components
    return builder.create();
  }

  // Format a collection of grouped namespaced keys to an array of chat components
  public static <T> BaseComponent[] formatGroupedKeys(Collection<NamespacedKey> keys, MessageFunction groupFunc, MessageFunction keyFunc)
  {
    // Create a new chat component builder
    var builder = new ComponentBuilder();

    // Iterate over the grouped keys
    for (var group : groupKeys(keys).toList())
    {
      // Append a newline
      builder.append("\n", ComponentBuilder.FormatRetention.NONE);

      // Append the group name
      builder.append(groupFunc.apply(group.getKey()), ComponentBuilder.FormatRetention.NONE);

      // Append a colon
      builder.append(": ", ComponentBuilder.FormatRetention.NONE);
      
      // Iterate over the keys in the group
      var first = true;
      for (var key : sortKeys(group.getValue()).toList())
      {
        // Append a comma if this is not the first key
        if (first)
          first = false;
        else
          builder.append(", ", ComponentBuilder.FormatRetention.NONE);

        // Append the key name
        builder.append(keyFunc.apply(key.getKey()), ComponentBuilder.FormatRetention.NONE);
      }
    }

    // Return the built chat components
    return builder.create();
  }
}
