package com.svashishtha.conference;

import static org.junit.Assert.*;

import org.junit.Test;

public class RangeTest {

	@Test
	public void shouldReturnFalseForOutsideRange() {
		Range range = new Range(25, 40);
		assertFalse(range.contains(24));
		assertFalse(range.contains(41));
	}
	
	@Test
	public void shouldReturnTrueForInsideRange() {
		Range range = new Range(25, 40);
		assertTrue(range.contains(25));
		assertTrue(range.contains(30));
		assertTrue(range.contains(40));
	}
}