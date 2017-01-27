package com.codegreenllc.linear;

public interface Segment<ID,T> {

	public ID getId();
	public T getStart();
	public T getEnd();
}
