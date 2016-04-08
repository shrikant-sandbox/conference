package com.svashishtha.conference;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.joda.time.LocalTime;

public class ConferenceSession {
	enum SessionType{
		MORNING, AFTERNOON
	}
	
	List<Talk> talks = new ArrayList<Talk>();
	int sessionLimit;
	boolean housefull = false;
	private int totalMinutes;
	private Range networkingEventMinutesRange;
	private LocalTime sessionBeginTime;
	private LocalTime nextTalkBeginTime;
	private boolean networkEventFilled = false;
	final private SessionType sessionType;
	public ConferenceSession(Integer sessionLimit, LocalTime sessionBeginTime, SessionType sessionType) {
		this.sessionLimit = sessionLimit;
		this.sessionBeginTime = sessionBeginTime;
		this.nextTalkBeginTime = sessionBeginTime;
		this.sessionType = sessionType;
	}

	public List<Talk> getTalks() {
		return Collections.unmodifiableList(talks);
	}
	
	public void addTalk(Talk talk) {
		talk.setBeginTime(nextTalkBeginTime);
		talks.add(talk);
		totalMinutes = getAlreadyFilledDuration();
		nextTalkBeginTime = nextTalkBeginTime.plusMinutes(talk.getDuration());
		if(sessionLimit == totalMinutes){
			housefull = true;
		}
	}
	
	public int getAlreadyFilledDuration() {
		int totalMinutes = 0;
		for (Talk talk : talks) {
			totalMinutes += talk.getDuration();
		}
		return totalMinutes;
	}

	public boolean isFull(){
		return housefull;
	}
	
	public void setTalks(List<Talk> talks) {
		this.talks = talks;
	}
	
	public int getSessionLimit() {
		return sessionLimit;
	}
	
	public void setSessionLimit(int sessionLimit) {
		this.sessionLimit = sessionLimit;
	}

	public String toString(){
		StringBuffer buffer = new StringBuffer();
		for (Talk talk : talks) {
			String talkNote = getTalkNote(talk);
			buffer.append(talk.getBeginTime().toString("hh:mmaa") + " " + talk.getName() + " " + talkNote);
		}
		return buffer.toString();
	}

	private String getTalkNote(Talk talk) {
		if(TalkType.NETWORKING_EVENT == talk.getTalkType()){
			return "\n";
		} else if(TalkType.LIGHTENING_TALK == talk.getTalkType()){
			return "lightening\n";
		}
		return talk.getDuration()+"min\n";
	}

	public LocalTime getSessionBeginTime() {
		return sessionBeginTime;
	}

	public void setSessionBeginTime(LocalTime sessionBeginTime) {
		this.sessionBeginTime = sessionBeginTime;
	}

	public Range getNetworkingEventMinutesRange() {
		return networkingEventMinutesRange;
	}

	public void setNetworkingEventMinutesRange(
			Range networkingEventMinutesRange) {
		this.networkingEventMinutesRange = networkingEventMinutesRange;
	}

	public boolean isNetworkEventFilled() {
		return networkEventFilled;
	}

	public void setNetworkEventFilled(boolean networkEventFilled) {
		this.networkEventFilled = networkEventFilled;
	}
	public SessionType getSessionType() {
		return sessionType;
	}
}


