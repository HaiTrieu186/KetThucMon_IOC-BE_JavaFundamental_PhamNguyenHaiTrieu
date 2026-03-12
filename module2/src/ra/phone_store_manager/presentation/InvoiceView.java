package ra.phone_store_manager.presentation;

import ra.phone_store_manager.business.ICustomerService;
import ra.phone_store_manager.business.IInvoiceService;
import ra.phone_store_manager.business.IProductService;
import ra.phone_store_manager.business.impl.CustomerServiceImpl;
import ra.phone_store_manager.business.impl.InvoiceServiceImpl;
import ra.phone_store_manager.business.impl.ProductServiceImpl;
import ra.phone_store_manager.model.Customer;
import ra.phone_store_manager.model.Invoice;
import ra.phone_store_manager.model.InvoiceDetails;
import ra.phone_store_manager.model.Product;
import ra.phone_store_manager.utils.helper.Color;
import ra.phone_store_manager.utils.helper.FormatUtils;
import ra.phone_store_manager.utils.helper.InputUtils;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class InvoiceView {
    private static final IInvoiceService invoiceService = new InvoiceServiceImpl();
    private static final IProductService productService = new ProductServiceImpl();
    private static final ICustomerService customerService = new CustomerServiceImpl();

    public static void showInvoiceMenu() {
        Scanner sc = new Scanner(System.in);
        int choice;
        String[] menuOptions = {
                "1. Hiển thị danh sách hóa đơn",
                "2. Thêm mới hóa đơn",
                "3. Tìm kiếm hóa đơn",
                "4. Xem chi tiết hóa đơn",
                "5. Quay lại menu chính"
        };

        while (true) {
            System.out.println("\n" +
                    Color.VANG + Color.BOLD + "+" + "-".repeat(10)
                    + Color.CYAN + Color.BOLD + " QUẢN LÝ HÓA ĐƠN "
                    + Color.VANG + Color.BOLD + "-".repeat(10) + "+" + Color.RESET);

            for (String option : menuOptions) {
                System.out.printf(
                        Color.VANG + Color.BOLD + "|" + Color.RESET
                                + " %-35s " +
                                Color.VANG + Color.BOLD + "|\n" + Color.RESET,
                        option
                );
            }

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
                    showInvoiceList();
                    break;
                case 2:
                    handleCreateInvoice();
                    break;
                case 3:
                    handleSearchInvoice();
                    break;
                case 4:
                    handleViewInvoiceDetails();
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

    private static void handleCreateInvoice() {
        System.out.println("\n" + Color.CYAN + "--- THÊM HÓA ĐƠN MỚI ---" + Color.RESET);
        BigDecimal totalAmount = BigDecimal.ZERO;
        List<InvoiceDetails> cart = new ArrayList<>();

        /// Check danh sách hàng hiện tại còn hàng
        List<Product> availableProducts = productService.getProductList().stream()
                .filter(p -> p.getStock() > 0)
                .toList();

        if (availableProducts.isEmpty()) {
            System.out.println(Color.DO + "  * Hiện tại tất cả sản phẩm đều đã hết hàng!" + Color.RESET);
            return;
        }

        /// Nhập ID khách mua
        int customerId = InputUtils.getPositiveInt("Mời bạn nhập ID khách mua: ");

        if (customerService.findCustomerById(customerId) == null) {
            System.out.println(Color.DO + "  * Lỗi: Không tồn tại khách hàng trên!" + Color.RESET);
            return;
        }

        while (true) {
            displayAvailableProducts(availableProducts);
            int productId = InputUtils.getPositiveInt("Mời bạn chọn ID sản phẩm cần mua: ");

            Product product = productService.getProductById(productId);
            if (product == null || product.getStock() <= 0) {
                System.out.println(Color.DO + "  * Lỗi: Không tồn tại sản phẩm hoặc đã hết hàng, vui lòng nhập lại!" + Color.RESET);
                continue;
            }

            int quantity;
            while (true) {
                quantity = InputUtils.getPositiveInt("Mời bạn nhập số lượng cần mua: ");

                // số lượng hiện tại của sản phẩm trong giỏ
                int currentInCart = 0;
                for (InvoiceDetails item : cart) {
                    if (item.getProduct_id() == productId) {
                        currentInCart += item.getQuantity();
                    }
                }

                if (quantity + currentInCart > product.getStock()) {
                    System.out.println(Color.DO + "  * Lỗi: Số lượng tồn kho không đủ (Kho còn " + product.getStock() + ", trong giỏ đã có " + currentInCart + ")!" + Color.RESET);
                    continue;
                }
                break;
            }

            /// Cập nhật tổng tiền
            totalAmount = totalAmount.add(product.getPrice().multiply(BigDecimal.valueOf(quantity)));

            /// Thêm vào danh sách (nếu trùng sản phẩm thì cộng dồn)
            boolean isExistingInCart = false;
            for (InvoiceDetails currentItem : cart) {
                if (currentItem.getProduct_id() == productId) {
                    currentItem.setQuantity(currentItem.getQuantity() + quantity);
                    isExistingInCart = true;
                    break;
                }
            }

            if (!isExistingInCart) {
                InvoiceDetails newItem = new InvoiceDetails();
                newItem.setProduct_id(productId);
                newItem.setQuantity(quantity);
                newItem.setUnit_price(product.getPrice());
                cart.add(newItem);
            }

            // In rổ hàng hiện tại
            displayCurrentCart(cart, totalAmount);

            /// Xác nhận mua tiếp hay không
            if (!confirmAction("Bạn có muốn mua thêm sản phẩm khác không?")) {
                System.out.println(Color.VANG + "=> Đang tiến hành chốt đơn..." + Color.RESET);
                break;
            }
        }

        // ============ LƯU HÓA ĐƠN ============
        Invoice invoice = new Invoice();
        invoice.setTotal_amount(totalAmount);
        invoice.setCustomer_id(customerId);


        if ( !invoiceService.addInvoice(invoice, cart)) {
            System.out.println(Color.DO + "=> Lỗi: Thêm hóa đơn thất bại!" + Color.RESET);
        } else {
            System.out.println(Color.BOLD + Color.XANH_LA + "==> Tạo hóa đơn thành công với !!!" + Color.RESET);
        }
    }

    private static void showInvoiceList() {
        System.out.println("\n" + Color.CYAN + "--- DANH SÁCH HÓA ĐƠN ---" + Color.RESET);
        List<Invoice> list = invoiceService.getAllInvoices();
        displayInvoices(list);
    }

    private static void displayInvoices(List<Invoice> invoices) {
        if (invoices == null || invoices.isEmpty()) {
            System.out.println(Color.DO + "  * Không có dữ liệu hóa đơn!" + Color.RESET);
            return;
        }

        String line = "+" + "-".repeat(6) + "+" + "-".repeat(17) + "+" + "-".repeat(17) + "+" + "-".repeat(30) + "+";
        System.out.println(Color.VANG + line);
        System.out.printf("| %-4s | %-15s | %-15s | %-28s |\n",
                "ID", "ID Khách Hàng", "Ngày Tạo", "Tổng tiền");
        System.out.println(line + Color.RESET);

        for (Invoice i : invoices) {
            String formattedPrice = FormatUtils.formatVND(i.getTotal_amount());
            String formattedDate = FormatUtils.formatDate(i.getCreated_at());
            String customerDisplay = (i.getCustomer_id() == 0) ? "Khách đã xóa" : String.valueOf(i.getCustomer_id());

            System.out.printf(Color.VANG + "| " + Color.RESET
                            + "%-4d" + Color.VANG + " | " + Color.RESET
                            + "%-15s" + Color.VANG + " | " + Color.RESET
                            + "%-15s" + Color.VANG + " | " + Color.RESET
                            + "%-28s " + Color.VANG + "|\n" + Color.RESET,
                    i.getId(),
                    customerDisplay,
                    formattedDate,
                    formattedPrice
            );
        }
        System.out.println(Color.VANG + line + Color.RESET);
    }

    private static void displayAvailableProducts(List<Product> products) {
        if (products == null || products.isEmpty()) {
            System.out.println(Color.DO + "  * Không tìm thấy sản phẩm nào!" + Color.RESET);
            return;
        }

        String line = "+" + "-".repeat(6) + "+" + "-".repeat(27) + "+" + "-".repeat(17) + "+" + "-".repeat(20) + "+" + "-".repeat(10) + "+";
        System.out.println(Color.VANG + line);

        System.out.printf("| "
                        + Color.CAM + "%-4s" + Color.RESET + Color.VANG + " | " + Color.RESET
                        + Color.CAM + "%-25s" + Color.RESET + Color.VANG + " | " + Color.RESET
                        + Color.CAM + "%-15s" + Color.RESET + Color.VANG + " | " + Color.RESET
                        + Color.CAM + "%-18s" + Color.RESET + Color.VANG + " | " + Color.RESET
                        + Color.CAM + "%-8s" + Color.RESET + Color.VANG + " |\n" + Color.RESET,
                "ID", "Tên Sản Phẩm", "Thương Hiệu", "Giá (VNĐ)", "Kho");
        System.out.println(Color.VANG + line + Color.RESET);

        for (Product p : products) {
            String formattedPrice = FormatUtils.formatVND(p.getPrice()); // Đảm bảo bạn gọi đúng hàm formatVND
            System.out.printf(Color.VANG + "| " + Color.RESET
                            + "%-4d" + Color.VANG + " | " + Color.RESET
                            + "%-25s" + Color.VANG + " | " + Color.RESET
                            + "%-15s" + Color.VANG + " | " + Color.RESET
                            + "%-18s" + Color.VANG + " | " + Color.RESET
                            + "%-8d " + Color.VANG + "|\n" + Color.RESET,
                    p.getId(),
                    p.getName().length() > 25 ? p.getName().substring(0, 22) + "..." : p.getName(),
                    p.getBrand().length() > 15 ? p.getBrand().substring(0, 12) + "..." : p.getBrand(),
                    formattedPrice,
                    p.getStock());
        }

        System.out.println(Color.VANG + line + Color.RESET);
    }

    private static void displayCurrentCart(List<InvoiceDetails> cart, BigDecimal totalAmount) {
        if (cart == null || cart.isEmpty()) {
            System.out.println(Color.VANG + "  * Giỏ hàng hiện đang trống!" + Color.RESET);
            return;
        }

        System.out.println("\n" + Color.XANH_DUONG + "=== GIỎ HÀNG HIỆN TẠI CỦA KHÁCH ===" + Color.RESET);

        // Mã SP (8) | Đơn giá (20) | SL (10) | Thành tiền (22)
        String line = "+" + "-".repeat(8) + "+" + "-".repeat(20) + "+" + "-".repeat(10) + "+" + "-".repeat(22) + "+";
        System.out.println(Color.VANG + line);

        System.out.printf("| "
                        + Color.CAM + "%-6s" + Color.RESET + Color.VANG + " | " + Color.RESET
                        + Color.CAM + "%-18s" + Color.RESET + Color.VANG + " | " + Color.RESET
                        + Color.CAM + "%-8s" + Color.RESET + Color.VANG + " | " + Color.RESET
                        + Color.CAM + "%-20s" + Color.RESET + Color.VANG + " |\n" + Color.RESET,
                "Mã SP", "Đơn Giá", "Số Lượng", "Thành Tiền");
        System.out.println(Color.VANG + line + Color.RESET);

        for (InvoiceDetails item : cart) {
            BigDecimal lineTotal = item.getUnit_price().multiply(BigDecimal.valueOf(item.getQuantity()));

            System.out.printf(Color.VANG + "| " + Color.RESET
                            + "%-6d" + Color.VANG + " | " + Color.RESET
                            + "%-18s" + Color.VANG + " | " + Color.RESET
                            + "%-8d" + Color.VANG + " | " + Color.RESET
                            + "%-20s" + Color.VANG + " |\n" + Color.RESET,
                    item.getProduct_id(),
                    FormatUtils.formatVND(item.getUnit_price()),
                    item.getQuantity(),
                    FormatUtils.formatVND(lineTotal));
        }

        System.out.println(Color.VANG + line + Color.RESET);
        System.out.printf(Color.XANH_LA + "=> TỔNG TIỀN TẠM TÍNH: %s\n" + Color.RESET, FormatUtils.formatVND(totalAmount));
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

    private static void handleSearchInvoice() {
        while (true) {
            System.out.println("\n" + Color.CYAN + "--- TÌM KIẾM HÓA ĐƠN ---" + Color.RESET);
            System.out.println("1. Tìm kiếm theo tên khách hàng");
            System.out.println("2. Tìm kiếm theo ngày lập (dd/MM/yyyy)");
            System.out.println("3. Quay lại");

            int choice = InputUtils.getInt("Chọn chức năng (1-3): ");

            switch (choice) {
                case 1:
                    String name = InputUtils.getString("Nhập tên khách hàng cần tìm: ");
                    List<Invoice> listByName = invoiceService.findInvoicesByCustomerName(name);

                    System.out.println(Color.XANH_LA + "=> Kết quả tìm kiếm cho: " + name + Color.RESET);
                    displayInvoices(listByName);
                    break;
                case 2:
                    LocalDate searchDate = InputUtils.getLocalDate("Nhập ngày lập hóa đơn (dd/MM/yyyy): ");
                    List<Invoice> listByDate = invoiceService.findInvoicesByCreateDate(searchDate);

                    System.out.println(Color.XANH_LA + "=> Kết quả tìm kiếm trong ngày: " + FormatUtils.formatDate(searchDate) + Color.RESET);
                    displayInvoices(listByDate);
                    break;
                case 3:
                    return;
                default:
                    System.out.println(Color.DO + "* Lỗi: Vui lòng chọn từ 1 đến 3!" + Color.RESET);
            }
        }
    }

    private static void handleViewInvoiceDetails() {
        System.out.println("\n" + Color.CYAN + "--- XEM CHI TIẾT HÓA ĐƠN ---" + Color.RESET);
        int invoiceId = InputUtils.getPositiveInt("Mời bạn nhập ID Hóa Đơn cần xem: ");
        List<InvoiceDetails> details = invoiceService.getInvoiceDetailsByInvoiceID(invoiceId);

        if (details == null || details.isEmpty()) {
            System.out.println(Color.DO + "  * Lỗi: Hóa đơn này không tồn tại hoặc trống!" + Color.RESET);
            return;
        }

        System.out.println("\n" + Color.XANH_DUONG + "=== CHI TIẾT SẢN PHẨM TRONG HÓA ĐƠN #" + invoiceId + " ===" + Color.RESET);
        String line = "+" + "-".repeat(8) + "+" + "-".repeat(25) + "+" + "-".repeat(17) + "+" + "-".repeat(10) + "+" + "-".repeat(20) + "+";
        System.out.println(Color.VANG + line);

        System.out.printf("| "
                        + Color.CAM + "%-6s" + Color.RESET + Color.VANG + " | " + Color.RESET
                        + Color.CAM + "%-23s" + Color.RESET + Color.VANG + " | " + Color.RESET
                        + Color.CAM + "%-15s" + Color.RESET + Color.VANG + " | " + Color.RESET
                        + Color.CAM + "%-8s" + Color.RESET + Color.VANG + " | " + Color.RESET
                        + Color.CAM + "%-18s" + Color.RESET + Color.VANG + " |\n" + Color.RESET,
                "Mã SP", "Tên Sản Phẩm", "Đơn Giá", "Số Lượng", "Thành Tiền");
        System.out.println(Color.VANG + line + Color.RESET);

        BigDecimal finalTotal = BigDecimal.ZERO;

        for (InvoiceDetails item : details) {
            Product p = productService.getProductById(item.getProduct_id());
            String productName = (p != null) ? p.getName() : "SP đã bị xóa khỏi hệ thống";
            productName = productName.length() > 22 ? productName.substring(0, 19) + "..." : productName;

            BigDecimal lineTotal = item.getUnit_price().multiply(BigDecimal.valueOf(item.getQuantity()));
            finalTotal = finalTotal.add(lineTotal);

            System.out.printf(Color.VANG + "| " + Color.RESET
                            + "%-6d" + Color.VANG + " | " + Color.RESET
                            + "%-23s" + Color.VANG + " | " + Color.RESET
                            + "%-15s" + Color.VANG + " | " + Color.RESET
                            + "%-8d" + Color.VANG + " | " + Color.RESET
                            + "%-18s" + Color.VANG + " |\n" + Color.RESET,
                    item.getProduct_id(),
                    productName,
                    FormatUtils.formatVND(item.getUnit_price()),
                    item.getQuantity(),
                    FormatUtils.formatVND(lineTotal));
        }

        System.out.println(Color.VANG + line + Color.RESET);
        System.out.printf(Color.XANH_LA + "=> TỔNG CỘNG HÓA ĐƠN NÀY: %s\n" + Color.RESET, FormatUtils.formatVND(finalTotal));
    }

}
