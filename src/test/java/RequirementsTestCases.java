import common.business.Interval;
import domain.Counter;
import domain.Range;
import domain.Requirement;
import domain.Zone;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Optional;

import static common.business.TimeTools.fromTime;

public class RequirementsTestCases {
    @Test
    public void overlap() {
        Counter c1 = new Counter(1, null, null, 1, 1, null, null, 1);
        Counter c2 = new Counter(2, null, null, 1, 1, null, null, 1);
        Requirement r1 = new Requirement(1, null, fromTime("08:00:00"), fromTime("10:45:00"), 10, 1);
        Requirement r2 = new Requirement(2, null, fromTime("10:40:00"), fromTime("12:00:00"), 10, 1);
        System.out.println(r1.isOverlappedWith(r2));
        System.out.println(r2.isOverlappedWith(r1));
    }

    @Test
    public void timeStampOverlap() {
        Requirement r11 = new Requirement(1, null, fromTime("08:00:00"), fromTime("10:45:00"), 10, 1);
        Requirement r12 = new Requirement(1, null, fromTime("09:00:00"), fromTime("11:45:00"), 10, 1);
        Requirement r13 = new Requirement(1, null, fromTime("10:00:00"), fromTime("12:45:00"), 10, 1);

        Requirement r21 = new Requirement(1, null, fromTime("15:00:00"), fromTime("16:45:00"), 10, 1);
        Requirement r22 = new Requirement(1, null, fromTime("14:00:00"), fromTime("15:45:00"), 10, 1);
        Requirement r23 = new Requirement(1, null, fromTime("16:00:00"), fromTime("16:45:00"), 10, 1);

        ArrayList<Requirement> requirements = new ArrayList<>();
        requirements.add(r11);
        requirements.add(r12);
        requirements.add(r13);
        requirements.add(r21);
        requirements.add(r22);
        requirements.add(r23);

        Range range = new Range(1, new Zone(1, "samira", 1, null), 1);
        Counter c1 = new Counter(11, null, null, 1, 1, range, null, 1);
        r11.setCounter(c1);
        Counter c2 = new Counter(12, null, null, 1, 1, range, null, 2);
        r12.setCounter(c2);
        Counter c3 = new Counter(13, null, null, 1, 1, range, null, 3);
        r13.setCounter(c3);
        Counter c4 = new Counter(14, null, null, 1, 1, range, null, 4);
        r21.setCounter(c4);
        Counter c5 = new Counter(15, null, null, 1, 1, range, null, 5);
        r22.setCounter(c5);
        Counter c6 = new Counter(16, null, null, 1, 1, range, null, 6);
        r23.setCounter(c6);
        range.addCounter(c1);
        range.addCounter(c2);
        range.addCounter(c3);
        range.addCounter(c4);
        range.addCounter(c5);
        range.addCounter(c6);


        HashMap<Interval, ArrayList<Counter>> overlappedCounters = new HashMap<>();
        for (Requirement requirement : requirements) {
            Optional<Interval> interval = overlappedCounters.keySet().stream()
                    .filter(t -> t.overlapped(requirement.getInterval())).findAny();
            if (interval.isPresent()) {
                overlappedCounters.get(interval.get()).add(requirement.getCounter());
                interval.get().expand(requirement.getInterval());
            } else {
                ArrayList<Counter> clist = new ArrayList<>();
                clist.add(requirement.getCounter());
                overlappedCounters.put(requirement.getInterval(), new ArrayList<>(clist));
            }
        }


        /**
         * if the next counter of the current one inside the list of all counter
         * that means good
         * if not
         * this is an error
         * if there is more than 1 error
         * flightgroup is unplanned
         */
        for (Interval interval : overlappedCounters.keySet()) {
            ArrayList<Counter> overlappedInTimestamp = overlappedCounters.get(interval);
            int errors = 0;
            for (Counter counter : overlappedInTimestamp) {
                if (!overlappedInTimestamp.contains(counter.next())) {
                    errors += 1;
                }
            }
            if (errors > 1) {
                System.out.println("unplanned");
            }
        }
        System.out.println(overlappedCounters);


    }

}