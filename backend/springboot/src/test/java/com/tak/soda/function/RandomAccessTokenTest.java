package com.tak.soda.function;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
class RandomAccessTokenTest {

    @Autowired RandomAccessToken randomAccessToken;

    @DisplayName("인증번호 생성 테스트")
    @Test
    void testSecureRandom() {
        //Given
        String token = randomAccessToken.makeToken(15);
        //When
        System.out.println(token);

        //Then
        assertEquals(15, token.length());
    }
}