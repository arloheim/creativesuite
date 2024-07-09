package dev.danae.gregocommands.plugin.commands.admin;

import dev.danae.gregocommands.plugin.GregoCommandsPlugin;
import dev.danae.gregocommands.plugin.commands.CommandContext;
import dev.danae.gregocommands.plugin.commands.CommandException;
import dev.danae.gregocommands.plugin.commands.CommandUsageException;
import dev.danae.gregocommands.plugin.commands.PluginCommand;
import java.util.List;


public class AdminReloadCommand extends PluginCommand
{  
  
  // Constructor
  public AdminReloadCommand(GregoCommandsPlugin plugin)
  {
    super(plugin, "gregocommands.admin");
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
