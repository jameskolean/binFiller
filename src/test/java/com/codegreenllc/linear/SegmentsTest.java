package com.codegreenllc.linear;

import static org.junit.Assert.assertEquals;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.junit.Test;

public class SegmentsTest {
	public static class TestInteger {

		Comparator<Integer> comparatorInteger = new Comparator<Integer>() {
			@Override
			public int compare(Integer o1, Integer o2) {
				return o1.compareTo(o2);
			}
		};

		List<Segment<String, Integer>> createFromSegments() {
			List<Segment<String, Integer>> result = new ArrayList<Segment<String, Integer>>();
			result.add(new SegmentImpl<String, Integer>("A", 0, 10));
			result.add(new SegmentImpl<String, Integer>("B", 0, 20));
			result.add(new SegmentImpl<String, Integer>("C", 5, 10));
			result.add(new SegmentImpl<String, Integer>("D", 5, 20));
			return result;
		}

		List<Segment<String, Integer>> createToSegments() {
			List<Segment<String, Integer>> result = new ArrayList<Segment<String, Integer>>();
			result.add(new SegmentImpl<String, Integer>("X", 10, 35));
			result.add(new SegmentImpl<String, Integer>("Y", 20, 30));
			result.add(new SegmentImpl<String, Integer>("Z", 5, 40));
			return result;
		}

		Segments<String, Integer> target = new Segments<String, Integer>();

		@Test(expected = IllegalArgumentException.class)
		public void mergeSegments_givenNulls_throwsIAE() {
			target.mergeSegments(null, null, null);
		}

		@Test(expected = IllegalArgumentException.class)
		public void filterEarliestEndSegments_givenNulls_throwsIAE() {
			target.filterEarliestEndSegments(null, null);
		}

		@Test(expected = IllegalArgumentException.class)
		public void filterEarliestStartSegments_givenNulls_throwsIAE() {
			target.filterEarliestStartSegments(null, null);
		}

		@Test(expected = IllegalArgumentException.class)
		public void filterLatestEndSegments_givenNulls_throwsIAE() {
			target.filterLatestEndSegments(null, null);
		}

		@Test(expected = IllegalArgumentException.class)
		public void filterLatestStartSegments_givenNulls_throwsIAE() {
			target.filterLatestStartSegments(null, null);
		}

		@Test(expected = IllegalArgumentException.class)
		public void mergeSegments_givenFirstNull_throwsIAE() {
			target.mergeSegments(null, new ArrayList<Segment<String, Integer>>(), comparatorInteger);
		}

		@Test(expected = IllegalArgumentException.class)
		public void mergeSegments_givenSeconedNull_throwsIAE() {
			target.mergeSegments(new ArrayList<Segment<String, Integer>>(), null, comparatorInteger);
		}

		@Test()
		public void mergeSegments_givenEmptyLists_returnEmptyList() {
			List<SegmentPair<String, Integer>> result = target.mergeSegments(new ArrayList<Segment<String, Integer>>(),
					new ArrayList<Segment<String, Integer>>(), comparatorInteger);

			assertEquals(result.isEmpty(), true);
		}

		@Test()
		public void mergeSegments_givenEmptyFromList_returnEmptyList() {
			List<SegmentPair<String, Integer>> result = target.mergeSegments(new ArrayList<Segment<String, Integer>>(),
					createToSegments(), comparatorInteger);

			assertEquals(result.isEmpty(), true);
		}

		@Test()
		public void mergeSegments_givenEmptyToList_returnEmptyList() {
			List<SegmentPair<String, Integer>> result = target.mergeSegments(createFromSegments(),
					new ArrayList<Segment<String, Integer>>(), comparatorInteger);

			assertEquals(result.isEmpty(), true);
		}

		@Test()
		public void mergeSegments_givenReversedList_returnEmptyList() {
			List<SegmentPair<String, Integer>> result = target.mergeSegments(createToSegments(), createFromSegments(),
					comparatorInteger);
			assertEquals(result.isEmpty(), true);
		}

