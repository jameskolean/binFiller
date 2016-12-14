package com.codegreenllc.util;

import static org.junit.Assert.assertEquals;

import java.util.HashMap;
import java.util.Map;

import org.junit.Test;

public class BinCapacityValidationTest extends BinCapacityValidation {

	@Test
	public void pack_givenOneItem_success() {
		BinCapacityValidation target = new BinCapacityValidation();
		Map<String, Integer> items = new HashMap<String, Integer>();
		items.put("S1", 1);

		Map<String, Integer> result = target.addBin(1, "S1").pack(items);

		assertEquals(0, result.size());
	}

	@Test
	public void pack_givenTwoItems_failure() {
		BinCapacityValidation target = new BinCapacityValidation();
		Map<String, Integer> items = new HashMap<String, Integer>();
		items.put("S1", 2);

		Map<String, Integer> result = target.addBin(1, "S1").pack(items);

		assertEquals(1, result.size());
		assertEquals(1, result.get("S1").intValue());
	}

	@Test
	public void pack_givenFourKeys_success() {
		BinCapacityValidation target = new BinCapacityValidation();
		Map<String, Integer> items = new HashMap<String, Integer>();
		items.put("S1", 2);
		items.put("S2", 2);
		items.put("S3", 2);
		items.put("S4", 2);

		Map<String, Integer> result = target.addBin(1, "S1", "S2", "S3", "S4").addBin(1, "S1", "S3", "S4")
				.addBin(1, "S1", "S3", "S4").addBin(1, "S1", "S2").addBin(1, "S3", "S2").addBin(1, "S3", "S2")
				.addBin(1, "S3", "S2").addBin(1, "S3", "S2", "S4").pack(items);

		assertEquals(0, result.size());
	}

	@Test
	public void pack_givenFourKeysTooMuch_failure() {
		BinCapacityValidation target = new BinCapacityValidation();
		Map<String, Integer> items = new HashMap<String, Integer>();
		items.put("S1", 2);
		items.put("S2", 2);
		items.put("S3", 2);
		items.put("S4", 3);

		Map<String, Integer> result = target.addBin(1, "S1", "S2", "S3", "S4").addBin(1, "S1", "S3", "S4")
				.addBin(1, "S1", "S3", "S4").addBin(1, "S1", "S2").addBin(1, "S3", "S2").addBin(1, "S3", "S2")
				.addBin(1, "S3", "S2").addBin(1, "S3", "S2", "S4").pack(items);

		assertEquals(1, result.size());
	}
}
