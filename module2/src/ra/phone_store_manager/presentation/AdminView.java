package ra.phone_store_manager.presentation;

import ra.phone_store_manager.business.IAdminService;
import ra.phone_store_manager.business.impl.AdminSerciveImpl;
import ra.phone_store_manager.model.Admin;
import ra.phone_store_manager.utils.helper.Color;
import ra.phone_store_manager.utils.helper.InputUtils;

public class AdminView {
    public static Admin adminLogin= null;
    private static final IAdminService adminService= new AdminSerciveImpl();

    public static void showMenuLogin(){
        int attempt = 0; // Biến đếm số lần nhập sai

        while (attempt < 3) { // Cho phép tối đa 3 lần

            System.out.println();
            System.out.println(Color.CYAN + "+".repeat(10)
                    + Color.VANG + Color.BOLD + " ĐĂNG NHẬP TÀI KHOẢN QUẢN TRỊ "
                    + Color.CYAN + "+".repeat(10) + Color.RESET);

            String username = InputUtils.getString("  Mời bạn nhập username: " );
            String password = InputUtils.getString("  Mời bạn nhập password: " );
            System.out.println(Color.XANH_DUONG + "=".repeat(49) + Color.RESET);
            System.out.println();

            Admin admin = adminService.loginAdmin(username, password);
            if (admin != null) {
                System.out.println(Color.BOLD
                        + Color.DO +"==> "
                        +Color.CYAN+"Đăng nhập thành công !"
                        + Color.DO +" <=="
                        +Color.RESET);
                adminLogin = admin;
                MainMenuView.showMainMenu();
                return;
            } else {
                attempt++;
                System.out.println(Color.DO + "* Lỗi: Username hoặc mật khẩu không đúng!");
                if (attempt < 3) {
                    System.out.println(Color.TIM + "  (Bạn còn " + (3 - attempt) + " lần thử)" + Color.RESET);
                }
            }
        }

        System.out.println(Color.DO + "* Bạn đã nhập sai quá nhiều lần. Quay lại Menu chính..." + Color.RESET);
    }

    public static void showMenuRegister(){
        System.out.println();
        System.out.println(Color.CYAN + "+".repeat(10)
                + Color.VANG + Color.BOLD + " ĐĂNG KÝ TÀI KHOẢN QUẢN TRỊ "
                + Color.CYAN + "+".repeat(10) + Color.RESET);


        // Check trùng username
        String username;
        while (true) {
            username = InputUtils.getString(Color.XANH_LA + "  Mời bạn nhập username mới: " + Color.RESET);

            if (adminService.checkExistUsername(username)) {
                System.out.println(Color.DO + "  * Lỗi: Username này đã tồn tại, vui lòng chọn tên khác!" + Color.RESET);
            } else {
                break;
            }
        }
        String password = InputUtils.getString(Color.XANH_LA + "  Mời bạn nhập password: " + Color.RESET);

        Admin newAdmin = new Admin();
        newAdmin.setUsername(username);
        newAdmin.setPassword(password);

        adminService.registerAdmin(newAdmin);
        System.out.printf(" %-46s \n","==>  Đăng ký tài khoản Admin thành công! <==");
        System.out.println(Color.XANH_DUONG + "=".repeat(48) + Color.RESET);
        System.out.println();

    }
}
