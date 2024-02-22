package codingtasks.ratelimit;

import java.util.HashMap;
import java.util.Map;

public class RateLimiter {
    private final int maxRequestPerWindow;
    private final long windowSizeInMillis;
    private final Map<String, Window> store = new HashMap<>();
    public RateLimiter(int maxRequestPerWindow, long windowSizeInMillis){
        this.maxRequestPerWindow=maxRequestPerWindow;
        this.windowSizeInMillis=windowSizeInMillis;
    }
    public boolean isAllowed(String clientId){
        long currentTimeMillis = System.currentTimeMillis();
        Window window = store.get(clientId);

        // If the window doesn't exist or has expired, create a new window
        if (window == null || window.getStartTime() < currentTimeMillis - windowSizeInMillis) {
            window = new Window(currentTimeMillis, 0);
        }

        // Check if the number of requests in the window exceeds the maximum allowed
        if (window.getRequestCount() >= maxRequestPerWindow) {
            return false; // Request is not allowed
        }

        // Increment the request count and update the window in the store
        window.setRequestCount(window.getRequestCount() + 1);
        store.put(clientId, window);
        return true; // Request is allowed
    }

    public static void main(String[] args) throws InterruptedException {
        RateLimiter limiter = new RateLimiter(5, 1000);
        for (int i = 0; i < 10; i++) {
            System.out.println("+"+i+limiter.isAllowed("Yury"));
        }
        Thread.sleep(1000);
        for (int i = 0; i < 100; i++) {
            System.out.println("+"+i+limiter.isAllowed("Yury"));
        }


    }

    private static class Window{
        private final long startTime;
        private int requestCount;
    public Window(long startTime, int requestCount){
            this.startTime=startTime;
            this.requestCount=requestCount;
    }

        public int getRequestCount() {
            return requestCount;
        }

        public long getStartTime() {
            return startTime;
        }

        public void setRequestCount(int requestCount) {
            this.requestCount = requestCount;
        }

    }

}
