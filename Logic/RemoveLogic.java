package Logic;

import java.sql.*;
import java.util.*;
import java.io.*;

public class RemoveLogic {

    private Connection cn;

    public RemoveLogic(ConnectLogic logic) {
        this.cn = logic.getConnection();
    }
    
}