package com.svashishtha.conference;

import org.joda.time.LocalTime;

public class TrackConfig {
	Integer maxMinutesForMorningSession;
	Integer maxMinutesForAfternoonSession;
	Range networkingEventMinutesRange;
	Integer numTracks;
	private LocalTime morningSessionStartTime;
	private LocalTime afternoonSessionStartTime;

	public Integer getMaxMinutesForMorningSession() {
		return maxMinutesForMorningSession;
	}

	public Integer getMaxMinutesForAfternoonSession() {
		return maxMinutesForAfternoonSession;
	}

	public Range getNetworkingEventMinutesRange() {
		return networkingEventMinutesRange;
	}

	public Integer getNumTracks() {
		return numTracks;
	}

	public void setMaxMinutesForMorningSession(Integer maxMinutesForMorningSession) {
		this.maxMinutesForMorningSession = maxMinutesForMorningSession;
	}

	public void setMaxMinutesForAfternoonSession(
			Integer maxMinutesForAfternoonSession) {
		this.maxMinutesForAfternoonSession = maxMinutesForAfternoonSession;
	}

	public void setNetworkingEventMinutesRange(Range networkingEventMinutesRange) {
		this.networkingEventMinutesRange = networkingEventMinutesRange;
	}

	public void setNumTracks(Integer numTracks) {
		this.numTracks = numTracks;
	}

	public void setMorningSessionStartTime(LocalTime startTime) {
		morningSessionStartTime = startTime;
	}

	public void setAfternoonSessionStartTime(LocalTime afternoonSessionStartTime) {
		this.afternoonSessionStartTime = afternoonSessionStartTime;
	}

	public LocalTime getMorningSessionStartTime() {
		return morningSessionStartTime;
	}

	public LocalTime getAfternoonSessionStartTime() {
		return afternoonSessionStartTime;
	}
}
