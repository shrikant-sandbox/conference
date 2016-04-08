package com.svashishtha.conference;
import static com.svashishtha.conference.InstantiationAndPropertiesTester.shouldHaveValidGettersFor;

import org.junit.Test;

public class GetterAndSetterTest {
	@Test
    public void invokeGetterAndSetters() throws Exception {
        shouldHaveValidGettersFor(Talk.class, TrackEvent.class, TrackConfig.class);
    }
}