		@SuppressWarnings("unused")
		private void inspect(List<SegmentPair<String, Integer>> segmentPairs) {
			for (SegmentPair<String, Integer> segmentPair : segmentPairs) {
				inspect(segmentPair);
			}

		}

		@SuppressWarnings("unused")
		private void inspect(SegmentPair<String, Integer> segmentPair) {
			System.out.println(String.format("From: %s %d:%d To: %s %d:%d", segmentPair.getFirstSegment().getId(),
					segmentPair.getFirstSegment().getStart(), segmentPair.getFirstSegment().getEnd(),
					segmentPair.getSecondSegment().getId(), segmentPair.getSecondSegment().getStart(),
					segmentPair.getSecondSegment().getEnd()));

		}

		@Test()
		public void mergeSegments_givenValidList_returnMegredList() {
			List<SegmentPair<String, Integer>> result = target.mergeSegments(createFromSegments(), createToSegments(),
					comparatorInteger);

			assertEquals(false, result.isEmpty());
			assertEquals(6, result.size());
			Iterator<SegmentPair<String, Integer>> iterator = result.iterator();
			assertSegment(iterator.next(), "A", 0, 10, "X", 10, 35);
			assertSegment(iterator.next(), "A", 0, 10, "Y", 20, 30);
			assertSegment(iterator.next(), "B", 0, 20, "Y", 20, 30);
			assertSegment(iterator.next(), "C", 5, 10, "X", 10, 35);
			assertSegment(iterator.next(), "C", 5, 10, "Y", 20, 30);
			assertSegment(iterator.next(), "D", 5, 20, "Y", 20, 30);

		}

		private void assertSegment(SegmentPair<String, Integer> segmentPair, String id1, Integer start1, Integer end1,
				String id2, Integer start2, Integer end2) {
			assertEquals(id1, segmentPair.getFirstSegment().getId());
			assertEquals(id2, segmentPair.getSecondSegment().getId());
			assertEquals(start1, segmentPair.getFirstSegment().getStart());
			assertEquals(start2, segmentPair.getSecondSegment().getStart());
			assertEquals(end1, segmentPair.getFirstSegment().getEnd());
			assertEquals(end2, segmentPair.getSecondSegment().getEnd());
		}

		@Test()
		public void filterEarliestEndSegments_givenValidList_returnFilteresList() {
			List<SegmentPair<String, Integer>> result = target.filterEarliestEndSegments(
					target.mergeSegments(createFromSegments(), createToSegments(), comparatorInteger),
					comparatorInteger);

			assertEquals(false, result.isEmpty());
			assertEquals(4, result.size());
			Iterator<SegmentPair<String, Integer>> iterator = result.iterator();
			assertSegment(iterator.next(), "A", 0, 10, "Y", 20, 30);
			assertSegment(iterator.next(), "B", 0, 20, "Y", 20, 30);
			assertSegment(iterator.next(), "C", 5, 10, "Y", 20, 30);
			assertSegment(iterator.next(), "D", 5, 20, "Y", 20, 30);
		}

		@Test()
		public void filterEarliestStartSegments_givenValidList_returnFilteresList() {
			List<SegmentPair<String, Integer>> result = target.filterEarliestStartSegments(
					target.mergeSegments(createFromSegments(), createToSegments(), comparatorInteger),
					comparatorInteger);
			assertEquals(false, result.isEmpty());
			assertEquals(3, result.size());
			Iterator<SegmentPair<String, Integer>> iterator = result.iterator();
			assertSegment(iterator.next(), "A", 0, 10, "X", 10, 35);
			assertSegment(iterator.next(), "A", 0, 10, "Y", 20, 30);
			assertSegment(iterator.next(), "B", 0, 20, "Y", 20, 30);
		}

