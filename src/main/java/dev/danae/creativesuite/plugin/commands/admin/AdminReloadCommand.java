package dev.danae.gregocommands.plugin.commands.admin;

import dev.danae.gregocommands.plugin.CreativeSuitePlugin;
import dev.danae.gregocommands.plugin.commands.PluginCommand;
import dev.danae.gregocommands.util.commands.CommandContext;
import dev.danae.gregocommands.util.commands.CommandException;
import dev.danae.gregocommands.util.commands.CommandUsageException;
import java.util.List;


public class AdminReloadCommand extends PluginCommand
{  
  // Constructor
  public AdminReloadCommand(CreativeSuitePlugin plugin)
  {
    super(plugin, "creativesuite.admin");
  }
    
  
  // Handle the command
  @Override
  public void handle(CommandContext context) throws CommandException, CommandUsageException
  {     
    // Reload the plugin
    this.getPlugin().loadPlugin();
    
    // Send information about the reload
    context.sendMessage(String.format("Reloaded %s", this.getPlugin().getDescription().getName()));
  }

  // Handle tab completion of the command
  @Override
  public List<String> handleTabCompletion(CommandContext context)
  {
    return List.of();
  }
}
