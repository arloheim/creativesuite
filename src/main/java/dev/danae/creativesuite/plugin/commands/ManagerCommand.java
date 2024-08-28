package dev.danae.creativesuite.plugin.commands;

import dev.danae.commons.commands.Command;
import dev.danae.commons.commands.CommandUtils;
import dev.danae.commons.messages.MessageManager;
import dev.danae.commons.messages.NamespacedKeyFormatter;
import dev.danae.creativesuite.model.Manager;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.bukkit.NamespacedKey;


public abstract class ManagerCommand extends Command
{    
  // The manager of the command
  private final Manager manager;
  
  
  // Constructor
  public ManagerCommand(Manager manager, String... permissions)
  {
    super(permissions);
    
    this.manager = manager;
  }
  
  // Constructor without permissions
  public ManagerCommand(Manager manager)
  {    
    this.manager = manager;
  }
  
  
  // Return the manager of the command
  protected Manager getManager()
  {
    return this.manager;
  }
  

  // Handle tab completion of an alias argument
  public List<String> handleAliasTabCompletion(String arg)
  {
    return CommandUtils.handleSearchTabCompletion(arg, this.manager.getDefinedAliases().keySet().stream()
      .sorted(NamespacedKeys.CASE_INSENSITIVE_ORDER)
      .map(key -> key.toString())
      .toList());
  }

  // Handle tab completion of a hotbar argument
  public List<String> handleHotbarTabCompletion(String arg)
  {
    return CommandUtils.handleSearchTabCompletion(arg, this.manager.getDefinedHotbars().keySet().stream()
      .sorted((a, b) -> a.toString().compareToIgnoreCase(b.toString()))
      .map(key -> key.toString())
      .toList());
  }
}
