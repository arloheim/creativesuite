package dev.danae.creativesuite.model.alias;

import java.util.regex.Matcher;


@FunctionalInterface
public interface MatcherSupplier<T>
{
  // Supply a result
  public T get(Matcher matcher);
}
