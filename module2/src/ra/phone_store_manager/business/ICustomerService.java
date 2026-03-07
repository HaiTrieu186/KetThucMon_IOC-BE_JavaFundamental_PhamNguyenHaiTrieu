package ra.phone_store_manager.business;

import ra.phone_store_manager.model.Customer;

import java.util.List;

public interface ICustomerService {
    boolean addCustomer(Customer customer);
    boolean updateCustomer(Customer customer);
    boolean deleteCustomer(int id);
    List<Customer> getCustomerList();
    Customer findCustomerById(int id);
    Customer findCustomerByEmail(String email);
}
