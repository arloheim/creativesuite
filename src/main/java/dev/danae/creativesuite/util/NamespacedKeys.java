package dev.danae.creativesuite.util;

import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import org.bukkit.NamespacedKey;

public class NamespacedKeys 
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


  // Create a text message listing all sorted namespaced keys
  public static <T> BaseComponent[] createSortedKeysMessage(Collection<NamespacedKey> keys, Function<NamespacedKey, ClickEvent> clickEventFunction)
  {
    var builder = new ComponentBuilder();

    var first = true;
    for (var key : sortKeys(keys).toList())
    {
      if (first)
        first = false;
      else
        builder.append(", ", ComponentBuilder.FormatRetention.NONE);

      builder
        .append(key.getKey(), ComponentBuilder.FormatRetention.NONE).color(ChatColor.GREEN).underlined(true)
        .event(clickEventFunction.apply(key));
    }

    return builder.create();
  }

  // Create a text message listing all grouped namespaced keys
  public static <T> BaseComponent[] createGroupedKeysMessage(Collection<NamespacedKey> keys, Function<NamespacedKey, ClickEvent> clickEventFunction)
  {
    var builder = new ComponentBuilder();

    for (var e : groupKeys(keys).toList())
    {
      builder
        .append("\n", ComponentBuilder.FormatRetention.NONE)
        .append(e.getKey().toString(), ComponentBuilder.FormatRetention.NONE).bold(true)
        .append(": ", ComponentBuilder.FormatRetention.NONE)
        .append(createSortedKeysMessage(e.getValue(), clickEventFunction));
    }

    return builder.create();
  }
}
