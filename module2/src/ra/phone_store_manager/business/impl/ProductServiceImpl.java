package ra.phone_store_manager.business.impl;

import ra.phone_store_manager.business.IProductService;
import ra.phone_store_manager.dao.IProductDAO;
import ra.phone_store_manager.dao.impl.ProductDAOImpl;
import ra.phone_store_manager.model.Product;
import ra.phone_store_manager.utils.helper.Color;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class ProductServiceImpl implements IProductService {
    private static final IProductDAO productDAO = new ProductDAOImpl();

    @Override
    public boolean addProduct(Product product) {
        return productDAO.addProduct(product);
    }

    @Override
    public boolean updateProduct(Product product) {

        if (productDAO.findProductByID(product.getId()) == null) {
            System.out.println(Color.DO+
                    "* Lỗi: Sản phẩm không tồn tại !"
                    + Color.RESET);
            return false;
        }

        return productDAO.updateProduct(product);
    }

    @Override
    public boolean deleteProduct(int id) {
        if (productDAO.findProductByID(id) == null) {
            System.out.println(Color.DO+
                    "* Lỗi: Sản phẩm không tồn tại !"
                    + Color.RESET);
            return false;
        }

        return productDAO.deleteProduct(id);
    }

    @Override
    public Product getProductById(int id) {
        return productDAO.findProductByID(id);
    }

    @Override
    public List<Product> getProductList() {
        return productDAO.findAllProducts();
    }

    @Override
    public List<Product> findProductsByBrand(String brand) {
        return productDAO.findProductsByBrand(brand);
    }

    @Override
    public List<Product> findProductsByName(String name) {
        return productDAO.findProductsByName(name);
    }

    @Override
    public List<Product> findProductsByStock(int stock) {
        return productDAO.findProductsByStock(stock);
    }

    @Override
    public List<Product> findProductsByPriceRange(BigDecimal min, BigDecimal max) {
        if (min.compareTo(max) > 0) {
            System.out.println(Color.DO + "  * Lỗi: Giá tối thiểu không được lớn hơn giá tối đa!" + Color.RESET);
            return new ArrayList<>();
        }
        return productDAO.findProductsByPriceRange(min, max);
    }
}
