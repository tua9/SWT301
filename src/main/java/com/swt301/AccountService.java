
package com.swt301;

/*
    Note: 
 */
public class AccountService {

    public boolean registerAccount(String username, String password, String email) {
        if (username == null || username.isBlank()) return false;
        if (password == null || password.length() < 6) return false;
        if (!isValidEmail(email)) return false;

        // giả lập đăng ký thành công
        return true;
    }

    public boolean isValidEmail(String email) {
        if (email == null) return false;
        return email.matches("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$");
    }
}
