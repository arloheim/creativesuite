package dev.danae.gregocommands.plugin.commands.admin;

import dev.danae.gregocommands.plugin.GregoCommandsPlugin;
import dev.danae.gregocommands.plugin.commands.CommandContext;
import dev.danae.gregocommands.plugin.commands.CommandException;
import dev.danae.gregocommands.plugin.commands.CommandUsageException;
import dev.danae.gregocommands.plugin.commands.PluginCommand;
import java.util.List;


public class AdminVersionCommand extends PluginCommand
{  
  // Constructor
  public AdminVersionCommand(GregoCommandsPlugin plugin)
  {
    super(plugin, "gregocommands.admin");
  }
    
  
  // Handle the command
  @Override
  public void handle(CommandContext context) throws CommandException, CommandUsageException
  {     
    // Send information about the version
    var desc = this.getPlugin().getDescription();
    context.sendMessage(String.format("%s %s (API version %s)", desc.getName(), desc.getVersion(), desc.getAPIVersion()));
  }

  // Handle tab completion of the command
  @Override
  public List<String> handleTabCompletion(CommandContext context)
  {
    return List.of();
  }
}
