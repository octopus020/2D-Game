package main;
import java.sql.*;

public class GameDataBase
{
    public static Connection connect()
    {
        Connection con = null;
        try
        {
            Class.forName("org.sqlite.JDBC");
            con=DriverManager.getConnection("jdbc:sqlite:game.db");
            System.out.println("Connected to the database successfully");
        }catch(ClassNotFoundException e)
        {
            System.err.println("SQLite JDBC Driver not found. Make sure sqlite-jdbc-3.8.11.2.jar is in the classpath.");
            e.printStackTrace();
        }catch(SQLException e)
        {
            System.err.println("Database connection failed: " + e.getMessage());
            e.printStackTrace();
        }
        return con;
    }
    public static void insert(int map,int col,int row,int hearts,int keys)
    {
        Connection con = connect();
        if (con == null) {
            System.err.println("Failed to connect to database. Insert operation cancelled.");
            return;
        }
        PreparedStatement ps = null;
        try {
            String sql = "INSERT INTO player_pos(map,col,row,hearts,keys) " +
                    "VALUES (?,?,?,?,?);";
            ps=con.prepareStatement(sql);
            ps.setInt(1,map);
            ps.setInt(2,col);
            ps.setInt(3,row);
            ps.setInt(4,hearts);
            ps.setInt(5,keys);
            ps.execute();
            ps.close();
            con.close();
            System.out.println("Inserted successfully");
        } catch ( Exception e ) {
            e.printStackTrace();
        }
    }
    public static void update(int map,int col,int row,int hearts,int keys)
    {
        Connection con = connect();
        if (con == null) {
            System.err.println("Failed to connect to database. Update operation cancelled.");
            return;
        }
        PreparedStatement ps=null;
        try
        {
            String sql="UPDATE player_pos SET map=?,col=?,row=?,hearts=?,keys=?;";
            ps=con.prepareStatement(sql);
            ps.setInt(1,map);
            ps.setInt(2,col);
            ps.setInt(3,row);
            ps.setInt(4,hearts);
            ps.setInt(5,keys);
            ps.execute();
            ps.close();
            con.close();
            System.out.println("Updated the infos successfully");
        }catch(SQLException e)
        {
            e.printStackTrace();
        }
    }
    public static void delete()
    {
        Connection con=connect();
        if (con == null) {
            System.err.println("Failed to connect to database. Delete operation cancelled.");
            return;
        }
        PreparedStatement ps=null;
        try
        {
            String sql="DELETE FROM player_pos;";
            ps=con.prepareStatement(sql);
            ps.execute();
            ps.close();
            con.close();
            System.out.println("Successfully deleted");
        }catch(SQLException e)
        {
            e.printStackTrace();
        }
    }
    public static int []load()
    {
        int []x=new int[5];
        Connection con=connect();
        if (con == null) {
            System.err.println("Failed to connect to database. Using default values.");
            return x;
        }
        PreparedStatement ps=null;
        ResultSet rs=null;
        try {
            String sql ="SELECT * FROM player_pos;";
            ps=con.prepareStatement(sql);
            rs=ps.executeQuery();
            while(rs.next())
            {
                x[0]=rs.getInt("map");
                x[1]=rs.getInt("col");
                x[2]=rs.getInt("row");
                x[3]=rs.getInt("hearts");
                x[4]=rs.getInt("keys");
            }
        }catch(SQLException e)
        {
            System.out.println();
        }finally
        {
            try
            {
                if (rs != null) rs.close();
                if (ps != null) ps.close();
                if (con != null) con.close();
            }catch(SQLException e)
            {
                System.err.println( e.getClass().getName() + ": " + e.getMessage() );
                System.exit(0);
            }
        }
        return x;
    }
}

