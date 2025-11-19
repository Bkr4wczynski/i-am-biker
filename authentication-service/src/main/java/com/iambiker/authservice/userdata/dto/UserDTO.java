package com.iambiker.authservice.userdata.dto;

import com.iambiker.authservice.userdata.entity.User;
import com.iambiker.authservice.userdata.entity.UserDetails;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDTO {
    private Integer id;
    private String username;
    private String email;
    private String password;
    private UserDetailsDTO userDetailsDTO;

    public User convertToUser() {
        User user = new User();
        user.setUsername(getUsername());
        user.setEmail(getEmail());
        user.setPassword(getPassword());
        UserDetails userDetails = new UserDetails();
        userDetails.setUser(user);
        userDetails.setRegistry_date(LocalDate.now());
        userDetails.setBirthday(getUserDetailsDTO().getBirthday());
        user.setUser_details(userDetails);

        return user;
    }
}
