/**
 * Event class
 */
public class Event {

    //The available event states defined:
    public enum EventState {UP, DOWN}

    private EventState currentState;

    /**
     * Constructor
     */
    public Event() {
        // Initializing the current event state to DOWN
        // to let the Sender start:
        this.currentState = EventState.DOWN;
    }

    /**
     * Wait conditionally for the correct state
     * @param state the state to wait for
     * @throws InterruptedException exception
     */
    public synchronized void await(EventState state)
            throws InterruptedException {
        while (this.currentState != state) {
            wait();
        }
    }

    /**
     * Set state to UP
     */
    public synchronized void set() {
        this.currentState = EventState.UP;
        notify();
    }

    /**
     * Reset state
     */
    public synchronized void reset() {
        this.currentState = EventState.DOWN;
        notify();
    }

    /**
     * Toggle the state
     */
    public synchronized void toggle() {
        if (this.currentState == EventState.DOWN) {
            this.currentState = EventState.UP;
        }
        else {
            this.currentState = EventState.DOWN;
        }
        notify();
    }

    /**
     * Returns the current state
     * @return the current state
     */
    public synchronized EventState state() {
        return this.currentState;
    }



}
