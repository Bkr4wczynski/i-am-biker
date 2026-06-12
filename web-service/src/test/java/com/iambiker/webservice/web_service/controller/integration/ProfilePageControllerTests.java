package com.iambiker.webservice.web_service.controller.integration;

import com.iambiker.webservice.model.dto.authentication.UserDetailsDTO;
import com.iambiker.webservice.security.AuthConnectionService;
import com.iambiker.webservice.webcontent.profile.ProfilePageController;
import jakarta.servlet.http.HttpServletRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import javax.naming.AuthenticationException;
import java.time.LocalDate;

import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(ProfilePageController.class)
public class ProfilePageControllerTests {
    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private AuthConnectionService authConnectionService;

    @BeforeEach
    void setup() throws AuthenticationException {
        UserDetailsDTO userDetailsDTO = new UserDetailsDTO();
        userDetailsDTO.setUser_id(0);
        userDetailsDTO.setBirthday(LocalDate.now().minusYears(20).minusDays(20));
        userDetailsDTO.setRegistry_date(LocalDate.now());
        userDetailsDTO.setUsername("stub");
        Mockito.when(authConnectionService.getUserDetails(any(HttpServletRequest.class)))
                .thenReturn(userDetailsDTO);
    }

    @Test
    void shouldDisplayProfilePage() throws Exception{
        mockMvc.perform(get("/my-profile"))
                .andExpect(status().isOk())
                .andExpect(view().name("profile/myProfile"))
                .andExpect(model().attributeExists("user_details"))
                .andExpect(model().attributeExists("isBirthday"));
    }

    @Test
    void shouldDisplaySettingsPage() throws Exception {
        mockMvc.perform(get("/settings"))
                .andExpect(status().isOk())
                .andExpect(view().name("profile/settings"))
                .andExpect(model().attributeExists("user_details"));
    }

}
