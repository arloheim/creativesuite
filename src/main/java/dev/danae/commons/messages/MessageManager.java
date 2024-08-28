package dev.danae.commons.messages;

import java.util.Map;
import net.md_5.bungee.api.chat.BaseComponent;


public interface MessageManager 
{
  // Return the message with the specified name
  public String getMessage(String name);

  // Format the message with the specified name and arguments
  public default BaseComponent[] formatMessage(String name, Map<String, Object> args)
  {
    var message = this.getMessage(name);
    return message != null ? MessageFormatter.format(message, args) : new BaseComponent[0];
  }
}
