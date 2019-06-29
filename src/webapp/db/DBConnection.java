package webapp.db;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

public class DBConnection {

    public static DataSource getConnectionPool(){
        InitialContext initialContext = null;
        DataSource pool = null;

        try {
            initialContext = new InitialContext();
            pool = (DataSource) initialContext.lookup("java:comp/env/ThogaKade");
        } catch (NamingException e) {
            e.printStackTrace();
        }
        return pool;
    }
}
