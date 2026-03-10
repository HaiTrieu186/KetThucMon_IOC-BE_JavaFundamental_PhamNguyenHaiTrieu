package ra.phone_store_manager.business.impl;

import ra.phone_store_manager.business.IInvoiceService;
import ra.phone_store_manager.dao.IInvoiceDAO;
import ra.phone_store_manager.dao.impl.InvoiceDAOImpl;
import ra.phone_store_manager.model.Invoice;
import ra.phone_store_manager.model.InvoiceDetails;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public class InvoiceServiceImpl implements IInvoiceService {
    private static final IInvoiceDAO invoiceDAO = new InvoiceDAOImpl();
    @Override
    public boolean addInvoice(Invoice invoice, List<InvoiceDetails> invoiceDetails) {
        return invoiceDAO.createInvoice(invoice, invoiceDetails);
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

    @Override
    public BigDecimal getRevenueByDate(LocalDate date) {
        return invoiceDAO.calculateRevenueByDate(date);
    }

    @Override
    public BigDecimal getRevenueByMonth(int month, int year) {
        return invoiceDAO.calculateRevenueByMonth(month, year);
    }

    @Override
    public BigDecimal getRevenueByYear(int year) {
        return invoiceDAO.calculateRevenueByYear(year);
    }
}
