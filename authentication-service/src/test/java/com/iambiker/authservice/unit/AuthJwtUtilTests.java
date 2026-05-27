package com.iambiker.authservice.unit;

import com.iambiker.authservice.jwt.AuthJwtUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.lang.reflect.Field;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class AuthJwtUtilTests {
    private AuthJwtUtil authJwtUtil;

    @BeforeEach
    void setup() throws Exception {
        authJwtUtil = new AuthJwtUtil();

        Field secretField = AuthJwtUtil.class.getDeclaredField("secret");
        secretField.setAccessible(true);

        // this is randomly generated secret 128 bits only for testing
        secretField.set(authJwtUtil, "61b927150bfa78b39d3fa1c634133db6");

        Field expirationField = AuthJwtUtil.class.getDeclaredField("expiration");
        expirationField.setAccessible(true);
        expirationField.set(authJwtUtil, 3600000);

        authJwtUtil.init();
    }

    @Test
    void shouldGenerateAndValidateToken() {
        String token = authJwtUtil.generateToken("user123", 1);

        assertNotNull(token);
        assertTrue(authJwtUtil.validateToken(token));
    }

    @Test
    void shouldReturnCorrectUserId() {
        String token = authJwtUtil.generateToken("user123", 42);

        Integer userId = authJwtUtil.getUserIdFromToken(token);

        assertEquals(42, userId);
    }

    @Test
    void shouldReturnFalseForInvalidToken() {
        boolean result = authJwtUtil.validateToken("invalid.token.xyz");

        assertFalse(result);
    }

    @Test
    void shouldReturnFalseForTamperedToken() {
        String token = authJwtUtil.generateToken("user123", 1);
        String tampered = token + "abc";

        assertFalse(authJwtUtil.validateToken(tampered));
    }

    @Test
    void shouldReturnFalseForExpiredToken() throws Exception {
        Field expirationField = AuthJwtUtil.class.getDeclaredField("expiration");
        expirationField.setAccessible(true);
        expirationField.set(authJwtUtil, -1000);

        String token = authJwtUtil.generateToken("user123", 1);

        assertFalse(authJwtUtil.validateToken(token));
    }
}
