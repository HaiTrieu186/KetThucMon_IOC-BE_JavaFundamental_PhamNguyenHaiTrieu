package ra.phone_store_manager.presentation;

import ra.phone_store_manager.utils.helper.Color;

import java.util.Scanner;

public class InvoiceView {
    public static void showInvoiceMenu() {
        Scanner sc = new Scanner(System.in);
        int choice;

        while (true) {
            System.out.println("\n" +
                    Color.VANG + Color.BOLD + "+" + "-".repeat(10)
                    + Color.CYAN + Color.BOLD + " QUẢN LÝ HÓA ĐƠN "
                    + Color.VANG + Color.BOLD + "-".repeat(9) + "+" + Color.RESET);
            System.out.printf(
                    Color.VANG + Color.BOLD + "|" + Color.RESET
                            + " %-35s " +
                            Color.VANG + Color.BOLD + "|\n" + Color.RESET,
                    "1. Hiển thị danh sách hóa đơn");
            System.out.printf(
                    Color.VANG + Color.BOLD + "|" + Color.RESET
                            + " %-35s " +
                            Color.VANG + Color.BOLD + "|\n" + Color.RESET,
                    "2. Thêm mới hóa đơn");
            System.out.printf(
                    Color.VANG + Color.BOLD + "|" + Color.RESET
                            + " %-35s " +
                            Color.VANG + Color.BOLD + "|\n" + Color.RESET,
                    "3. Tìm kiếm hóa đơn");
            System.out.printf(
                    Color.VANG + Color.BOLD + "|" + Color.RESET
                            + " %-35s " +
                            Color.VANG + Color.BOLD + "|\n" + Color.RESET,
                    "4. Xem chi tiết hóa đơn");
            System.out.printf(
                    Color.VANG + Color.BOLD + "|" + Color.RESET
                            + " %-35s " +
                            Color.VANG + Color.BOLD + "|\n" + Color.RESET,
                    "5. Quay lại menu chính");
            System.out.println(
                    Color.VANG + Color.BOLD + "+" + "-".repeat(37) + "+" + Color.RESET);

            System.out.print(Color.XANH_DUONG + "- Mời bạn chọn chức năng"
                    + Color.RESET + " (1-5): ");

            choice = -1;
            try {
                choice = Integer.parseInt(sc.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.println(Color.DO +
                        "* Lỗi: Vui lòng nhập một số hợp lệ !"
                        + Color.RESET);
                continue;
            }

            switch (choice) {
                case 1:

                    break;
                case 2:

                    break;
                case 3:

                    break;
                case 4:

                    break;
                case 5:
                    System.out.println(Color.BOLD +
                            Color.VANG + "==> " +
                            Color.HONG_NHAT + "Quay về Menu chính" +
                            Color.VANG + " <==" + Color.RESET);
                    return;
                default:
                    System.out.println(Color.DO +
                            "* Lỗi: Lựa chọn không hợp lệ, vui lòng chọn 1-5 !"
                            + Color.RESET);
            }
        }
    }



}
