package domain;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import common.domain.AbstractPersistable;

import java.util.ArrayList;
import java.util.List;

@XStreamAlias("Belt")
public class Belt extends AbstractPersistable {
    private int ratioBagPerTimeUnit;
    private String name;
    private Conjunction conjunction;

    public Belt() {
    }

    public Belt(long id, int ratioBagPerTimeUnit, String name) {
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

    public int getRatioBagPerTimeUnit() {
        return ratioBagPerTimeUnit;
    }

    public void setRatioBagPerTimeUnit(int ratioBagPerTimeUnit) {
        this.ratioBagPerTimeUnit = ratioBagPerTimeUnit;
    }


}
