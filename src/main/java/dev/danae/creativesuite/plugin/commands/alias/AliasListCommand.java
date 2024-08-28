package dev.danae.creativesuite.plugin.commands.alias;

import dev.danae.commons.commands.CommandContext;
import dev.danae.commons.commands.CommandException;
import dev.danae.commons.commands.CommandUsageException;
import dev.danae.creativesuite.model.Manager;
import dev.danae.creativesuite.plugin.commands.ManagerCommand;
import java.util.List;


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
    context.sendMessage(AliasFormatter.formatAliasListMessage(this.getManager().getDefinedAliases()));
  }

  // Handle tab completion of the command
  @Override
  public List<String> handleTabCompletion(CommandContext context)
  {
    return List.of();
  }
}
