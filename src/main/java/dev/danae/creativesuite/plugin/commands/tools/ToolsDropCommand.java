package dev.danae.creativesuite.plugin.commands.tools;

import dev.danae.commons.Locations;
import dev.danae.commons.Materials;
import dev.danae.commons.commands.CommandContext;
import dev.danae.commons.commands.CommandException;
import dev.danae.commons.commands.CommandUsageException;
import dev.danae.commons.parser.ParserException;
import dev.danae.creativesuite.model.Manager;
import dev.danae.creativesuite.plugin.commands.ManagerCommand;
import java.util.List;
import java.util.Optional;
import org.bukkit.GameMode;
import org.bukkit.Material;


public class ToolsDropCommand extends ManagerCommand
{
  // The predefined material to use
  private final Optional<Material> material;


  // Constructor
  private ToolsDropCommand(Manager manager, Optional<Material> material)
  {
    super(manager, "creativesuite.tools.drop");

    if (material.isPresent() && !Materials.matches(material.get(), Materials.Filter.GRAVITY_BLOCKS))
      throw new IllegalArgumentException(String.format("%s is not a valid gravity block material", material.get().getKey()));

    this.material = material;
  }

  // Constructor with a predefined material
  public ToolsDropCommand(Manager manager, Material material)
  {
    this(manager, Optional.of(material));
  }

  // Constructor without a predefined material
  public ToolsDropCommand(Manager manager)
  {
    this(manager, Optional.empty());
  }
    
  
  // Handle the command
  @Override
  public void handle(CommandContext context) throws CommandException, CommandUsageException
  {     
    try
    {
      // Assert that the command sender is a player and in creative mode
      var player = context.assertSenderIsPlayer(GameMode.CREATIVE);

      // Validate the number of arguments
      if (!context.hasAtLeastArgumentsCount(this.material.isEmpty() ? 2 : 1))
        throw new CommandUsageException();
      
      // Create a scanner for the arguments
      var scanner = context.getArgumentsScanner();
      
      // Parse the arguments
      var material = this.material.isPresent() ? this.material.get() : scanner.nextMaterial(Materials.Filter.GRAVITY_BLOCKS);
      var location = scanner.nextLocation(player.getLocation());

      // Drop a gravity-affected block at the location
      this.getManager().drop(material, location);
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
    if (this.material.isPresent())
    {
      if (context.hasAtLeastArgumentsCount(1))
        return Locations.handleTabCompletion(context, 0);
      else
        return List.of();
    }
    else
    {
      if (context.hasAtLeastArgumentsCount(2))
        return Locations.handleTabCompletion(context, 1);
      else if (context.hasArgumentsCount(1))
        return Materials.handleTabCompletion(context.getArgument(0), Materials.Filter.GRAVITY_BLOCKS);
      else
        return List.of();
    }
  }
}
