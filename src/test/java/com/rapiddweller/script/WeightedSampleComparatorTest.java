package com.rapiddweller.script;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class WeightedSampleComparatorTest {
    @Test
    public void testCompare() {
        WeightedSample<Object> s1 = new WeightedSample<Object>("value", 10.0);
        WeightedSample<Object> s2 = new WeightedSample<Object>("value", 10.0);
        assertEquals(0, (new WeightedSampleComparator<>()).compare(s1, s2));
    }
}

