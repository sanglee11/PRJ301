/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dal;

import dto.InfoDTO;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;

/**
 *
 * @author duongpv
 */
public class DBContext {

    protected Connection connection;

    public DBContext() throws SQLException, ClassNotFoundException {
        String url = "jdbc:sqlserver://localhost:1433;databaseName=PRJ30X_PE_SPRING2023;encrypt=false;";
        String username = "sa";
        String password = "1";
        Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
        connection = DriverManager.getConnection(url, username, password);
    }

    public ArrayList<InfoDTO> getAllInfo(String role) throws SQLException {
        ArrayList<InfoDTO> lst_infos = new ArrayList<>();
        String sql = null;
        if (role.equals("can")) {
            sql = "select c.cid, c.cname, c.gender, c.dob, g.gname from CandidateTBL c join GroupTBL g on c.gid = g.gid";
        }else{
            sql = "select m.mid, m.mname, m.gender, m.dob, g.gname from MemberTBL m join GroupTBL g on m.gid = g.gid";
        }
        PreparedStatement statement = connection.prepareStatement(sql);
        ResultSet rs = statement.executeQuery();
        while (rs.next()) {
            int id = rs.getInt(1);
            String name = rs.getString(2);
            Boolean gender = rs.getBoolean(3);
            Date dob = rs.getDate(4);
            String groupname = rs.getString(5);
            InfoDTO info = new InfoDTO(id, name, gender, dob, groupname);
            lst_infos.add(info);
        }
        return lst_infos;
    }
    public static void main(String[] args) throws SQLException, ClassNotFoundException {
        DBContext db = new DBContext();
        db.getAllInfo("can");
        System.out.println(db.getAllInfo("can"));
    }

}
