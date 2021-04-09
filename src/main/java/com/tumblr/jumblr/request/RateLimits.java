package com.tumblr.jumblr.request;

import org.apache.commons.lang3.math.NumberUtils;

import java.util.Map;

public class RateLimits {
  private final int perhourRemaining, perhourLimit, perhourReset, perdayRemaining, perdayLimit, perdayReset;

  public RateLimits() {
    perhourRemaining = 0;
    perhourLimit = 0;
    perhourReset = 0;
    perdayRemaining = 0;
    perdayLimit = 0;
    perdayReset = 0;
  }

  public RateLimits(final Map<String, String> response) {
    perhourRemaining = NumberUtils.toInt(response.get("X-Ratelimit-Perhour-Remaining"));
    perhourLimit = NumberUtils.toInt(response.get("X-Ratelimit-Perhour-Limit"));
    perhourReset = NumberUtils.toInt(response.get("X-Ratelimit-Perhour-Reset"));
    perdayRemaining = NumberUtils.toInt(response.get("X-Ratelimit-Perday-Remaining"));
    perdayLimit = NumberUtils.toInt(response.get("X-Ratelimit-Perday-Limit"));
    perdayReset = NumberUtils.toInt(response.get("X-Ratelimit-Perday-Reset"));
  }

  /**
   * @return the perhourRemaining
   */
  public int getPerhourRemaining() {
    return perhourRemaining;
  }

  /**
   * @return the perhourLimit
   */
  public int getPerhourLimit() {
    return perhourLimit;
  }

  /**
   * @return the perhourReset
   */
  public int getPerhourReset() {
    return perhourReset;
  }

  /**
   * @return the perdayRemaining
   */
  public int getPerdayRemaining() {
    return perdayRemaining;
  }

  /**
   * @return the perdayLimit
   */
  public int getPerdayLimit() {
    return perdayLimit;
  }

  /**
   * @return the perdayReset
   */
  public int getPerdayReset() {
    return perdayReset;
  }
}
