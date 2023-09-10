package co.edu.unicauca.openmarket.domain.service;

import co.edu.unicauca.openmarket.access.IProductRepository;
import co.edu.unicauca.openmarket.domain.Product;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Libardo, Julio
 */
public class ProductService {

    // Ahora hay una dependencia de una abstracción, no es algo concreto,
    // no sabe cómo está implementado.
    private final IProductRepository productRepository;

    /**
     * Inyección de dependencias en el constructor.Ya no conviene que el mismo
     * servicio cree un repositorio concreto
     *
     * @param productRepository una clase hija de IProductRepository
     */
    public ProductService(IProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public boolean saveProduct(String name, String description) {

        Product newProduct = new Product();
        newProduct.setName(name);
        newProduct.setDescription(description);

        //Validate product
        if (newProduct.getName().isEmpty()) {
            return false;
        }

        return productRepository.save(newProduct);

    }

    public List<Product> findAllProducts() {
        List<Product> products = new ArrayList<>();
        products = productRepository.findAll();;

        return products;
    }

    public Product findProductById(Long id) {

        return productRepository.findById(id);
    }

    public boolean deleteProduct(Long id) {
        return productRepository.delete(id);
    }

    public boolean editProduct(Long productId, Product prod) {

        //Validate product
        if (prod == null || prod.getName().isEmpty()) {
            return false;
        }
        return productRepository.edit(productId, prod);
    }

    public Product findProductByName(String name) {
        // Validar el nombre del producto
        if (name == null || name.isEmpty()) {
            return null;
        }

        // Llamar al método en el repositorio que realiza la búsqueda por nombre
        return productRepository.findByName(name);
    }

}