		@Test()
		public void filterLatestEndSegments_givenValidList_returnFilteresList() {
			List<SegmentPair<String, Integer>> result = target.filterLatestEndSegments(
					target.mergeSegments(createFromSegments(), createToSegments(), comparatorInteger),
					comparatorInteger);

			assertEquals(false, result.isEmpty());
			assertEquals(2, result.size());
			Iterator<SegmentPair<String, Integer>> iterator = result.iterator();
			assertSegment(iterator.next(), "A", 0, 10, "X", 10, 35);
			assertSegment(iterator.next(), "C", 5, 10, "X", 10, 35);
		}

		@Test()
		public void filterlatestStartSegments_givenValidList_returnFilteresList() {
			List<SegmentPair<String, Integer>> result = target.filterLatestStartSegments(
					target.mergeSegments(createFromSegments(), createToSegments(), comparatorInteger),
					comparatorInteger);

			assertEquals(false, result.isEmpty());
			assertEquals(3, result.size());
			Iterator<SegmentPair<String, Integer>> iterator = result.iterator();
			assertSegment(iterator.next(), "C", 5, 10, "X", 10, 35);
			assertSegment(iterator.next(), "C", 5, 10, "Y", 20, 30);
			assertSegment(iterator.next(), "D", 5, 20, "Y", 20, 30);
		}

	}
	public static class TestDate {
		SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
		
		Comparator<Date> comparatorDate = new Comparator<Date>() {
			@Override
			public int compare(Date o1, Date o2) {
				return o1.compareTo(o2);
			}
		};

		Date date(String dateString) {
			try {
				return dateFormat.parse(dateString);
			} catch (ParseException e) {
				throw new RuntimeException(e);
			}
		}
		List<Segment<String, Date>> createFromSegments() {
				List<Segment<String, Date>> result = new ArrayList<Segment<String, Date>>();
				result.add(new SegmentImpl<String, Date>("A", date("01/1/2017"), date("01/10/2017")));
				result.add(new SegmentImpl<String, Date>("B", date("01/1/2017"), date("01/20/2017")));
				result.add(new SegmentImpl<String, Date>("C", date("01/5/2017"), date("01/10/2017")));
				result.add(new SegmentImpl<String, Date>("D", date("01/5/2017"), date("01/20/2017")));
				return result;
		}

		List<Segment<String, Date>> createToSegments() {
				List<Segment<String, Date>> result = new ArrayList<Segment<String, Date>>();
			result.add(new SegmentImpl<String, Date>("X", date("01/10/2017"), date("02/10/2017")));
			result.add(new SegmentImpl<String, Date>("Y", date("01/20/2017"), date("01/30/2017")));
			result.add(new SegmentImpl<String, Date>("Z", date("01/5/2017"), date("01/40/2017")));
			return result;
		}

		Segments<String, Date> target = new Segments<String, Date>();

		@Test(expected = IllegalArgumentException.class)
		public void mergeSegments_givenNulls_throwsIAE() {
			target.mergeSegments(null, null, null);
		}

		@Test(expected = IllegalArgumentException.class)
		public void filterEarliestEndSegments_givenNulls_throwsIAE() {
			target.filterEarliestEndSegments(null, null);
		}

		@Test(expected = IllegalArgumentException.class)
		public void filterEarliestStartSegments_givenNulls_throwsIAE() {
			target.filterEarliestStartSegments(null, null);
		}

		@Test(expected = IllegalArgumentException.class)
		public void filterLatestEndSegments_givenNulls_throwsIAE() {
			target.filterLatestEndSegments(null, null);
		}

		@Test(expected = IllegalArgumentException.class)
		public void filterLatestStartSegments_givenNulls_throwsIAE() {
			target.filterLatestStartSegments(null, null);
		}

		@Test(expected = IllegalArgumentException.class)
		public void mergeSegments_givenFirstNull_throwsIAE() {
			target.mergeSegments(null, new ArrayList<Segment<String, Date>>(), comparatorDate);
		}

