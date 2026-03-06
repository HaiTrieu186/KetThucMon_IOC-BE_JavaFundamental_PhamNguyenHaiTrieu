package ra.phone_store_manager.dao;

import ra.phone_store_manager.model.Product;

import java.math.BigDecimal;
import java.util.List;

public interface IProductDAO {
    boolean addProduct(Product product);
    boolean deleteProduct(int id);
    boolean updateProduct(Product product);
    Product findProductByID(int id);
    List<Product> findAllProducts();
    List<Product> findProductsByBrand(String brand);
    List<Product> findProductsByName(String name);
    List<Product> findProductsByStock(int stock);
    List<Product> findProductsByPriceRange(BigDecimal min, BigDecimal max);
}
