package dryrun.eventStats;

public class Event {
    String clientId;
    String type;
    long timestamp;
    public Event(String clientId, String type, long timestamp) {
        this.clientId=clientId;
        this.type=type;
        this.timestamp = timestamp;
    }
}
