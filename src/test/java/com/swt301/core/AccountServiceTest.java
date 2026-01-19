
package com.swt301.core;

import com.swt301.AccountService;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;

import static org.junit.jupiter.api.Assertions.*;

class AccountServiceTest {

    @ParameterizedTest
    @CsvFileSource(
        resources = "/test-data.csv",
        numLinesToSkip = 1
    )
    void registerAccount_withCsvFile(
            String username,
            String password,
            String email,
            boolean expected
    ) {
        AccountService service = new AccountService();

        // CSV rỗng -> ""
        String u = (username == null || username.isBlank()) ? null : username;

        boolean actual = service.registerAccount(u, password, email);

        assertEquals(expected, actual);
    }
}