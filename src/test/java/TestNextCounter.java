import common.domain.IdGenerator;
import domain.Counter;
import domain.Range;
import domain.Terminal;
import org.junit.jupiter.api.Test;

public class TestNextCounter {
    @Test
    public void next() {
        Counter counter = new Counter(1, null, null, 1, 1, new Range(1, null, 1), null, 1);
        Counter counter2 = new Counter(1, null, null, 1, 1, new Range(1, null, 1), null, 2);
        System.out.println(counter.next());
    }
}