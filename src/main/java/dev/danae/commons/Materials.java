package dev.danae.commons;

import java.util.Arrays;
import java.util.List;
import java.util.function.UnaryOperator;
import java.util.stream.Stream;
import org.bukkit.Material;


public class Materials 
{
  // Enum that defines the filter of the materials
  public static enum Filter
  {
    ALL,
    BLOCKS,
    GRAVITY_BLOCKS,
    ITEMS,
  }


  // Return if a material matches the specified filter
  public static boolean matches(Material material, Filter filter)
  {
    return switch (filter)
    {
      case BLOCKS -> material.isBlock();
      case GRAVITY_BLOCKS -> material.isBlock() && material.hasGravity();
      case ITEMS -> material.isItem();
      default -> true;
    };
  }

  // Return a stream of all materials matching the filter
  public static Stream<Material> values(Filter filter)
  {
    return Arrays.stream(Material.values())
      .filter(material -> matches(material, filter));
  }
  
  // Return a stream of all material names matching the filter mapped with the specified operator
  public static Stream<String> names(Filter filter, UnaryOperator<String> mapper)
  {
    return values(filter)
      .map(material -> mapper.apply(material.name().toLowerCase()))
      .sorted();
  }

  // Return a stream of all material names matching the filter
  public static Stream<String> names(Filter filter)
  {
    return names(filter, UnaryOperator.identity());
  }


  // Handle tab completion of a material argument mapped with the specified operator
  public static List<String> handleTabCompletion(String arg, Filter filter, UnaryOperator<String> mapper)
  {
    return names(filter, mapper)
      .filter(s -> arg.isEmpty() || s.startsWith(arg))
      .toList();
  }

  // Handle tab completion of a material argument mapped with the specified operator
  public static List<String> handleTabCompletion(String arg, Filter filter)
  {
    return handleTabCompletion(arg, filter, UnaryOperator.identity());
  }
}
