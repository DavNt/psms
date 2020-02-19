import com.sun.rowset.CachedRowSetImpl;

import java.sql.*;

public class DBcon {

    static Connection conn = null;
    public static Connection conDB() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/psms","root","davx");
            return conn;
        } catch (ClassNotFoundException | SQLException ex) {
            System.err.println("ConnectionUtil : "+ex.getMessage());
            return null;
        }
    }

    public static void dbDisconn() throws SQLException{
        try {
            if (conn != null && !conn.isClosed() ){
                conn.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void dbExcQ(String sqlStmt) throws SQLException{
        Statement stmt = null;
        try {
            conDB();
            stmt = conn.createStatement();
            stmt.executeUpdate(sqlStmt);
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (stmt!=null){
                stmt.close();
            }
            dbDisconn();
        }
    }

    public static ResultSet dbExc(String sql) throws SQLException {
        Statement stmt = null;
        ResultSet rS = null;
        CachedRowSetImpl crs = null;

        try {
            conDB();
            stmt = conn.createStatement();
            rS = stmt.executeQuery(sql);
            crs = new CachedRowSetImpl();
            crs.populate(rS);

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (rS != null){
                rS.close();
            }
            if (stmt != null){
                stmt.close();
            }
            dbDisconn();
        }
        return crs;
    }
}
