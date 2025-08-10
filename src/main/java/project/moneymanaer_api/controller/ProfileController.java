package project.moneymanaer_api.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import project.moneymanaer_api.dto.AuthDTO;
import project.moneymanaer_api.dto.ProfileDTO;
import project.moneymanaer_api.service.ProfileService;

@RestController
@RequiredArgsConstructor
public class ProfileController {

    private final ProfileService profileService;

    @PostMapping("/register")
    public ResponseEntity<ProfileDTO> register(@RequestBody ProfileDTO profileDTO){
        ProfileDTO newProfile = profileService.register(profileDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(newProfile);
    }

    @PostMapping("/login")
    public ResponseEntity<ProfileDTO> login(@RequestBody AuthDTO authDTO){
        ProfileDTO profile = profileService.login(authDTO);
       return ResponseEntity.ok(profile);
    }
}