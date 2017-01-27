package com.codegreenllc.linear;

public interface SegmentPair<ID, T> {
	public Segment<ID, T> getFirstSegment();

	public Segment<ID, T> getSecondSegment();

	public T getStart();

	public T getEnd();
}
