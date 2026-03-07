package ra.phone_store_manager.utils.helper;

import java.math.BigDecimal;
import java.util.Scanner;

public class InputUtils {
    private static final Scanner sc = new Scanner(System.in);


    // ================== REGEX CONSTANTS ==================
    public static final String REGEX_EMAIL = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,6}$";
    public static final String REGEX_PHONE_VN = "^(0|\\+84)[35789][0-9]{8}$"; // // Regex cho SĐT Việt Nam: Bắt đầu bằng 0 hoặc +84, theo sau là các đầu số hợp lệ (3,5,7,8,9) và 8 chữ số cuối
    // =====================================================

    // Nhập chuỗi (String)
    public static String getString(String prompt) {
        while (true) {
            System.out.print(prompt);
            String input = sc.nextLine().trim();
            if (input.isEmpty()) {
                System.out.println(Color.DO + "* Lỗi: Dữ liệu không được để trống. Vui lòng nhập lại!" + Color.RESET);
            } else {
                return input;
            }
        }
    }

    // Nhập chuỗi với Regex truyền vào
    public static String getStringWithRegex(String prompt, String regex, String errorMsg) {
        while (true) {
            String input = getString(prompt);
            if (input.matches(regex)) {
                return input;
            }
            System.out.println(Color.DO + "* Lỗi: " + errorMsg + Color.RESET);
        }
    }

    // Nhập số nguyên (int)
    public static int getInt(String prompt) {
        while (true) {
            System.out.print(prompt);
            try {
                String input = sc.nextLine().trim();
                if (input.isEmpty()) {
                    System.out.println(Color.DO + "* Lỗi: Dữ liệu không được để trống!" + Color.RESET);
                    continue;
                }
                return Integer.parseInt(input);
            } catch (NumberFormatException e) {
                System.out.println(Color.DO + "* Lỗi: Vui lòng nhập một số nguyên hợp lệ (VD: 1, 2, 3...)!" + Color.RESET);
            }
        }
    }

    // Nhập số nguyên dương (stock, quantity)
    public static int getPositiveInt(String prompt) {
        while (true) {
            int number = getInt(prompt);
            if (number > 0) {
                return number;
            }
            System.out.println(Color.DO + "* Lỗi: Số lượng phải lớn hơn 0!" + Color.RESET);
        }
    }

    // Nhập số thực lớn (BigDecimal) (price, total_amount)
    public static BigDecimal getBigDecimal(String prompt) {
        while (true) {
            System.out.print(prompt);
            try {
                String input = sc.nextLine().trim();
                if (input.isEmpty()) {
                    System.out.println(Color.DO + "* Lỗi: Không được để trống!" + Color.RESET);
                    continue;
                }
                return new BigDecimal(input);
            } catch (NumberFormatException e) {
                System.out.println(Color.DO + "* Lỗi: Vui lòng nhập định dạng số tiền hợp lệ!" + Color.RESET);
            }
        }
    }

    // Nhập BigDecimal dương - Giá tiền không được âm
    public static BigDecimal getPositiveBigDecimal(String prompt) {
        while (true) {
            BigDecimal number = getBigDecimal(prompt);
            if (number.compareTo(BigDecimal.ZERO) > 0) {
                return number;
            }
            System.out.println(Color.DO + "* Lỗi: Số tiền phải lớn hơn 0!" + Color.RESET);
        }
    }



    ///  CÁC HÀM NHẬP CHUỖI OPTIONAL (ENTER ĐỂ KHÔNG NHẬP)
    public static String getOptionalString(String prompt) {
        System.out.print( prompt);
        String input = sc.nextLine().trim();
        return input.isEmpty() ? null : input;
    }

    public static String getOptionalStringWithRegex(String prompt, String regex, String errorMsg) {
        while (true) {
            String input = getOptionalString(prompt);
            if (input == null) {
                return null;
            }

            if (input.matches(regex)) {
                return input;
            }
            System.out.println(Color.DO + "  * Lỗi: " + errorMsg + Color.RESET);
        }
    }
}
