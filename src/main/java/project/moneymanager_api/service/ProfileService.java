package project.moneymanager_api.service;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import project.moneymanager_api.dto.*;
import project.moneymanager_api.entity.ProfileEntity;
import project.moneymanager_api.repository.ProfileRepository;
import project.moneymanager_api.service.mapper.ProfileMapper;
import project.moneymanager_api.util.JwtUtil;

import java.util.Map;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ProfileService {

    private final ProfileRepository profileRepository;
    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;

    public ProfileDTO register(ProfileDTO profileDTO){
        if (profileRepository.findByEmail(profileDTO.getEmail()).isPresent())
            throw new ResponseStatusException(HttpStatus.CONFLICT, "User already exists.");

        ProfileEntity newProfile = ProfileMapper.getInstance().toEntity(profileDTO);

        newProfile.setActivationToken(UUID.randomUUID().toString());
        newProfile.setIsActive(false);
        newProfile = profileRepository.save(newProfile);

        //email activation goes where...

        return ProfileMapper.getInstance().toDto(newProfile);
    }

    public boolean activateProfile(String activationToken) {
        ProfileEntity profile = profileRepository.findByActivationToken(activationToken).orElse(null);
        if (profile != null) {
            profile.setIsActive(true);
            profileRepository.save(profile);
            return true;
        }
        return false;
    }

    public Map<String, Object> login(AuthDTO authDTO) {
        try{
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authDTO.getEmail(), authDTO.getPassword()));
            //Generate JWT token
            String token = jwtUtil.generateToken(authDTO.getEmail());

            return Map.of("token", token,
                    "user", getCurrentProfileDTO(authDTO.getEmail()));
        } catch (Exception e) {
            throw new RuntimeException("Invalid username/password supplied");
        }
    }

    public ProfileEntity getCurrentProfile(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();

        return profileRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("Profile not found: " + email));
    }

    public ProfileDTO getCurrentProfileDTO(String email){
        ProfileEntity currentProfile;
        if (email == null) currentProfile = getCurrentProfile();
        else currentProfile = profileRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("Profile not found: " + email));

        return ProfileMapper.getInstance().toDto(currentProfile);
    }

    public boolean isProfileActive(String email) {
        return profileRepository.findByEmail(email).map(ProfileEntity::getIsActive).orElse(false);
    }

    /**
     * Delete the current profile and all categories, expenses and incomes associated with it.
     */
    public void deleteCurrentProfile(){
        ProfileEntity currentProfile = getCurrentProfile();
        profileRepository.delete(currentProfile);
    }
}