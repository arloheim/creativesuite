package dev.danae.gregocommands.plugin.components.charmap;

import dev.danae.gregocommands.plugin.GregoCommandsPlugin;
import dev.danae.gregocommands.util.data.Data;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;
import org.bukkit.configuration.ConfigurationSection;


public class Charmap extends Data
{
  // The code points of the charmap
  private List<Integer> codePoints;


  // Constructor for a list of code points
  public Charmap(GregoCommandsPlugin plugin, File file)
  {
    super(plugin, file);

    this.codePoints = new ArrayList<Integer>();
    this.load();
  }


  // Get the code points in the charmap
  public List<Integer> getCodePoints()
  {
    return this.codePoints;
  }

  // Get the code points in the charmap as strings
  public List<String> getCodePointsAsStrings()
  {
    return this.codePoints.stream()
      .sorted()
      .map(Charmap::codePointToString)
      .toList();
  }

  // Add a code point to the charmap
  public void addCodePoint(int codePoint)
  {
    if (this.codePoints.contains(codePoint))
      return;
    
    this.codePoints.add(codePoint);
    this.save();
  }

  // Add an array of code points to the charmap
  public void addCodePoints(int[] codePoints)
  {
    for (var codePoint : codePoints)
      this.addCodePoint(codePoint);
  }

  // Add a stream of code points to the charmap
  public void addCodePoints(IntStream codePoints)
  {
    this.addCodePoints(codePoints.toArray());
  }

  // Add a string of code points to the charmap
  public void addCodePoints(String codePoints)
  {
    this.addCodePoints(codePoints.codePoints());
  }

  // Remove a code point from the charmap
  public void removeCodePoint(int codePoint)
  {
    this.codePoints.remove((Object)codePoint);
    this.save();
  }

  // Remove an array of code points from the charmap
  public void removeCodePoints(int[] codePoints)
  {
    for (var codePoint : codePoints)
      this.removeCodePoint(codePoint);
  }

  // Remove a stream of code points from the charmap
  public void removeCodePoints(IntStream codePoints)
  {
    this.removeCodePoints(codePoints.toArray());
  }

  // Remove a string of code points from the charmap
  public void removeCodePoints(String codePoints)
  {
    this.removeCodePoints(codePoints.codePoints());
  }
  

  // Serialize the configuration
  public void serialize(ConfigurationSection config)
  {
    config.set("codePoints", this.codePoints);
  }
  
  // Deserialize the configuration
  public void deserialize(ConfigurationSection config)
  {
    this.codePoints = config.getIntegerList("codePoints");
  }


  // Return the string representation of a code point
  public static String codePointToString(int codePoint)
  {
    return new String(Character.toChars(codePoint));
  }
}
