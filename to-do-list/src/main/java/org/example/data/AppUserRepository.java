package org.example.data;
import org.example.model.AppUser;

public interface AppUserRepository {
    AppUser findUserById(int appUserId);
    AppUser create(AppUser user);
    AppUser findByUsername(String username);

}
