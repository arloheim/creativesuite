package dev.danae.gregocommands.plugin.components.alias;

import dev.danae.gregocommands.plugin.CreativeSuitePluginComponent;
import dev.danae.gregocommands.plugin.CreativeSuitePlugin;
import dev.danae.gregocommands.plugin.commands.alias.AliasListCommand;
import dev.danae.gregocommands.plugin.commands.alias.AliasRemoveCommand;
import dev.danae.gregocommands.plugin.commands.alias.AliasRunCommand;
import dev.danae.gregocommands.plugin.commands.alias.AliasSaveCommand;
import dev.danae.gregocommands.util.NamespacedKeys;
import dev.danae.gregocommands.util.TabCompleters;
import dev.danae.gregocommands.util.commands.CommandGroup;
import java.util.List;
import java.util.Map;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import org.bukkit.NamespacedKey;


public class AliasComponent extends CreativeSuitePluginComponent
{
  // The configuration map of the defined aliases
  private final AliasMap aliases;


  // Constructor
  public AliasComponent(CreativeSuitePlugin plugin) 
  {
    super(plugin);

    this.aliases = new AliasMap(plugin, "aliases.yml");
  }
  

  // Load the component
  @Override
  public void loadComponent()
  {
    super.loadComponent();

    // Load the data
    this.aliases.load();
  }

  // Enable the component
  @Override
  public void enableComponent()
  {
    super.enableComponent();

    // Register the commands
    this.registerCommandHandler("alias", new CommandGroup()
      .registerSubcommand("list", new AliasListCommand(this))
      .registerSubcommand("overwrite", new AliasSaveCommand(this, true))
      .registerSubcommand("remove", new AliasRemoveCommand(this))
      .registerSubcommand("save", new AliasSaveCommand(this, false))
      .registerSubcommand("run", new AliasRunCommand(this)));
    this.registerCommandHandler("run", new AliasRunCommand(this));
  }


  // Return the aliases
  public Map<NamespacedKey, Alias> getAliases()
  {
    return this.aliases;
  }

  // Handle tab completion of an alias argument
  public List<String> handleAliasTabCompletion(String arg)
  {
    return TabCompleters.handleSearchTabCompletion(arg, this.aliases.keySet().stream()
      .sorted(NamespacedKeys.CASE_INSENSITIVE_ORDER)
      .map(key -> key.toString())
      .toList());
  }

  // Format an alias list message
  public BaseComponent[] formatAliasListMessage()
  {
    return new ComponentBuilder(String.format("%d aliases are defined", this.aliases.size()))
      .append(NamespacedKeys.createGroupedKeysMessage(this.aliases.keySet(), 
        key -> new ClickEvent(ClickEvent.Action.SUGGEST_COMMAND, String.format("/alias run %s", key.toString()))))
      .create();
  }

  // Format an alias saved message
  public BaseComponent[] formatAliasSavedMessage(NamespacedKey key, String command, boolean existingKey)
  {
    return new ComponentBuilder()
      .append("The alias ", ComponentBuilder.FormatRetention.NONE)
      .append(key.toString(), ComponentBuilder.FormatRetention.NONE).color(ChatColor.GREEN)
      .append(" has been ", ComponentBuilder.FormatRetention.NONE)
      .append(existingKey ? "overwritten" : "saved", ComponentBuilder.FormatRetention.NONE)
      .append(" with command ", ComponentBuilder.FormatRetention.NONE)
      .append(command, ComponentBuilder.FormatRetention.NONE).color(ChatColor.GREEN)
      .create();
  }

  // Format an alias overwrite message
  public BaseComponent[] formatAliasOverwriteMessage(NamespacedKey key, String command)
  {
    var overwriteCommand = String.format("/alias overwrite %s %s", key.toString(), command);

    return new ComponentBuilder()
      .append("The alias ", ComponentBuilder.FormatRetention.NONE)
      .append(key.toString(), ComponentBuilder.FormatRetention.NONE).color(ChatColor.GREEN)
      .append(" already exists. Use ", ComponentBuilder.FormatRetention.NONE)
      .append(overwriteCommand, ComponentBuilder.FormatRetention.NONE).underlined(true)
        .event(new ClickEvent(ClickEvent.Action.SUGGEST_COMMAND, overwriteCommand))
      .append(" to overwrite the alias", ComponentBuilder.FormatRetention.NONE)
      .create();
  }
  
  // Format an alias removed message
  public BaseComponent[] formatAliasRemovedMessage(NamespacedKey key)
  {
    return new ComponentBuilder()
      .append("The alias ", ComponentBuilder.FormatRetention.NONE)
      .append(key.toString(), ComponentBuilder.FormatRetention.NONE).color(ChatColor.GREEN)
      .append(" has been removed", ComponentBuilder.FormatRetention.NONE)
      .create();
  }
}
