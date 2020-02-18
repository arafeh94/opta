package domain;

import common.domain.AbstractPersistable;
import common.domain.IdGenerator;
import org.jfree.data.time.TimePeriod;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

public class Zone extends AbstractPersistable {
    private static final long LEVEL_1 = 60 * 60 * 1000 + 30 * 60 * 1000;
    private static final long LEVEL_2 = 60 * 60 * 1000 + 15 * 60 * 1000;
    private static final long LEVEL_3 = 60 * 60 * 1000;
    private static final long LEVEL_4 = 45 * 60 * 1000;
    private static final long LEVEL_5 = 30 * 60 * 1000;
    private static final long LEVEL_6 = 15 * 60 * 1000;

    private String name;
    private int maxPassenger;
    private Terminal terminal;
    private ArrayList<Range> ranges;

    public Zone() {
    }

    public Zone(long id, String name, int maxPassenger, Terminal terminal) {
        super(id);
        this.name = name;
        this.maxPassenger = maxPassenger;
        this.terminal = terminal;
    }

    public Terminal getTerminal() {
        return terminal;
    }

    public void setTerminal(Terminal terminal) {
        this.terminal = terminal;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getMaxPassenger() {
        return maxPassenger;
    }

    public void setMaxPassenger(int maxPassenger) {
        this.maxPassenger = maxPassenger;
    }

    public ArrayList<Range> getRanges() {
        if (ranges == null) ranges = new ArrayList<>();
        return ranges;
    }

    public void setRanges(ArrayList<Range> ranges) {
        this.ranges = ranges;
    }

    public void addRange(Range range){
        if (ranges == null) ranges = new ArrayList<>();
        ranges.add(range);
    }

    /**
     * mazbut ? yes
     * return congestion value of a requirement
     * congestion = sum(max capacity - total passenger) calculate in 15 min time period
     * LEVEL_1 = remaining time < 1h30min => 10%
     * LEVEL_2 = remaining time < 1h15min => 15%
     * LEVEL_3 = remaining time < 1h => 25%
     * LEVEL_4 = remaining time < 45min => 25%
     * LEVEL_5 = remaining time < 30min => 20%
     * LEVEL_6 = remaining time < 15min => 5%
     *
     * @return congestion value
     */
    public int congestion(FlightGroup flightGroup) {
        long remaining = flightGroup.getTotalCounterUsage();
        int totalFlightGroupExpectedPassengers = flightGroup.getTotalPassenger();
        int congestion = 0;
        while (remaining > 0) {
            double expectedPassenger = 0;
            if (LEVEL_1 <= remaining) {
                expectedPassenger = 0.1 * totalFlightGroupExpectedPassengers;
                remaining = LEVEL_2;
            } else if (LEVEL_2 <= remaining) {
                expectedPassenger = 0.15 * totalFlightGroupExpectedPassengers;
                remaining = LEVEL_3;
            } else if (LEVEL_3 <= remaining) {
                expectedPassenger = 0.25 * totalFlightGroupExpectedPassengers;
                remaining = LEVEL_4;
            } else if (LEVEL_4 <= remaining) {
                expectedPassenger = 0.25 * totalFlightGroupExpectedPassengers;
                remaining = LEVEL_5;
            } else if (LEVEL_5 <= remaining) {
                expectedPassenger = 0.2 * totalFlightGroupExpectedPassengers;
                remaining = LEVEL_6;
            } else if (LEVEL_6 <= remaining) {
                expectedPassenger = 0.05 * totalFlightGroupExpectedPassengers;
                remaining = 0;
            } else {
                remaining = 0;
            }
            if (expectedPassenger > this.maxPassenger) {
                congestion += expectedPassenger - this.maxPassenger;
            }
        }
        return congestion;
    }

}
