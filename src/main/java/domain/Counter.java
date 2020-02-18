package domain;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import common.business.TimeTools;
import common.domain.AbstractPersistable;
import common.domain.Labeled;
import domain.json.JSCounter;

import java.time.LocalTime;
import java.util.Calendar;
import java.util.Comparator;
import java.util.Date;
import java.util.Optional;

import static common.business.TimeTools.*;

@XStreamAlias("Counter")
public class Counter extends AbstractPersistable implements Labeled {
    private LocalTime unavailabilityPeriodStartTime;
    private LocalTime unavailabilityPeriodEndTime;
    private int proximity;
    private float ratioPassengerPerTimeUnit;
    private Range range;
    private Belt belt;
    private int positionInRange;

    public Counter() {
    }

    public Counter(long id, LocalTime unavailabilityPeriodStartTime, LocalTime unavailabilityPeriodEndTime, int proximity, float ratioPassengerPerTimeUnit, Range range, Belt belt, int positionInRange) {
        super(id);
        this.unavailabilityPeriodStartTime = unavailabilityPeriodStartTime;
        this.unavailabilityPeriodEndTime = unavailabilityPeriodEndTime;
        this.proximity = proximity;
        this.ratioPassengerPerTimeUnit = ratioPassengerPerTimeUnit;
        this.range = range;
        this.belt = belt;
        this.positionInRange = positionInRange;
    }

    public int getPositionInRange() {
        return positionInRange;
    }

    public void setPositionInRange(int positionInRange) {
        this.positionInRange = positionInRange;
    }

    public Belt getBelt() {
        return belt;
    }

    public void setBelt(Belt belt) {
        this.belt = belt;
    }

    public Range getRange() {
        return range;
    }

    public void setRange(Range range) {
        this.range = range;
    }

    public float getRatioPassengerPerTimeUnit() {
        return ratioPassengerPerTimeUnit;
    }

    public void setRatioPassengerPerTimeUnit(float ratioPassengerPerTimeUnit) {
        this.ratioPassengerPerTimeUnit = ratioPassengerPerTimeUnit;
    }

    public LocalTime getUnavailabilityPeriodStartTime() {
        return unavailabilityPeriodStartTime;
    }

    public void setUnavailabilityPeriodStartTime(LocalTime unavailabilityPeriodStartTime) {
        this.unavailabilityPeriodStartTime = unavailabilityPeriodStartTime;
    }

    public LocalTime getUnavailabilityPeriodEndTime() {
        return unavailabilityPeriodEndTime;
    }

    public void setUnavailabilityPeriodEndTime(LocalTime unavailabilityPeriodEndTime) {
        this.unavailabilityPeriodEndTime = unavailabilityPeriodEndTime;
    }

    public int getProximity() {
        return proximity;
    }

    public void setProximity(int proximity) {
        this.proximity = proximity;
    }

    public boolean isAvailable(Date startDate, Date endDate) {
        LocalTime start = time(startDate);
        LocalTime end = time(endDate);
        return !TimeTools.overlap(this.unavailabilityPeriodStartTime, this.unavailabilityPeriodEndTime, start, end);
    }

    public Counter next() {
        Counter next = range.getCounters().stream().sorted(Comparator.comparingInt(c -> c.positionInRange)).filter(c -> c.positionInRange > positionInRange).findFirst().orElse(null);
        if (next == null && getRange().next() != null) {
            Optional<Counter> counter = getRange().next().getCounters().stream().min(Comparator.comparingInt(c -> c.positionInRange));
            return counter.orElse(null);
        }
        return next;
    }

    public boolean isInSameZone(Counter counter) {
        if (counter == null) return false;
        return this.getRange().getZone().equals(counter.getRange().getZone());
    }


    @Override
    public String getLabel() {
        return "Counter id:" + id + ", ";
    }

    @Override
    public String toString() {
        return getLabel();
    }


}
