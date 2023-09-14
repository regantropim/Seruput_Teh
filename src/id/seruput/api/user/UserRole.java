package id.seruput.api.user;

import id.seruput.api.util.FakeOption;

public enum UserRole {
    ADMIN("Admin", "AD"),
    USER("User", "CU");

    private final String name;
    private final String prefix;

    UserRole(String name, String prefix) {
        this.name = name;
        this.prefix = prefix;
    }

    public String getName() {
        return name;
    }

    public String prefix() {
        return prefix;
    }

    public static FakeOption<UserRole> fromName(String name) {
        switch (name.toLowerCase()) {
            case "admin":
                return FakeOption.of(ADMIN);
            case "user":
                return FakeOption.of(USER);
            default:
                return FakeOption.empty();
        }
    }

    public static FakeOption<UserRole> fromPrefix(String prefix) {
        switch (prefix.toLowerCase()) {
            case "ad":
                return FakeOption.of(ADMIN);
            case "cu":
                return FakeOption.of(USER);
            default:
                return FakeOption.empty();
        }
    }

    @Override
    public String toString() {
        return name;
    }

}
