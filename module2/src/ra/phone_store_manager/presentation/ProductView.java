package ra.phone_store_manager.presentation;

import ra.phone_store_manager.business.IProductService;
import ra.phone_store_manager.business.impl.ProductServiceImpl;
import ra.phone_store_manager.model.Product;
import ra.phone_store_manager.utils.helper.Color;
import ra.phone_store_manager.utils.helper.InputUtils;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;

public class ProductView {
    private static final IProductService productService = new ProductServiceImpl();

    public static void showProductMenu() {
        Scanner sc = new Scanner(System.in);
        int choice;

        while (true) {
            System.out.println("\n" +
                    Color.VANG + Color.BOLD + "+" + "-".repeat(10)
                    + Color.CYAN + Color.BOLD + " QUẢN LÝ SẢN PHẨM "
                    + Color.VANG + Color.BOLD + "-".repeat(9) + "+" + Color.RESET);
            System.out.printf(
                    Color.VANG + Color.BOLD + "|" + Color.RESET
                            + " %-35s " +
                            Color.VANG + Color.BOLD + "|\n" + Color.RESET,
                    "1. Hiển thị danh sách sản phẩm");
            System.out.printf(
                    Color.VANG + Color.BOLD + "|" + Color.RESET
                            + " %-35s " +
                            Color.VANG + Color.BOLD + "|\n" + Color.RESET,
                    "2. Thêm sản phẩm mới");
            System.out.printf(
                    Color.VANG + Color.BOLD + "|" + Color.RESET
                            + " %-35s " +
                            Color.VANG + Color.BOLD + "|\n" + Color.RESET,
                    "3. Cập nhật thông tin sản phẩm");
            System.out.printf(
                    Color.VANG + Color.BOLD + "|" + Color.RESET
                            + " %-35s " +
                            Color.VANG + Color.BOLD + "|\n" + Color.RESET,
                    "4. Xóa sản phẩm theo ID");
            System.out.printf(
                    Color.VANG + Color.BOLD + "|" + Color.RESET
                            + " %-35s " +
                            Color.VANG + Color.BOLD + "|\n" + Color.RESET,
                    "5. Tìm kiếm theo Brand (Nhãn hiệu)");
            System.out.printf(
                    Color.VANG + Color.BOLD + "|" + Color.RESET
                            + " %-35s " +
                            Color.VANG + Color.BOLD + "|\n" + Color.RESET,
                    "6. Tìm kiếm theo tên sản phẩm");
            System.out.printf(
                    Color.VANG + Color.BOLD + "|" + Color.RESET
                            + " %-35s " +
                            Color.VANG + Color.BOLD + "|\n" + Color.RESET,
                    "7. Tìm kiếm theo khoảng giá");
            System.out.printf(
                    Color.VANG + Color.BOLD + "|" + Color.RESET
                            + " %-35s " +
                            Color.VANG + Color.BOLD + "|\n" + Color.RESET,
                    "8. Tìm kiếm theo tồn kho");
            System.out.printf(
                    Color.VANG + Color.BOLD + "|" + Color.RESET
                            + " %-35s " +
                            Color.VANG + Color.BOLD + "|\n" + Color.RESET,
                    "9. Quay lại menu chính");
            System.out.println(
                    Color.VANG + Color.BOLD + "+" + "-".repeat(37) + "+" + Color.RESET);

            System.out.print(Color.XANH_DUONG + "- Mời bạn chọn chức năng"
                    + Color.RESET + " (1-9): ");

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
                    showAllProducts();
                    break;
                case 2:
                    handleAddProduct();
                    break;
                case 3:
                    handleUpdateProduct();
                    break;
                case 4:
                    handleDeleteProduct();
                    break;
                case 5:
                    handleSearchByBrand();
                    break;
                case 6:
                    handleSearchByName();
                    break;
                case 7:
                    handleSearchByPriceRange();
                    break;
                case 8:
                    handleSearchByStock();
                    break;
                case 9:
                    System.out.println(Color.HONG_NHAT+"-- Quay về Menu chính --"+Color.RESET);
                    return;
                default:
                    System.out.println(Color.DO +
                            "* Lỗi: Lựa chọn không hợp lệ, vui lòng chọn 1-9 !"
                            + Color.RESET);
            }
        }
    }

    private static void showAllProducts() {
        System.out.println("\n" + Color.CYAN + "--- DANH SÁCH TOÀN BỘ SẢN PHẨM ---" + Color.RESET);
        List<Product> list = productService.getProductList();
        displayProducts(list);
    }

    private static void handleAddProduct() {
        System.out.println("\n" + Color.CYAN + "--- THÊM SẢN PHẨM MỚI ---" + Color.RESET);
        Product p = new Product();
        p.setName(InputUtils.getString("Nhập tên sản phẩm: "));
        p.setBrand(InputUtils.getString("Nhập thương hiệu: "));
        p.setPrice(InputUtils.getPositiveBigDecimal("Nhập giá sản phẩm (VNĐ): "));
        p.setStock(InputUtils.getPositiveInt("Nhập số lượng tồn kho: "));

        if (productService.addProduct(p)) {
            System.out.println(Color.XANH_LA + "=> Thêm sản phẩm thành công!" + Color.RESET);
        } else {
            System.out.println(Color.DO + "* Lỗi: Thêm sản phẩm thất bại!" + Color.RESET);
        }
    }

    private static void handleUpdateProduct() {
        System.out.println("\n" + Color.CYAN + "--- CẬP NHẬT SẢN PHẨM ---" + Color.RESET);
        int id = InputUtils.getPositiveInt("Nhập ID sản phẩm cần cập nhật: ");

        Product currentProduct = productService.getProductById(id);
        if (currentProduct == null) {
            System.out.println(Color.DO + "  * Lỗi: Không tìm thấy sản phẩm có ID = " + id + Color.RESET);
            return;
        }

        // SHOW THÔNG TIN HIỆN TẠI
        System.out.println(Color.XANH_DUONG + "  [Thông tin hiện tại của sản phẩm]" + Color.RESET);
        System.out.println("  - Tên SP: " + currentProduct.getName());
        System.out.println("  - Thương hiệu: " + currentProduct.getBrand());
        System.out.println("  - Giá: " + currentProduct.getPrice() + " VNĐ");
        System.out.println("  - Kho: " + currentProduct.getStock());

        // NHẬP THÔNG TIN MỚI
        System.out.println(Color.VANG + "\n(Mời bạn nhập dữ liệu mới bên dưới)" + Color.RESET);
        Product p = new Product();
        p.setId(id); // Giữ nguyên ID cũ
        p.setName(InputUtils.getString("Nhập tên mới: "));
        p.setBrand(InputUtils.getString("Nhập thương hiệu mới: "));
        p.setPrice(InputUtils.getPositiveBigDecimal("Nhập giá mới (VNĐ): "));
        p.setStock(InputUtils.getPositiveInt("Nhập số lượng mới: "));

        // XÁC NHẬN LƯU THAY ĐỔI
        if (!confirmAction("Bạn có chắc chắn muốn LƯU các thay đổi này không?")) {
            System.out.println(Color.VANG + "=> Đã hủy thao tác cập nhật." + Color.RESET);
            return;
        }

        if (productService.updateProduct(p)) {
            System.out.println(Color.XANH_LA + "=> Cập nhật sản phẩm thành công!" + Color.RESET);
        } else {
            System.out.println(Color.DO + "=> Lỗi: Cập nhật thất bại!" + Color.RESET);
        }
    }

    private static void handleDeleteProduct() {
        System.out.println("\n" + Color.CYAN + "--- XÓA SẢN PHẨM ---" + Color.RESET);
        int id = InputUtils.getPositiveInt("Nhập ID sản phẩm cần xóa: ");

        Product currentProduct = productService.getProductById(id);
        if (currentProduct == null) {
            System.out.println(Color.DO + "  * Lỗi: Không tìm thấy sản phẩm có ID = " + id + Color.RESET);
            return;
        }

        // NHẮC NHỞ
        System.out.println(Color.DO + "  [CẢNH BÁO] Sản phẩm chuẩn bị xóa: " + Color.RESET
                + currentProduct.getName() + " (" + currentProduct.getBrand() + ")");

        // XÁC NHẬN
        if (!confirmAction("Bạn có CHẮC CHẮN muốn xóa sản phẩm này không? Thao tác không thể hoàn tác!")) {
            System.out.println(Color.VANG + "=> Đã hủy thao tác xóa." + Color.RESET);
            return;
        }

        if (productService.deleteProduct(id)) {
            System.out.println(Color.XANH_LA + "=> Xóa sản phẩm thành công!" + Color.RESET);
        } else {
            System.out.println(Color.DO + "=> Lỗi: Xóa sản phẩm thất bại!" + Color.RESET);
        }
    }

    private static void handleSearchByName() {
        System.out.println("\n" + Color.CYAN + "--- TÌM THEO TÊN SẢN PHẨM ---" + Color.RESET);
        String name = InputUtils.getString("Nhập tên sản phẩm cần tìm: ");
        displayProducts(productService.findProductsByName(name));
    }

    private static void handleSearchByBrand() {
        System.out.println("\n" + Color.CYAN + "--- TÌM THEO THƯƠNG HIỆU ---" + Color.RESET);
        String brand = InputUtils.getString("Nhập thương hiệu cần tìm: ");
        displayProducts(productService.findProductsByBrand(brand));
    }

    private static void handleSearchByPriceRange() {
        System.out.println("\n" + Color.CYAN + "--- TÌM THEO KHOẢNG GIÁ ---" + Color.RESET);
        BigDecimal min = InputUtils.getPositiveBigDecimal("Nhập giá thấp nhất (Min): ");
        BigDecimal max = InputUtils.getPositiveBigDecimal("Nhập giá cao nhất (Max): ");
        displayProducts(productService.findProductsByPriceRange(min, max));
    }

    private static void handleSearchByStock() {
        System.out.println("\n" + Color.CYAN + "--- TÌM THEO SỐ LƯỢNG TỒN KHO ---" + Color.RESET);
        int stock = InputUtils.getPositiveInt("Nhập số lượng cần tìm: ");
        displayProducts(productService.findProductsByStock(stock));
    }

    private static void displayProducts(List<Product> products) {
        if (products == null || products.isEmpty()) {
            System.out.println(Color.DO + "  * Không tìm thấy sản phẩm nào phù hợp!" + Color.RESET);
            return;
        }

        String line = "+" + "-".repeat(6) + "+" + "-".repeat(27) + "+" + "-".repeat(17) + "+" + "-".repeat(17) + "+" + "-".repeat(10) + "+";
        System.out.println(Color.VANG + line);

        // | %-4s | %-25s | %-15s | %-15s | %-8s |
        System.out.printf("| "
                        + Color.CAM + "%-4s" + Color.RESET + Color.VANG + " | " + Color.RESET
                        + Color.CAM + "%-25s" + Color.RESET + Color.VANG + " | " + Color.RESET
                        + Color.CAM + "%-15s" + Color.RESET + Color.VANG + " | " + Color.RESET
                        + Color.CAM + "%-15s" + Color.RESET + Color.VANG + " | " + Color.RESET
                        + Color.CAM + "%-8s" + Color.RESET + Color.VANG + " |\n" + Color.RESET,
                "ID", "Tên Sản Phẩm", "Thương Hiệu", "Giá (VNĐ)", "Kho");
        System.out.println(Color.VANG + line + Color.RESET);


        //| %-4d | %-25s | %-15s | %-15.2f | %-8d |
        for (Product p : products) {
            System.out.printf(Color.VANG + "| " + Color.RESET
                            + "%-4d" + Color.VANG + " | " + Color.RESET
                            + "%-25s" + Color.VANG + " | " + Color.RESET
                            + "%-15s" + Color.VANG + " | " + Color.RESET
                            + "%-15.2f" + Color.VANG + " | " + Color.RESET + "%-8d "
                            + Color.VANG + "|\n" + Color.RESET,
                    p.getId(),
                    p.getName().length() > 25 ? p.getName().substring(0, 22) + "..." : p.getName(),
                    p.getBrand().length() > 15 ? p.getBrand().substring(0, 12) + "..." : p.getBrand(),
                    p.getPrice(),
                    p.getStock());
        }

        System.out.println(Color.VANG + line + Color.RESET);
    }

    private static boolean confirmAction(String message) {
        while (true) {
            String confirm = InputUtils.getString(Color.VANG + message + " (Y/N): " + Color.RESET);
            if (confirm.equalsIgnoreCase("Y")) {
                return true;
            } else if (confirm.equalsIgnoreCase("N")) {
                return false;
            }
            System.out.println(Color.DO + "* Lỗi: Vui lòng chỉ nhập Y (Đồng ý) hoặc N (Hủy bỏ)!" + Color.RESET);
        }
    }
}
