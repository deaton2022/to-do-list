package org.example.domain;

import org.example.data.AppUserRepository;
import org.example.model.AppUser;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AppUserService implements UserDetailsService {

    private final AppUserRepository repository;
    private final PasswordEncoder encoder;

    public AppUserService(AppUserRepository repository,
                          PasswordEncoder encoder) {
        this.repository = repository;
        this.encoder = encoder;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        AppUser appUser = repository.findByUsername(username);

        if (appUser == null || !appUser.isEnabled()) {
            throw new UsernameNotFoundException(username + " not found");
        }

        return appUser;
    }
    public AppUser findUserById(int appUserId) { return repository.findUserById(appUserId);}

    public Result<AppUser> create(String username, String password) {
        Result<AppUser> result = validate(username, password);
        if (!result.isSuccess()) {
            return result;
        }

        password = encoder.encode(password);

        AppUser appUser = new AppUser(0, username, password, true, List.of("USER"));

        try {
            appUser = repository.create(appUser);
            result.setPayload(appUser);
        } catch (DuplicateKeyException e) {
            result.addErrorMessage("The provided username already exists", ResultType.INVALID);
        }

        return result;
    }

    private Result<AppUser> validate(String username, String password) {
        Result<AppUser> result = new Result<>();
        if (username == null || username.isBlank()) {
            result.addErrorMessage("Username is required.", ResultType.INVALID);
            return result;
        }

        if (password == null) {
            result.addErrorMessage("Password is required.", ResultType.INVALID);
            return result;
        }

        if (username.length() > 50) {
            result.addErrorMessage("Username must be less than 50 characters.", ResultType.INVALID);
        }

        if (!isValidPassword(password)) {
            result.addErrorMessage("Password must be at least 8 character and contain a digit," +
                    " a letter, and a non-digit/non-letter.", ResultType.INVALID);
        }
        if(username.length() > 50){
            result.addErrorMessage("Username must not exceed 50 characters. ",ResultType.INVALID);
        }
        if(!(username.contains("@"))){
            result.addErrorMessage("Email must contain '@'.",ResultType.INVALID);
        }

        return result;
    }

    private boolean isValidPassword(String password) {
        if (password.length() < 8) {
            return false;
        }

        int digits = 0;
        int letters = 0;
        int others = 0;
        for (char c : password.toCharArray()) {
            if (Character.isDigit(c)) {
                digits++;
            } else if (Character.isLetter(c)) {
                letters++;
            } else {
                others++;
            }
        }

        return digits > 0 && letters > 0 && others > 0;
    }

}
