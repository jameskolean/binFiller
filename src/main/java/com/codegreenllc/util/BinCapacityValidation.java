package com.codegreenllc.util;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

/**
 * Given a set of bins (warehouse locations) with available storage capacity,
 * determine if the inventory (stocking programs) will fit. The bins are
 * specialized so they can only hold certain types on inventory. The packing
 * strategy fills the most restrictive bins first (those supporting the fewest
 * inventory types)
 */
public class BinCapacityValidation {
	final List<Bin> bins;

	protected BinCapacityValidation(List<Bin> bins) {
		this.bins = bins;
	}

	/**
	 * Attempts to pack all the inventory into bins and return a report of
	 * inventory that did not fit
	 * 
	 * @param inventory
	 *            map of inventoryType and quantity
	 * @return a map of inventoryType and quantity that did not fit
	 */
	public Map<String, Integer> pack(Map<String, Integer> inventory) {
		Map<String, Integer> result = new HashMap<String, Integer>();
		for (Entry<String, Integer> item : inventory.entrySet()) {
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

	/**
	 * Gets a list of bins that support the inventoryType sorted so the most
	 * restrictive bins are first in the list.
	 * 
	 * @param inventoryType
	 * @return a list of bins that support the inventoryType sorted so the most
	 *         restrictive bins are first in the list
	 */
	private List<Bin> orderBins(final String inventoryType) {
		List<Bin> result = new ArrayList<Bin>();
		for (Bin bin : bins) {
			if (bin.supports(inventoryType)) {
				result.add(bin);
			}
		}
		Collections.sort(result, new Comparator<Bin>() {
			@Override
			public int compare(Bin b1, Bin b2) {
				return b1.supportsCount() - b2.supportsCount();
			}
		});
		return result;
	}

	/**
	 * 
	 * Builder class to make it easier to use the feature
	 *
	 */
	public static class Builder {
		public Builder() {
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

	/**
	 * 
	 * Abstract idea of a container with finite capacity that only accepts
	 * specific types of inventory
	 *
	 */
	static class Bin {
		/**
		 * A Bin can only hold specific types of inventory. This method returns
		 * the number of those types.
		 * 
		 * @return number of types we can store
		 */
		public int supportsCount() {
			return supported.length;
		}

		/**
		 * @return true if the bin supports the given inventoryType, otherwise
		 *         false
		 */
		public boolean supports(String inventoryType) {
			for (String anInventoryType : supported) {
				if (anInventoryType.equals(inventoryType)) {
					return true;
				}
			}
			return false;
		}

		/**
		 * @return true if the bin has capacity to store the quantity specified
		 *         by the amount, otherwise false
		 */
		public boolean canDecrementCapacity(int amount) {
			return capactityRemaining >= amount;
		}

		/**
		 * Store as much of the amount in the bin as possible. The portion of
		 * the amount that does not fit in the bin is returned
		 * 
		 * @return the portion of amount that can not be stored in the bin
		 */
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
