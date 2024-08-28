package dev.danae.commons.commands;

import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.TextComponent;


public class CommandException extends Exception
{
  // The message of the exception
  private final BaseComponent[] componentMessage;


  // Constructor for a chat component message and cause
  public CommandException(BaseComponent[] componentMessage, Throwable cause)
  {
    super(TextComponent.toPlainText(componentMessage), cause);

    this.componentMessage = componentMessage;
  }

  // Constructor for a chat component message
  public CommandException(BaseComponent[] componentMessage)
  {
    super(TextComponent.toPlainText(componentMessage));

    this.componentMessage = componentMessage;
  }

  // Constructor for a message and cause
  public CommandException(String message, Throwable cause)
  {
    super(message, cause);

    this.componentMessage = new ComponentBuilder(message).color(ChatColor.RED).create();
  }

  // Constructor for a message
  public CommandException(String message)
  {
    super(message);

    this.componentMessage = new ComponentBuilder(message).color(ChatColor.RED).create();
  }


  // Return the message of the exception
  public BaseComponent[] getComponentMessage()
  {
    return this.componentMessage;
  }
}
