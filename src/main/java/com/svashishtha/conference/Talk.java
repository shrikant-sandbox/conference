package com.svashishtha.conference;

import java.util.Comparator;

import org.joda.time.LocalTime;

public class Talk {
	
	public static final Comparator<Talk> BLOCK_COMPARATOR = new Comparator<Talk>() {
        public int compare(final Talk firstBlock, final Talk secondBlock) {
            return Integer.valueOf(secondBlock.getDuration()).compareTo(Integer.valueOf(firstBlock.getDuration()));
        }
    };

	final String name;
	final int duration;
	private LocalTime beginTime;
	private TalkType talkType = TalkType.DEFAULT;

	public String getName() {
		return name;
	}

	public int getDuration() {
		return duration;
	}
	
	
	public Talk(String name, int duration) {
		this.name = name;
		this.duration = duration;
	}
	
	public Talk(String name, int duration, TalkType conferenceTalkType) {
		this.name = name;
		this.duration = duration;
		this.talkType = conferenceTalkType;
	}

	public LocalTime getBeginTime() {
		return beginTime;
	}

	public void setBeginTime(LocalTime beginTime) {
		this.beginTime = beginTime;
	}

	public void setTalkType(TalkType conferenceTalkType) {
		this.talkType = conferenceTalkType;
	}
	
	public TalkType getTalkType() {
		return talkType;
	}
}
