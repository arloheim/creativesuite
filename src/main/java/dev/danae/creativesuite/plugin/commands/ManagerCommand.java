package dev.danae.creativesuite.plugin.commands;

import dev.danae.commons.commands.Command;
import dev.danae.commons.commands.CommandUtils;
import dev.danae.commons.messages.MessageManager;
import dev.danae.commons.messages.NamespacedKeyFormatter;
import dev.danae.commons.parser.Parser;
import dev.danae.commons.parser.ParserException;
import dev.danae.creativesuite.model.Manager;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import net.md_5.bungee.api.chat.BaseComponent;


public abstract class ManagerCommand extends Command implements MessageManager
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

  // Return the message with the specified name
  public String getMessage(String name)
  {
    return this.manager.getMessage(name);
  }

  // Format the message with the specified name and arguments
  public BaseComponent[] formatMessage(String name, Map<String, Object> args)
  {
    return this.manager.formatMessage(name, args);
  }
  

  // Handle tab completion of an alias argument
  public List<String> handleAliasTabCompletion(String arg)
  {
    return CommandUtils.handleSearchTabCompletion(arg, this.manager.getDefinedAliases().keySet().stream()
      .sorted(NamespacedKeyFormatter.CASE_INSENSITIVE_ORDER)
      .map(key -> key.toString())
      .toList());
  }

  // Handle tab completion of an alias parameter argument
  public List<String> handleAliasParameterTabCompletion(String arg, String aliasName)
  {
    try
    {
      var aliasKey = Parser.parseNamespacedKey(aliasName);
      var alias = this.getManager().getAlias(aliasKey);

      return CommandUtils.handleSearchTabCompletion(arg, alias.getParameters().stream()
        .sorted(Comparator.comparing(param -> param.getName(), String.CASE_INSENSITIVE_ORDER))
        .map(param -> param.toString())
        .toList());
    }
    catch (ParserException ex)
    {
      return List.of();
    }
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
