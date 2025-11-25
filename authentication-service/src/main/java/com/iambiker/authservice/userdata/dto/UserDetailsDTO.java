package com.iambiker.authservice.userdata.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDetailsDTO {
    private Integer user_id;
    private String username;
    private LocalDate registry_date;
    private LocalDate birthday;

}
