package dev.danae.commons.messages;

import java.util.Map;
import java.util.regex.Pattern;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.TextComponent;


public class MessageFormatter 
{
  // Pattern for matching message variables
  private static final Pattern VARIABLE_PATTERN = Pattern.compile("\\{\\{(?<name>[a-z_][0-9a-z_]*)(\\s*:\\s*(?<content>[^}]+))?\\}\\}", Pattern.CASE_INSENSITIVE);


  // Format a message to an array of chat components
  public static BaseComponent[] format(String message, Map<String, Object> args)
  {
    // Create a new chat component builder
    var builder = new ComponentBuilder();

    // Find variables in the message
    var matcher = VARIABLE_PATTERN.matcher(message);
    var lastIndex = 0;
    while (matcher.find())
    {
      // Append the message before the matched variable
      builder.append(TextComponent.fromLegacyText(message.substring(lastIndex, matcher.start())));
      lastIndex = matcher.end();

      // Append the variable
      var arg = args.getOrDefault(matcher.group("name"), matcher.group());
      if (arg instanceof MessageFunction func)
      {
        var components = func.apply(matcher.group("content"));
        if (components.length > 0)
          builder.append(components);
      }
      else
      {
        builder.append(TextComponent.fromLegacyText(arg.toString()));
      }
    }

    // Append the tail and return the built chat components
    builder.append(TextComponent.fromLegacyText(message.substring(lastIndex)));
    return builder.create();
  }
}
