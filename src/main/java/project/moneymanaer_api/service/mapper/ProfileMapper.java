package project.moneymanaer_api.service.mapper;

import project.moneymanaer_api.dto.ProfileDTO;
import project.moneymanaer_api.entity.ProfileEntity;

public class ProfileMapper {

    private static ProfileMapper instance;

    public static ProfileMapper getInstance() {
        if (instance == null) {
            instance = new ProfileMapper();
        }
        return instance;
    }

    public ProfileEntity toEntity(ProfileDTO dto){
        return ProfileEntity.builder()
                .userName(dto.getUserName())
                .password(dto.getPassword())
                .profileImageUrl(dto.getProfileImageUrl())
                .build();
    }

    public ProfileDTO toDto(ProfileEntity entity){
        return ProfileDTO.builder()
                .id(entity.getId())
                .userName(entity.getUserName())
                .profileImageUrl(entity.getProfileImageUrl())
                .createdAt(entity.getCreatedAt())
                .updatedAt(entity.getUpdatedAt())
                .build();
    }
}
