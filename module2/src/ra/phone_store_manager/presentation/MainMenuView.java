package ra.phone_store_manager.presentation;

import ra.phone_store_manager.utils.helper.Color;

import java.util.Scanner;

public class MainMenuView {
    public static void showMainMenu(){
        Scanner sc = new Scanner(System.in);
        int choice;

        while (true) {
            System.out.println("\n"+
                    Color.TIM + Color.BOLD + "+" + "-".repeat(13)
                            +Color.XANH_LA + Color.BOLD + " MAIN MENU "
                            +Color.TIM + Color.BOLD + "-".repeat(13) + "+" + Color.RESET);
            System.out.printf(
                    Color.TIM + Color.BOLD + "|" + Color.RESET
                            +" %-35s "+
                            Color.TIM + Color.BOLD + "|\n" + Color.RESET,
                    "1. Quản lý sản phẩm điện thoại");
            System.out.printf(
                    Color.TIM + Color.BOLD + "|" + Color.RESET
                            +" %-35s "+
                            Color.TIM + Color.BOLD + "|\n" + Color.RESET,
                    "2. Quản lý khách hàng");
            System.out.printf(
                    Color.TIM + Color.BOLD + "|" + Color.RESET
                            +" %-35s "+
                            Color.TIM + Color.BOLD + "|\n" + Color.RESET,
                    "3. Quản lý hóa đơn");
            System.out.printf(
                    Color.TIM + Color.BOLD + "|" + Color.RESET
                            +" %-35s "+
                            Color.TIM + Color.BOLD + "|\n" + Color.RESET,
                    "4. Thống kê doanh thu");
            System.out.printf(
                    Color.TIM + Color.BOLD + "|" + Color.RESET
                            +" %-35s "+
                            Color.TIM + Color.BOLD + "|\n" + Color.RESET,
                    "5. Đăng xuất");
            System.out.println(
                    Color.TIM + Color.BOLD + "+" + "-".repeat(37) + "+" + Color.RESET);

            System.out.print(Color.XANH_DUONG+"- Mời bạn chọn chức năng"
                    +Color.RESET+" (1-5): ");

            choice = -1;
            try {
                choice = Integer.parseInt(sc.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.println(Color.DO+
                        "* Lỗi: Vui lòng nhập một số hợp lệ !"
                        + Color.RESET);
                continue;
            }

            switch (choice) {
                case 1:
                    ProductView.showProductMenu();
                    break;
                case 2:
                    CustomerView.showCustomerMenu();
                    break;
                case 3:
                    InvoiceView.showInvoiceMenu();
                    break;
                case 4:
                    StatisticView.showSatisticView();
                    break;
                case 5:
                    System.out.println("\n"+Color.BOLD+Color.VANG+" +---- "+
                            Color.HONG_NHAT+" Đăng xuất, tạm biệt "+
                            Color.RESET+AdminView.adminLogin.getUsername()+
                            Color.HONG_NHAT+" !!!"+ Color.VANG+" ----+ "+Color.RESET);
                    return;
                default:
                    System.out.println(Color.DO+
                            "* Lỗi: Lựa chọn không hợp lệ, vui lòng chọn 1-5 !"
                            + Color.RESET);
            }
        }
    }
}
