package com.luna.lib.event;

/**
 * Base of all events
 *
 * @author godshawk
 */
public abstract class EventBase {
    /**
     * Class the event originated from
     */
    private final Object source;

    public EventBase(final Object source) {
        this.source = source;
    }

    public final Object getSource() {
        return source;
    }
}
