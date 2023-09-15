package id.seruput.api.data.user;

import id.seruput.api.util.FakeOption;

public enum UserGender {
    MALE("Male"),
    FEMALE("Female");

    private final String name;

    UserGender(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    /**
     * Find UserGender by name.
     *
     * @param name Name of UserGender.
     * @return {@link FakeOption} of {@link UserGender}.
     */
    public static FakeOption<UserGender> fromName(String name) {
        switch (name.toLowerCase()) {
            case "female":
                return FakeOption.of(FEMALE);
            case "male":
                return FakeOption.of(MALE);
            default:
                return FakeOption.empty();
        }
    }

    @Override
    public String toString() {
        return name;
    }

}
