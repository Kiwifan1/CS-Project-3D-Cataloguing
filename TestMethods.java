import java.util.*;
import Logic.*;
import java.io.*;
import java.sql.*;
import org.junit.*;
import static org.junit.Assert.*;

/** A test class to test whether the logic functions work correctly */
public class TestMethods {

    @Test
    public void testConnection() {
        ConnectLogic cl = new ConnectLogic();
        Connection cn = cl.getConnection();
        assertNotNull(cn);
    }

    @Test
    public void testAddUser() {
        ConnectLogic logic = new ConnectLogic();
        Login login = new Login(logic);

        login.setCurrentUser("testUser");

        login.removeUser();

        String username = "testUser";
        String password = "test";

        login.addUser(username, password);
        assertTrue(login.login(username, password));
    }

    @Test
    public void testAddAttribute() {
        ConnectLogic logic = new ConnectLogic();
        Attribute attribute = new Attribute(logic);

        attribute.removeAttribute("test");

        String name = "test";
        String description = "test";

        attribute.addAttribute(name, description);
        ArrayList<String> attributes = attribute.getAllAttributes();

        boolean found = false;
        for (String a : attributes) {
            if (a.equals(name)) {
                found = true;
            }
        }

        assertTrue(found);
    }
}
