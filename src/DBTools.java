package nikos.giantbombcrawler;

import com.mysql.jdbc.exceptions.jdbc4.MySQLIntegrityConstraintViolationException;
import java.io.FileWriter;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DBTools {

    static Connection con = null;
    static Statement stmt = null;

    private static void initDB() {
        if (con == null) {
            try {//kanw prwta to connection 
                Class.forName("com.mysql.jdbc.Driver");
                con = DriverManager.getConnection("jdbc:mysql://localhost:3306/giantbomb?useSSL=false&autoReconnect=true", "root", "");
                stmt = con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
                //check = con.prepareStatement("SELECT movieName FROM ratingstable where movieName =?");
            } catch (SQLException ex) {
                System.out.println("ERROR: in initDB " + ex.getMessage());
            } catch (ClassNotFoundException ex) {
                System.out.println("ERROR: in initDB sto 2ro catch ");
            }
        }
    }

    public static void insertToTable(String val, String table) {
        initDB();
        if (!val.isEmpty()) {
            try {
                if (table.equalsIgnoreCase("gameinfos")) {
                    System.out.println("INSERT into " + table + " VALUES (" + "\"" + val + "\"" + ")");
                    stmt.executeUpdate("INSERT into " + table + " VALUES (" + "\"" + val + "\"" + ")");
                } else if (table.equalsIgnoreCase("gameIds")) {
                    System.out.println("INSERT into " + table + " VALUES (" + val + ")");
                    stmt.executeUpdate("INSERT into " + table + " VALUES (" + val + ")");
                }

            } catch (MySQLIntegrityConstraintViolationException ex) {
                System.out.println("already in db");
            } catch (SQLException ex) {
                System.out.println("error in: " + table + " message: " + ex.getMessage() + " query: " + "INSERT into " + table + " VALUES ('" + val + "')");
                Logger.getLogger(DBTools.class.getName()).log(Level.SEVERE, null, ex);
            }

        }
    }

}
