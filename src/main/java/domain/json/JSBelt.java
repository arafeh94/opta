package domain.json;

public class JSBelt {
    public long id;
    public String name;
    public int ratio_bag_per_timeunit;

    public JSBelt() {
    }

    public JSBelt(long id, String name, int ratio_bag_per_timeunit) {
        this.id = id;
        this.name = name;
        this.ratio_bag_per_timeunit = ratio_bag_per_timeunit;
    }
}

