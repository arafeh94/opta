import domain.Counter;
import domain.Range;
import domain.Requirement;
import org.junit.jupiter.api.Test;

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
}