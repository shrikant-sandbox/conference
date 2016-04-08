package com.svashishtha.conference;

import java.util.Arrays;
import java.util.List;

import org.joda.time.LocalTime;
import org.slf4j.LoggerFactory;

public class Main {
	private static final org.slf4j.Logger log = LoggerFactory
			.getLogger(Main.class);

	public static void main(String[] args) {
		final Talk[] talkArray = {
				new Talk("Writing Fast Tests Against Enterprise Rails", 60),
				new Talk("Overdoing it in Python", 45),
				new Talk("Lua for the Masses", 30),
				new Talk("Ruby Errors from Mismatched Gem Versions", 45),
				new Talk("Common Ruby Errors", 45),
				new Talk("Rails for Python Developers", 5,
						TalkType.LIGHTENING_TALK),
				new Talk("Communicating Over Distance", 60),
				new Talk("Accounting-Driven Development", 45),
				new Talk("Woah", 30), new Talk("Sit Down and Write", 30),
				new Talk("Pair Programming vs Noise", 45),
				new Talk("Rails Magic", 60),
				new Talk("Ruby on Rails: Why We Should Move On", 60),
				new Talk("Clojure Ate Scala (on my project)", 45),
				new Talk("Programming in the Boondocks of Seattle", 30),
				new Talk("Ruby vs. Clojure for Back-End Development", 30),
				new Talk("Ruby on Rails Legacy App Maintenance", 60),
				new Talk("A World Without HackerNews", 30),
				new Talk("User Interface CSS in Rails Apps", 30) };

		final List<Talk> talks = Arrays.asList(talkArray);

		// add one long string that can't be added.
		final String ignoreMe = "This is an annoyingly long string that will no fit in any bins, so"
				+ " we should have an ignored block";

		TrackConfig trackConfig = new TrackConfig();
		initaliseTrackConfig(trackConfig);
		final TrackPacker packer = new CustomTrackPacker(talks, trackConfig);

		packer.addBinEventListener(new TrackEventListener() {

			public void binCreated(final TrackEvent event) {
				log.debug("Bin Created");
			}

			public void itemAdded(final TrackEvent event) {
				log.debug("Item added");
			}

			public void itemIgnored(final TrackEvent event) {
				log.debug("Item ignored");
				if (ignoreMe.equals(event.getBlock().getName())) {
					log.debug("Expected item was ignored.");
				} else {
					log.debug("Whe we have tests, this would be a failure");
				}
			}
		});

		final List<Track> tracks = packer.packBlocks().getTracks();
		for (Track track : tracks) {
			track.print();
		}
	}

	private static void initaliseTrackConfig(TrackConfig trackConfig) {
		trackConfig.setMaxMinutesForAfternoonSession(240);
		trackConfig.setMorningSessionStartTime(new LocalTime(9, 0, 0));
		trackConfig.setAfternoonSessionStartTime(new LocalTime(13, 0, 0));
		trackConfig.setMaxMinutesForMorningSession(180);
		trackConfig.setNetworkingEventMinutesRange(new Range(180, 240));
		trackConfig.setNumTracks(2);
	}
}
