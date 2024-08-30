package id.seruput.api.data;

public class CompositeKey<T, U> {

    private T t;
    private U u;

    private CompositeKey(T t, U u) {
        this.t = t;
        this.u = u;
    }

    public T first() {
        return t;
    }

    public CompositeKey<T, U> first(T t) {
        this.t = t;
        return this;
    }

    public U second() {
        return u;
    }

    public CompositeKey<T, U> second(U u) {
        this.u = u;
        return this;
    }

    public static <T, U> CompositeKey<T, U> of(T t, U u) {
        return new CompositeKey<>(t, u);
    }

    @Override
    public String toString() {
        return "CompositeKey{" +
                "t=" + t +
                ", u=" + u +
                '}';
    }

}
