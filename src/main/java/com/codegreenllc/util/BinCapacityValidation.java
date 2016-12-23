package com.codegreenllc.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Collectors;

public class BinCapacityValidation {
	final List<Bin> bins;

	protected BinCapacityValidation(List<Bin> bins) {
		this.bins = bins;
	}

	public Map<String, Integer> pack(Map<String, Integer> items) {
		Map<String, Integer> result = new HashMap<String, Integer>();
		for (Entry<String, Integer> item : items.entrySet()) {
			int currentAmount = item.getValue();
			for (Bin bin : orderBins(item.getKey())) {
				currentAmount -= bin.fill(currentAmount);
			}
			if (currentAmount > 0) {
				result.put(item.getKey(), currentAmount);
			}
		}
		return result;
	}

	private List<Bin> orderBins(final String key) {
		return bins.stream().filter(b -> b.supports(key)).sorted((b1, b2) -> b1.supportsCount() - b2.supportsCount())
				.collect(Collectors.toList());
	}

	public static class Builder {
		public Builder(){
			super();
		}
		final List<Bin> bins = new ArrayList<Bin>();

		public Builder addBin(int amount, String... supported) {
			bins.add(new Bin(amount, supported));
			return this;
		}

		public BinCapacityValidation build() {
			return new BinCapacityValidation(bins);
		}
	}

	static class Bin {
		public int supportsCount() {
			return supported.length;
		}

		public boolean supports(String key) {
			return Arrays.stream(supported).anyMatch(k -> k.equals(key));
		}

		public boolean canDecrementCapacity(int amount) {
			return capactityRemaining >= amount;
		}

		public int fill(int amount) {
			int canStore = Math.min(capactityRemaining, amount);
			capactityRemaining -= canStore;
			return canStore;
		}

		public int getCapactityOriginal() {
			return capactityOriginal;
		}

		public int getCapactityRemaining() {
			return capactityRemaining;
		}

		public String[] getSupported() {
			return supported;
		}

		private final int capactityOriginal;
		private int capactityRemaining;
		private final String[] supported;

		Bin(int capactity, String... supported) {
			this.capactityOriginal = capactity;
			this.capactityRemaining = capactity;
			this.supported = supported;
		}

	}
}
