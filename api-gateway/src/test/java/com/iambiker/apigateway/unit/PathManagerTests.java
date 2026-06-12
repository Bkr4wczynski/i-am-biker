package com.iambiker.apigateway.unit;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class PathManagerTests {
    private final PathManager pathManager = new PathManager();

    @ParameterizedTest
    @CsvSource({
            "'/web/my-profile', false",
            "'/web/authentication/login?error=Logout%20successful', true",
            "'/web/images/logo.png', true",
            "'http://localhost:8765/web/quiz', false",
            "'/authentication/api/public/generate-token', true"
    })
    void testIsPathPublic(String path, boolean expected) {
        assertEquals(expected, pathManager.isPathPublic(path));
    }

}
