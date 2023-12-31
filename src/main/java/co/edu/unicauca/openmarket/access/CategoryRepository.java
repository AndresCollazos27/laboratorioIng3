/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package co.edu.unicauca.openmarket.access;

import co.edu.unicauca.openmarket.domain.Category;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Valentina Fernandez y Andres Collazos
 */
public class CategoryRepository implements ICategoryRepository {

    private Connection conn;

    public CategoryRepository() {
        initDatabase();
    }
    @Override
    public List<Category> findAll() {
        List<Category> categories = new ArrayList<>();
        try {
            String sql = "SELECT * FROM categories;";
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                Category category = new Category();
                category.setCategoryId(rs.getLong("categoryId"));
                category.setName(rs.getString("name"));
                categories.add(category);
            }
        } catch (SQLException ex) {
            Logger.getLogger(CategoryRepository.class.getName()).log(Level.SEVERE, null, ex);
        }
        return categories;
    }

    @Override
    public Category findById(Long id) {
        try {
            this.connect();
            String sql = "SELECT * FROM categories WHERE categoryId = ?;";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setLong(1, id);
            ResultSet res = pstmt.executeQuery();

            if (res.next()) {
                Category category = new Category();
                category.setCategoryId(res.getLong("categoryId"));
                category.setName(res.getString("name"));
                res.close();
                return category;
            } else {
                return null;
            }
            
        } catch (SQLException ex) {
            Logger.getLogger(CategoryRepository.class.getName()).log(Level.SEVERE, null, ex);
        }finally{
            this.disconnect();
        }
        return null;
    }

    @Override
    public Category findByName(String name) {
        try {
            this.connect();
            String sql = "SELECT * FROM categories WHERE name = ?;";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, name);
            ResultSet res = pstmt.executeQuery();

            if (res.next()) {
                Category category = new Category();
                category.setCategoryId(res.getLong("categoryId"));
                category.setName(res.getString("name"));
                res.close();
                return category;
            } else {
                return null;
            }
        } catch (SQLException ex) {
            Logger.getLogger(CategoryRepository.class.getName()).log(Level.SEVERE, null, ex);
        }finally{
            this.disconnect();
        }
        return null;
    }

    @Override
    public boolean save(Category newCategory) {
        try {
            if (newCategory == null || newCategory.getName().isEmpty()) {
                return false;
            }
            this.connect();
            // Iniciar una transacción
            conn.setAutoCommit(false);

            String sql = "INSERT INTO categories (name) VALUES (?);";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, newCategory.getName());
            pstmt.executeUpdate();

            // Confirmar la transacción
            conn.commit();

            return true;
        } catch (SQLException ex) {
            // En caso de error, revertir la transacción
            try {
                conn.rollback();
            } catch (SQLException rollbackEx) {
                Logger.getLogger(CategoryRepository.class.getName()).log(Level.SEVERE, null, rollbackEx);
            }
            Logger.getLogger(CategoryRepository.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        } finally {
            // Restaurar el modo de autoconfirmación
            try {
                conn.setAutoCommit(true);
                this.disconnect();
            } catch (SQLException autoCommitEx) {
                Logger.getLogger(CategoryRepository.class.getName()).log(Level.SEVERE, null, autoCommitEx);
            }
        }
    }

    @Override
    public boolean edit(Long id, Category category) {
        try {
            if (id <= 0 || category == null || category.getName().isEmpty()) {
                return false;
            }
            this.connect();
            // Iniciar una transacción
            conn.setAutoCommit(false);

            String sql = "UPDATE categories SET name=? WHERE categoryId = ?;";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, category.getName());
            pstmt.setLong(2, id);
            pstmt.executeUpdate();

            // Confirmar la transacción
            conn.commit();

            return true;
        } catch (SQLException ex) {
            // En caso de error, revertir la transacción
            try {
                conn.rollback();
            } catch (SQLException rollbackEx) {
                Logger.getLogger(CategoryRepository.class.getName()).log(Level.SEVERE, null, rollbackEx);
            }
            Logger.getLogger(CategoryRepository.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        } finally {
            // Restaurar el modo de autoconfirmación
            try {
                conn.setAutoCommit(true);
                this.disconnect();
            } catch (SQLException autoCommitEx) {
                Logger.getLogger(CategoryRepository.class.getName()).log(Level.SEVERE, null, autoCommitEx);
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

            String sql = "DELETE FROM categories WHERE categoryId = ?;";
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
                Logger.getLogger(CategoryRepository.class.getName()).log(Level.SEVERE, null, rollbackEx);
            }
            Logger.getLogger(CategoryRepository.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        } finally {
            // Restaurar el modo de autoconfirmación
            try {
                conn.setAutoCommit(true);
                this.disconnect();
            } catch (SQLException autoCommitEx) {
                Logger.getLogger(CategoryRepository.class.getName()).log(Level.SEVERE, null, autoCommitEx);
            }
        }
    }

    private void initDatabase() {
        // Código para crear la tabla de categorías, similar al de productos
        String createTableSQL = "CREATE TABLE IF NOT EXISTS categories ("
                + "categoryId INTEGER PRIMARY KEY AUTOINCREMENT,\n "
                + "name TEXT NOT NULL);";

        try {
            // Crear la tabla de categorías si no existe
            this.connect();
            Statement stmt = conn.createStatement();

            // Iniciar una transacción
            conn.setAutoCommit(false);

            stmt.execute(createTableSQL);

            // Confirmar la transacción
            conn.commit();
        } catch (SQLException ex) {
            // En caso de error, revertir la transacción
            try {
                conn.rollback();
            } catch (SQLException rollbackEx) {
                Logger.getLogger(CategoryRepository.class.getName()).log(Level.SEVERE, null, rollbackEx);
            }
            Logger.getLogger(CategoryRepository.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            // Restaurar el modo de autoconfirmación
            try {
                conn.setAutoCommit(true);
                this.disconnect();
            } catch (SQLException autoCommitEx) {
                Logger.getLogger(CategoryRepository.class.getName()).log(Level.SEVERE, null, autoCommitEx);
            }
        }
    }

    public void connect() {
        // SQLite connection string
        String url = "jdbc:sqlite:myDatabase.db";

        try {
            conn = DriverManager.getConnection(url);
        } catch (SQLException ex) {
            Logger.getLogger(CategoryRepository.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void disconnect() {
        try {
            if (conn != null) {
                conn.close();
            }
        } catch (SQLException ex) {
            Logger.getLogger(CategoryRepository.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
