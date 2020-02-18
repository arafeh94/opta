package domain.json;

public class JSZone {
    public int id;
    public String name;
    public int max_passenger;
    public int terminal_id;

    public JSZone(int id, String name, int max_passenger, int terminal_id) {
        this.id = id;
        this.name = name;
        this.max_passenger = max_passenger;
        this.terminal_id = terminal_id;
    }

    public JSZone() {
    }
}