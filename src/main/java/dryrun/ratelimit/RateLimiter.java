package dryrun.ratelimit;

public interface RateLimiter {
    boolean allowRequest();
}
