package demo1;

public interface RateLimiter {
     boolean allowRequest(String clientId);
}
