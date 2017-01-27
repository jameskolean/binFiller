package com.codegreenllc.linear;

public class SegmentPairImpl<ID, T> implements SegmentPair<ID, T> {

	Segment<ID, T> firstSegment, secondSegment;

	public SegmentPairImpl(Segment<ID, T> firstSegment, Segment<ID, T> secondSegment) {
		this.firstSegment = firstSegment;
		this.secondSegment = secondSegment;
	}

	@Override
	public Segment<ID, T> getFirstSegment() {
		return firstSegment;
	}

	@Override
	public Segment<ID, T> getSecondSegment() {
		return secondSegment;
	}

	@Override
	public T getStart() {
		return firstSegment.getStart();
	}

	@Override
	public T getEnd() {
		return secondSegment.getEnd();
	}
}
