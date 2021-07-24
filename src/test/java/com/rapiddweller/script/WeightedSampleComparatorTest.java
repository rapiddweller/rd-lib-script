package com.rapiddweller.script;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * The type Weighted sample comparator test.
 */
public class WeightedSampleComparatorTest {
  /**
   * Test compare.
   */
  @Test
  public void testCompare() {
    WeightedSample<Object> s1 = new WeightedSample<>("value", 10.0);
    WeightedSample<Object> s2 = new WeightedSample<>("value", 10.0);
    assertEquals(0, (new WeightedSampleComparator<>()).compare(s1, s2));
  }
}

