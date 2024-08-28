package dev.danae.creativesuite.plugin.commands.alias;

import dev.danae.commons.commands.CommandContext;
import dev.danae.commons.commands.CommandException;
import dev.danae.commons.commands.CommandUsageException;
import dev.danae.commons.messages.MessageFunction;
import dev.danae.commons.parser.ParserException;
import dev.danae.creativesuite.model.Alias;
import dev.danae.creativesuite.model.Manager;
import dev.danae.creativesuite.plugin.commands.ManagerCommand;
import java.util.List;
import java.util.Map;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.hover.content.Text;
import org.bukkit.NamespacedKey;


public class AliasSaveCommand extends ManagerCommand
{
  // Boolean that indicates if aliases automatically get overwritten
  private final boolean overwriteAliases;

  // Constructor
  public AliasSaveCommand(Manager manager, boolean overwriteAliases)
  {
    super(manager, "creativesuite.alias.save");

    this.overwriteAliases = overwriteAliases;
  }
    
  
  // Handle the command
  @Override
  public void handle(CommandContext context) throws CommandException, CommandUsageException
  {
    try
    {
      // Validate the number of arguments
      if (!context.hasAtLeastArgumentsCount(2))
        throw new CommandUsageException();
      
      // Create a scanner for the arguments
      var scanner = context.getArgumentsScanner();
      
      // Parse the arguments
      var key = scanner.nextNamespacedKey();
      var command = scanner.rest("command");
      
      var alias = this.getManager().getAlias(key);

      // Check if we can overwrite and existing alias
      if (!this.overwriteAliases && alias != null)
      {
        // Send a message about the otherwise overwritten alias
        context.sendMessage(this.formatMessage("alias-cannot-save", Map.of("name", key, "overwrite", this.getOverwriteComponent(context, key, command))));
      }
      else
      {
        // Save the alias
        this.getManager().setAlias(key, new Alias(command));

        // Send a message about the saved alias
        context.sendMessage(this.formatMessage(alias != null ? "alias-overwritten" : "alias-saved", Map.of("name", key)));
      }
    }
    catch (ParserException ex)
    {
      throw new CommandException(ex.getMessage(), ex);
    }
  }
  
  // Handle tab completion of the command
  @Override
  public List<String> handleTabCompletion(CommandContext context)
  {
    if (context.hasArgumentsCount(1))
      return this.handleAliasTabCompletion(context.getArgument(0));
    else
      return List.of();
  }


  // Create an overwrite command chat component
  private MessageFunction getOverwriteComponent(CommandContext context, NamespacedKey key, String command)
  {
    var overwriteCommand = String.format("/%s overwrite %s %s", context.getCommand().getName(), key.toString(), command);

    return (String content) -> {
      return new ComponentBuilder()
        .append(content, ComponentBuilder.FormatRetention.NONE).underlined(true)
          .event(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new Text(overwriteCommand)))
          .event(new ClickEvent(ClickEvent.Action.SUGGEST_COMMAND, overwriteCommand))
        .create();
    };
  }
}
