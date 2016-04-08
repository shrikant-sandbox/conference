package com.svashishtha.conference;

import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;

public class CustomTrackPacker extends FirstFitTrackPacker {
	private List<Talk> mutableTalksCopy;
	private BusinessRules businessRules;


	public CustomTrackPacker(final List<Talk> talks,
			final TrackConfig trackConfig) {
		

		super(talks, trackConfig);
		mutableTalksCopy = new ArrayList<Talk>();
		mutableTalksCopy.addAll(talks);
		businessRules = new BusinessRulesForCustomTrackPacker(mutableTalksCopy, trackConfig);
	}

	public List<Track> getPackedBlocks() {
		packBlocks();
		return getTracks();
	}

	@Override
	public TrackPacker packBlocks() {
		while(hasNextTalk()){
			Talk talk = getNextTalk();
			add(talk);
		}
		Talk networkingTalk = new Talk("Networking Event", 60, TalkType.NETWORKING_EVENT);
		add(networkingTalk);
		return this;
	}

	private Talk getNextTalk() {
		return mutableTalksCopy.iterator().next();
	}

	private boolean hasNextTalk() {
		return mutableTalksCopy.iterator().hasNext();
	}

	public Track add(final Talk talk) {
		Assert.assertNotNull(talk);

		Track targetTrack = null;
		Talk addedTalk = null;
		int i = 0;
		int j = trackList.size();

		do {
			if (i >= j) {
				break;
			}

			targetTrack = trackList.get(i);
			if ((addedTalk = targetTrack.addItem(talk)) != null) {
				mutableTalksCopy.remove(addedTalk);
				break;
			}
			i++;
		} while (true);
		if (addedTalk == null) {
			targetTrack = createTrack();
			targetTrack.setBusinessRules(businessRules);
			targetTrack.addItem(talk);
			mutableTalksCopy.remove(talk);
			trackList.add(targetTrack);
		}
		fireAddedEvent(new TrackEvent(targetTrack, talk));
		return targetTrack;
	}

	protected boolean candidateBlock(Talk talk) {
		boolean candidate = false;
		// if block could be added to morning or afternoon track
		return candidate;
	}

	public void reset() {
		trackList.clear();
		talks.clear();
	}

	public List<Talk> getTalks() {
		return talks;
	}
}
