package com.codegreenllc.linear;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.junit.Test;

public class SegmentsTest {
	List<Segment<String>> createFromSegments() {
		List<Segment<String>> result = new ArrayList<Segment<String>>();
		result.add(new SegmentImpl<String>("A", 0, 10));
		result.add(new SegmentImpl<String>("B", 0, 20));
		result.add(new SegmentImpl<String>("C", 5, 10));
		result.add(new SegmentImpl<String>("D", 5, 20));
		return result;
	}

	List<Segment<String>> createToSegments() {
		List<Segment<String>> result = new ArrayList<Segment<String>>();
		result.add(new SegmentImpl<String>("X", 10, 35));
		result.add(new SegmentImpl<String>("Y", 20, 30));
		result.add(new SegmentImpl<String>("Z", 5, 40));
		return result;
	}

	Segments<String> target = new Segments<String>();

	@Test(expected = IllegalArgumentException.class)
	public void mergeSegments_givenNulls_throwsIAE() {
		target.mergeSegments(null, null);
	}

	@Test(expected = IllegalArgumentException.class)
	public void filterEarliestEndSegments_givenNulls_throwsIAE() {
		target.filterEarliestEndSegments(null);
	}

	@Test(expected = IllegalArgumentException.class)
	public void filterEarliestStartSegments_givenNulls_throwsIAE() {
		target.filterEarliestStartSegments(null);
	}

	@Test(expected = IllegalArgumentException.class)
	public void filterLatestEndSegments_givenNulls_throwsIAE() {
		target.filterLatestEndSegments(null);
	}

	@Test(expected = IllegalArgumentException.class)
	public void filterLatestStartSegments_givenNulls_throwsIAE() {
		target.filterLatestStartSegments(null);
	}

	@Test(expected = IllegalArgumentException.class)
	public void mergeSegments_givenFirstNull_throwsIAE() {
		target.mergeSegments(null, new ArrayList<Segment<String>>());
	}

	@Test(expected = IllegalArgumentException.class)
	public void mergeSegments_givenSeconedNull_throwsIAE() {
		target.mergeSegments(new ArrayList<Segment<String>>(), null);
	}

	@Test()
	public void mergeSegments_givenEmptyLists_returnEmptyList() {
		List<SegmentPair<String>> result = target.mergeSegments(new ArrayList<Segment<String>>(),
				new ArrayList<Segment<String>>());

		assertEquals(result.isEmpty(), true);
	}

	@Test()
	public void mergeSegments_givenEmptyFromList_returnEmptyList() {
		List<SegmentPair<String>> result = target.mergeSegments(new ArrayList<Segment<String>>(), createToSegments());

		assertEquals(result.isEmpty(), true);
	}

	@Test()
	public void mergeSegments_givenEmptyToList_returnEmptyList() {
		List<SegmentPair<String>> result = target.mergeSegments(createFromSegments(), new ArrayList<Segment<String>>());

		assertEquals(result.isEmpty(), true);
	}

	@Test()
	public void mergeSegments_givenReversedList_returnEmptyList() {
		List<SegmentPair<String>> result = target.mergeSegments(createToSegments(), createFromSegments());
		assertEquals(result.isEmpty(), true);
	}

	@SuppressWarnings("unused")
	private void inspect(List<SegmentPair<String>> segmentPairs) {
		for (SegmentPair<String> segmentPair : segmentPairs) {
			inspect(segmentPair);
		}

	}

	@SuppressWarnings("unused")
	private void inspect(SegmentPair<String> segmentPair) {
		System.out.println(String.format("From: %s %d:%d To: %s %d:%d", segmentPair.getFirstSegment().getId(),
				segmentPair.getFirstSegment().getStart(), segmentPair.getFirstSegment().getEnd(),
				segmentPair.getSecondSegment().getId(), segmentPair.getSecondSegment().getStart(),
				segmentPair.getSecondSegment().getEnd()));

	}

