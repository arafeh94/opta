package domain.json;

public class JSRange {
    public long id;
    public long zone_id;
    public int position_in_zone;

    public JSRange() {
    }

    public JSRange(long id, long zone_id, int position_in_zone) {
        this.id = id;
        this.zone_id = zone_id;
        this.position_in_zone = position_in_zone;
    }
}
