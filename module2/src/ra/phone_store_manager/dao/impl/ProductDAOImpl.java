package ra.phone_store_manager.dao.impl;

import ra.phone_store_manager.dao.IProductDAO;
import ra.phone_store_manager.model.Product;
import ra.phone_store_manager.utils.database.ConnectionDB;
import ra.phone_store_manager.utils.helper.Color;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ProductDAOImpl implements IProductDAO {
    @Override
    public boolean addProduct(Product product) {
        String sql = "insert into product (name, brand, price, stock) values (?,?,?,?)";

        try (Connection conn = ConnectionDB.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)
        ) {
            pstmt.setString(1, product.getName());
            pstmt.setString(2, product.getBrand());
            pstmt.setBigDecimal(3, product.getPrice());
            pstmt.setInt(4, product.getStock());

            int result =pstmt.executeUpdate();
            return result > 0;

        } catch (SQLException e) {
            System.out.println(Color.DO + "Lỗi SQL: " + e.getMessage() + Color.RESET);
            return false;
        }
    }

    @Override
    public boolean deleteProduct(int id) {
        String sql = "delete from product where id=?";

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
    public boolean updateProduct(Product product) {
        String sql = """
                update data.product
                set name = ?,
                    brand= ?,
                    price= ?,
                    stock= ?
                where id=?;
                """;

        try (Connection conn = ConnectionDB.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)
        ) {
            pstmt.setString(1, product.getName());
            pstmt.setString(2, product.getBrand());
            pstmt.setBigDecimal(3, product.getPrice());
            pstmt.setInt(4, product.getStock());
            pstmt.setInt(5, product.getId());

            int result =pstmt.executeUpdate();
            return result > 0;

        } catch (SQLException e) {
            System.out.println(Color.DO + "Lỗi SQL: " + e.getMessage() + Color.RESET);
            return false;
        }
    }

    @Override
    public Product findProductByID(int id) {

        String sql = "select * from product where id=?";

        try (Connection conn = ConnectionDB.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)
        ) {
            pstmt.setInt(1, id);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                return new Product(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getString("brand"),
                        rs.getBigDecimal("price"),
                        rs.getInt("stock")
                );
            }

        } catch (SQLException e) {
            System.out.println(Color.DO + "Lỗi SQL: " + e.getMessage() + Color.RESET);
        }
        return null;
    }

    @Override
    public List<Product> findAllProducts() {
        List<Product> productList = new ArrayList<>();
        String sql = "select * from product";

        try (Connection conn = ConnectionDB.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)
        ) {
            ResultSet rs = pstmt.executeQuery();

            Product product;
            if (rs.next()) {
                product = new Product(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getString("brand"),
                        rs.getBigDecimal("price"),
                        rs.getInt("stock")
                );
                productList.add(product);
            }

        } catch (SQLException e) {
            System.out.println(Color.DO + "Lỗi SQL: " + e.getMessage() + Color.RESET);
        }
        return productList;
    }

    @Override
    public List<Product> findProductsByBrand(String brand) {
        List<Product> productList = new ArrayList<>();
        String sql = "select * from product where brand ILIKE ?";

        try (Connection conn = ConnectionDB.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)
        ) {
            pstmt.setString(1, "%" + brand + "%");
            ResultSet rs = pstmt.executeQuery();

            Product product;
            if (rs.next()) {
                product = new Product(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getString("brand"),
                        rs.getBigDecimal("price"),
                        rs.getInt("stock")
                );
                productList.add(product);
            }

        } catch (SQLException e) {
            System.out.println(Color.DO + "Lỗi SQL: " + e.getMessage() + Color.RESET);
        }
        return productList;
    }

    @Override
    public List<Product> findProductsByName(String name) {
        List<Product> productList = new ArrayList<>();
        String sql = "select * from product where name ILIKE ?";

        try (Connection conn = ConnectionDB.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)
        ) {
            pstmt.setString(1, "%" +name + "%");
            ResultSet rs = pstmt.executeQuery();

            Product product;
            if (rs.next()) {
                product = new Product(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getString("brand"),
                        rs.getBigDecimal("price"),
                        rs.getInt("stock")
                );
                productList.add(product);
            }

        } catch (SQLException e) {
            System.out.println(Color.DO + "Lỗi SQL: " + e.getMessage() + Color.RESET);
        }
        return productList;
    }

    @Override
    public List<Product> findProductsByStock(int stock) {
        List<Product> productList = new ArrayList<>();
        String sql = "select * from product where stock = ?";

        try (Connection conn = ConnectionDB.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)
        ) {
            pstmt.setInt(1, stock);
            ResultSet rs = pstmt.executeQuery();

            Product product;
            if (rs.next()) {
                product = new Product(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getString("brand"),
                        rs.getBigDecimal("price"),
                        rs.getInt("stock")
                );
                productList.add(product);
            }

        } catch (SQLException e) {
            System.out.println(Color.DO + "Lỗi SQL: " + e.getMessage() + Color.RESET);
        }
        return productList;
    }

    @Override
    public List<Product> findProductsByPriceRange(BigDecimal min, BigDecimal max) {
        List<Product> productList = new ArrayList<>();
        String sql = "select * from product where price between ? and ?";

        try (Connection conn = ConnectionDB.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)
        ) {
            pstmt.setBigDecimal(1, min);
            pstmt.setBigDecimal(2, max);
            ResultSet rs = pstmt.executeQuery();

            Product product;
            if (rs.next()) {
                product = new Product(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getString("brand"),
                        rs.getBigDecimal("price"),
                        rs.getInt("stock")
                );
                productList.add(product);
            }

        } catch (SQLException e) {
            System.out.println(Color.DO + "Lỗi SQL: " + e.getMessage() + Color.RESET);
        }
        return productList;
    }
}
