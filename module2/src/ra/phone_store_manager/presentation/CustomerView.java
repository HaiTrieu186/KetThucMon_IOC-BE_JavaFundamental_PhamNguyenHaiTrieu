package ra.phone_store_manager.presentation;

import ra.phone_store_manager.business.ICustomerService;
import ra.phone_store_manager.business.impl.CustomerServiceImpl;
import ra.phone_store_manager.model.Customer;
import ra.phone_store_manager.utils.helper.Color;
import ra.phone_store_manager.utils.helper.InputUtils;

import java.util.List;
import java.util.Scanner;

public class CustomerView {
    private static final ICustomerService customerService = new CustomerServiceImpl();
    public static void showCustomerMenu(){
        Scanner sc = new Scanner(System.in);
        int choice;

        while (true) {
            System.out.println("\n" +
                    Color.VANG + Color.BOLD + "+" + "-".repeat(9)
                    + Color.CYAN + Color.BOLD + " QUẢN LÝ KHÁCH HÀNG "
                    + Color.VANG + Color.BOLD + "-".repeat(8) + "+" + Color.RESET);
            System.out.printf(
                    Color.VANG + Color.BOLD + "|" + Color.RESET
                            + " %-35s " +
                            Color.VANG + Color.BOLD + "|\n" + Color.RESET,
                    "1. Hiển thị danh sách khách hàng");
            System.out.printf(
                    Color.VANG + Color.BOLD + "|" + Color.RESET
                            + " %-35s " +
                            Color.VANG + Color.BOLD + "|\n" + Color.RESET,
                    "2. Thêm khách hàng mới");
            System.out.printf(
                    Color.VANG + Color.BOLD + "|" + Color.RESET
                            + " %-35s " +
                            Color.VANG + Color.BOLD + "|\n" + Color.RESET,
                    "3. Cập nhật thông tin khách hàng");
            System.out.printf(
                    Color.VANG + Color.BOLD + "|" + Color.RESET
                            + " %-35s " +
                            Color.VANG + Color.BOLD + "|\n" + Color.RESET,
                    "4. Xóa khách hàng theo ID");
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
                    showAllCustomers();
                    break;
                case 2:
                    handleAddCustomer();
                    break;
                case 3:
                    handleUpdateCustomer();
                    break;
                case 4:
                    handleDeleteCustomer();
                    break;
                case 5:
                    System.out.println(Color.BOLD+
                            Color.VANG+"==> "+
                            Color.HONG_NHAT+"Quay về Menu chính"+
                            Color.VANG+" <=="+ Color.RESET);
                    return;
                default:
                    System.out.println(Color.DO +
                            "* Lỗi: Lựa chọn không hợp lệ, vui lòng chọn 1-5 !"
                            + Color.RESET);
            }
        }
    }

    private static void showAllCustomers() {
        System.out.println("\n" + Color.CYAN + "--- DANH SÁCH KHÁCH HÀNG ---" + Color.RESET);
        List<Customer> list = customerService.getCustomerList();
        displayCustomers(list);
    }

    private static void handleAddCustomer() {
        System.out.println("\n" + Color.CYAN + "--- THÊM KHÁCH HÀNG MỚI ---" + Color.RESET);
        Customer c = new Customer();

        c.setName(InputUtils.getString("Nhập tên khách hàng (*Bắt buộc): "));
        c.setPhone(InputUtils.getOptionalStringWithRegex(
                "Nhập SĐT (Bắt đầu 0 hoặc +84, 10 số, Enter để bỏ qua): ",
                InputUtils.REGEX_PHONE_VN,
                "Định dạng Số điện thoại không hợp lệ!"
        ));
        c.setEmail(handleInputEmail(null));
        c.setAddress(InputUtils.getOptionalString("Nhập địa chỉ (Nhấn Enter để bỏ qua): "));


        if (customerService.addCustomer(c)) {
            System.out.println(Color.XANH_LA + "=> Thêm khách hàng thành công!" + Color.RESET);
        } else {
            System.out.println(Color.DO + "=> Lỗi: Thêm khách hàng thất bại!" + Color.RESET);
        }
    }

    private static void handleUpdateCustomer() {
        System.out.println("\n" + Color.CYAN + "--- CẬP NHẬT THÔNG TIN KHÁCH HÀNG ---" + Color.RESET);
        int id = InputUtils.getPositiveInt("Nhập ID khách hàng cần cập nhật: ");

        // Kiểm tra tồn tại
        Customer currentCustomer = customerService.findCustomerById(id);
        if (currentCustomer == null) {
            System.out.println(Color.DO + "  * Lỗi: Không tìm thấy khách hàng có ID = " + id + Color.RESET);
            return;
        }

        // Hiện thông tin cũ
        System.out.println(Color.XANH_DUONG + "  [Thông tin hiện tại]" + Color.RESET);
        System.out.println("  - Tên KH: " + currentCustomer.getName());
        System.out.println("  - SĐT   : " + (currentCustomer.getPhone() != null ? currentCustomer.getPhone() : "Trống"));
        System.out.println("  - Email : " + (currentCustomer.getEmail() != null ? currentCustomer.getEmail() : "Trống"));
        System.out.println("  - Đ/chỉ : " + (currentCustomer.getAddress() != null ? currentCustomer.getAddress() : "Trống"));

        // Nhập mới
        System.out.println(Color.VANG + "\n Nhập thông tin mới. (GIỮ NGUYÊN thì nhấn Enter)" + Color.RESET);
        Customer c = new Customer();
        c.setId(id); // Giữ nguyên ID

        /// Tên
        String newName = InputUtils.getOptionalString("Nhập tên khách hàng mới (Nhấn Enter để giữ nguyên): ");
        c.setName(newName != null ? newName : currentCustomer.getName());

        /// SĐT
        String newPhone = InputUtils.getOptionalStringWithRegex(
                "Nhập SĐT mới (Bắt đầu 0 hoặc +84, 10 số, Enter để giữ nguyên): ",
                InputUtils.REGEX_PHONE_VN,
                "Định dạng Số điện thoại không hợp lệ!"
        );
        c.setPhone(newPhone != null ? newPhone : currentCustomer.getPhone());

        /// Email
        String newEmail = handleInputEmail(currentCustomer.getEmail());
        c.setEmail(newEmail != null ? newEmail : currentCustomer.getEmail());

        /// Địa chỉ
        String newAddress = InputUtils.getOptionalString("Nhập địa chỉ mới (Nhấn Enter để giữ nguyên): ");
        c.setAddress(newAddress != null ? newAddress : currentCustomer.getAddress());

        // Xác nhận
        if (!confirmAction("Bạn có chắc chắn muốn LƯU các thay đổi này không?")) {
            System.out.println(Color.VANG + "=> Đã hủy thao tác cập nhật." + Color.RESET);
            return;
        }

        if (customerService.updateCustomer(c)) {
            System.out.println(Color.XANH_LA + "=> Cập nhật khách hàng thành công!" + Color.RESET);
        } else {
            System.out.println(Color.DO + "=> Lỗi: Cập nhật thất bại!" + Color.RESET);
        }
    }

    private static void handleDeleteCustomer() {
        System.out.println("\n" + Color.CYAN + "--- XÓA KHÁCH HÀNG ---" + Color.RESET);
        int id = InputUtils.getPositiveInt("Nhập ID khách hàng cần xóa: ");

        Customer currentCustomer = customerService.findCustomerById(id);
        if (currentCustomer == null) {
            System.out.println(Color.DO + "  * Lỗi: Không tìm thấy khách hàng có ID = " + id + Color.RESET);
            return;
        }

        System.out.println(Color.DO + "  [CẢNH BÁO] Bạn chuẩn bị xóa khách hàng: " + Color.RESET + currentCustomer.getName());

        if (!confirmAction("Bạn có CHẮC CHẮN muốn xóa khách hàng này không? Thao tác không thể hoàn tác!")) {
            System.out.println(Color.VANG + "=> Đã hủy thao tác xóa." + Color.RESET);
            return;
        }

        if (customerService.deleteCustomer(id)) {
            System.out.println(Color.XANH_LA + "=> Xóa khách hàng thành công!" + Color.RESET);
        } else {
            System.out.println(Color.DO + "=> Lỗi: Xóa khách hàng thất bại!" + Color.RESET);
        }
    }

    private static void displayCustomers(List<Customer> customers) {
        if (customers == null || customers.isEmpty()) {
            System.out.println(Color.DO + "  * Không có dữ liệu khách hàng!" + Color.RESET);
            return;
        }

        String line = "+" + "-".repeat(6) + "+" + "-".repeat(27) + "+" + "-".repeat(17) + "+" + "-".repeat(27) + "+" + "-".repeat(30) + "+";
        System.out.println(Color.VANG + line);
        System.out.printf("| %-4s | %-25s | %-15s | %-25s | %-28s |\n",
                "ID", "Tên Khách Hàng", "Số Điện Thoại", "Email", "Địa Chỉ");
        System.out.println(line + Color.RESET);

        for (Customer c : customers) {
            String phoneStr = c.getPhone() != null ? c.getPhone() : "-";
            String emailStr = c.getEmail() != null ? c.getEmail() : "-";
            String addrStr = c.getAddress() != null ? c.getAddress() : "-";

            System.out.printf(Color.VANG + "| " + Color.RESET
                            + "%-4d" + Color.VANG + " | " + Color.RESET
                            + "%-25s" + Color.VANG + " | " + Color.RESET
                            + "%-15s" + Color.VANG + " | " + Color.RESET
                            + "%-25s" + Color.VANG + " | " + Color.RESET
                            + "%-28s " + Color.VANG + "|\n" + Color.RESET,
                    c.getId(),
                    c.getName().length() > 25 ? c.getName().substring(0, 22) + "..." : c.getName(),
                    phoneStr,
                    emailStr.length() > 25 ? emailStr.substring(0, 22) + "..." : emailStr,
                    addrStr.length() > 28 ? addrStr.substring(0, 25) + "..." : addrStr
            );
        }
        System.out.println(Color.VANG + line + Color.RESET);
    }

    private static boolean confirmAction(String message) {
        Scanner sc = new Scanner(System.in);
        while (true) {
            System.out.print(Color.VANG + message + " (Y/N): " + Color.RESET);
            String confirm = sc.nextLine().trim();
            if (confirm.equalsIgnoreCase("Y")) return true;
            if (confirm.equalsIgnoreCase("N")) return false;
            System.out.println(Color.DO + "* Lỗi: Vui lòng chỉ nhập Y (Đồng ý) hoặc N (Hủy bỏ)!" + Color.RESET);
        }
    }

    ////// Hàm xử lý nhập liệu cho email
    private static String handleInputEmail(String currentEmail) {
        while (true) {

            String email = InputUtils.getOptionalStringWithRegex(
                    "Nhập Email (Nhấn Enter để bỏ qua): ",
                    InputUtils.REGEX_EMAIL,
                    "Định dạng Email không hợp lệ!"
            );

            if (email == null) {
                return null;
            }

            // Nếu hập lại đúng email cũ -> Hợp lệ
            if (currentEmail != null && email.equalsIgnoreCase(currentEmail)) {
                return email;
            }

            // Check trùng lặp
            if (customerService.findCustomerByEmail(email) != null) {
                System.out.println(Color.DO + "  * Lỗi: Email này đã có người sử dụng. Vui lòng nhập Email khác!" + Color.RESET);
                continue;
            }

            return email;
        }
    }
}
