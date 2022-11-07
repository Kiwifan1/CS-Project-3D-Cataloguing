import java.util.*;
import Logic.*;
import java.io.*;
import java.sql.*;
import org.junit.*;
import static org.junit.Assert.*;

/** A test class to test whether the logic functions work correctly */
public class TestMethods {

    @Test
    public void testAddUser() {
        ConnectLogic logic = new ConnectLogic();
        Login login = new Login(logic);

        String username = "test";
        String password = "test";

        login.addUser(username, password);
        assertTrue(login.login(username, password));
    }
}
