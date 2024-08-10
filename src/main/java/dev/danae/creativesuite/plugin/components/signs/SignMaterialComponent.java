package dev.danae.creativesuite.plugin.components.signs;

import dev.danae.creativesuite.plugin.CreativeSuitePlugin;
import dev.danae.creativesuite.plugin.CreativeSuitePluginComponent;
import java.util.List;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.block.Sign;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.Sound;
import org.bukkit.SoundCategory;
import org.bukkit.block.sign.Side;
import org.bukkit.block.sign.SignSide;


public class SignMaterialComponent extends CreativeSuitePluginComponent
{
  // List containing all sign materials
  private static final List<Material> SIGN_MATERIALS = List.of(
    Material.OAK_SIGN,
    Material.SPRUCE_SIGN,
    Material.BIRCH_SIGN,
    Material.JUNGLE_SIGN,
    Material.ACACIA_SIGN,
    Material.DARK_OAK_SIGN,
    Material.MANGROVE_SIGN,
    Material.CHERRY_SIGN,
    Material.BAMBOO_SIGN,
    Material.CRIMSON_SIGN,
    Material.WARPED_SIGN
  );
  
  // List containing all wall sign materials
  private static final List<Material> WALL_SIGN_MATERIALS = List.of(
    Material.OAK_WALL_SIGN,
    Material.SPRUCE_WALL_SIGN,
    Material.BIRCH_WALL_SIGN,
    Material.JUNGLE_WALL_SIGN,
    Material.ACACIA_WALL_SIGN,
    Material.DARK_OAK_WALL_SIGN,
    Material.MANGROVE_WALL_SIGN,
    Material.CHERRY_WALL_SIGN,
    Material.BAMBOO_WALL_SIGN,
    Material.CRIMSON_WALL_SIGN,
    Material.WARPED_WALL_SIGN
  );
  
  // List containing all hanging sign materials
  private static final List<Material> HANGING_SIGN_MATERIALS = List.of(
    Material.OAK_HANGING_SIGN,
    Material.SPRUCE_HANGING_SIGN,
    Material.BIRCH_HANGING_SIGN,
    Material.JUNGLE_HANGING_SIGN,
    Material.ACACIA_HANGING_SIGN,
    Material.DARK_OAK_HANGING_SIGN,
    Material.MANGROVE_HANGING_SIGN,
    Material.CHERRY_HANGING_SIGN,
    Material.BAMBOO_HANGING_SIGN,
    Material.CRIMSON_HANGING_SIGN,
    Material.WARPED_HANGING_SIGN
  );
  
