package fr.unice.scale.latencyaware.e2e_analyzer.entity.event;

public class E2EEvent {
    private String nodeOrigin;
    private long timestamp;

    public E2EEvent(String nodeOrigin, long timestamp) {
        this.nodeOrigin = nodeOrigin;
        this.timestamp = timestamp;
    }

    public String getNodeOrigin() {
        return nodeOrigin;
    }

    public void setNodeOrigin(String nodeOrigin) {
        this.nodeOrigin = nodeOrigin;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }
}
