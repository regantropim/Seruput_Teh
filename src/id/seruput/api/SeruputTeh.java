package id.seruput.api;

import id.seruput.api.database.Database;
import id.seruput.api.data.user.UserManager;

public interface SeruputTeh {

    UserManager userManager();

    Database database();

}
