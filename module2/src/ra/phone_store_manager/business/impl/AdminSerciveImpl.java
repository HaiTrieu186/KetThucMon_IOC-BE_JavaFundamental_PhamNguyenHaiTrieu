package ra.phone_store_manager.business.impl;

import org.mindrot.jbcrypt.BCrypt;
import ra.phone_store_manager.business.IAdminService;
import ra.phone_store_manager.dao.impl.AdminDAOImpl;
import ra.phone_store_manager.model.Admin;

public class AdminSerciveImpl implements IAdminService {
    private static final AdminDAOImpl adminDao = new AdminDAOImpl();

    @Override
    public void registerAdmin(Admin admin) {
        admin.setPassword(BCrypt.hashpw(admin.getPassword(), BCrypt.gensalt(12)));
        adminDao.addAdmin(admin);
    }

    @Override
    public Admin loginAdmin(String username, String password) {
        Admin find = adminDao.findAdminByUsername(username);

        if (find != null && BCrypt.checkpw(password, find.getPassword())) {
            return find;
        }
        return null;
    }

    @Override
    public boolean checkExistUsername(String username) {
        return adminDao.findAdminByUsername(username) != null;
    }
}
