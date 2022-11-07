package Logic;

import java.sql.*;
import java.util.*;
import java.io.*;

public class AuditLog {
    
    private Connection cn;

    public AuditLog(ConnectLogic logic) {
        this.cn = logic.getConnection();
    }
}
