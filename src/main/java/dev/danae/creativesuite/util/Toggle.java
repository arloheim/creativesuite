package dev.danae.creativesuite.util;


public enum Toggle 
{
  OFF,
  ON,
  TOGGLE;


  // Convert the toggle to a boolean
  public boolean toBoolean(boolean toggle)
  {
    if (this == Toggle.OFF)
      return false;
    else if (this == Toggle.ON)
      return true;
    else
      return !toggle;
  }
}
