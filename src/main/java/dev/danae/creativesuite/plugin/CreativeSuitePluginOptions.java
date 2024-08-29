package dev.danae.creativesuite.plugin;


public class CreativeSuitePluginOptions
{
  // The hotbar to fill the inventory with when clearing using the clearfill tool
  private String clearfillToolHotbar = null;

  // Should elytra be equipped when clearing using the clearfill tool?
  private boolean clearfillToolElytraAdded = true;

  // The relative height in blocks from which to drop a block using the drop tool
  private int dropToolRelativeHeight = 5;


  // Return the hotbar to fill the inventory with when clearing using the clearfill tool
  public String getClearfillToolHotbar()
  {
    return this.clearfillToolHotbar;
  }

  // Set the hotbar to fill the inventory with when clearing using the clearfill tool
  public void setClearfillToolHotbar(String clearfillToolHotbar)
  {
    this.clearfillToolHotbar = clearfillToolHotbar;
  }

  // Return if elytra should be equipped when clearing using the clearfill tool
  public boolean isClearfillToolElytraAdded()
  {
    return this.clearfillToolElytraAdded;
  }

  // Set if elytra should be equipped when clearing using the clearfill tool
  public void setClearfillToolElytraAdded(boolean clearfillToolElytraAdded)
  {
    this.clearfillToolElytraAdded = clearfillToolElytraAdded;
  }

  // Return the relative height in blocks from which to drop a block using the drop tool
  public int getDropToolRelativeHeight()
  {
    return this.dropToolRelativeHeight;
  }

  // Set the relative height in blocks from which to drop a block using the drop tool
  public void setDropToolRelativeHeight(int dropToolRelativeHeight)
  {
    this.dropToolRelativeHeight = dropToolRelativeHeight;
  }
}
