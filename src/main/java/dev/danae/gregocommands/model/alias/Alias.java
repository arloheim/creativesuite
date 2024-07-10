package dev.danae.gregocommands.model.alias;

import java.util.HashMap;
import java.util.Map;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.serialization.ConfigurationSerializable;


public class Alias implements ConfigurationSerializable
{
  // The command of the alias
  private final String command;


  // Constructor
  public Alias(String command)
  {
    this.command = command;
  }


  // Return the command of the alias
  public String getCommand()
  {
    return this.command;
  }

  // Dispatch the command
  public void dispatchCommand(CommandSender sender)
  {
    if (command != null && !command.isEmpty())
      Bukkit.dispatchCommand(sender, this.command);
  }


  // Serialize the alias to a map representation
  public Map<String, Object> serialize()
  {
    var map = new HashMap<String, Object>();
    map.put("command", this.command);
    return map;
  }
  
  // Deserialize a map representation to an alias
  public static Alias deserialize(Map<String, Object> map)
  {
    var command = (String)map.getOrDefault("command", null);
    return new Alias(command);
  }
}
