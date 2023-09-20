
package co.edu.unicauca.openmarket.access;

import co.edu.unicauca.openmarket.domain.Product;
import java.util.List;

/**
 *
 * @author Libardo, Julio
 */
public interface IProductRepository {
    boolean save(Product newProduct);
    
    boolean edit(Long id, Product product);
    
    boolean delete(Long id);

    List<Product> findById(Long id);
    
    List<Product> findAll();
    
    List<Product> findByName(String name);
    
    List<Product> findByCategoryId(Long categoryId);
}
