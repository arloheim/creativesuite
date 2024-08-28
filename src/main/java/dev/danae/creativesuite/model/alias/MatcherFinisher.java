package dev.danae.creativesuite.model.alias;

import java.util.regex.Matcher;


@FunctionalInterface
public interface MatcherFinisher<T, R>
{
  // Finish an accumulation
  public R finish(Matcher matcher, T container);
}
