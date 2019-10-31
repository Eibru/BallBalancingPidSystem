public class Event {

    // the available event states defined:
    public enum EventState {UP, DOWN};

    private EventState currentState;

    // Constructor for the Event:
    public Event() {
        // Initializing the current event state to DOWN
        // to let the Sender start:
        currentState = EventState.DOWN;
    }

    // wait conditionally for the correct state
    public synchronized void await(EventState state)
            throws InterruptedException {
        while (currentState != state) {
            wait();
        }
    }

    // set the current state
    public synchronized void set() {
        currentState = EventState.UP;
        notify();
    }
    // reset the current state
    public synchronized void reset() {
        currentState = EventState.DOWN;
        notify();
    }
    // toggle the current state
    public synchronized void toggle() {
        if (currentState == EventState.DOWN) {
            currentState = EventState.UP;
        }
        else {
            currentState = EventState.DOWN;
        }
        notify();
    }
    // return the current state
    public synchronized EventState state() {
        return currentState;
    }



}
