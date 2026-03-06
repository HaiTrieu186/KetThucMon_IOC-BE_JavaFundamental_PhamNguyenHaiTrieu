package ra.phone_store_manager.business;

import ra.phone_store_manager.model.Product;

import java.math.BigDecimal;
import java.util.List;

public interface IProductService {
    boolean addProduct(Product product);
    boolean updateProduct(Product product);
    boolean deleteProduct(int id);
    Product getProductById(int id);
    List<Product> getProductList();
    List<Product> findProductsByBrand(String brand);
    List<Product> findProductsByName(String name);
    List<Product> findProductsByStock(int stock);
    List<Product> findProductsByPriceRange(BigDecimal min, BigDecimal max);

}
