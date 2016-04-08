package com.svashishtha.conference;

import java.util.List;

/**
 * Describes the operations needed for a a Bin Packing API.
 * @param <T>
 */
public interface TrackPacker{

    /**
     * The number of bins currently in use by the bin packer.
     *
     * @return A non-negative value
     */
    public abstract long getSize();

    /**
     * Adds the given block to the bin packer.
     *
     * @param block A block (non-null)
     * @return A reference to the Bin which the block was added
     */
    public abstract Track add(Talk block);

    /**
     * Adds all the given items to the bin packer.
     *
     * @param list A list of items (non-null)
     */
    public abstract TrackPacker packBlocks();

    /**
     * Returns all the bins (with their associated blocks) in the bin packer.
     *
     * @return A non-null value
     */
    public abstract List<Track> getTracks();

    /**
     * Adds an event listener to the bin packer.
     *
     * @param listener A listener (non-null)
     */
    public abstract void addBinEventListener(TrackEventListener listener);

    /**
     * Removes an event listener from the bin packer.
     *
     * @param listener A listener (non-null)
     */
    public abstract void removeBinEventListener(TrackEventListener listener);
}
