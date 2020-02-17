package domain.json;

public class JSConjunction {
    public long id;
    public long belt_id_parent;
    public long belt_id_child;

    public JSConjunction() {
    }

    public JSConjunction(long id, long belt_id_parent, long belt_id_child) {
        this.id = id;
        this.belt_id_parent = belt_id_parent;
        this.belt_id_child = belt_id_child;
    }
}
