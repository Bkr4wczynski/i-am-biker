package com.iambiker.webservice.model.dto.authentication;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RegisterDTO {
    private Integer id;
    private String username;
    private String email;
    private String password;
    private UserDetailsDTO userDetailsDTO;
}
