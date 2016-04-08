package com.svashishtha.conference;

import java.util.List;

public interface BusinessRules {
	Talk applyMorningRules(List<Talk> morningTalksInTrack, Talk talkToAdd);
	Talk applyAfternoonRules(List<Talk> afternoonTalksInTrack, Talk talkToAdd);
	Talk getAvailableTalkForRemainingMinutes(int remainingMinutes);
}
