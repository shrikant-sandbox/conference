package com.svashishtha.conference;

import java.util.EventObject;

/**
 * Represents an event during the bin packing process.
 *
 * @param <T>
 * @see TrackEventListener
 */
public class TrackEvent extends EventObject {
	private static final long serialVersionUID = 1L;
	private final Talk block;

    public TrackEvent(final Track source) {
        this(source, null);
    }

    public TrackEvent(final Object source, final Talk block) {
        super(source);
        this.block = block;
    }

    public Talk getBlock() {
        return block;
    }
}
