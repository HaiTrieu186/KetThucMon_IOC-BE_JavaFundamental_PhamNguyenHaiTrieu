package ra.phone_store_manager.business.impl;

import ra.phone_store_manager.business.IInvoiceService;
import ra.phone_store_manager.dao.IInvoiceDAO;
import ra.phone_store_manager.dao.impl.InvoiceDAOImpl;
import ra.phone_store_manager.model.Invoice;
import ra.phone_store_manager.model.InvoiceDetails;

import java.time.LocalDate;
import java.util.List;

public class InvoiceServiceImpl implements IInvoiceService {
    private static final IInvoiceDAO invoiceDAO = new InvoiceDAOImpl();
    @Override
    public boolean addInvoice(Invoice invoice) {

        return invoiceDAO.createInvoice(invoice);
    }

    @Override
    public List<Invoice> getAllInvoices() {
        return invoiceDAO.getAllInvoices();
    }

    @Override
    public List<Invoice> findInvoicesByCustomerName(String name) {
        return invoiceDAO.findInvoicesByCustomerName(name);
    }

    @Override
    public List<Invoice> findInvoicesByCreateDate(LocalDate createDate) {
        return invoiceDAO.findInvoicesByCreateDate(createDate);
    }

    @Override
    public List<InvoiceDetails> getInvoiceDetailsByInvoiceID(int id) {
        return invoiceDAO.findInvoiceDetailsByInvoiceID(id);
    }
}
