package id.seruput.api;

import id.seruput.api.database.Database;
import id.seruput.api.user.UserManager;

public interface SeruputTeh {

    UserManager userManager();

    Database database();

}
