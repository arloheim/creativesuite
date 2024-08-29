package dev.danae.commons;

import java.util.function.UnaryOperator;
import java.util.stream.Stream;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class Players 
{
  // Return a stream of all online players matching the filter
  public static Stream<? extends Player> values()
  {
    return Bukkit.getOnlinePlayers().stream();
  }
  
  // Return a stream of all online player names mapped with the specified operator
  public static Stream<String> names(UnaryOperator<String> mapper)
  {
    return values()
      .map(player -> mapper.apply(player.getName()))
      .sorted();
  }

  // Return a stream of all online player names matching the filter
  public static Stream<String> names()
  {
    return names(UnaryOperator.identity());
  }
}