		@Test(expected = IllegalArgumentException.class)
		public void mergeSegments_givenSeconedNull_throwsIAE() {
			target.mergeSegments(new ArrayList<Segment<String, Date>>(), null, comparatorDate);
		}

		@Test()
		public void mergeSegments_givenEmptyLists_returnEmptyList() {
			List<SegmentPair<String, Date>> result = target.mergeSegments(new ArrayList<Segment<String, Date>>(),
					new ArrayList<Segment<String, Date>>(), comparatorDate);

			assertEquals(result.isEmpty(), true);
		}

		@Test()
		public void mergeSegments_givenEmptyFromList_returnEmptyList() {
			List<SegmentPair<String, Date>> result = target.mergeSegments(new ArrayList<Segment<String, Date>>(),
					createToSegments(), comparatorDate);

			assertEquals(result.isEmpty(), true);
		}

		@Test()
		public void mergeSegments_givenEmptyToList_returnEmptyList() {
			List<SegmentPair<String, Date>> result = target.mergeSegments(createFromSegments(),
					new ArrayList<Segment<String, Date>>(), comparatorDate);

			assertEquals(result.isEmpty(), true);
		}

		@Test()
		public void mergeSegments_givenReversedList_returnEmptyList() {
			List<SegmentPair<String, Date>> result = target.mergeSegments(createToSegments(), createFromSegments(),
					comparatorDate);
			assertEquals(result.isEmpty(), true);
		}

		@SuppressWarnings("unused")
		private void inspect(List<SegmentPair<String, Date>> segmentPairs) {
			for (SegmentPair<String, Date> segmentPair : segmentPairs) {
				inspect(segmentPair);
			}

		}

		@SuppressWarnings("unused")
		private void inspect(SegmentPair<String, Date> segmentPair) {
			System.out.println(String.format("From: %s %d:%d To: %s %d:%d", segmentPair.getFirstSegment().getId(),
					segmentPair.getFirstSegment().getStart(), segmentPair.getFirstSegment().getEnd(),
					segmentPair.getSecondSegment().getId(), segmentPair.getSecondSegment().getStart(),
					segmentPair.getSecondSegment().getEnd()));

		}

		@Test()
		public void mergeSegments_givenValidList_returnMegredList() {
			List<SegmentPair<String, Date>> result = target.mergeSegments(createFromSegments(), createToSegments(),
					comparatorDate);

			assertEquals(false, result.isEmpty());
			assertEquals(6, result.size());
			Iterator<SegmentPair<String, Date>> iterator = result.iterator();
			assertSegment(iterator.next(), "A", date("01/1/2017"), date("01/10/2017"), "X", date("01/10/2017"), date("02/10/2017"));
			assertSegment(iterator.next(), "A", date("01/1/2017"), date("01/10/2017"), "Y", date("01/20/2017"), date("01/30/2017"));
			assertSegment(iterator.next(), "B", date("01/1/2017"), date("01/20/2017"), "Y", date("01/20/2017"), date("01/30/2017"));
			assertSegment(iterator.next(), "C", date("01/5/2017"), date("01/10/2017"), "X", date("01/10/2017"), date("02/10/2017"));
			assertSegment(iterator.next(), "C", date("01/5/2017"), date("01/10/2017"), "Y", date("01/20/2017"), date("01/30/2017"));
			assertSegment(iterator.next(), "D", date("01/5/2017"), date("01/20/2017"), "Y", date("01/20/2017"), date("01/30/2017"));

		}

		private void assertSegment(SegmentPair<String, Date> segmentPair, String id1, Date start1, Date end1,
				String id2, Date start2, Date end2) {
			assertEquals(id1, segmentPair.getFirstSegment().getId());
			assertEquals(id2, segmentPair.getSecondSegment().getId());
			assertEquals(start1, segmentPair.getFirstSegment().getStart());
			assertEquals(start2, segmentPair.getSecondSegment().getStart());
			assertEquals(end1, segmentPair.getFirstSegment().getEnd());
			assertEquals(end2, segmentPair.getSecondSegment().getEnd());
		}

