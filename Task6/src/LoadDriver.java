import java.sql.*;

public class LoadDriver {
    public static void main(String[] args) {
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            // 1. Load MySQL JDBC Driver
            Class.forName("com.mysql.cj.jdbc.Driver");

            // 2. Connect to database
            con = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/testdb",
                    "root",
                    "Ankit@408"
            );

            // 3. Correct SQL to create table
            String sql = "CREATE TABLE IF NOT EXISTS users ("
                    + "id INT AUTO_INCREMENT PRIMARY KEY, "
                    + "name VARCHAR(100), "
                    + "email VARCHAR(100))";  // <-- added closing parenthesis

            ps = con.prepareStatement(sql);
            ps.executeUpdate();

            System.out.println("Users table created successfully!");

            String insertSql = "INSERT INTO users (name, email) VALUES (?, ?)";
            ps = con.prepareStatement(insertSql);

            ps.setString(1, "Ankit Kumar");
            ps.setString(2, "ankitmehta78816@example.com");

            int rowsInserted = ps.executeUpdate();
            System.out.println(rowsInserted + " row(s) inserted successfully!");

            String selectSQL = "SELECT * FROM users";
            ps = con.prepareStatement(selectSQL);
            rs = ps.executeQuery();
            System.out.println("=== Users Table ===");
            while (rs.next()) {
                int id = rs.getInt("id");
                String name = rs.getString("name");
                String email = rs.getString("email");

                System.out.println(id + " | " + name + " | " + email);
            }

        } catch (ClassNotFoundException e) {
            System.out.println("MySQL Driver not found.");
            throw new RuntimeException(e);
        } catch (SQLException e) {
            System.out.println("SQL Error: " + e.getMessage());
            throw new RuntimeException(e);
        } finally {
            // 4. Close resources
            try { if (ps != null) ps.close(); } catch (SQLException ignored) {}
            try { if (con != null) con.close(); } catch (SQLException ignored) {}
        }
    }
}
