package com.svashishtha.conference;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.junit.Assert;

/**
 * A TrackPacker which will simply fit a block within the first available bin. No optimization is done.
 *
 * @param <T> The type encapsulated within the bins of the bin packer
 */
public class FirstFitTrackPacker extends AbstractTrackerPacker {



    public FirstFitTrackPacker(final List<Talk> items, TrackConfig trackConfig) {
		super(items, trackConfig);
	}

	protected void sort(List<Talk> blocks) {
        Collections.sort(blocks, Talk.BLOCK_COMPARATOR);
    }

    public Track add(final Talk item) {

        Assert.assertNotNull(item);

        Track targetBin = null;
        Talk addedTalk = null;
        int i = 0;
        int j = trackList.size();

        do {
            if (i >= j)
                break;
            if ((addedTalk = trackList.get(i).addItem(item)) != null) {
                targetBin = trackList.get(i);
                break;
            }
            i++;
        } while (true);

        if (addedTalk == null) {
            targetBin = createTrack();
            targetBin.addItem(item);
            trackList.add(targetBin);
        }
        fireAddedEvent(new TrackEvent(targetBin, item));

        return targetBin;
    }

    public TrackPacker packBlocks() {
        final List<Talk> copy = new ArrayList<Talk>();
        copy.addAll(talks);
        sort(copy);
        for (final Talk block : copy) {
            add(block);
        }
        return this;
    }

}
