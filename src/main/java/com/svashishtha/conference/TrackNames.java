package com.svashishtha.conference;

import java.util.LinkedList;

public class TrackNames {
	LinkedList<String> names = new LinkedList<String>();

	public TrackNames(int trackNumbers) {
		for (int i = 0; i < trackNumbers; i++) {
			names.add("Track " + (i+1));
		}
	}

	public String getNext() {
		return names.remove();
	}
	
	public boolean isTrackNameAvailable(){
		if(names.isEmpty()){
			return false;
		}
		return true;
	}
}
