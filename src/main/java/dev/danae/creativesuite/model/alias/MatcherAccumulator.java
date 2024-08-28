package dev.danae.creativesuite.model.alias;

import java.util.regex.Matcher;


@FunctionalInterface
public interface MatcherAccumulator<T, U>
{
  // Performs an accumulation
  public void accept(Matcher matcher, T container, U item);
}
