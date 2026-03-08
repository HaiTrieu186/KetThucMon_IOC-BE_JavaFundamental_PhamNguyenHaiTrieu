package ra.phone_store_manager.presentation;

import ra.phone_store_manager.business.IInvoiceService;
import ra.phone_store_manager.business.impl.InvoiceServiceImpl;
import ra.phone_store_manager.utils.helper.Color;
import ra.phone_store_manager.utils.helper.FormatUtils;
import ra.phone_store_manager.utils.helper.InputUtils;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Scanner;

public class StatisticView {
    private final static IInvoiceService invoiceService = new InvoiceServiceImpl();

    public static void showSatisticView() {
        Scanner sc = new Scanner(System.in);
        int choice;

        while (true) {
            System.out.println("\n" +
                    Color.VANG + Color.BOLD + "+" + "-".repeat(10)
                    + Color.CYAN + Color.BOLD + " THỐNG KÊ DOANH THU "
                    + Color.VANG + Color.BOLD + "-".repeat(9) + "+" + Color.RESET);
            System.out.printf(
                    Color.VANG + Color.BOLD + "|" + Color.RESET
                            + " %-35s " +
                            Color.VANG + Color.BOLD + "|\n" + Color.RESET,
                    "1. Doanh thu theo ngày");
            System.out.printf(
                    Color.VANG + Color.BOLD + "|" + Color.RESET
                            + " %-35s " +
                            Color.VANG + Color.BOLD + "|\n" + Color.RESET,
                    "2. Doanh thu theo tháng");
            System.out.printf(
                    Color.VANG + Color.BOLD + "|" + Color.RESET
                            + " %-35s " +
                            Color.VANG + Color.BOLD + "|\n" + Color.RESET,
                    "3. Doanh thu theo năm");
            System.out.printf(
                    Color.VANG + Color.BOLD + "|" + Color.RESET
                            + " %-35s " +
                            Color.VANG + Color.BOLD + "|\n" + Color.RESET,
                    "4. Xem chi tiết hóa đơn");
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
                    handleRevenueByDate();
                    break;
                case 2:
                    handleRevenueByMonth();
                    break;
                case 3:
                    hanldeRevenueByYear();
                    break;
                case 4:
                    System.out.println(Color.BOLD +
                            Color.VANG + "==> " +
                            Color.HONG_NHAT + "Quay về Menu chính" +
                            Color.VANG + " <==" + Color.RESET);
                    return;
                default:
                    System.out.println(Color.DO +
                            "* Lỗi: Lựa chọn không hợp lệ, vui lòng chọn 1-4 !"
                            + Color.RESET);
            }
        }
    }

    private static void hanldeRevenueByYear() {
        System.out.println("\n" + Color.CYAN + "--- XEM DOANH THU THEO NĂM ---" + Color.RESET);
        int year;
        while (true) {
            year = InputUtils.getPositiveInt("Mời bạn nhập năm cần xem: ");
            if (year <= LocalDate.now().getYear()) {
                break;
            }
            System.out.println(Color.DO +
                    "* Lỗi: Năm không hợp lệ (phải nhỏ hơn năm hiện tại - " +
                    LocalDate.now().getYear() + ") !"
                    + Color.RESET);
        }

        BigDecimal amount = invoiceService.getRevenueByYear(year);
        if (amount.compareTo(BigDecimal.ZERO) == 0) {
            System.out.println(Color.VANG + "==>"
                    + Color.HONG_NHAT + " Chưa có doanh thu năm  "
                    + year + " !");
        } else {
            System.out.println(Color.VANG + "==>"
                    + Color.HONG_NHAT + " Doanh thu năm " + year + ": "
                    + FormatUtils.formatVND(amount));

        }
    }

    private static void handleRevenueByMonth() {
        System.out.println("\n" + Color.CYAN + "--- XEM DOANH THU THEO THÁNG ---" + Color.RESET);
        int year;
        int month;
        while (true) {
            year = InputUtils.getPositiveInt("Mời bạn nhập năm cần xem: ");
            if (year <= LocalDate.now().getYear()) {
                break;
            }
            System.out.println(Color.DO +
                    "* Lỗi: Năm không hợp lệ (phải nhỏ hơn năm hiện tại - " +
                    LocalDate.now().getYear() + ") !"
                    + Color.RESET);
        }

        while (true) {
            month = InputUtils.getPositiveInt("Mời bạn nhập năm tháng cần xem: ");
            if (month >= 1 && month <= 12) {
                break;
            }
            System.out.println(Color.DO +
                    "* Lỗi: Tháng không hợp lệ (1-12) !"
                    + Color.RESET);
        }


        BigDecimal amount = invoiceService.getRevenueByMonth(month, year);

        if (amount.compareTo(BigDecimal.ZERO) == 0) {
            System.out.println(Color.VANG + "==>"
                    + Color.HONG_NHAT + " Chưa có doanh thu tháng  "
                    + month + "/ " + year + " !");
        } else {
            System.out.println(Color.VANG + "==>"
                    + Color.HONG_NHAT + " Doanh thu tháng "
                    + month + "/ " + year + ": "
                    + FormatUtils.formatVND(amount));
        }
    }

    private static void handleRevenueByDate() {
        System.out.println("\n" + Color.CYAN + "--- XEM DOANH THU THEO THÁNG ---" + Color.RESET);
        LocalDate date;
        while (true) {
            date = InputUtils.getLocalDate("Mời bạn nhập ngày cần xem: ");

            if (date.isBefore(LocalDate.now()) || date.isEqual(LocalDate.now())) {
                break;
            }
            System.out.println(Color.DO +
                    "* Lỗi: Năm không hợp lệ (phải nhỏ ngày hiện tại - " +
                    FormatUtils.formatDate(LocalDate.now()) + ") !"
                    + Color.RESET);
        }

        BigDecimal amount = invoiceService.getRevenueByDate(date);

        if (amount.compareTo(BigDecimal.ZERO) == 0) {
            System.out.println(Color.VANG + "==>"
                    + Color.HONG_NHAT + " Chưa có doanh thu ngày " + FormatUtils.formatDate(date) + " !");
        } else {
            System.out.println(Color.VANG + "==>"
                    + Color.HONG_NHAT + " Doanh thu ngày "
                    + FormatUtils.formatDate(date) + ": "
                    + FormatUtils.formatVND(amount));
        }
    }
}
