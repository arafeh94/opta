package domain;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import common.domain.AbstractPersistable;

import java.util.ArrayList;
import java.util.List;

@XStreamAlias("Belt")
public class Belt extends AbstractPersistable {
    private float ratioBagPerTimeUnit;
    private String name;
    private Conjunction conjunction;

    public Belt() {
    }

    public Belt(long id, float ratioBagPerTimeUnit, String name) {
        super(id);
        this.ratioBagPerTimeUnit = ratioBagPerTimeUnit;
        this.name = name;
    }

    public Conjunction getConjunction() {
        return conjunction;
    }

    public void setConjunction(Conjunction conjunction) {
        this.conjunction = conjunction;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public float getRatioBagPerTimeUnit() {
        return ratioBagPerTimeUnit;
    }

    public void setRatioBagPerTimeUnit(float ratioBagPerTimeUnit) {
        this.ratioBagPerTimeUnit = ratioBagPerTimeUnit;
    }


}
