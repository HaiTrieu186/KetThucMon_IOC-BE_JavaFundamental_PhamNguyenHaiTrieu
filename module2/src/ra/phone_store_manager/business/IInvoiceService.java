package ra.phone_store_manager.business;

import ra.phone_store_manager.model.Invoice;
import ra.phone_store_manager.model.InvoiceDetails;

import java.time.LocalDate;
import java.util.List;

public interface IInvoiceService {
    boolean addInvoice(Invoice invoice);
    List<Invoice> getAllInvoices();
    List<Invoice> findInvoicesByCustomerName(String name);
    List<Invoice> findInvoicesByCreateDate(LocalDate createDate);
    List<InvoiceDetails> getInvoiceDetailsByInvoiceID(int id);
}
