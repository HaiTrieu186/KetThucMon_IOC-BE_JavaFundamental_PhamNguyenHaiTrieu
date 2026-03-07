package ra.phone_store_manager.business.impl;

import ra.phone_store_manager.business.ICustomerService;
import ra.phone_store_manager.dao.ICustomerDAO;
import ra.phone_store_manager.dao.impl.CustomerDAOImpl;
import ra.phone_store_manager.model.Customer;
import ra.phone_store_manager.utils.helper.Color;

import java.util.List;

public class CustomerServiceImpl implements ICustomerService {
    private static final ICustomerDAO customerDAO = new CustomerDAOImpl();

    @Override
    public boolean addCustomer(Customer customer) {
        if (
            customer.getEmail() != null &&
            customerDAO.findCustomerByEmail(customer.getEmail()) != null
        ){
            System.out.println(Color.DO+
                    "* Lỗi: Khách hàng với email trên đã tồn tại !"
                    + Color.RESET);
            return false;
        }

        return customerDAO.createCustomer( customer );

    }

    @Override
    public boolean updateCustomer(Customer customer) {
        if (customerDAO.findCustomerByID(customer.getId()) == null){
            System.out.println(Color.DO+
                    "* Lỗi: Khách hàng không tồn tại !"
                    + Color.RESET);
            return false;
        }

        if (customer.getEmail() != null){
            Customer customerByNewEmail= findCustomerByEmail(customer.getEmail());

            if (customerByNewEmail != null && customerByNewEmail.getId() != customer.getId()){
                System.out.println(Color.DO+
                        "* Lỗi: Email này đã tồn tại, vui lòng chọn email khác !"
                        + Color.RESET);
                return false;
            }
        }

        return customerDAO.updateCustomer( customer );
    }

    @Override
    public boolean deleteCustomer(int id) {
        if (customerDAO.findCustomerByID(id) == null){
            System.out.println(Color.DO+
                    "* Lỗi: Khách hàng không tồn tại !"
                    + Color.RESET);
            return false;
        }

        return customerDAO.deleteCustomer( id );
    }

    @Override
    public List<Customer> getCustomerList() {
        return customerDAO.findAllCustomers();
    }

    @Override
    public Customer findCustomerById(int id) {
        return  customerDAO.findCustomerByID(id);
    }

    @Override
    public Customer findCustomerByEmail(String email) {
        return customerDAO.findCustomerByEmail(email);
    }
}
