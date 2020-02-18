package domain;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import common.domain.AbstractPersistable;

import java.util.ArrayList;
import java.util.List;

@XStreamAlias("Conjunction")
public class Conjunction extends AbstractPersistable {
    private Belt parent;
    private Belt child;
    private int maxCapacity;

    public Conjunction() {
    }

    public Conjunction(long id, Belt parent, Belt child, int maxCapacity) {
        super(id);
        this.parent = parent;
        this.child = child;
        this.maxCapacity = maxCapacity;
    }

    public int getMaxCapacity() {
        return maxCapacity;
    }

    public void setMaxCapacity(int maxCapacity) {
        this.maxCapacity = maxCapacity;
    }

    public Belt getChild() {
        return child;
    }

    public void setChild(Belt child) {
        this.child = child;
    }

    public Belt getParent() {
        return parent;
    }

    public void setParent(Belt parent) {
        this.parent = parent;
    }


}
