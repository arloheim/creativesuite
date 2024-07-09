package dev.danae.gregocommands.model.hotbar;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.bukkit.configuration.serialization.ConfigurationSerializable;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

public class Hotbar implements ConfigurationSerializable
{
  // Constants that defines the index and size of the quickbar
  public static int QUICKBAR_INDEX = 0;
  public static int QUICKBAR_SIZE = 9;


  // The items of the hotbar
  private final ItemStack[] items;


  // Constructor for an array of items
  public Hotbar(ItemStack[] items)
  {
    this.items = items;
  }

  // Constructor for a player inventory
  public Hotbar(PlayerInventory inventory)
  {
    this(new ItemStack[QUICKBAR_SIZE]);
    for (var i = 0; i < QUICKBAR_INDEX + QUICKBAR_SIZE; i ++)
      this.items[i] = inventory.getItem(QUICKBAR_INDEX + i);
  }
  

  // Apply the hotbar to a player inventory
  public void applyTo(PlayerInventory inventory)
  {
    for (var i = 0; i < QUICKBAR_INDEX + QUICKBAR_SIZE; i ++)
      inventory.setItem(QUICKBAR_INDEX + i, this.items[i]);
  }


  // Serialize the hotbar to a map representation
  public Map<String, Object> serialize()
  {
    var map = new HashMap<String, Object>();
    map.put("items", this.items);
    return map;
  }
  
  // Deserialize a map representation to a hotbar
  public static Hotbar deserialize(Map<String, Object> map)
  {
    var items = new ItemStack[QUICKBAR_SIZE];
    var serializedItems = (List<Object>)map.getOrDefault("items", new ArrayList<Object>());
    for (var i = 0; i < Math.min(serializedItems.size(), QUICKBAR_SIZE); i ++)
      items[i] = (ItemStack)serializedItems.get(i);    
    return new Hotbar(items);
  }
}
