package ra.phone_store_manager;

import ra.phone_store_manager.presentation.AdminView;
import ra.phone_store_manager.utils.database.ConnectionDB;
import ra.phone_store_manager.utils.helper.Color;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int choice;

        while (true) {
            System.out.println("\n"+
                    Color.CYAN + Color.BOLD + "+" + "-".repeat(5)
                    +Color.VANG + Color.BOLD + " HỆ THỐNG QUẢN LÝ CỬA HÀNG "
                    +Color.CYAN + Color.BOLD + "-".repeat(5) + "+" + Color.RESET);
            System.out.printf(
                    Color.CYAN + Color.BOLD + "|" + Color.RESET
                            +" %-35s "+
                            Color.CYAN + Color.BOLD + "|\n" + Color.RESET,
                    "1. Đăng nhập admin");
            System.out.printf(
                    Color.CYAN + Color.BOLD + "|" + Color.RESET
                            +" %-35s "+
                            Color.CYAN + Color.BOLD + "|\n" + Color.RESET,
                    "2. Đăng ký admin");
            System.out.printf(
                    Color.CYAN + Color.BOLD + "|" + Color.RESET
                            +" %-35s "+
                            Color.CYAN + Color.BOLD + "|\n" + Color.RESET,
                    "3. Thoát");
            System.out.println(
                    Color.CYAN + Color.BOLD + "+" + "-".repeat(37) + "+" + Color.RESET);

            System.out.print(Color.XANH_LA+"- Mời bạn chọn chức năng"
                    +Color.RESET+" (1-3): ");



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
                    AdminView.showMenuLogin();
                    break;
                case 2:
                    AdminView.showMenuRegister();
                    break;
                case 3:
                    System.out.println("\n"+Color.BOLD+Color.VANG+" +---- "+
                            Color.TIM+"Kết thúc chương trình, hẹn gặp lại !!!"+
                            Color.VANG+" ----+ "+Color.RESET);
                    sc.close();
                    System.exit(0);
                default:
                    System.out.println(Color.DO+
                            "* Lỗi: Lựa chọn không hợp lệ, vui lòng chọn 1-3 !"
                            + Color.RESET);
            }
        }
    }
}