	@Test()
	public void mergeSegments_givenValidList_returnMegredList() {
		List<SegmentPair<String>> result = target.mergeSegments(createFromSegments(), createToSegments());

		assertEquals(false, result.isEmpty());
		assertEquals(6, result.size());
		Iterator<SegmentPair<String>> iterator = result.iterator();
		assertSegment(iterator.next(), "A", 0, 10, "X", 10, 35);
		assertSegment(iterator.next(), "A", 0, 10, "Y", 20, 30);
		assertSegment(iterator.next(), "B", 0, 20, "Y", 20, 30);
		assertSegment(iterator.next(), "C", 5, 10, "X", 10, 35);
		assertSegment(iterator.next(), "C", 5, 10, "Y", 20, 30);
		assertSegment(iterator.next(), "D", 5, 20, "Y", 20, 30);

	}

	private void assertSegment(SegmentPair<String> segmentPair, String id1, int start1, int end1, String id2,
			int start2, int end2) {
		assertEquals(id1, segmentPair.getFirstSegment().getId());
		assertEquals(id2, segmentPair.getSecondSegment().getId());
		assertEquals(start1, segmentPair.getFirstSegment().getStart());
		assertEquals(start2, segmentPair.getSecondSegment().getStart());
		assertEquals(end1, segmentPair.getFirstSegment().getEnd());
		assertEquals(end2, segmentPair.getSecondSegment().getEnd());
	}

	@Test()
	public void filterEarliestEndSegments_givenValidList_returnFilteresList() {
		List<SegmentPair<String>> result = target
				.filterEarliestEndSegments(target.mergeSegments(createFromSegments(), createToSegments()));

		assertEquals(false, result.isEmpty());
		assertEquals(4, result.size());
		Iterator<SegmentPair<String>> iterator = result.iterator();
		assertSegment(iterator.next(), "A", 0, 10, "Y", 20, 30);
		assertSegment(iterator.next(), "B", 0, 20, "Y", 20, 30);
		assertSegment(iterator.next(), "C", 5, 10, "Y", 20, 30);
		assertSegment(iterator.next(), "D", 5, 20, "Y", 20, 30);
	}

	@Test()
	public void filterEarliestStartSegments_givenValidList_returnFilteresList() {
		List<SegmentPair<String>> result = target
				.filterEarliestStartSegments(target.mergeSegments(createFromSegments(), createToSegments()));
		assertEquals(false, result.isEmpty());
		assertEquals(3, result.size());
		Iterator<SegmentPair<String>> iterator = result.iterator();
		assertSegment(iterator.next(), "A", 0, 10, "X", 10, 35);
		assertSegment(iterator.next(), "A", 0, 10, "Y", 20, 30);
		assertSegment(iterator.next(), "B", 0, 20, "Y", 20, 30);
	}

	@Test()
	public void filterLatestEndSegments_givenValidList_returnFilteresList() {
		List<SegmentPair<String>> result = target
				.filterLatestEndSegments(target.mergeSegments(createFromSegments(), createToSegments()));


		assertEquals(false, result.isEmpty());
		assertEquals(2, result.size());
		Iterator<SegmentPair<String>> iterator = result.iterator();
		assertSegment(iterator.next(), "A", 0, 10, "X", 10, 35);
		assertSegment(iterator.next(), "C", 5, 10, "X", 10, 35);
	}

	@Test()
	public void filterlatestStartSegments_givenValidList_returnFilteresList() {
		List<SegmentPair<String>> result = target
				.filterLatestStartSegments(target.mergeSegments(createFromSegments(), createToSegments()));

		assertEquals(false, result.isEmpty());
		assertEquals(3, result.size());
		Iterator<SegmentPair<String>> iterator = result.iterator();
		assertSegment(iterator.next(), "C", 5, 10, "X", 10, 35);
		assertSegment(iterator.next(), "C", 5, 10, "Y", 20, 30);
		assertSegment(iterator.next(), "D", 5, 20, "Y", 20, 30);
	}

}
