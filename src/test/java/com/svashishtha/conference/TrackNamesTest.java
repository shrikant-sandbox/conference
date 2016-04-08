package com.svashishtha.conference;

import static org.junit.Assert.*;

import java.util.NoSuchElementException;

import org.junit.Test;

public class TrackNamesTest {

	@Test
	public void shouldProvideAvailableTrackNames() {
		int numberOfTracks = 2;int count = 1;
		TrackNames trackNames = new TrackNames(numberOfTracks);
		while (trackNames.isTrackNameAvailable()) {
			String trackName = trackNames.getNext();
			assertEquals("Track " + count, trackName);
			count++;
		}
	}
	
	@Test(expected=NoSuchElementException.class)
	public void shouldThrowExceptionIfNoNameAvailable(){
		TrackNames trackNames = new TrackNames(0);
		trackNames.getNext();
	}

}