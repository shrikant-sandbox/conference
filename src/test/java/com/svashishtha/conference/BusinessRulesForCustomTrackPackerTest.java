package com.svashishtha.conference;

import java.util.ArrayList;
import java.util.List;


import org.joda.time.LocalTime;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import static org.mockito.Mockito.*;

public class BusinessRulesForCustomTrackPackerTest {
	BusinessRulesForCustomTrackPacker businessRules;
	TrackConfig trackConfig;
	private List<Talk> availableTalksForConference;
	private Talk talkToAdd;
	private List<Talk> morningTalksInTrack;
	private List<Talk> afternoonTalksInTrack;
	
	@Before
	public void setUp(){
		trackConfig = mock(TrackConfig.class);
		setMockForTrackConfig();
		availableTalksForConference = new ArrayList<Talk>();
		talkToAdd = new Talk("sample talk", 60);
		morningTalksInTrack = new ArrayList<Talk>();
	}

	private void setMockForTrackConfig() {
		when(trackConfig.getMaxMinutesForMorningSession()).thenReturn(new Integer(180));
		when(trackConfig.getMorningSessionStartTime()).thenReturn(new LocalTime(9, 0));
		when(trackConfig.getMaxMinutesForAfternoonSession()).thenReturn(new Integer(240));
	}


	@Test
	public void testApplyMorningRules_shouldAddTalkForEmptyMorningList() {
		availableTalksForConference.add(talkToAdd);
		businessRules = new BusinessRulesForCustomTrackPacker(availableTalksForConference, trackConfig);
		Talk resultantMorningTalk = businessRules.applyMorningRules(morningTalksInTrack, talkToAdd);
		Assert.assertNotNull(resultantMorningTalk);
		Assert.assertEquals(talkToAdd, resultantMorningTalk);
	}

	@Test
	public void testApplyMorningRules_shouldAddAlternateTalkIfSessionDoesntFillWithNextTalk() {
		prepareTestData_ShouldAddAlternateTalkIfSessionDoesntFillWithNextTalk();
		businessRules = new BusinessRulesForCustomTrackPacker(availableTalksForConference, trackConfig);
		Talk resultantMorningTalk = businessRules.applyMorningRules(morningTalksInTrack, talkToAdd);
		Assert.assertNotNull(resultantMorningTalk);
		Assert.assertEquals(30, resultantMorningTalk.getDuration());
	}
	

	private void prepareTestData_ShouldAddAlternateTalkIfSessionDoesntFillWithNextTalk() {
		availableTalksForConference.add(new Talk("Clojure Ate Scala (on my project)",45));
        availableTalksForConference.add(new Talk("Programming in the Boondocks of Seattle",30));
        availableTalksForConference.add(new Talk("Ruby vs. Clojure for Back-End Development",30));
        morningTalksInTrack.add(new Talk("Rails Magic",60));
        morningTalksInTrack.add(new Talk("Ruby on Rails: Why We Should Move On",60));
        talkToAdd = new Talk("Clojure Ate Scala (on my project)",45);
	}
	
	@Test
	public void testApplyMorningRules_shouldCheckMinimumFourTalksInMorningSession() {
		prepareTestData_shouldCheckMinimumFourTalksInMorningSession();
		businessRules = new BusinessRulesForCustomTrackPacker(availableTalksForConference, trackConfig);
		Talk resultantMorningTalk = businessRules.applyMorningRules(morningTalksInTrack, talkToAdd);
		Assert.assertNotNull(resultantMorningTalk);
		Assert.assertEquals(30, resultantMorningTalk.getDuration());
	}

	private void prepareTestData_shouldCheckMinimumFourTalksInMorningSession() {
		morningTalksInTrack.add(new Talk("Rails Magic",60));
        morningTalksInTrack.add(new Talk("Ruby on Rails: Why We Should Move On",60));
        talkToAdd = new Talk("Clojure Ate Scala (on my project)",60);
        availableTalksForConference.add(new Talk("Clojure Ate Scala (on my project)",60));
        availableTalksForConference.add(new Talk("Programming in the Boondocks of Seattle",30));
        availableTalksForConference.add(new Talk("Ruby vs. Clojure for Back-End Development",30));
	}
	
