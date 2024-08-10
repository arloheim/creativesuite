package dev.danae.gregocommands.plugin.commands.hotbar;

import dev.danae.gregocommands.plugin.commands.PluginComponentCommand;
import dev.danae.gregocommands.plugin.components.hotbar.HotbarComponent;
import dev.danae.gregocommands.util.parser.ParserException;
import dev.danae.gregocommands.util.commands.CommandContext;
import dev.danae.gregocommands.util.commands.CommandException;
import dev.danae.gregocommands.util.commands.CommandUsageException;
import java.util.List;


public class HotbarRemoveCommand extends PluginComponentCommand<HotbarComponent>
{
  // Constructor
  public HotbarRemoveCommand(HotbarComponent component)
  {
    super(component, "gregocommands.hotbar.remove");
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
      var existingKey = this.getComponent().getHotbars().containsKey(key);
      if (!existingKey)
        throw new CommandException(String.format("Hotbar %s does not exist", key.toString()));

      // Remove the hotbar
      this.getComponent().getHotbars().remove(key);

      // Send a message about the removed hotbar
      context.sendMessage(this.getComponent().formatHotbarRemovedMessage(key));
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
      return this.getComponent().handleHotbarTabCompletion(context.getArgument(0));
    else
      return List.of();
  }
}
