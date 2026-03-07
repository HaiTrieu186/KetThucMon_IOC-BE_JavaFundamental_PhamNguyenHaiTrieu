package ra.phone_store_manager.dao;

import ra.phone_store_manager.model.Customer;

import java.util.List;

public interface ICustomerDAO {
    boolean createCustomer(Customer customer);
    boolean updateCustomer(Customer customer);
    boolean deleteCustomer(int id);
    Customer findCustomerByID(int id);
    Customer findCustomerByEmail(String email);
    List<Customer> findAllCustomers();
}
