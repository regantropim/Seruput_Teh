package id.seruput.api.data;

public abstract class Identity {

    private final int identity;
    private final String prefix;

    public Identity(int identity, String prefix) {
        this.identity = identity;
        this.prefix = prefix;
    }

    public String asString() {
        return String.format("%s%03d", prefix(), identity());
    }

    public int identity() {
        return identity;
    }

    public String prefix() {
        return prefix;
    }

    @Override
    public boolean equals(Object obj) {
        if (super.equals(obj)) {
            return true;
        } else if (obj instanceof Identity) {
            Identity other = (Identity) obj;
            return identity() == other.identity() && prefix().equals(other.prefix());
        } else {
            return false;
        }
    }

}
