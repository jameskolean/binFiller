package com.codegreenllc.linear;

import java.util.ArrayList;
import java.util.List;

public class Segments<ID> {

	public List<SegmentPair<ID>> mergeSegments(List<Segment<ID>> fromList,List<Segment<ID>> toList){
		if (fromList == null || toList == null) {
			throw new IllegalArgumentException("Arguments may not be null.");
		}
		List<SegmentPair<ID>> result =  new ArrayList<SegmentPair<ID>>();
		for (Segment<ID> fromSegment : fromList) {
			for (Segment<ID> toSegment : toList) {
				if (fromSegment.getEnd() <= toSegment.getStart()) {
					result.add(new SegmentPairImpl<ID>( fromSegment,toSegment));
				}
			}
		}
		return result;
	}
	public List<SegmentPair<ID>> filterEarliestStartSegments(List<SegmentPair<ID>> segmentPairs){
		if (segmentPairs == null) {
			throw new IllegalArgumentException("Arguments may not be null.");
		}
		List<SegmentPair<ID>> result =  new ArrayList<SegmentPair<ID>>();
		for (SegmentPair<ID> segmentPair : segmentPairs) {
			if (result.isEmpty()) {
				result.add(segmentPair);
			} else if (segmentPair.getStart() == result.get(0).getStart()) {
				result.add(segmentPair);
			} else if (segmentPair.getStart() < result.get(0).getStart()) {
				result.clear();;
				result.add(segmentPair);
			}
		}
		return result;
	}
	public List<SegmentPair<ID>> filterEarliestEndSegments(List<SegmentPair<ID>> segmentPairs){
		if (segmentPairs == null) {
			throw new IllegalArgumentException("Arguments may not be null.");
		}
		List<SegmentPair<ID>> result =  new ArrayList<SegmentPair<ID>>();
		for (SegmentPair<ID> segmentPair : segmentPairs) {
			if (result.isEmpty()) {
				result.add(segmentPair);
			} else if (segmentPair.getEnd() == result.get(0).getEnd()) {
				result.add(segmentPair);
			} else if (segmentPair.getEnd() < result.get(0).getEnd()) {
				result.clear();;
				result.add(segmentPair);
			}
		}
		return result;
	}
	public List<SegmentPair<ID>> filterLatestStartSegments(List<SegmentPair<ID>> segmentPairs){
		if (segmentPairs == null) {
			throw new IllegalArgumentException("Arguments may not be null.");
		}
		List<SegmentPair<ID>> result =  new ArrayList<SegmentPair<ID>>();
		for (SegmentPair<ID> segmentPair : segmentPairs) {
			if (result.isEmpty()) {
				result.add(segmentPair);
			} else if (segmentPair.getStart() == result.get(0).getStart()) {
				result.add(segmentPair);
			} else if (segmentPair.getStart() > result.get(0).getStart()) {
				result.clear();;
				result.add(segmentPair);
			}
		}
		return result;
	}
	public List<SegmentPair<ID>> filterLatestEndSegments(List<SegmentPair<ID>> segmentPairs){
		if (segmentPairs == null) {
			throw new IllegalArgumentException("Arguments may not be null.");
		}
		List<SegmentPair<ID>> result =  new ArrayList<SegmentPair<ID>>();
		for (SegmentPair<ID> segmentPair : segmentPairs) {
			if (result.isEmpty()) {
				result.add(segmentPair);
			} else if (segmentPair.getEnd() == result.get(0).getEnd()) {
				result.add(segmentPair);
			} else if (segmentPair.getEnd() > result.get(0).getEnd()) {
				result.clear();;
				result.add(segmentPair);
			}
		}
		return result;
	}

}
