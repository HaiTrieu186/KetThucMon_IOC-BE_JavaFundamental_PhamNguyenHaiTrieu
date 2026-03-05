package ra.phone_store_manager.dao;

import ra.phone_store_manager.model.Admin;

public interface IAdminDAO {
    void addAdmin(Admin admin);
    Admin findAdminByUsername(String username);
}
