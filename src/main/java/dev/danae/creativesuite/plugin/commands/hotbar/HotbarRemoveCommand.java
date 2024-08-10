package dev.danae.creativesuite.plugin.commands.hotbar;

import dev.danae.creativesuite.plugin.commands.PluginComponentCommand;
import dev.danae.creativesuite.plugin.components.commands.HotbarComponent;
import dev.danae.creativesuite.util.parser.ParserException;
import dev.danae.creativesuite.util.commands.CommandContext;
import dev.danae.creativesuite.util.commands.CommandException;
import dev.danae.creativesuite.util.commands.CommandUsageException;
import java.util.List;


public class HotbarRemoveCommand extends PluginComponentCommand<HotbarComponent>
{
  // Constructor
  public HotbarRemoveCommand(HotbarComponent component)
  {
    super(component, "creativesuite.hotbar.remove");
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
