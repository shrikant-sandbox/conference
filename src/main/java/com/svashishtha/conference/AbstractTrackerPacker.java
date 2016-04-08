package com.svashishtha.conference;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.swing.event.EventListenerList;

import org.junit.Assert;

public abstract class AbstractTrackerPacker implements TrackPacker {

    protected TrackConfig trackConfig;
    protected final List<Track> trackList;
    protected EventListenerList listenerList;
	private TrackNames trackNames;
	protected List<Talk> talks;

    public AbstractTrackerPacker() {
        trackList = new ArrayList<Track>();
        listenerList = new EventListenerList();
    }

    public AbstractTrackerPacker(List<Talk> items, final TrackConfig trackConfig) {
    	Assert.assertNotNull(items);
        trackList = new ArrayList<Track>();
        listenerList = new EventListenerList();
        this.trackConfig = trackConfig;
        trackNames = new TrackNames(trackConfig.getNumTracks().intValue());
        this.talks = items;
    }

    public long getSize() {
        long size = 0L;
        for (Track aBinList : trackList) {
            size += ((Track) aBinList).getSize();
        }
        return size;
    }

    protected final Track createTrack() {
        Track track = new Track (trackConfig, trackNames.getNext());
        fireCreatedEvent(new TrackEvent(track));
        return track;
    }


    public List<Track> getTracks() {
        return Collections.unmodifiableList(trackList);
    }

    public void addBinEventListener(final TrackEventListener listener) {
        listenerList.add(TrackEventListener.class, listener);
    }

    public void removeBinEventListener(final TrackEventListener listener) {
        listenerList.remove(TrackEventListener.class, listener);
    }

    protected void fireCreatedEvent(final TrackEvent evt) {
        final Object listeners[] = listenerList.getListenerList();

        for (int i = 0; i < listeners.length; i += 2) {
            if (listeners[i] == TrackEventListener.class) {
                ((TrackEventListener) listeners[i + 1]).binCreated(evt);
            }
        }
    }

    protected void fireAddedEvent(final TrackEvent evt) {
        final Object listeners[] = listenerList.getListenerList();

        for (int i = 0; i < listeners.length; i += 2) {
            if (listeners[i] == TrackEventListener.class) {
                ((TrackEventListener) listeners[i + 1]).itemAdded(evt);
            }
        }
    }

    protected void fireIgnoredEvent(final TrackEvent evt) {
        final Object listeners[] = listenerList.getListenerList();

        for (int i = 0; i < listeners.length; i += 2) {
            if (listeners[i] == TrackEventListener.class) {
                ((TrackEventListener) listeners[i + 1]).itemIgnored(evt);
            }
        }
    }
}
