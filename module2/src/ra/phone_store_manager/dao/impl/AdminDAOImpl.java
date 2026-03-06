package ra.phone_store_manager.dao.impl;

import ra.phone_store_manager.dao.IAdminDAO;
import ra.phone_store_manager.model.Admin;
import ra.phone_store_manager.utils.database.ConnectionDB;
import ra.phone_store_manager.utils.helper.Color;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class AdminDAOImpl implements IAdminDAO {
    @Override
    public void addAdmin(Admin admin) {
        String sql= "insert into admin (username, password) values (?,?)";

        try (Connection conn= ConnectionDB.getConnection();
             PreparedStatement pstmt=conn.prepareStatement(sql)
        ){
            pstmt.setString(1,admin.getUsername());
            pstmt.setString(2,admin.getPassword());

            pstmt.executeUpdate();

        }catch (SQLException e){
            System.out.println(Color.DO +"Lỗi SQL: "+e.getMessage()+Color.RESET);
        }
    }

    @Override
    public Admin findAdminByUsername(String username) {
        String sql= "select * from admin where username = ?";

        try (Connection conn= ConnectionDB.getConnection();
             PreparedStatement pstmt=conn.prepareStatement(sql)
        ){
            pstmt.setString(1,username);
            ResultSet rs=pstmt.executeQuery();

            if(rs.next()){
                 return new Admin(
                    rs.getInt("id"),
                    rs.getString("username"),
                    rs.getString("password")
                );
            }

        }catch (SQLException e){
            System.out.println(Color.DO +"Lỗi SQL: "+e.getMessage()+Color.RESET);
        }
        return null;
    }
}
