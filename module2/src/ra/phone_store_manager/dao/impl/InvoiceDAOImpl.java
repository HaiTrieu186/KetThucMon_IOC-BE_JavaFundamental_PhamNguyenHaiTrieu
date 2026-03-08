package ra.phone_store_manager.dao.impl;

import ra.phone_store_manager.dao.IInvoiceDAO;
import ra.phone_store_manager.model.Invoice;
import ra.phone_store_manager.model.InvoiceDetails;
import ra.phone_store_manager.utils.database.ConnectionDB;
import ra.phone_store_manager.utils.helper.Color;

import java.math.BigDecimal;
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class InvoiceDAOImpl implements IInvoiceDAO {
    @Override
    public int createInvoice(Invoice invoice) {
        String sql = """
                insert into invoice (customer_id, total_amount)
                values (?,?);
                """;
        try (Connection conn = ConnectionDB.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS)
        ) {
            pstmt.setInt(1, invoice.getCustomer_id());
            pstmt.setBigDecimal(2, invoice.getTotal_amount());

            int result = pstmt.executeUpdate();
            if (result > 0) {
                try (ResultSet rs = pstmt.getGeneratedKeys()) {
                    if (rs.next()) {
                        return rs.getInt(1); // Trả về cái ID
                    }
                }
            }
            ;

        } catch (SQLException e) {
            System.out.println(Color.DO + "Lỗi SQL: " + e.getMessage() + Color.RESET);
        }
        return -1;
    }

    @Override
    public boolean createInvoiceDetails(InvoiceDetails invoiceDetails) {
        String sql = """
                insert into invoice_details (invoice_id, product_id, quantity, unit_price)
                values (?,?,?,?);
                """;
        try (Connection conn = ConnectionDB.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)
        ) {
            pstmt.setInt(1, invoiceDetails.getInvoice_id());
            pstmt.setInt(2, invoiceDetails.getProduct_id());
            pstmt.setInt(3, invoiceDetails.getQuantity());
            pstmt.setBigDecimal(4, invoiceDetails.getUnit_price());

            int result = pstmt.executeUpdate();
            return result > 0;

        } catch (SQLException e) {
            System.out.println(Color.DO + "Lỗi SQL: " + e.getMessage() + Color.RESET);
            return false;
        }
    }

    @Override
    public Invoice findInvoiceByID(int id) {
        String sql = """
                select *
                from invoice
                where id=?;
                """;

        try (Connection conn = ConnectionDB.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)
        ) {
            pstmt.setInt(1, id);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                return new Invoice(
                        rs.getInt("id"),
                        rs.getInt("customer_id"),
                        rs.getTimestamp("created_at").toLocalDateTime(),
                        rs.getBigDecimal("total_amount")
                );
            }

        } catch (SQLException e) {
            System.out.println(Color.DO + "Lỗi SQL: " + e.getMessage() + Color.RESET);
        }
        return null;
    }

    @Override
    public List<Invoice> getAllInvoices() {
        List<Invoice> invoiceList = new ArrayList<>();
        String sql = "select * from invoice";

        try (Connection conn = ConnectionDB.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)
        ) {
            ResultSet rs = pstmt.executeQuery();

            Invoice invoice;
            while (rs.next()) {
                invoice = new Invoice(
                        rs.getInt("id"),
                        rs.getInt("customer_id"),
                        rs.getTimestamp("created_at").toLocalDateTime(),
                        rs.getBigDecimal("total_amount")
                );
                invoiceList.add(invoice);
            }

        } catch (SQLException e) {
            System.out.println(Color.DO + "Lỗi SQL: " + e.getMessage() + Color.RESET);
        }
        return invoiceList;
    }

    @Override
    public List<Invoice> findInvoicesByCustomerName(String customerName) {
        List<Invoice> invoiceList = new ArrayList<>();
        String sql = """
                select i.*
                from invoice i
                join customer c on i.customer_id = c.id
                where name ilike ?;
                """;

        try (Connection conn = ConnectionDB.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)
        ) {
            pstmt.setString(1, "%" + customerName + "%");
            ResultSet rs = pstmt.executeQuery();

            Invoice invoice;
            while (rs.next()) {
                invoice = new Invoice(
                        rs.getInt("id"),
                        rs.getInt("customer_id"),
                        rs.getTimestamp("created_at").toLocalDateTime(),
                        rs.getBigDecimal("total_amount")
                );
                invoiceList.add(invoice);
            }

        } catch (SQLException e) {
            System.out.println(Color.DO + "Lỗi SQL: " + e.getMessage() + Color.RESET);
        }
        return invoiceList;
    }

    @Override
    public List<Invoice> findInvoicesByCreateDate(LocalDate createDate) {
        List<Invoice> invoiceList = new ArrayList<>();
        String sql = """
                SELECT *
                FROM invoice
                WHERE created_at::date = ?
                """;

        try (Connection conn = ConnectionDB.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)
        ) {
            pstmt.setDate(1, java.sql.Date.valueOf(createDate));

            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                Invoice invoice = new Invoice(
                        rs.getInt("id"),
                        rs.getInt("customer_id"),
                        rs.getTimestamp("created_at").toLocalDateTime(),
                        rs.getBigDecimal("total_amount")
                );
                invoiceList.add(invoice);
            }

        } catch (SQLException e) {
            System.out.println(Color.DO + "Lỗi SQL: " + e.getMessage() + Color.RESET);
        }
        return invoiceList;
    }

    @Override
    public List<InvoiceDetails> findInvoiceDetailsByInvoiceID(int id) {
        List<InvoiceDetails> invoiceDetailList = new ArrayList<>();
        String sql = """
                select *
                from invoice_details
                where invoice_id = ?;
                """;

        try (Connection conn = ConnectionDB.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)
        ) {
            pstmt.setInt(1, id);
            ResultSet rs = pstmt.executeQuery();

            InvoiceDetails invoiceDetails;
            while (rs.next()) {
                invoiceDetails = new InvoiceDetails(
                        rs.getInt("id"),
                        rs.getInt("invoice_id"),
                        rs.getInt("product_id"),
                        rs.getInt("quantity"),
                        rs.getBigDecimal("unit_price")
                );
                invoiceDetailList.add(invoiceDetails);
            }

        } catch (SQLException e) {
            System.out.println(Color.DO + "Lỗi SQL: " + e.getMessage() + Color.RESET);
        }
        return invoiceDetailList;
    }

    @Override
    public BigDecimal calculateRevenueByDate(LocalDate date) {
        String sql = "SELECT SUM(total_amount) FROM invoice WHERE created_at::date = ?";

        try (Connection conn = ConnectionDB.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)
        ) {
            pstmt.setDate(1, java.sql.Date.valueOf(date));
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                BigDecimal result = rs.getBigDecimal(1);
                return result != null ? result : BigDecimal.ZERO;
            }

        } catch (SQLException e) {
            System.out.println(Color.DO + "Lỗi SQL: " + e.getMessage() + Color.RESET);
        }
        return BigDecimal.ZERO;
    }

    @Override
    public BigDecimal calculateRevenueByMonth(int month, int year) {
        String sql = """
                select sum(total_amount)
                from invoice
                where extract(MONTH from created_at) = ?
                and  extract(YEAR from created_at) = ?;
                """;

        try (Connection conn = ConnectionDB.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)
        ) {
            pstmt.setInt(1, month);
            pstmt.setInt(2, year);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                BigDecimal result = rs.getBigDecimal(1);
                return result != null ? result : BigDecimal.ZERO;
            }

        } catch (SQLException e) {
            System.out.println(Color.DO + "Lỗi SQL: " + e.getMessage() + Color.RESET);
        }
        return BigDecimal.ZERO;
    }

    @Override
    public BigDecimal calculateRevenueByYear(int year) {
        String sql = """
                select sum(total_amount)
                from invoice
                where extract(YEAR from created_at) = ?;
                """;

        try (Connection conn = ConnectionDB.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)
        ) {
            pstmt.setInt(1, year);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                BigDecimal result = rs.getBigDecimal(1);
                return result != null ? result : BigDecimal.ZERO;
            }

        } catch (SQLException e) {
            System.out.println(Color.DO + "Lỗi SQL: " + e.getMessage() + Color.RESET);
        }
        return BigDecimal.ZERO;
    }
}
