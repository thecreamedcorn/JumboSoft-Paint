package utils;

public class Ref<T> {
    private T data;

    public Ref(T data) {
        this.data = data;
    }

    public T get() {
        return data;
    }

    public void set(T data) {
        this.data = data;
    }
}
