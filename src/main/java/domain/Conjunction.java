package domain;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import common.domain.AbstractPersistable;

import java.util.ArrayList;
import java.util.List;

@XStreamAlias("Conjunction")
public class Conjunction extends AbstractPersistable {
    private Belt parent;
    private Belt child;

    public Conjunction() {
    }

    public Conjunction(long id, Belt parent, Belt child) {
        super(id);
        this.parent = parent;
        this.child = child;
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
