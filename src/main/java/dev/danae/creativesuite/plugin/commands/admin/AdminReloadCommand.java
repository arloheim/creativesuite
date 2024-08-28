package dev.danae.creativesuite.plugin.commands.admin;

import dev.danae.commons.commands.CommandContext;
import dev.danae.commons.commands.CommandException;
import dev.danae.commons.commands.CommandUsageException;
import dev.danae.creativesuite.model.Manager;
import dev.danae.creativesuite.plugin.CreativeSuitePlugin;
import dev.danae.creativesuite.plugin.commands.ManagerCommand;
import java.util.List;


public class AdminReloadCommand extends ManagerCommand
{  
  // The plugin of the command
  private final CreativeSuitePlugin plugin;


  // Constructor
  public AdminReloadCommand(Manager manager, CreativeSuitePlugin plugin)
  {
    super(manager, "creativesuite.admin");

    this.plugin = plugin;
  }
    
  
  // Handle the command
  @Override
  public void handle(CommandContext context) throws CommandException, CommandUsageException
  {     
    // Reload the plugin
    this.plugin.loadPlugin();
    
    // Send information about the reload
    context.sendMessage(String.format("Reloaded %s", this.plugin.getDescription().getName()));
  }

  // Handle tab completion of the command
  @Override
  public List<String> handleTabCompletion(CommandContext context)
  {
    return List.of();
  }
}
