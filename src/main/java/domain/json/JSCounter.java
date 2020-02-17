package domain.json;

public class JSCounter {
    public long id;
    public long range_id;
    public long belt_id;
    public String unavailabilityPeriodStartTime;
    public String unavailabilityPeriodEndTime;
    public float ratio_passenger_per_timeunit;
    public int proximity;

    public JSCounter(long id, long range_id, long belt_id, String unavailabilityPeriodStartTime, String unavailabilityPeriodEndTime, float ratio_passenger_per_timeunit, int proximity, int total_passengers) {
        this.id = id;
        this.range_id = range_id;
        this.belt_id = belt_id;
        this.unavailabilityPeriodStartTime = unavailabilityPeriodStartTime;
        this.unavailabilityPeriodEndTime = unavailabilityPeriodEndTime;
        this.ratio_passenger_per_timeunit = ratio_passenger_per_timeunit;
        this.proximity = proximity;
    }

    public JSCounter() {
    }
}
