package dev.danae.creativesuite.plugin.commands.alias;

import dev.danae.commons.commands.CommandContext;
import dev.danae.commons.commands.CommandException;
import dev.danae.commons.commands.CommandUsageException;
import dev.danae.commons.parser.Parser;
import dev.danae.commons.parser.ParserException;
import dev.danae.commons.parser.Scanner;
import dev.danae.creativesuite.model.Manager;
import dev.danae.creativesuite.model.alias.Parameter;
import dev.danae.creativesuite.plugin.commands.ManagerCommand;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class AliasRunCommand extends ManagerCommand
{
  // Constructor
  public AliasRunCommand(Manager manager)
  {
    super(manager, "creativesuite.alias.run");
  }
    
  
  // Handle the command
  @Override
  public void handle(CommandContext context) throws CommandException, CommandUsageException
  {
    try
    {
      // Validate the number of arguments
      if (!context.hasAtLeastArgumentsCount(1))
        throw new CommandUsageException();
      
      // Create a scanner for the arguments
      var scanner = context.getArgumentsScanner();
      
      // Parse the arguments
      var key = scanner.nextNamespacedKey();
      
      var alias = this.getManager().getAlias(key);
      if (alias == null)
        throw new CommandException(this.formatMessage("alias-not-found", Map.of("name", key)));

      var args = this.parseArguments(scanner);

      // Dispatch the command of the alias
      alias.dispatchCommand(context.getSender(), args);
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
    if (context.hasAtLeastArgumentsCount(2))
      return this.handleAliasParameterTabCompletion(context.getLastArgument(0), context.getArgument(0));
    if (context.hasArgumentsCount(1))
      return this.handleAliasTabCompletion(context.getArgument(0));
    else
      return List.of();
  }


  // Parse a list of arguments from the specified scanner
  private Map<String, String> parseArguments(Scanner scanner) throws ParserException
  {
    var map = new HashMap<String, String>();

    while (!scanner.isAtEnd())
    {
      var e = scanner.take(this::parseArgument , "argument");
      map.put(e.getKey(), e.getValue());
    }

    return map;
  }

  // Parse an argument from the specified string
  private Map.Entry<String, String> parseArgument(String string) throws ParserException
  {
    var components = string.split(Parameter.SEPARATOR, 2);
    if (components.length != 2)
      throw new ParserException(String.format("\"%s\" is an invalid argument value", string));

    var name = Parser.parseIdentifier(components[0]);
    var value = components[1];
    return Map.entry(name, value);
  }
}
