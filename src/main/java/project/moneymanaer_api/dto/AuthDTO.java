package project.moneymanaer_api.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Dto for uer authentication (only with the required fields)
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AuthDTO {
    private String userName;
    private String email;
    private String password;
}