package com.svashishtha.conference;

import java.util.EventListener;

/**
    Describes the callbacks that an instance of a {@link TrackPacker} can invoke on its listeners.
    Clients can inspect the {@link TrackEvent} to determine the data and the bins associated with the
    event.
 */
public interface TrackEventListener extends EventListener {

    /**
     * A new bin was created.
     *
     * @param event The event
     */
    public abstract void binCreated(TrackEvent event);

    /**
     * An item added to a bin.
     *
     * @param event The event
     */
    public abstract void itemAdded(TrackEvent event);

    /**
     * An item was ignored during the process. Clients can inspect the event's
     * {@link com.svashishtha.conference.TrackEvent#getBlock()} value to determine why it was ignored.
     *
     * @param event The event
     */
    public abstract void itemIgnored(TrackEvent event);
}
