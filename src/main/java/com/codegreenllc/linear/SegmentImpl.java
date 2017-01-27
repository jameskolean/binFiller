package com.codegreenllc.linear;

public class SegmentImpl<ID, T> implements Segment<ID, T> {
	final ID id;
	final T start;
	final T end;

	public SegmentImpl(ID id, T start, T end) {
		this.id = id;
		this.start = start;
		this.end = end;
	}

	@Override
	public ID getId() {
		return id;
	}

	@Override
	public T getStart() {
		return start;
	}

	@Override
	public T getEnd() {
		return end;
	}
}
