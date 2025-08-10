package project.moneymanaer_api.service;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import project.moneymanaer_api.dto.AuthDTO;
import project.moneymanaer_api.dto.ProfileDTO;
import project.moneymanaer_api.entity.ProfileEntity;
import project.moneymanaer_api.repository.ProfileRepository;
import project.moneymanaer_api.service.mapper.ProfileMapper;

@Service
@RequiredArgsConstructor
public class ProfileService {

    private final ProfileRepository profileRepository;

    public ProfileDTO register(ProfileDTO profileDTO){
        if (profileRepository.findByUserName(profileDTO.getUserName()).isPresent())
            throw new ResponseStatusException(HttpStatus.CONFLICT, "User already exists.");
        ProfileEntity newProfile = ProfileMapper.getInstance().toEntity(profileDTO);

        newProfile = profileRepository.save(newProfile);

        return ProfileMapper.getInstance().toDto(newProfile);
    }

    public ProfileDTO login(AuthDTO authDTO){
        ProfileEntity profile = profileRepository.findByUserName(authDTO.getUserName()).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.CONFLICT, "User not found.")
        );
        return ProfileMapper.getInstance().toDto(profile);
    }

    public ProfileEntity getCurrentProfile(String userName){
        return profileRepository.findByUserName(userName).orElse(null);
    }

}
