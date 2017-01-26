package com.codegreenllc.linear;

public class SegmentImpl<ID> implements Segment<ID> {
	final ID id;
	final long start;
	final long end;

	public SegmentImpl(ID id, long start, long end) {
		this.id = id;
		this.start = start;
		this.end = end;
	}

	@Override
	public ID getId() {
		return id;
	}

	@Override
	public long getStart() {
		return start;
	}

	@Override
	public long getEnd() {
		return end;
	}
}