	@Test
	public void testApplyMorningRules_shouldCheckBetterTalkComparedToCurrentOne() {
		prepareTestData_shouldCheckBetterTalkComparedToCurrentOne();
		businessRules = new BusinessRulesForCustomTrackPacker(availableTalksForConference, trackConfig);
		Talk resultantMorningTalk = businessRules.applyMorningRules(morningTalksInTrack, talkToAdd);
		Assert.assertNotNull(resultantMorningTalk);
		Assert.assertEquals(45, resultantMorningTalk.getDuration());
	}

	private void prepareTestData_shouldCheckBetterTalkComparedToCurrentOne() {
		morningTalksInTrack.add(new Talk("Rails Magic",60));
        morningTalksInTrack.add(new Talk("Ruby on Rails: Why We Should Move On",45));
        morningTalksInTrack.add(new Talk("Sample",30));
        
        talkToAdd = new Talk("Clojure Ate Scala (on my project)",30);
        
        availableTalksForConference.add(new Talk("Clojure Ate Scala (on my project)",30));
        availableTalksForConference.add(new Talk("Ruby vs. Clojure for Back-End Development",30));
        availableTalksForConference.add(new Talk("Programming in the Boondocks of Seattle",45));
	}
	
	@Test
	public void testApplyMorningRules_shouldCheckTalkForNextAvailableDenominator() {
		prepareTestData_shouldCheckTalkForNextAvailableDenominator();
		businessRules = new BusinessRulesForCustomTrackPacker(availableTalksForConference, trackConfig);
		Talk resultantMorningTalk = businessRules.applyMorningRules(morningTalksInTrack, talkToAdd);
		Assert.assertNotNull(resultantMorningTalk);
		Assert.assertEquals(40, resultantMorningTalk.getDuration());
	}

	private void prepareTestData_shouldCheckTalkForNextAvailableDenominator() {
		morningTalksInTrack.add(new Talk("Rails Magic",60));
        morningTalksInTrack.add(new Talk("Ruby on Rails: Why We Should Move On",60));
        
        talkToAdd = new Talk("Clojure Ate Scala (on my project)",45);
        
        availableTalksForConference.add(new Talk("Clojure Ate Scala (on my project)",45));
        availableTalksForConference.add(new Talk("Another Talk",40));
        availableTalksForConference.add(new Talk("Programming in the Boondocks of Seattle",20));
	}
	
	@Test
	public void testApplyMorningRules_shouldReturnTalkIfNextSlotAvailable() {
		prepareTestData_shouldReturnTalkIfNextSlotAvailable();
		businessRules = new BusinessRulesForCustomTrackPacker(availableTalksForConference, trackConfig);
		Talk resultantMorningTalk = businessRules.applyMorningRules(morningTalksInTrack, talkToAdd);
		Assert.assertNotNull(resultantMorningTalk);
		Assert.assertEquals(45, resultantMorningTalk.getDuration());
	}

	private void prepareTestData_shouldReturnTalkIfNextSlotAvailable() {
		morningTalksInTrack.add(new Talk("Rails Magic",60));
        morningTalksInTrack.add(new Talk("Ruby on Rails: Why We Should Move On",60));
        
        talkToAdd = new Talk("Clojure Ate Scala (on my project)",45);
        
        availableTalksForConference.add(new Talk("Clojure Ate Scala (on my project)",45));
        availableTalksForConference.add(new Talk("Another Talk",15));
		
	}
	
	@Test
	public void testApplyAfternoonRules_shouldReturnTalkAsItIs() {
		availableTalksForConference.add(talkToAdd);
		afternoonTalksInTrack = new ArrayList<Talk>();
		businessRules = new BusinessRulesForCustomTrackPacker(availableTalksForConference, trackConfig);
		Talk resultantMorningTalk = businessRules.applyAfternoonRules(afternoonTalksInTrack, talkToAdd);
		Assert.assertNotNull(resultantMorningTalk);
		Assert.assertEquals(60	, resultantMorningTalk.getDuration());
	}
	
	@Test
	public void testGetAvailableTalkForRemainingMinutes() {
		availableTalksForConference.add(talkToAdd);
		afternoonTalksInTrack = new ArrayList<Talk>();
		businessRules = new BusinessRulesForCustomTrackPacker(availableTalksForConference, trackConfig);
		Talk resultantMorningTalk = businessRules.applyAfternoonRules(afternoonTalksInTrack, talkToAdd);
		Assert.assertNotNull(resultantMorningTalk);
		Assert.assertEquals(60	, resultantMorningTalk.getDuration());
	}
}
