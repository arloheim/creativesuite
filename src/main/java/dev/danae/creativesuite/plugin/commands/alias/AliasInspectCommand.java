package dev.danae.creativesuite.plugin.commands.alias;

import dev.danae.commons.commands.CommandContext;
import dev.danae.commons.commands.CommandException;
import dev.danae.commons.commands.CommandUsageException;
import dev.danae.commons.messages.MessageFunction;
import dev.danae.commons.parser.ParserException;
import dev.danae.creativesuite.model.Manager;
import dev.danae.creativesuite.model.alias.Alias;
import dev.danae.creativesuite.model.alias.Parameter;
import dev.danae.creativesuite.plugin.commands.ManagerCommand;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.chat.hover.content.Text;


public class AliasInspectCommand extends ManagerCommand
{
  // Constructor
  public AliasInspectCommand(Manager manager)
  {
    super(manager, "creativesuite.alias.inspect");
  }
    
  
  // Handle the command
  @Override
  public void handle(CommandContext context) throws CommandException, CommandUsageException
  {
    try
    {
      // Validate the number of arguments
      if (!context.hasArgumentsCount(1))
        throw new CommandUsageException();
      
      // Create a scanner for the arguments
      var scanner = context.getArgumentsScanner();
      
      // Parse the arguments
      var key = scanner.nextNamespacedKey();
      
      var alias = this.getManager().getAlias(key);
      if (alias == null)
        throw new CommandException(this.formatMessage("alias-not-found", Map.of("name", key)));

      // Send a message about the inspected alias
      context.sendMessage(this.formatMessage("alias-inspect", Map.of("name", key, "command", this.createCommandComponent(alias))));
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


  // Create a command component
  private MessageFunction createCommandComponent(Alias alias)
  {
    return (String content) -> {
      var formattedCommand = "/" + Parameter.replace(alias.getCommand(), createParameterFormatter(content));
      var command = "/" + Parameter.replace(alias.getCommand(), Map.of());

      return new ComponentBuilder()
        .append("")
          .event(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new Text(this.formatMessage("alias-copy-to-clipboard", Map.of("command", command)))))
          .event(new ClickEvent(ClickEvent.Action.COPY_TO_CLIPBOARD, command))
        .append(TextComponent.fromLegacyText(formattedCommand))
        .create();
    };
  }

  // Format a parameter
  private Function<Parameter, String> createParameterFormatter(String content)
  {
    return (Parameter param) -> {
      if (content != null && !content.isEmpty())
        return String.format(content, param.toCommandString());
      else 
        return param.toCommandString();
    };
  }
}
