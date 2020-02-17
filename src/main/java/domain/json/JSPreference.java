package domain.json;

public class JSPreference {
    public int id;
    public int flight_group_id;
    public int zone_id;
    public int points;

    public JSPreference() {
    }

    public JSPreference(int id, int flight_group_id, int zone_id, int points) {
        this.id = id;
        this.flight_group_id = flight_group_id;
        this.zone_id = zone_id;
        this.points = points;
    }
}
