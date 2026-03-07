package ra.phone_store_manager.dao;

import ra.phone_store_manager.model.Invoice;
import ra.phone_store_manager.model.InvoiceDetails;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public interface IInvoiceDAO {
    boolean createInvoice(Invoice invoice);
    boolean createInvoiceDetails(InvoiceDetails invoiceDetails);
    Invoice findInvoiceByID(int id);
    List<Invoice> getAllInvoices();
    List<Invoice> findInvoicesByCustomerName(String customerName);
    List<Invoice> findInvoicesByCreateDate(LocalDate createDate);
    List<InvoiceDetails> findInvoiceDetailsByInvoiceID(int id);
}
