package dev.danae.commons.commands;


public class CommandUsageException extends Exception
{
  // Constructor for a cause
  public CommandUsageException(Throwable cause)
  {
    super(cause);
  }

  // Constructor
  public CommandUsageException()
  {
    super();
  }
}
