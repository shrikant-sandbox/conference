package com.svashishtha.conference;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

public class BusinessRulesForCustomTrackPacker implements BusinessRules {

	private List<Talk> remainingTalks;
	private TrackConfig trackConfig;

	public BusinessRulesForCustomTrackPacker(List<Talk> remainingTalksAvailableForConference, TrackConfig trackConfig) {
		this.remainingTalks = remainingTalksAvailableForConference;
		this.trackConfig = trackConfig;
	}
	
	@Override
	public Talk applyMorningRules(List<Talk> morningTalksInTrack, Talk talkToAdd) {
		int minutesOver = currentMorningTalksMinutes(morningTalksInTrack);
		Integer maxMinutesForMorningSession = trackConfig.getMaxMinutesForMorningSession();
		int supposedlyRemainingMinutesForNextSession = maxMinutesForMorningSession
				- (minutesOver + talkToAdd.getDuration());

		boolean nextSlotAvailable = doesAnyTalkFitsForNextSlot(supposedlyRemainingMinutesForNextSession);
		if (!nextSlotAvailable) {
			int remainingMinutes = maxMinutesForMorningSession - minutesOver;
			int minutesForPotentialTwoTalks = remainingMinutes / 2;
			List<Talk> availableTalks = getTalksFor(minutesForPotentialTwoTalks);
			Talk resultantTalk;
			if (availableTalks.size() > 1) {
				return availableTalks.get(0);
			} else if ((resultantTalk = isAnyOtherBetterTalkForCurrentOne((maxMinutesForMorningSession-minutesOver)))!= null){
				return resultantTalk;
			}else {
				Talk talk = getTalksForNextDenominator(remainingMinutes, talkToAdd.getDuration());
				if(talk != null){
					return talk;
				}
			}
		}
		return talkToAdd;
	}

	private Talk isAnyOtherBetterTalkForCurrentOne(int remainingMinutes) {
		Iterator<Talk> iterator = remainingTalks.iterator();
		while (iterator.hasNext()) {
			Talk talk = iterator.next();
			if (talk.getDuration() == remainingMinutes) {
				return talk;
			}
		}
		return null;
	}

	private Talk getTalksForNextDenominator(int remainingMinutes, int currentTalkDuration) {
		List<Talk> talksCopy = new ArrayList<Talk>();
		talksCopy.addAll(remainingTalks);
		Collections.sort(talksCopy, Talk.BLOCK_COMPARATOR);
		for(int i=0; i<talksCopy.size();i++){
			Talk talk1 = talksCopy.get(i);
			for(int j=(i+1);j<talksCopy.size();j++){
				Talk talk2 = talksCopy.get(j);
				if(talk1.getDuration()+talk2.getDuration() == remainingMinutes){
					return talk1;
				}
			}
		}
		return null;
	}

	private List<Talk> getTalksFor(int minutesForPotentialTwoTalks) {
		Iterator<Talk> iterator = remainingTalks.iterator();
		List<Talk> potentialTalks = new ArrayList<Talk>();
		
		while (iterator.hasNext()) {
			Talk talk = iterator.next();
			if (talk.getDuration() == minutesForPotentialTwoTalks) {
				potentialTalks.add(talk);
			}
		}
		return potentialTalks;
	}

	private boolean doesAnyTalkFitsForNextSlot(
			int supposedlyRemainingMinutesForNextSession) {
		Iterator<Talk> iterator = remainingTalks.iterator();
		while (iterator.hasNext()) {
			Talk talk = iterator.next();
			if (talk.getDuration() == supposedlyRemainingMinutesForNextSession) {
				return true;
			}
		}
		return false;
	}

	private int currentMorningTalksMinutes(List<Talk> morningTalksInTrack) {
		int min = 0;
		for (Talk talk : morningTalksInTrack) {
			min += talk.getDuration();
		}
		return min;
	}
	
	

	@Override
	public Talk applyAfternoonRules(List<Talk> afternoonTalksInTrack,
			Talk talkToAdd) {
		return talkToAdd;
	}

	@Override
	public Talk getAvailableTalkForRemainingMinutes(int remainingMinutes) {
		Iterator<Talk> iterator = remainingTalks.iterator();
		while (iterator.hasNext()) {
			Talk talk = (Talk) iterator.next();
			if(talk.getDuration() == remainingMinutes){
				return talk;
			}
		}
		return null;
	}
}
