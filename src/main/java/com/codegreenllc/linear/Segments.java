package com.codegreenllc.linear;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class Segments<ID, T> {

	public List<SegmentPair<ID, T>> mergeSegments(List<Segment<ID, T>> fromList, List<Segment<ID, T>> toList,
			Comparator<T> comparator) {
		if (fromList == null || toList == null || comparator == null) {
			throw new IllegalArgumentException("Arguments may not be null.");
		}
		List<SegmentPair<ID, T>> result = new ArrayList<SegmentPair<ID, T>>();
		for (Segment<ID, T> fromSegment : fromList) {
			for (Segment<ID, T> toSegment : toList) {
				if (comparator.compare(fromSegment.getEnd(), toSegment.getStart()) <= 0) {
					result.add(new SegmentPairImpl<ID, T>(fromSegment, toSegment));
				}
			}
		}
		return result;
	}

	public List<SegmentPair<ID, T>> filterEarliestStartSegments(List<SegmentPair<ID, T>> segmentPairs,
			Comparator<T> comparator) {
		if (segmentPairs == null || comparator == null) {
			throw new IllegalArgumentException("Arguments may not be null.");
		}
		List<SegmentPair<ID, T>> result = new ArrayList<SegmentPair<ID, T>>();
		for (SegmentPair<ID, T> segmentPair : segmentPairs) {
			if (result.isEmpty()) {
				result.add(segmentPair);
			} else if (comparator.compare(segmentPair.getStart(), result.get(0).getStart()) == 0) {
				result.add(segmentPair);
			} else if (comparator.compare(segmentPair.getStart(), result.get(0).getStart()) < 0) {
				result.clear();
				;
				result.add(segmentPair);
			}
		}
		return result;
	}

	public List<SegmentPair<ID, T>> filterEarliestEndSegments(List<SegmentPair<ID, T>> segmentPairs,
			Comparator<T> comparator) {
		if (segmentPairs == null || comparator == null) {
			throw new IllegalArgumentException("Arguments may not be null.");
		}
		List<SegmentPair<ID, T>> result = new ArrayList<SegmentPair<ID, T>>();
		for (SegmentPair<ID, T> segmentPair : segmentPairs) {
			if (result.isEmpty()) {
				result.add(segmentPair);
			} else if (comparator.compare(segmentPair.getEnd(), result.get(0).getEnd()) == 0) {
				result.add(segmentPair);
			} else if (comparator.compare(segmentPair.getEnd(), result.get(0).getEnd()) < 0) {
				result.clear();
				;
				result.add(segmentPair);
			}
		}
		return result;
	}

	public List<SegmentPair<ID, T>> filterLatestStartSegments(List<SegmentPair<ID, T>> segmentPairs,
			Comparator<T> comparator) {
		if (segmentPairs == null || comparator == null) {
			throw new IllegalArgumentException("Arguments may not be null.");
		}
		List<SegmentPair<ID, T>> result = new ArrayList<SegmentPair<ID, T>>();
		for (SegmentPair<ID, T> segmentPair : segmentPairs) {
			if (result.isEmpty()) {
				result.add(segmentPair);
			} else if (comparator.compare(segmentPair.getStart(), result.get(0).getStart()) == 0) {
				result.add(segmentPair);
			} else if (comparator.compare(segmentPair.getStart(), result.get(0).getStart()) > 0) {
				result.clear();
				;
				result.add(segmentPair);
			}
		}
		return result;
	}

	public List<SegmentPair<ID, T>> filterLatestEndSegments(List<SegmentPair<ID, T>> segmentPairs,
			Comparator<T> comparator) {
		if (segmentPairs == null || comparator == null) {
			throw new IllegalArgumentException("Arguments may not be null.");
		}
		List<SegmentPair<ID, T>> result = new ArrayList<SegmentPair<ID, T>>();
		for (SegmentPair<ID, T> segmentPair : segmentPairs) {
			if (result.isEmpty()) {
				result.add(segmentPair);
			} else if (comparator.compare(segmentPair.getEnd(), result.get(0).getEnd()) == 0) {
				result.add(segmentPair);
			} else if (comparator.compare(segmentPair.getEnd(), result.get(0).getEnd()) > 0) {
				result.clear();
				;
				result.add(segmentPair);
			}
		}
		return result;
	}

}
