package ra.phone_store_manager.utils.helper;

import java.math.BigDecimal;
import java.text.NumberFormat;
import java.util.Locale;

public class FormatUtils {
    public static String formatVND(BigDecimal amount) {
        if (amount == null) {
            amount = BigDecimal.ZERO;
        }

        // Sử dụng Locale Việt Nam
        Locale localeVN = new Locale("vi", "VN");
        NumberFormat currencyVN = NumberFormat.getCurrencyInstance(localeVN);

        // Hiển thị đúng 2 chữ số thập phân
        currencyVN.setMinimumFractionDigits(2);
        currencyVN.setMaximumFractionDigits(2);

        return currencyVN.format(amount);
    }
}
