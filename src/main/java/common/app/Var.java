package common.app;

public class Var<T> {
    public T value;

    public Var(T value) {
        this.value = value;
    }

    public Var() {

    }

    public T get() {
        return value;
    }

    public void set(T value) {
        this.value = value;
    }

    public static <T> Var<T> of(T value){
        return new Var<>(value);
    }
}
