package domain;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import common.domain.AbstractPersistable;

import java.util.ArrayList;
import java.util.List;

@XStreamAlias("Terminal")
public class Terminal extends AbstractPersistable {
    private String name;
    private List<Zone> zones;

    public Terminal() {

    }

    public Terminal(long id, String name) {
        super(id);
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Terminal(List<Zone> zones) {
        this.zones = zones;
    }

    public List<Zone> getZones() {
        if (this.zones == null) this.zones = new ArrayList<>();
        return zones;
    }

    public void setZones(List<Zone> zones) {
        this.zones = zones;
    }

    public void addZone(Zone zone) {
        if (this.zones == null) this.zones = new ArrayList<>();
        zones.add(zone);
    }


}
