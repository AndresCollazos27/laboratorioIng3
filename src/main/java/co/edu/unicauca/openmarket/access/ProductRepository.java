package co.edu.unicauca.openmarket.access;

import co.edu.unicauca.openmarket.domain.Category;
import co.edu.unicauca.openmarket.domain.Product;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Es una implementación que tiene libertad de hacer una implementación del
 * contrato. Lo puede hacer con Sqlite, postgres, mysql, u otra tecnología
 *
 * @author Libardo, Julio
 */
public class ProductRepository implements IProductRepository {

    private Connection conn;

    public ProductRepository() {
        initDatabase();
    }

    @Override
    public boolean save(Product newProduct) {
        try {
            if (newProduct == null || newProduct.getName().isEmpty()) {
                return false;
            }
            this.connect();
            // Iniciar una transacción
            conn.setAutoCommit(false);

            String sql = "INSERT INTO products (name, description, categoryId) VALUES (?,?,?);";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, newProduct.getName());
            pstmt.setString(2, newProduct.getDescription());
            pstmt.setLong(3, newProduct.getCategory().getCategoryId());
            pstmt.executeUpdate();

            // Confirmar la transacción
            conn.commit();

            return true;
        } catch (SQLException ex) {
            // En caso de error, revertir la transacción
            try {
                conn.rollback();
            } catch (SQLException rollbackEx) {
                Logger.getLogger(ProductRepository.class.getName()).log(Level.SEVERE, null, rollbackEx);
            }
            Logger.getLogger(ProductRepository.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        } finally {
            // Restaurar el modo de autoconfirmación
            try {
                conn.setAutoCommit(true);
                this.disconnect();
            } catch (SQLException autoCommitEx) {
                Logger.getLogger(ProductRepository.class.getName()).log(Level.SEVERE, null, autoCommitEx);
            }
        }
    }

    @Override
    public boolean edit(Long id, Product product) {
        try {
            if (id <= 0 || product == null) {
                return false;
            }
            this.connect();
            // Iniciar una transacción
            conn.setAutoCommit(false);

            String sql = "UPDATE products SET name=?, description=? WHERE productId = ?;";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, product.getName());
            pstmt.setString(2, product.getDescription());
            pstmt.setLong(3, id);
            pstmt.executeUpdate();

            // Confirmar la transacción
            conn.commit();

            return true;
        } catch (SQLException ex) {
            // En caso de error, revertir la transacción
            try {
                conn.rollback();
            } catch (SQLException rollbackEx) {
                Logger.getLogger(ProductRepository.class.getName()).log(Level.SEVERE, null, rollbackEx);
            }
            Logger.getLogger(ProductRepository.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        } finally {
            // Restaurar el modo de autoconfirmación
            try {
                conn.setAutoCommit(true);
                this.disconnect();
            } catch (SQLException autoCommitEx) {
                Logger.getLogger(ProductRepository.class.getName()).log(Level.SEVERE, null, autoCommitEx);
            }
        }
    }