		@Test()
		public void filterEarliestEndSegments_givenValidList_returnFilteresList() {
			List<SegmentPair<String, Date>> result = target.filterEarliestEndSegments(
					target.mergeSegments(createFromSegments(), createToSegments(), comparatorDate),
					comparatorDate);

			assertEquals(false, result.isEmpty());
			assertEquals(4, result.size());
			Iterator<SegmentPair<String, Date>> iterator = result.iterator();
			assertSegment(iterator.next(), "A", date("01/1/2017"), date("01/10/2017"), "Y", date("01/20/2017"), date("01/30/2017"));
			assertSegment(iterator.next(), "B", date("01/1/2017"), date("01/20/2017"), "Y", date("01/20/2017"), date("01/30/2017"));
			assertSegment(iterator.next(), "C", date("01/5/2017"), date("01/10/2017"), "Y", date("01/20/2017"), date("01/30/2017"));
			assertSegment(iterator.next(), "D", date("01/5/2017"), date("01/20/2017"), "Y", date("01/20/2017"), date("01/30/2017"));
		}

		@Test()
		public void filterEarliestStartSegments_givenValidList_returnFilteresList() {
			List<SegmentPair<String, Date>> result = target.filterEarliestStartSegments(
					target.mergeSegments(createFromSegments(), createToSegments(), comparatorDate),
					comparatorDate);
			assertEquals(false, result.isEmpty());
			assertEquals(3, result.size());
			Iterator<SegmentPair<String, Date>> iterator = result.iterator();
			assertSegment(iterator.next(), "A", date("01/1/2017"), date("01/10/2017"), "X", date("01/10/2017"), date("02/10/2017"));
			assertSegment(iterator.next(), "A", date("01/1/2017"), date("01/10/2017"), "Y", date("01/20/2017"), date("01/30/2017"));
			assertSegment(iterator.next(), "B", date("01/1/2017"), date("01/20/2017"), "Y", date("01/20/2017"), date("01/30/2017"));
		}

		@Test()
		public void filterLatestEndSegments_givenValidList_returnFilteresList() {
			List<SegmentPair<String, Date>> result = target.filterLatestEndSegments(
					target.mergeSegments(createFromSegments(), createToSegments(), comparatorDate),
					comparatorDate);

			assertEquals(false, result.isEmpty());
			assertEquals(2, result.size());
			Iterator<SegmentPair<String, Date>> iterator = result.iterator();
			assertSegment(iterator.next(), "A", date("01/1/2017"), date("01/10/2017"), "X", date("01/10/2017"), date("02/10/2017"));
			assertSegment(iterator.next(), "C",  date("01/5/2017"), date("01/10/2017"), "X", date("01/10/2017"), date("02/10/2017"));
		}

		@Test()
		public void filterlatestStartSegments_givenValidList_returnFilteresList() {
			List<SegmentPair<String, Date>> result = target.filterLatestStartSegments(
					target.mergeSegments(createFromSegments(), createToSegments(), comparatorDate),
					comparatorDate);

			assertEquals(false, result.isEmpty());
			assertEquals(3, result.size());
			Iterator<SegmentPair<String, Date>> iterator = result.iterator();
			assertSegment(iterator.next(), "C", date("01/5/2017"), date("01/10/2017"), "X", date("01/10/2017"), date("02/10/2017"));
			assertSegment(iterator.next(), "C", date("01/5/2017"), date("01/10/2017"), "Y", date("01/20/2017"), date("01/30/2017"));
			assertSegment(iterator.next(), "D", date("01/5/2017"), date("01/20/2017"), "Y", date("01/20/2017"), date("01/30/2017"));
		}

	}
}
