package com.codegreenllc.linear;

public interface SegmentPair<ID> {
	public Segment<ID> getFirstSegment();
	public Segment<ID> getSecondSegment();
	public long getStart();
	public long getEnd();
}