    @Override
    public boolean delete(Long id) {
        try {
            if (id <= 0) {
                return false;
            }
            this.connect();
            // Iniciar una transacción
            conn.setAutoCommit(false);

            String sql = "DELETE FROM products WHERE productId = ?;";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setLong(1, id);
            pstmt.executeUpdate();

            // Confirmar la transacción
            conn.commit();

            return true;
        } catch (SQLException ex) {
            // En caso de error, revertir la transacción
            try {
                conn.rollback();
            } catch (SQLException rollbackEx) {
                Logger.getLogger(ProductRepository.class.getName()).log(Level.SEVERE, null, rollbackEx);
            }
            Logger.getLogger(ProductRepository.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        } finally {
            // Restaurar el modo de autoconfirmación
            try {
                conn.setAutoCommit(true);
                this.disconnect();
            } catch (SQLException autoCommitEx) {
                Logger.getLogger(ProductRepository.class.getName()).log(Level.SEVERE, null, autoCommitEx);
            }
        }
    }

    private void initDatabase() {
        // SQL statement for creating a new table
        String sql = "CREATE TABLE IF NOT EXISTS products (\n"
                + "    productId integer PRIMARY KEY AUTOINCREMENT,\n"
                + "    name text NOT NULL,\n"
                + "    description text NULL, \n"
                + "    categoryId integer NOT NULL,\n"
                + "    FOREIGN KEY (categoryId) REFERENCES categories(categoryId)\n"
                + ");";

        try {
            this.connect();
            Statement stmt = conn.createStatement();

            // Iniciar una transacción
            conn.setAutoCommit(false);

            stmt.execute(sql);

            // Confirmar la transacción
            conn.commit();
        } catch (SQLException ex) {
            // En caso de error, revertir la transacción
            try {
                conn.rollback();
            } catch (SQLException rollbackEx) {
                Logger.getLogger(ProductRepository.class.getName()).log(Level.SEVERE, null, rollbackEx);
            }
            Logger.getLogger(ProductRepository.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            // Restaurar el modo de autoconfirmación
            try {
                conn.setAutoCommit(true);
                this.disconnect();
            } catch (SQLException autoCommitEx) {
                Logger.getLogger(ProductRepository.class.getName()).log(Level.SEVERE, null, autoCommitEx);
            }
        }
    }

    @Override
    public List<Product> findAll() {
        List<Product> products = new ArrayList<>();
        try {
            
            String sql = "SELECT products.*, categories.name AS nameCategory\n" +
                         "FROM products\n" +
                         "INNER JOIN categories ON products.categoryId = categories.categoryId;";
            this.connect();
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                Product newProduct = new Product();
                newProduct.setProductId(rs.getLong("productId"));
                newProduct.setName(rs.getString("name"));
                newProduct.setDescription(rs.getString("description"));
                Category category = new Category();
                category.setName(rs.getString("nameCategory"));
                newProduct.setCategory(category);
                //newProduct.
                products.add(newProduct);
            }

        } catch (SQLException ex) {
            Logger.getLogger(ProductRepository.class.getName()).log(Level.SEVERE, null, ex);
        }finally{
            this.disconnect();
        }
        return products;
    }

    public void connect() {
        // SQLite connection string
        //String url = "jdbc:sqlite:./myDatabase.db"; //Para Linux/Mac
        //String url = "jdbc:sqlite:C:/sqlite/db/myDatabase.db"; //Para Windows
        String url = "jdbc:sqlite:myDatabase.db";

        try {
            conn = DriverManager.getConnection(url);

        } catch (SQLException ex) {
            Logger.getLogger(ProductRepository.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void disconnect() {
        try {
            if (conn != null) {
                conn.close();
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }

    }

    @Override
    public List<Product> findById(Long id) {
        List<Product> prods = new ArrayList<>();
        try {
            String sql = "SELECT products.*, categories.name AS nameCategory\n" +
                         "FROM products\n" +
                         "INNER JOIN categories ON products.categoryId = categories.categoryId\n"+
                         "WHERE products.productId = ?;";
            this.connect();
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setLong(1, id);

            ResultSet res = pstmt.executeQuery();
            
            while(res.next()){
                Product prod = new Product();
                prod.setProductId(res.getLong("productId"));
                prod.setName(res.getString("name"));
                prod.setDescription(res.getString("description"));
                Category cat = new Category();
                cat.setName(res.getString("nameCategory"));
                prod.setCategory(cat);
                prods.add(prod);
            }
            res.close();
        } catch (SQLException ex) {
            Logger.getLogger(ProductRepository.class.getName()).log(Level.SEVERE, null, ex);
        }finally{
            this.disconnect();
        }
        return prods;
    }

    @Override
    public List<Product> findByName(String name) {
        List<Product> prods = new ArrayList<>();
        try {
            String sql = "SELECT products.*, categories.name AS nameCategory\n" +
                         "FROM products\n" +
                         "INNER JOIN categories ON products.categoryId = categories.categoryId\n"+
                         "WHERE products.name = ?;";
            this.connect();
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, name);
            ResultSet res = pstmt.executeQuery();

            while(res.next()) {
                Product prod = new Product();
                prod.setProductId(res.getLong("productId"));
                prod.setName(res.getString("name"));
                prod.setDescription(res.getString("description"));
                Category cat = new Category();
                cat.setName(res.getString("nameCategory"));
                prod.setCategory(cat);
                prods.add(prod);
            } 
        } catch (SQLException ex) {
            Logger.getLogger(ProductRepository.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }finally{
            this.disconnect();
        }
        return prods;
    }
    @Override
        public List<Product> findByCategoryId(Long categoryId){
            List<Product> prods = new ArrayList<>();
        try {
            String sql = "SELECT products.*, categories.name AS nameCategory\n" +
                         "FROM products\n" +
                         "INNER JOIN categories ON products.categoryId = categories.categoryId\n"+
                         "WHERE categories.categoryId = ?;";
            this.connect();
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setLong(1, categoryId);
            ResultSet res = pstmt.executeQuery();

            while(res.next()) {
                Product prod = new Product();
                prod.setProductId(res.getLong("productId"));
                prod.setName(res.getString("name"));
                prod.setDescription(res.getString("description"));
                Category cat = new Category();
                cat.setName(res.getString("nameCategory"));
                prod.setCategory(cat);
                prods.add(prod);
            } 
        } catch (SQLException ex) {
            Logger.getLogger(ProductRepository.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }finally{
            this.disconnect();
        }
    return prods;
    }
}
