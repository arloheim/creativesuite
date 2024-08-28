package dev.danae.creativesuite.plugin.commands.charmap;

import dev.danae.commons.commands.CommandContext;
import dev.danae.commons.commands.CommandException;
import dev.danae.commons.commands.CommandUsageException;
import dev.danae.commons.parser.ParserException;
import dev.danae.creativesuite.model.Manager;
import dev.danae.creativesuite.model.charmap.Charmap;
import dev.danae.creativesuite.plugin.commands.ManagerCommand;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


public class CharmapAddCommand extends ManagerCommand
{
  // Constructor
  public CharmapAddCommand(Manager manager)
  {
    super(manager, "creativesuite.charmap.add");
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
      var codePoints = scanner.next();

      // Add the code points to the charmap
      this.getManager().addToCharmap(codePoints);

      // Send a message about the added code points
      context.sendMessage(this.formatMessage("charmap-added", Map.of("chars", codePoints.codePoints().boxed().sorted().map(Charmap::codePointToString).collect(Collectors.joining(" ")))));
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
    return List.of();
  }
}
