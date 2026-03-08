package ra.phone_store_manager.business;

import ra.phone_store_manager.model.Invoice;
import ra.phone_store_manager.model.InvoiceDetails;

import java.time.LocalDate;
import java.util.List;

public interface IInvoiceService {
    int addInvoice(Invoice invoice);
    boolean addInvoiceDetails(InvoiceDetails invoiceDetails);
    List<Invoice> getAllInvoices();
    List<Invoice> findInvoicesByCustomerName(String name);
    List<Invoice> findInvoicesByCreateDate(LocalDate createDate);
    List<InvoiceDetails> getInvoiceDetailsByInvoiceID(int id);
}
