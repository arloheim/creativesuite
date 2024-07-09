package dev.danae.gregocommands.util.parser;

import java.util.EnumSet;
import org.bukkit.NamespacedKey;


public class Scanner
{  
  // The arguments that will be iterated over
  private final String[] arguments;
  
  // The current index of the scanner
  private int index;
  
  
  // Constructor
  public Scanner(String[] arguments)
  {
    this.arguments = arguments;
    this.index = -1;
  }
  
  
  // Return if the scanner reached the end of the tokens at the next index
  private boolean isAtEnd()
  {
    return this.isAtEnd(1);
  }
  
  // Return if the scanner reached the end of the tokens at the index with the specified lookahead
  private boolean isAtEnd(int lookahead)
  {
    return this.index + lookahead >= this.arguments.length;
  }

  // Return the token that has just been scanned
  private String current()
  {
    return this.index >= 0  ? this.arguments[this.index] : null;
  }
  
  // Advance the index to the next position
  private String advance(String expected) throws ParserException
  {
    if (this.isAtEnd())
      throw new ParserException(String.format("Expected %s, but reached end of arguments", expected));
    
    this.index ++;
    return this.current();
  }  
  
  // Take the next token and parse it
  private <T> T take(ParserFunction<String, T> parser, String expected) throws ParserException
  {    
    var previousIndex = this.index;
    try
    {
      return parser.apply(this.advance(expected));
    }
    catch (ParserException ex)
    {
      this.index = previousIndex;
      throw ex;
    }
  }
  
  
  // Return the rest of the elements
  public String rest(String expected) throws ParserException
  {
    var string = "";
    while (!this.isAtEnd())
      string += (!string.isBlank() ? " " : "") + this.advance(expected);
    return string;
  }
  
  // Return the rest of the elements, or the default value if no such element exists
  public String rest(String expected, String defaultValue)
  {
    return ParserSupplier.getOrElse(() -> this.rest(expected), defaultValue);
  }

  // Return the next element in the scanner
  public String next() throws ParserException
  {
    return this.take(s -> s, "string");
  }

  // Return the next element in the scanner, , or the default value if no such element exists
  public String next(String defaultValue)
  {
    return ParserSupplier.getOrElse(() -> this.next(), defaultValue);
  }
  
  // Return the next element in the scanner as an integer
  public int nextInt() throws ParserException
  {
    return this.take(Parser::parseInt, "integer number");
  }
  
  // Return the next element in the scanner as an integer, or the default value if no such element exists
  public int nextInt(int defaultValue)
  {
    return ParserSupplier.getOrElse(() -> this.nextInt(), defaultValue);
  }
  
  // Return the next element in the scanner as a long
  public long nextLong() throws ParserException
  {
    return this.take(Parser::parseLong, "integer number");
  }
  
  // Return the next element in the scanner as a long, or the default value if no such element exists
  public long nextLong(long defaultValue)
  {
    return ParserSupplier.getOrElse(() -> this.nextLong(), defaultValue);
  }
  
  // Return the next element in the scanner as an unsigned integer
  public int nextUnsignedInt() throws ParserException
  {
    return this.take(Parser::parseUnsignedInt, "unsigned integer number");
  }
  
  // Return the next element in the scanner as an unsigned integer, or the default value if no such element exists
  public int nextUnsignedInt(int defaultValue)
  {
    return ParserSupplier.getOrElse(() -> this.nextUnsignedInt(), defaultValue);
  }
  
  // Return the next element in the scanner as an unsigned long
  public long nextUnsignedLong() throws ParserException
  {
    return this.take(Parser::parseUnsignedLong, "unsigned integer number");
  }
  
  // Return the next element in the scanner as an unsigned long, or the default value if no such element exists
  public long nextUnsignedLong(long defaultValue)
  {
    return ParserSupplier.getOrElse(() -> this.nextUnsignedLong(), defaultValue);
  }
  
  // Return the next element in the scanner as a float
  public float nextFloat() throws ParserException
  {
    return this.take(Parser::parseFloat, "floating-point number");
  }
  
  // Return the next element in the scanner as a float, or the default value if no such element exists
  public float nextFloat(float defaultValue)
  {
    return ParserSupplier.getOrElse(() -> this.nextFloat(), defaultValue);
  }
  
  // Return the next element in the scanner as a double
  public double nextDouble() throws ParserException
  {
    return this.take(Parser::parseDouble, "floating-point number");
  }
  
  // Return the next element in the scanner as a double, or the default value if no such element exists
  public double nextDouble(double defaultValue)
  {
    return ParserSupplier.getOrElse(() -> this.nextDouble(), defaultValue);
  }
  
  // Return the next element in the scanner as an identifier
  public String nextIdentifier() throws ParserException
  {
    return this.take(Parser::parseIdentifier, "identifier");
  }
  
  // Return the next element in the scanner as an identifier
  public String nextIdentifier(String defaultValue)
  {
    return ParserSupplier.getOrElse(() -> this.nextIdentifier(), defaultValue);
  }
  
  
  // Return the next element in the scanner as a namespaced key
  public NamespacedKey nextKey() throws ParserException
  {
    return this.take(Parser::parseKey, "namespaced key");
  }
  
  // Return the next element in the scanner as a namespaced key, or the default value if no such element exists
  public NamespacedKey nextKey(NamespacedKey defaultValue)
  {
    return ParserSupplier.getOrElse(() -> this.nextKey(), defaultValue);
  }
  
  // Return the next element in the scanner as an enum of the specified class
  public <T extends Enum<T>> T nextEnum(Class<T> cls, String expected) throws ParserException
  {
    return this.take(s -> Parser.parseEnum(s, cls), expected);
  }
  
  // Return the next element in the scanner as an enum of the specified class, or the default value if no such element exists
  public <T extends Enum<T>> T nextEnum(Class<T> cls, String expected, T defaultValue)
  {
    return ParserSupplier.getOrElse(() -> this.nextEnum(cls, expected), defaultValue);
  }
  
  // Return the next element in the scanner as an enum set of the specified class
  public <T extends Enum<T>> EnumSet<T> nextEnumSet(Class<T> cls, String expected) throws ParserException
  {
    return this.take(s -> Parser.parseEnumSet(s, ",", cls), expected);
  }
  
  // Return the next element in the scanner as an enum set of the specified class, or the default value if no such element exists
  public <T extends Enum<T>> EnumSet<T> nextEnumSet(Class<T> cls, String expected, EnumSet<T> defaultValue)
  {
    return ParserSupplier.getOrElse(() -> this.nextEnumSet(cls, expected), defaultValue);
  }
}
