package org.example.controller;

import org.example.domain.AppUserService;
import org.example.domain.Result;
import org.example.model.AppUser;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/dashboard")
public class AppUserController {
    private final AppUserService service;

    public AppUserController(AppUserService service) {
        this.service = service;
    }
    @GetMapping("/{id}")
    public ResponseEntity<AppUser> findUserById(@PathVariable int id){
        AppUser user = service.findUserById(id);
        if(user == null){
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(user,HttpStatus.OK);
    }
    @GetMapping("/username/{username}")
    public ResponseEntity<UserDetails> findByUsername(@PathVariable String username){
        UserDetails user = service.loadUserByUsername(username);
        if(user == null){
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(user,HttpStatus.OK);
    }
    @PostMapping
    public ResponseEntity<Object> create(@RequestBody AppUser user){
        Result<AppUser> result = service.create(user.getUsername(),user.getPassword());

        if(!result.isSuccess()){
            return ErrorResponse.build(result);
        }
        return new ResponseEntity<>(result.getPayload(),HttpStatus.OK);
    }

}

