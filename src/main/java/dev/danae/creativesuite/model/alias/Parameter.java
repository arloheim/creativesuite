package dev.danae.creativesuite.model.alias;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.regex.Pattern;


public class Parameter 
{
  // String that defines the key-value separator for a parameter
  public static final String SEPARATOR = "=";

  // Patterns for matching parameters and escaped characters
  private static final Pattern PARAM_PATTERN = Pattern.compile(String.format("<(?<name>[A-Za-z_][0-9A-Za-z_]*)%s(?:\"(?<q>(?:[^\\\\\"]|\\\\.)*)\"|(?<v>[^\\s]+))>", SEPARATOR));
  private static final Pattern ESCAPE_PATTERN = Pattern.compile("\\\\(?:(u([0-9A-Fa-f]{4}))|([\\\\\"btnfr]))");


  // The name of the parameter
  private final String name;

  // The default value of the parameter
  private final String defaultValue;


  // Constructor
  public Parameter(String name, String defaultValue)
  {
    this.name = name;
    this.defaultValue = defaultValue;
  }


  // Return the name of the parameter
  public String getName()
  {
    return this.name;
  }

  // Return the default value of the parameter
  public String getDefaultValue()
  {
    return this.defaultValue;
  }

  // Return the string representation of the parameter
  @Override
  public String toString()
  {
    return String.format("%s%s%s", this.name, SEPARATOR, this.defaultValue);
  }

  
  // Parse a string into a stream of parameters and collect them into a list
  public static List<Parameter> parse(String command)
  {
    return collect(command, m -> new ArrayList<Parameter>(), (m, c, param) -> c.add(param));
  }

  // Parse a string into a stream of parameters and replace them using the specified replacement function
  public static String replace(String command, Function<Parameter, String> replacer)
  {
    return collect(command, m -> new StringBuilder(), (m, b, param) -> m.appendReplacement(b, replacer.apply(param)), (m, b) -> { m.appendTail(b); return b.toString(); });
  }

  // Parse a string into a stream of parameters and replace them using the specified arguments
  public static String replace(String command, Map<String, String> args)
  {
    return replace(command, param -> args.getOrDefault(param.getName(), param.getDefaultValue()));
  }


  // Parse a string into a stream of parameters and collect them using the specified functions, having no intermediate type
  private static <R> R collect(String command, MatcherSupplier<R> supplier, MatcherAccumulator<R, Parameter> accumulator)
  {
    return collect(command, supplier, accumulator, (m, c) -> c);
  }

  // Parse a string into a stream of parameters and collect them using the specified functions
  private static <A, R> R collect(String command, MatcherSupplier<A> supplier, MatcherAccumulator<A, Parameter> accumulator, MatcherFinisher<A, R> finisher)
  {
    // Create a matcher for the command
    var matcher = PARAM_PATTERN.matcher(command);

    // Create a new result container
    var container = supplier.get(matcher);

    // Find variables in the message
    while (matcher.find())
    {
      var defaultValue = matcher.group("q") != null ? unescape(matcher.group("q")) : matcher.group("v");
      var parameter = new Parameter(matcher.group("name"), defaultValue);
      accumulator.accept(matcher, container, parameter);
    }

    // Return the result container
    return finisher.finish(matcher, container);
  }

  // Unescape a quoted string
  private static String unescape(String string)
  {
    var matcher = ESCAPE_PATTERN.matcher(string);
    return matcher.replaceAll(match -> {
      if (match.group(1) != null)
        return new String(Character.toChars(Integer.parseUnsignedInt(match.group(1))));

      return switch (match.group(2)) 
      {
        case "\\" -> "\\";
        case "\"" -> "\"";
        case "b" -> "\b";
        case "t" -> "\t";
        case "n" -> "\n";
        case "f" -> "\f";
        case "r" -> "\r";
        default -> "";
      };
    });
  }
}
