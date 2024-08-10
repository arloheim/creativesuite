package dev.danae.gregocommands.plugin.commands.alias;

import dev.danae.gregocommands.plugin.commands.PluginComponentCommand;
import dev.danae.gregocommands.plugin.components.alias.AliasComponent;
import dev.danae.gregocommands.util.commands.CommandContext;
import dev.danae.gregocommands.util.commands.CommandException;
import dev.danae.gregocommands.util.commands.CommandUsageException;
import java.util.List;


public class AliasListCommand extends PluginComponentCommand<AliasComponent>
{
  // Constant for the page size
  public static int PAGE_SIZE = 20;


  // Constructor
  public AliasListCommand(AliasComponent component)
  {
    super(component, "creativesuite.alias.list");
  }
    
  
  // Handle the command
  @Override
  public void handle(CommandContext context) throws CommandException, CommandUsageException
  {
    // Validate the number of arguments
    if (!context.hasArgumentsCount(0))
      throw new CommandUsageException();
    
    // Send a message listing the hotbars
    context.sendMessage(this.getComponent().formatAliasListMessage());
  }

  // Handle tab completion of the command
  @Override
  public List<String> handleTabCompletion(CommandContext context)
  {
    return List.of();
  }
}
