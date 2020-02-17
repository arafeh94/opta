package domain.json;

public class JSZone {
    public int id;
    public String name;
    public int maxPassenger;
    public int terminal_id;

    public JSZone(int id, String name, int maxPassenger, int terminal_id) {
        this.id = id;
        this.name = name;
        this.maxPassenger = maxPassenger;
        this.terminal_id = terminal_id;
    }

    public JSZone() {
    }
}