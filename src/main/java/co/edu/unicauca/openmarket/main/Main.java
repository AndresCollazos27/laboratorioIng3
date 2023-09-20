package co.edu.unicauca.openmarket.main;

import co.edu.unicauca.openmarket.access.Factory;
import co.edu.unicauca.openmarket.access.ICategoryRepository;
import co.edu.unicauca.openmarket.access.IProductRepository;
import co.edu.unicauca.openmarket.domain.service.ProductCategory;
import co.edu.unicauca.openmarket.domain.service.ProductService;
import co.edu.unicauca.openmarket.presentation.GUIProducts;

/**
 * Clase principal del programa
 * 
 * @author Libardo Pantoja
 */
public class Main {

    /**
     * Método principal del programa
     *
     * @param args Argumentos de línea de comandos (no se utilizan en este caso)
     */
    public static void main(String[] args) {

        // Obtener los repositorios tanto para productos como para categorías
        ICategoryRepository categoryRepository = Factory.getInstance().getCategoryRepository("default");
        IProductRepository productRepository = Factory.getInstance().getRepository("default");
        
        
        // Crear una instancia de ProductService que incluye ambos repositorios
        ProductService productService = new ProductService(productRepository);
        ProductCategory productCategory = new ProductCategory(categoryRepository);
        
        // Crear la interfaz gráfica de usuario (GUI) para productos
        GUIProducts instance = new GUIProducts(productService,productCategory);
        instance.setVisible(true);
    }

}
