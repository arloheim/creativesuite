package dev.danae.creativesuite.plugin.commands.admin;

import dev.danae.creativesuite.model.Manager;
import dev.danae.creativesuite.plugin.CreativeSuitePlugin;
import dev.danae.creativesuite.plugin.commands.ManagerCommand;
import dev.danae.creativesuite.util.commands.CommandContext;
import dev.danae.creativesuite.util.commands.CommandException;
import dev.danae.creativesuite.util.commands.CommandUsageException;
import java.util.List;


public class AdminVersionCommand extends ManagerCommand
{  
  // The plugin of the command
  private final CreativeSuitePlugin plugin;


  // Constructor
  public AdminVersionCommand(Manager manager, CreativeSuitePlugin plugin)
  {
    super(manager, "creativesuite.admin");

    this.plugin = plugin;
  }
    
  
  // Handle the command
  @Override
  public void handle(CommandContext context) throws CommandException, CommandUsageException
  {     
    // Send information about the version
    var desc = this.plugin.getDescription();
    context.sendMessage(String.format("%s %s (API version %s)", desc.getName(), desc.getVersion(), desc.getAPIVersion()));
  }

  // Handle tab completion of the command
  @Override
  public List<String> handleTabCompletion(CommandContext context)
  {
    return List.of();
  }
}
