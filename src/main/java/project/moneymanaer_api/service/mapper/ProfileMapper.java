package project.moneymanaer_api.service.mapper;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import project.moneymanaer_api.dto.ProfileDTO;
import project.moneymanaer_api.entity.ProfileEntity;

public class ProfileMapper {

    private static ProfileMapper instance;

    private final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public static ProfileMapper getInstance() {
        if (instance == null) {
            instance = new ProfileMapper();
        }
        return instance;
    }

    public ProfileEntity toEntity(ProfileDTO dto){
        return ProfileEntity.builder()
                .userName(dto.getUserName())
                .email(dto.getEmail())
                .password(passwordEncoder.encode(dto.getPassword()))
                .profileImageUrl(dto.getProfileImageUrl())
                .build();
    }

    public ProfileDTO toDto(ProfileEntity entity){
        return ProfileDTO.builder()
                .id(entity.getId())
                .userName(entity.getUserName())
                .email(entity.getEmail())
                .profileImageUrl(entity.getProfileImageUrl())
                .createdAt(entity.getCreatedAt())
                .updatedAt(entity.getUpdatedAt())
                .build();
    }
}
