package org.example.controller;

import org.example.domain.AppUserService;
import org.example.domain.Result;
import org.example.model.AppUser;
import org.example.security.JwtConverter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
public class AuthController {
    private final AuthenticationManager authenticationManager;
    private final JwtConverter converter;
    private final AppUserService appUserService;

    public AuthController(AuthenticationManager authenticationManager, JwtConverter converter, AppUserService appUserService) {
        this.authenticationManager = authenticationManager;
        this.converter = converter;
        this.appUserService = appUserService;
    }

    @PostMapping({"/authenticate"})
    public ResponseEntity<Map<String, String>> authenticate(@RequestBody Map<String, String> credentials) {
        UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(credentials.get("username"), credentials.get("password"));

        try {
            Authentication authentication = this.authenticationManager.authenticate(authToken);
            if (authentication.isAuthenticated()) {
                String jwtToken = this.converter.getTokenFromUser((AppUser)authentication.getPrincipal());
                HashMap<String, String> map = new HashMap();
                map.put("jwt_token", jwtToken);
                return new ResponseEntity(map, HttpStatus.OK);
            }
        } catch (AuthenticationException var6) {
            System.out.println(var6);
        }

        return new ResponseEntity(HttpStatus.FORBIDDEN);
    }

    @PostMapping({"/create_account"})
    public ResponseEntity<?> createAccount(@RequestBody Map<String, String> credentials) {
        String username = (String)credentials.get("username");
        String password = (String)credentials.get("password");
        Result<AppUser> result = this.appUserService.create(username, password);
        if (!result.isSuccess()) {
            return new ResponseEntity(result.getErrorMessages(), HttpStatus.BAD_REQUEST);
        } else {
            HashMap<String, Integer> map = new HashMap();
            map.put("appUserId", ((AppUser)result.getPayload()).getAppUserId());
            return new ResponseEntity(map, HttpStatus.CREATED);
        }
    }

    @PostMapping({"/refresh_token"})
    public ResponseEntity<Map<String, String>> refreshToken(@AuthenticationPrincipal AppUser appUser) {
        String jwtToken = this.converter.getTokenFromUser(appUser);
        HashMap<String, String> map = new HashMap();
        map.put("jwt_token", jwtToken);
        return new ResponseEntity(map, HttpStatus.OK);
    }
}
