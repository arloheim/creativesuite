package dev.danae.commons.messages;

import net.md_5.bungee.api.chat.BaseComponent;
import org.bukkit.NamespacedKey;


@FunctionalInterface
public interface NamespacedKeyFunction
{
  // Apply the message function with the specified content and namespaced key
  public BaseComponent[] apply(NamespacedKey key, String content);
}
