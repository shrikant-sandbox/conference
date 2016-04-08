package com.svashishtha.conference;

import org.junit.Assert;

public class Track {

	ConferenceSession morningSession;
	ConferenceSession afternoonSession;
	private long size;
	private TrackConfig trackConfig;
	private String trackName;
	private BusinessRules businessRules;

	protected Track(TrackConfig config, String trackName) {
		this.trackConfig = config;
		morningSession = new ConferenceSession(trackConfig
				.getMaxMinutesForMorningSession().intValue(),
				trackConfig.getMorningSessionStartTime(),ConferenceSession.SessionType.MORNING);
		afternoonSession = new ConferenceSession(trackConfig
				.getMaxMinutesForAfternoonSession().intValue(),
				trackConfig.getAfternoonSessionStartTime(),ConferenceSession.SessionType.AFTERNOON);
		afternoonSession.setNetworkingEventMinutesRange(trackConfig
				.getNetworkingEventMinutesRange());
		this.setTrackName(trackName);
	}

	public Talk addItem(Talk item) {
		Assert.assertNotNull(item);

		Talk addedTalk = checkAndAddInMorningSession(item);
		if (addedTalk == null)
			addedTalk = checkAndAddInAfternoonSession(item);
		return addedTalk;
	}

	private Talk checkAndAddInAfternoonSession(Talk item) {
		return applyBusinessRules(afternoonSession, item);
	}

	private Talk applyBusinessRules(ConferenceSession session, Talk item) {
		Talk talkToAddInSession = getTalkToAddInSession(session, item);

		if (talkToAddInSession != null) {
			if (businessRules != null) {
				talkToAddInSession = (session.getSessionType() == ConferenceSession.SessionType.AFTERNOON ? businessRules.applyAfternoonRules(
						session.getTalks(), talkToAddInSession):businessRules.applyMorningRules(
								session.getTalks(), talkToAddInSession));
			}
		}
		if (talkToAddInSession != null) {
			session.addTalk(talkToAddInSession);
			if (talkToAddInSession.getTalkType() == TalkType.NETWORKING_EVENT) {
				session.setNetworkEventFilled(true);
			}
		}
		return talkToAddInSession;
	}

	private Talk getTalkToAddInSession(ConferenceSession session, Talk talk) {
		Talk availableTalk = isRoom(session, talk);
		if (availableTalk != null) {
			return availableTalk;
		} else if (session.getNetworkingEventMinutesRange() != null
				&& !session.isNetworkEventFilled()) {
			availableTalk = new Talk("Networking Event", 60,
					TalkType.NETWORKING_EVENT);
		}
		return availableTalk;
	}

	private Talk checkAndAddInMorningSession(Talk item) {
		return applyBusinessRules(morningSession, item);
	}

	public long getSize() {
		return size;
	}

	protected Talk isRoom(ConferenceSession session, Talk talk) {
		boolean roomAvailable = isRoomAvailableForDefaultBusinessRule(session,
				talk);
		Talk availableTalk = null;
		if (roomAvailable) {
			availableTalk = talk;
		} else if (businessRules != null) {
			int remainingMinutes = session.getSessionLimit()
					- session.getAlreadyFilledDuration();
			availableTalk = businessRules.getAvailableTalkForRemainingMinutes(remainingMinutes);
		}
		return availableTalk;
	}

	private boolean isRoomAvailableForDefaultBusinessRule(
			ConferenceSession session, Talk talk) {
		return session.getAlreadyFilledDuration() + talk.getDuration() <= session
				.getSessionLimit();
	}

	public void print() {
		System.out.println(trackName);
		System.out.println("-----------------");
		System.out.println(morningSession);
		System.out.println("12:00PM Lunch");
		System.out.println(afternoonSession);
	}

	public String getTrackName() {
		return trackName;
	}

	public void setTrackName(String trackName) {
		this.trackName = trackName;
	}

	public void setBusinessRules(BusinessRules businessRules) {
		this.businessRules = businessRules;
	}
}
