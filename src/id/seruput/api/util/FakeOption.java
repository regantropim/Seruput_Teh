package id.seruput.api.util;

public class FakeOption<T> {

    private final T value;

    public FakeOption(T value) {
        this.value = value;
    }

    public T get() {
        return value;
    }

    public boolean isPresent() {
        return value != null;
    }

    public T orElse(T other) {
        return isPresent() ? value : other;
    }

    public T orElseGet(FakeSupplier<T> supplier) {
        return isPresent() ? value : supplier.supply();
    }

    public <X extends Throwable> T orElseThrow(FakeSupplier<? extends X> supplier) throws X{
        if (isPresent()) {
            return value;
        } else {
            throw supplier.supply();
        }
    }

    public T orElseThrow() {
        if (isPresent()) {
            return value;
        } else {
            throw new RuntimeException("No value present");
        }
    }

    public static <T> FakeOption<T> of(T value) {
        return new FakeOption<>(value);
    }

    public static <T> FakeOption<T> empty() {
        return new FakeOption<>(null);
    }

}