  // List containing all wall hanging sign materials
  private static final List<Material> WALL_HANGING_SIGN_MATERIALS = List.of(
    Material.OAK_WALL_HANGING_SIGN,
    Material.SPRUCE_WALL_HANGING_SIGN,
    Material.BIRCH_WALL_HANGING_SIGN,
    Material.JUNGLE_WALL_HANGING_SIGN,
    Material.ACACIA_WALL_HANGING_SIGN,
    Material.DARK_OAK_WALL_HANGING_SIGN,
    Material.MANGROVE_WALL_HANGING_SIGN,
    Material.CHERRY_WALL_HANGING_SIGN,
    Material.BAMBOO_WALL_HANGING_SIGN,
    Material.CRIMSON_WALL_HANGING_SIGN,
    Material.WARPED_WALL_HANGING_SIGN
  );
 
  
  // Constructor
  public SignMaterialComponent(CreativeSuitePlugin plugin)
  {
    super(plugin);
  }

  
  // Handler for when the player interacts
  @EventHandler
  public void onPlayerInteract(PlayerInteractEvent e)
  {
    // Check if the player is in creative mode
    if (e.getPlayer().getGameMode() != GameMode.CREATIVE)
      return;
    
    // Check if it is a right click interaction
    if (e.getAction() != Action.RIGHT_CLICK_BLOCK)
      return;
    
    // Check if there is a block clicked
    var block = e.getClickedBlock();
    if (block == null)
      return;
    
    // Check if it is an interaction with a sign
    var blockState = block.getState();
    if (!(blockState instanceof Sign sign))
      return;

    // Check with which item the sign is clicked
    var item = e.getItem();
    if (item != null)
    {
      // Replace the sign material
      var newMaterial = getReplacementMaterial(block.getType(), item.getType());
      if (newMaterial != null)
      {
        // Set the block data
        var blockData = block.getBlockData();
        if (blockData instanceof org.bukkit.block.data.type.Sign signData)
        {
          var newSignData = (org.bukkit.block.data.type.Sign)Bukkit.createBlockData(newMaterial);
          newSignData.setRotation(signData.getRotation());
          newSignData.setWaterlogged(signData.isWaterlogged());

          block.setBlockData(newSignData);
        }
        else if (blockData instanceof org.bukkit.block.data.type.HangingSign hangingSignData)
        {
          var newHangingSignData = (org.bukkit.block.data.type.HangingSign)Bukkit.createBlockData(newMaterial);
          newHangingSignData.setAttached(hangingSignData.isAttached());
          newHangingSignData.setRotation(hangingSignData.getRotation());
          newHangingSignData.setWaterlogged(hangingSignData.isWaterlogged());

          block.setBlockData(newHangingSignData);
        }
        else if (blockData instanceof org.bukkit.block.data.type.WallSign wallSignData)
        {
          var newWallSignData = (org.bukkit.block.data.type.WallSign)Bukkit.createBlockData(newMaterial);
          newWallSignData.setFacing(wallSignData.getFacing());
          newWallSignData.setWaterlogged(wallSignData.isWaterlogged());

          block.setBlockData(newWallSignData);
        }
        else if (blockData instanceof org.bukkit.block.data.type.WallHangingSign wallHangingSignData)
        {
          var newWallHangingSignData = (org.bukkit.block.data.type.WallHangingSign)Bukkit.createBlockData(newMaterial);
          newWallHangingSignData.setFacing(wallHangingSignData.getFacing());
          newWallHangingSignData.setWaterlogged(wallHangingSignData.isWaterlogged());

          block.setBlockData(newWallHangingSignData);
        }

        // Set the block state
        var newSign = (Sign)block.getState();
        SignMaterialComponent.copySign(sign, newSign);
        newSign.update();
        
        // Play a sound to indicate the change
        e.getPlayer().playSound(e.getPlayer(), Sound.BLOCK_WOOD_HIT, SoundCategory.BLOCKS, 1.0f, 1.0f);

        // Cancel the event
        e.setCancelled(true);
      }
    }
  }
  
  
  // Get the material to replace the block material with
  private static Material getReplacementMaterial(Material blockMaterial, Material itemMaterial)
  {
    if (SIGN_MATERIALS.contains(blockMaterial))
    {
      if (SIGN_MATERIALS.contains(itemMaterial))
        return itemMaterial;
      else if (HANGING_SIGN_MATERIALS.contains(itemMaterial))
        return getMatchingMaterial(itemMaterial, HANGING_SIGN_MATERIALS, SIGN_MATERIALS);
      else
        return null;
    }
    else if (WALL_SIGN_MATERIALS.contains(blockMaterial))
    {
      if (SIGN_MATERIALS.contains(itemMaterial))
        return getMatchingMaterial(itemMaterial, SIGN_MATERIALS, WALL_SIGN_MATERIALS);
      else if (HANGING_SIGN_MATERIALS.contains(itemMaterial))
        return getMatchingMaterial(itemMaterial, HANGING_SIGN_MATERIALS, WALL_SIGN_MATERIALS);
      else
        return null;
    }
    else if (HANGING_SIGN_MATERIALS.contains(blockMaterial))
    {
      if (HANGING_SIGN_MATERIALS.contains(itemMaterial))
        return itemMaterial;
      else if (SIGN_MATERIALS.contains(itemMaterial))
        return getMatchingMaterial(itemMaterial, SIGN_MATERIALS, HANGING_SIGN_MATERIALS);
      else
        return null;
    }
    else if (WALL_HANGING_SIGN_MATERIALS.contains(blockMaterial))
    {
      if (HANGING_SIGN_MATERIALS.contains(itemMaterial))
        return getMatchingMaterial(itemMaterial, HANGING_SIGN_MATERIALS, WALL_HANGING_SIGN_MATERIALS);
      else if (SIGN_MATERIALS.contains(itemMaterial))
        return getMatchingMaterial(itemMaterial, SIGN_MATERIALS, WALL_HANGING_SIGN_MATERIALS);
      else
        return null;
    }
    else
    {
      return null;
    }
  }
  
  // Return the wall sign material for a sign material
  private static Material getMatchingMaterial(Material material, List<Material> sourceList, List<Material> returnList)
  {
    int index = sourceList.indexOf(material);
    return index > -1 ? returnList.get(index) : null;
  }
  
  // Copy the state of a sign
  private static void copySign(Sign sign, Sign newSign)
  {
    copySignSide(sign.getSide(Side.FRONT), newSign.getSide(Side.FRONT));
    copySignSide(sign.getSide(Side.BACK), newSign.getSide(Side.BACK));
  }
  
  // Copy the state of a sign side
  private static void copySignSide(SignSide side, SignSide newSide)
  {
    newSide.setColor(side.getColor());
    newSide.setGlowingText(side.isGlowingText());
    for (int i = 0; i < 4; i++)
      newSide.setLine(i, side.getLine(i));
  }
}
