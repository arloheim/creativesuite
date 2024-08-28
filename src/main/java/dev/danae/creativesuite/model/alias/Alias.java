package dev.danae.creativesuite.model.alias;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.serialization.ConfigurationSerializable;
import org.bukkit.configuration.serialization.SerializableAs;


@SerializableAs("Alias")
public class Alias implements ConfigurationSerializable
{
  // The command of the alias
  private final String command;

  // The variables of the alias
  private final List<Parameter> parameters;


  // Constructor
  public Alias(String command)
  {
    this.command = command;
    this.parameters = Parameter.parse(command);
  }


  // Return the command of the alias
  public String getCommand()
  {
    return this.command;
  }

  // Return the parameters of the alias
  public List<Parameter> getParameters()
  {
    return this.parameters;
  }


  // Dispatch the command
  public void dispatchCommand(CommandSender sender, Map<String, String> args)
  {
    // Check if the command is not empty
    if (this.command == null || this.command.isEmpty())
      return;

    // Format the command
    var actualCommand = Parameter.replace(this.command, args);

    // Dispatch the command
    Bukkit.dispatchCommand(sender, actualCommand);
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
