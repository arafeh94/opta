package domain.json;

import app.CLIStarter;
import common.app.Tools;
import domain.Requirement;

import static common.business.TimeTools.dateToString;

public class JSRequirement {
    public long id;
    public long flight_group_id;
    public long counter_id;
    public int buffer_time;
    public int class_type;
    public String date_start;
    public String date_end;

    public JSRequirement() {
    }

    public JSRequirement(long id, int flight_group_id, int counter_id, int buffer_time, int class_type, String date_start, String date_end) {
        this.id = id;
        this.flight_group_id = flight_group_id;
        this.counter_id = counter_id;
        this.date_start = date_start;
        this.date_end = date_end;
        this.buffer_time = buffer_time;
        this.class_type = class_type;
    }

    public static JSRequirement from(Requirement requirement) {
        JSRequirement jsRequirement = new JSRequirement();
        jsRequirement.id = requirement.getId();
        jsRequirement.flight_group_id = requirement.getFlightGroup().getId();
        jsRequirement.counter_id = requirement.getCounter().getId();
        jsRequirement.date_start = dateToString(requirement.getStartTime());
        jsRequirement.date_end = dateToString(requirement.getStartTime());
        jsRequirement.buffer_time = requirement.getBufferTime();
        jsRequirement.class_type = requirement.getClassType();
        return jsRequirement;
    }

}
