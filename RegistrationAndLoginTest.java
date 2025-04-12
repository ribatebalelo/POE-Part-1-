package registrationandlogin;

import java.sql.Connection;
import org.junit.Test;
import static org.junit.Assert.*;

public class RegistrationAndLoginTest {
    
 
    @Test
    public void testDatabaseConnection() {
        System.out.println("Testing database connection");
        Connection result = Conn.connect();
        assertNotNull("Database connection should not be null", result);
        try {
            assertFalse("Connection should be open", result.isClosed());
            result.close();
        } catch (Exception e) {
            fail("Exception occurred: " + e.getMessage());
        }
    }
   
    @Test
    public void testLoginEmptyFieldValidation() {
        System.out.println("Testing login empty field validation");
        LoginTestHelper instance = new LoginTestHelper();
        
        assertFalse("Empty username should fail", 
            instance.validateInputs("", "password123", "0123456789"));
        assertFalse("Empty password should fail", 
            instance.validateInputs("username", "", "0123456789"));
        assertFalse("Empty phone number should fail", 
            instance.validateInputs("username", "password123", ""));
        assertFalse("All empty fields should fail", 
            instance.validateInputs("", "", ""));
        assertTrue("Valid inputs should pass", 
            instance.validateInputs("user", "pass123", "0123456789"));
    }
    
    @Test
    public void testLoginPhoneNumberValidation() {
        System.out.println("Testing login phone number validation");
        LoginTestHelper instance = new LoginTestHelper();
        
        assertTrue("Valid phone number should pass", 
            instance.validatePhoneNumber("0123456789"));
        
      
        assertFalse("Phone number not starting with 0 should fail", 
            instance.validatePhoneNumber("1234567890"));
        assertFalse("Too short phone number should fail", 
            instance.validatePhoneNumber("012345678"));
        assertFalse("Too long phone number should fail", 
            instance.validatePhoneNumber("01234567890"));
        assertFalse("Phone number with letters should fail", 
            instance.validatePhoneNumber("0123abc789"));
    }
    
   
    @Test
    public void testSignUpUsernameValidation() {
        System.out.println("Testing signup username validation");
        SignUpTestHelper instance = new SignUpTestHelper();
        
        
        assertTrue("Valid username should pass", 
            instance.validateUsername("user_"));
        assertTrue("Valid username should pass", 
            instance.validateUsername("a_b"));
       
        assertFalse("Empty username should fail", 
            instance.validateUsername(""));
        assertFalse("Username without underscore should fail", 
            instance.validateUsername("username"));
        assertFalse("Username with multiple underscores should fail", 
            instance.validateUsername("user__name"));
        assertFalse("Too long username should fail", 
            instance.validateUsername("verylongusername"));
    }
    
    @Test
    public void testSignUpPasswordValidation() {
        System.out.println("Testing signup password validation");
        SignUpTestHelper instance = new SignUpTestHelper();
        
       
        assertTrue("Valid password should pass", 
            instance.validatePassword("Passw0rd@"));
        assertTrue("Valid password should pass", 
            instance.validatePassword("A1@bcdefg"));
        
        
        assertFalse("Too short password should fail", 
            instance.validatePassword("short"));
        assertFalse("No uppercase password should fail", 
            instance.validatePassword("nouppercase1@"));
        assertFalse("No lowercase password should fail", 
            instance.validatePassword("NOLOWERCASE1@"));
        assertFalse("No special char password should fail", 
            instance.validatePassword("NoNumber1"));
        assertFalse("No number password should fail", 
            instance.validatePassword("NoSpecial@"));
    }
    
    @Test
    public void testSignUpPasswordMatch() {
        System.out.println("Testing signup password match");
        SignUpTestHelper instance = new SignUpTestHelper();
        
        assertTrue("Matching passwords should pass", 
            instance.validatePasswordMatch("password", "password"));
        assertFalse("Non-matching passwords should fail", 
            instance.validatePasswordMatch("password1", "password2"));
    }
   
    private class LoginTestHelper {
        public boolean validateInputs(String username, String password, String phoneNumber) {
            if(username.isEmpty() || password.isEmpty() || phoneNumber.isEmpty()) {
                return false;
            }
            return true;
        }
        
        public boolean validatePhoneNumber(String phoneNumber) {
            return phoneNumber.matches("^0\\d{9}$");
        }
    }
    
    private class SignUpTestHelper {
        public boolean validateUsername(String username) {
            if(username.isEmpty()) return false;
            if(username.length() > 5) return false;
            if(username.chars().filter(ch -> ch == '_').count() != 1) return false;
            return true;
        }
        
        public boolean validatePassword(String password) {
            if(password.length() < 8 || password.length() >= 16) return false;
            if(!password.matches(".*[A-Z].*")) return false;
            if(!password.matches(".*\\d.*")) return false;
            if(!password.matches(".*[@#$%^&*()_+=|<>{}\\[\\]~-].*")) return false;
            return true;
        }
        
        public boolean validatePasswordMatch(String password1, String password2) {
            return password1.equals(password2);
        }
    }
}
