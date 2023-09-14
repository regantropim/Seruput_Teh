package id.seruput.api.user;

public interface UserId {

    int identity();

    String asString();

    static UserId of(String userId) {
        return of(Integer.parseInt(userId.substring(2)), UserRole.fromPrefix(userId.substring(0, 2)).orElseThrow());
    }

    static UserId of(int userId, UserRole role) {
        return new UserId() {
            @Override
            public int identity() {
                return userId;
            }

            @Override
            public String toString() {
                return asString();
            }

            @Override
            public String asString() {
                return String.format("%s%03d", role.prefix(), identity());
            }
        };
    }

}
