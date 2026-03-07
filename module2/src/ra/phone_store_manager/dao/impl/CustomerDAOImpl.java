package ra.phone_store_manager.dao.impl;

import ra.phone_store_manager.dao.ICustomerDAO;
import ra.phone_store_manager.model.Customer;
import ra.phone_store_manager.model.Product;
import ra.phone_store_manager.utils.database.ConnectionDB;
import ra.phone_store_manager.utils.helper.Color;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CustomerDAOImpl implements ICustomerDAO {
    @Override
    public boolean createCustomer(Customer customer) {
        String sql= """
                insert into customer (name, phone, email, address)
                values (?, ?, ?, ?);
                """;

        try (Connection conn = ConnectionDB.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)
        ) {
            pstmt.setString(1, customer.getName());
            pstmt.setString(2, customer.getPhone());
            pstmt.setString(3, customer.getEmail());
            pstmt.setString(4, customer.getAddress());


            int result =pstmt.executeUpdate();
            return result > 0;

        } catch (SQLException e) {
            System.out.println(Color.DO + "Lỗi SQL: " + e.getMessage() + Color.RESET);
            return false;
        }

    }

    @Override
    public boolean updateCustomer(Customer customer) {
        String sql = """
                update customer
                set name = ?,
                    address=?,
                    email= ?,
                    phone= ?
                where id=?
                """;

        try (Connection conn = ConnectionDB.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)
        ) {
            pstmt.setString(1, customer.getName());
            pstmt.setString(2, customer.getAddress());
            pstmt.setString(3, customer.getEmail());
            pstmt.setString(4, customer.getPhone());
            pstmt.setInt(5, customer.getId());

            int result =pstmt.executeUpdate();
            return result > 0;

        } catch (SQLException e) {
            System.out.println(Color.DO + "Lỗi SQL: " + e.getMessage() + Color.RESET);
            return false;
        }
    }

    @Override
    public boolean deleteCustomer(int id) {
        String sql = "delete from customer where id=?";

        try (Connection conn = ConnectionDB.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)
        ) {
            pstmt.setInt(1, id);
            int result =pstmt.executeUpdate();
            return result > 0;

        } catch (SQLException e) {
            System.out.println(Color.DO + "Lỗi SQL: " + e.getMessage() + Color.RESET);
            return false;
        }
    }

    @Override
    public Customer findCustomerByID(int id) {
        String sql = "select * from customer where id=?";

        try (Connection conn = ConnectionDB.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)
        ) {
            pstmt.setInt(1, id);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                return new Customer(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getString("phone"),
                        rs.getString("email"),
                        rs.getString("address")

                );
            }

        } catch (SQLException e) {
            System.out.println(Color.DO + "Lỗi SQL: " + e.getMessage() + Color.RESET);
        }
        return null;
    }

    @Override
    public Customer findCustomerByEmail(String email) {
        String sql = "select * from customer where email=?";

        try (Connection conn = ConnectionDB.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)
        ) {
            pstmt.setString(1, email);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                return new Customer(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getString("phone"),
                        rs.getString("email"),
                        rs.getString("address")

                );
            }

        } catch (SQLException e) {
            System.out.println(Color.DO + "Lỗi SQL: " + e.getMessage() + Color.RESET);
        }
        return null;
    }

    @Override
    public List<Customer> findAllCustomers() {
        List<Customer> customerList = new ArrayList<>();
        String sql = "select * from customer";

        try (Connection conn = ConnectionDB.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)
        ) {
            ResultSet rs = pstmt.executeQuery();

            Customer customer;
            while (rs.next()) {
                customer = new Customer(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getString("phone"),
                        rs.getString("email"),
                        rs.getString("address")
                );
                customerList.add(customer);
            }

        } catch (SQLException e) {
            System.out.println(Color.DO + "Lỗi SQL: " + e.getMessage() + Color.RESET);
        }
        return customerList;
    }
}
