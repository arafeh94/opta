package domain.json;

import domain.FlightGroup;

import java.util.Optional;

public class JSFlightGroup {
    public long id;
    public String name;
    public int total_passengers;
    public boolean planned;


    public static JSFlightGroup from(FlightGroup flightGroup) {
        JSFlightGroup jsFlightGroup = new JSFlightGroup();
        jsFlightGroup.id = flightGroup.getId();
        jsFlightGroup.name = flightGroup.getName();
        jsFlightGroup.total_passengers = flightGroup.getTotalPassenger();
        jsFlightGroup.planned = flightGroup.getPlanned();
        return jsFlightGroup;
    }
}
