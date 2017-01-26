package com.codegreenllc.linear;

public class SegmentPairImpl<ID> implements SegmentPair<ID> {

	Segment<ID> firstSegment, secondSegment;

	public SegmentPairImpl(Segment<ID> firstSegment, Segment<ID> secondSegment) {
		this.firstSegment = firstSegment;
		this.secondSegment = secondSegment;
	}

	@Override
	public Segment<ID> getFirstSegment() {
		return firstSegment;
	}

	@Override
	public Segment<ID> getSecondSegment() {
		return secondSegment;
	}

	@Override
	public long getStart() {
		return firstSegment.getStart();
	}

	@Override
	public long getEnd() {
		return secondSegment.getEnd();
	}
}
