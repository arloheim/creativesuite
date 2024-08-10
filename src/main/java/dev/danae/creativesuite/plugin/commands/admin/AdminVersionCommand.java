package dev.danae.gregocommands.plugin.commands.admin;

import dev.danae.gregocommands.plugin.CreativeSuitePlugin;
import dev.danae.gregocommands.plugin.commands.PluginCommand;
import dev.danae.gregocommands.util.commands.CommandContext;
import dev.danae.gregocommands.util.commands.CommandException;
import dev.danae.gregocommands.util.commands.CommandUsageException;
import java.util.List;


public class AdminVersionCommand extends PluginCommand
{  
  // Constructor
  public AdminVersionCommand(CreativeSuitePlugin plugin)
  {
    super(plugin, "creativesuite.admin");
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
