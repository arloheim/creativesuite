package dev.danae.commons;

import dev.danae.commons.commands.CommandContext;
import java.util.List;
import java.util.stream.Stream;
import org.bukkit.Bukkit;
import org.bukkit.NamespacedKey;


public class Locations 
{
  // Handle tab completion of a location argument using the specified aliases
  public static List<String> handleTabCompletion(CommandContext context, int argumentIndex, Stream<NamespacedKey> aliases)
  {
    if (!context.hasAtLeastArgumentsCount(argumentIndex + 1) || context.hasAtLeastArgumentsCount(argumentIndex + 4))
      return List.of();

    var locations = Stream.concat(aliases.map(key -> String.format("#%s", key)), Stream.of("~"));
    if (context.hasArgumentsCount(argumentIndex + 1))
      locations = Stream.concat(locations, Bukkit.getOnlinePlayers().stream().map(p -> p.getName()));
  
    return locations.sorted().toList();
  }

  // Handle tab completion of a location argument 
  public static List<String> handleTabCompletion(CommandContext context, int argumentIndex)
  {
    return handleTabCompletion(context, argumentIndex, Stream.empty());
  }
}
