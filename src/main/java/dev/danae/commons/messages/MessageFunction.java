package dev.danae.commons.messages;

import net.md_5.bungee.api.chat.BaseComponent;


@FunctionalInterface
public interface MessageFunction
{
  // Apply the message function with the specified content
  public BaseComponent[] apply(String content);
}
