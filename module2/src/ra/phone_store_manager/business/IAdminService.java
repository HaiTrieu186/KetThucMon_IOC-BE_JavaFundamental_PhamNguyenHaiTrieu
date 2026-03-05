package ra.phone_store_manager.business;

import ra.phone_store_manager.model.Admin;

public interface IAdminService {
    void registerAdmin (Admin admin);
    Admin loginAdmin (String username, String password);
    boolean checkExistUsername(String username);
}
