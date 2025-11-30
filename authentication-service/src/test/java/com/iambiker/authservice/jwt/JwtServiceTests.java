package com.iambiker.authservice.jwt;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import static org.junit.jupiter.api.Assertions.assertEquals;


@ExtendWith(MockitoExtension.class)
public class JwtServiceTests {
    AuthJwtUtil authJwtUtil = Mockito.mock(AuthJwtUtil.class);
    JwtService jwtService;

    @BeforeEach
    public void init() {
        jwtService = new JwtService(authJwtUtil, new BCryptPasswordEncoder());
    }

    @ParameterizedTest
    @CsvSource({
            "SecurePassword99, $2a$12$Ld41WDaWK68MloDgOzMQQusy9O8BstA3INkUD2lePnsna2JzZvYvy, true",
            "DifferentPassword12, $2a$12$Ld41WDaWK68MloDgOzMQQusy9O8BstA3INkUD2lePnsna2JzZvYvy, false",
            "SecurePassword99, 2a$12$Ld41WDaWK68MloDgOzMQQusy9O8BstA3INkUD2lePnsna2JzZvYvy, false",
            "password, password, false"
    })
    void testPasswordEncoder(String given, String actual, boolean result) {
        assertEquals(result, jwtService.matchPasswords(given, actual));
    }


}
