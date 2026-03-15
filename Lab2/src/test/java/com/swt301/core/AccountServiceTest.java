package com.swt301.core;

import com.swt301.AccountService;
import org.junit.jupiter.api.Test;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.*;

class AccountServiceTest {

    @Test
    void registerAccount_writeResultToUnitTestCsv() throws Exception {
        AccountService service = new AccountService();

        InputStream is = getClass()
                .getClassLoader()
                .getResourceAsStream("test-data.csv");
        assertNotNull(is);

        BufferedReader reader = new BufferedReader(new InputStreamReader(is));

        // 👉 ROOT project (nơi có pom.xml)
        Path output = Path.of("UnitTest.csv");
        BufferedWriter writer = Files.newBufferedWriter(output);

        writer.write("username,password,email,expected,actual,result");
        writer.newLine();

        reader.readLine(); // skip header

        String line;
        while ((line = reader.readLine()) != null) {
            String[] d = line.split(",");

            String username = d[0].isBlank() ? null : d[0];
            String password = d[1];
            String email = d[2];
            boolean expected = Boolean.parseBoolean(d[3]);

            boolean actual = service.registerAccount(username, password, email);
            String result = (expected == actual) ? "PASS" : "FAIL";

            writer.write(String.join(",",
                    String.valueOf(username),
                    password,
                    email,
                    String.valueOf(expected),
                    String.valueOf(actual),
                    result
            ));
            writer.newLine();

            assertEquals(expected, actual);
        }

        writer.close();
        reader.close();
    }
}
