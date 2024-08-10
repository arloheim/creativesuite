package dev.danae.gregocommands.plugin.components.charmap;

import dev.danae.gregocommands.plugin.CreativeSuitePluginComponent;
import dev.danae.gregocommands.plugin.CreativeSuitePlugin;
import dev.danae.gregocommands.plugin.commands.charmap.CharmapAddCommand;
import dev.danae.gregocommands.plugin.commands.charmap.CharmapListCommand;
import dev.danae.gregocommands.plugin.commands.charmap.CharmapRemoveCommand;
import dev.danae.gregocommands.util.commands.CommandGroup;
import java.io.File;
import java.util.stream.Collectors;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.hover.content.Text;


public class CharmapComponent extends CreativeSuitePluginComponent
{
  // The charmap
  private Charmap charmap;


  // Constructor
  public CharmapComponent(CreativeSuitePlugin plugin) 
  {
    super(plugin);

    this.charmap = new Charmap(this.getPlugin(), new File(this.getPlugin().getDataFolder(), "charmap.yml"));
  }  
  

  // Load the component
  @Override
  public void loadComponent()
  {
    super.loadComponent();

    // Load the data
    this.charmap.load();
  }

  // Enable the component
  @Override
  public void enableComponent()
  {
    super.enableComponent();

    // Register the commands
    this.registerCommandHandler("charmap", new CommandGroup()
      .registerSubcommand("add", new CharmapAddCommand(this))
      .registerSubcommand("remove", new CharmapRemoveCommand(this))
      .registerEmptySubcommand(new CharmapListCommand(this)));
  }

  
  // Return the charmap
  public Charmap getCharmap()
  {
    return charmap;
  }

  // Format a charmap list message
  public BaseComponent[] formatCharmapListMessage(int columns)
  {
    var builder = new ComponentBuilder(String.format("%d characters are defined", this.charmap.getCodePoints().size()));
    var currentColumn = 0;
    for (var codePoint : this.charmap.getCodePointsAsStrings())
    {
      if (currentColumn == 0)
        builder.append("\n  ");
      currentColumn = (currentColumn + 1) % columns;

      builder
        .append(codePoint, ComponentBuilder.FormatRetention.NONE).color(ChatColor.GREEN)
          .event(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new Text(String.format("Click to copy \"%s\" to clipboard", codePoint))))
          .event(new ClickEvent(ClickEvent.Action.COPY_TO_CLIPBOARD, codePoint))
        .append(" ", ComponentBuilder.FormatRetention.NONE);
    }

    return builder.create();
  }

  // Format a charmap added message
  public BaseComponent[] formatCharmapAddedMessage(String codePoints)
  {
    var characters = codePoints.codePoints().boxed()
      .sorted()
      .map(Charmap::codePointToString)
      .collect(Collectors.joining(" "));

    return new ComponentBuilder()
      .append("The characters ", ComponentBuilder.FormatRetention.NONE)
      .append(characters, ComponentBuilder.FormatRetention.NONE).color(ChatColor.GREEN)
      .append(" have been added to the charmap", ComponentBuilder.FormatRetention.NONE)
      .create();
  }

  // Format a charmap removed message
  public BaseComponent[] formatCharmapRemovedMessage(String codePoints)
  {
    var characters = codePoints.codePoints().boxed()
      .sorted()
      .map(Charmap::codePointToString)
      .collect(Collectors.joining(" "));

    return new ComponentBuilder()
      .append("The characters ", ComponentBuilder.FormatRetention.NONE)
      .append(characters, ComponentBuilder.FormatRetention.NONE).color(ChatColor.GREEN)
      .append(" have been removed from the charmap", ComponentBuilder.FormatRetention.NONE)
      .create();
  }
}
