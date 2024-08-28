package dev.danae.creativesuite.plugin.commands.alias;

import dev.danae.commons.commands.CommandContext;
import dev.danae.commons.commands.CommandException;
import dev.danae.commons.commands.CommandUsageException;
import dev.danae.commons.messages.MessageFunction;
import dev.danae.commons.messages.NamespacedKeyFormatter;
import dev.danae.creativesuite.model.Manager;
import dev.danae.creativesuite.plugin.commands.ManagerCommand;
import java.util.List;
import java.util.Map;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.hover.content.Text;


public class AliasListCommand extends ManagerCommand
{
  // Constant for the page size
  public static int PAGE_SIZE = 20;


  // Constructor
  public AliasListCommand(Manager manager)
  {
    super(manager, "creativesuite.alias.list");
  }
    
  
  // Handle the command
  @Override
  public void handle(CommandContext context) throws CommandException, CommandUsageException
  {
    // Validate the number of arguments
    if (!context.hasArgumentsCount(0))
      throw new CommandUsageException();
    
    // Send a message listing the hotbars
    context.sendMessage(this.getManager().formatMessage("alias-list", Map.of("count", this.getManager().getDefinedAliases().size(), "aliases", this.createListComponent(context))));
  }

  // Handle tab completion of the command
  @Override
  public List<String> handleTabCompletion(CommandContext context)
  {
    return List.of();
  }


  // Create a list component
  private MessageFunction createListComponent(CommandContext context)
  {
    return (String content) -> {
      return NamespacedKeyFormatter.formatGroupedKeys(this.getManager().getDefinedAliases().keySet(), 
        group -> {
          return new ComponentBuilder()
            .append(group).bold(true)
            .append("").bold(false)
            .create();
        }, 
        (key, name) -> {
          var runCommand = String.format("/%s run %s", context.getCommand().getName(), key.toString());
          return new ComponentBuilder()
            .append(name).underlined(true)
              .event(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new Text(runCommand)))
              .event(new ClickEvent(ClickEvent.Action.SUGGEST_COMMAND, runCommand))
            .append("").underlined(false)
            .create();
        });
    };
  }
}
