package id.seruput.api.data.user;

import id.seruput.api.data.Identity;

public class UserId extends Identity {

    public UserId(int identity, String prefix) {
        super(identity, prefix);
    }

    public static UserId of(String userId) {
        return of(Integer.parseInt(userId.substring(2)), UserRole.fromPrefix(userId.substring(0, 2)).orElseThrow());
    }

    public static UserId of(int userId, UserRole role) {
        return new UserId(userId, role.prefix());
    }

}
