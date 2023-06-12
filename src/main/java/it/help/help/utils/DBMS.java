package it.help.help.utils;

import it.help.help.entity.Responsabile;

import java.sql.*;
import it.help.help.entity.*;
import java.time.LocalDate;

public class DBMS {
    private static String url = "jdbc:mysql://localhost:3306/help";
    private static String username = "root";
    private static String password = "";

    private static Connection connection = null;

    public static void connect() throws Exception {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection(url, username, password);
        } catch (java.sql.SQLException e) {
            e.printStackTrace();
        }
    }

    /*

    type:
    0 => HELP
    1 => DIOCESI
    2 => POLO
    3 => AZIENDA PARTNER
     */
    public static ResponsabileCompleto queryRegistraResponsabile(String email, String password, int type) throws Exception {
        connect();
        // Ottenere la data di oggi
        LocalDate today = LocalDate.now();
        // Convertire LocalDate in java.sql.Date
        java.sql.Date date = java.sql.Date.valueOf(today);

        String query = "INSERT INTO Responsabile (email, password, type, date) VALUES (?,?,?,?)";
        try (PreparedStatement stmt = connection.prepareStatement(query, PreparedStatement.RETURN_GENERATED_KEYS)) {
            stmt.setString(1, email);
            stmt.setString(2, password);
            stmt.setInt(3, type);
            stmt.setDate(4, date);
            // return stmt.executeUpdate() > 0;

            int rowsAffected = stmt.executeUpdate();
            if (rowsAffected > 0) {
                ResultSet generatedKeys = stmt.getGeneratedKeys();
                if (generatedKeys.next()) {
                    int id = generatedKeys.getInt(1);
                    Responsabile responsabile = queryControllaCredenzialiResponsabile(email, password);
                    switch (type) {
                        case 0:
                            break;
                        case 1:
                            // DIOCESI
                            int lastID = queryRegistraDiocesi(id);
                            Diocesi diocesi = getDiocesi(lastID);
                            ResponsabileCompleto responsabileCompleto = new ResponsabileCompleto(responsabile, diocesi);
                            return responsabileCompleto;
                        case 2:
                            break;
                        case 3:
                            break;
                        default:
                            break;
                    }
                }
            } else {
                System.out.println("Nessuna riga inserita");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    private static int queryRegistraDiocesi(int id_responsabile) throws Exception {
        connect();
        // Ottenere la data di oggi
        LocalDate today = LocalDate.now();
        // Convertire LocalDate in java.sql.Date
        java.sql.Date date = java.sql.Date.valueOf(today);
        String query = "INSERT INTO Diocesi (id_responsabile, stato_account, date) VALUES (?,?,?)";
        try (PreparedStatement stmt = connection.prepareStatement(query, PreparedStatement.RETURN_GENERATED_KEYS)) {
            stmt.setInt(1, id_responsabile);
            stmt.setBoolean(2, false);
            stmt.setDate(3, date);
            int rowsAffected = stmt.executeUpdate();
            if (rowsAffected > 0) {
                ResultSet generatedKeys = stmt.getGeneratedKeys();
                if (generatedKeys.next()) {
                    return generatedKeys.getInt(1);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public static boolean queryControllaEsistenzaEmail(String email) throws Exception {
        connect();
        var query = "SELECT * FROM Responsabile WHERE email = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, email);
            var r = stmt.executeQuery();
            if (r.next()) {
                return true;
            }
            return false;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return true;
    }

    public static Responsabile queryControllaCredenzialiResponsabile(String email, String password) throws Exception {
        connect();
        var query = "SELECT * FROM Responsabile WHERE email = ? and password = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query, PreparedStatement.RETURN_GENERATED_KEYS)) {
            stmt.setString(1, email);
            stmt.setString(2, password);
            var r = stmt.executeQuery();
            if (r.next()) {
                return Responsabile.createFromDB(r);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static Diocesi getDiocesi(int id) throws Exception {
        connect();
        var query = "SELECT * FROM Diocesi WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query, PreparedStatement.RETURN_GENERATED_KEYS)) {
            stmt.setInt(1, id);
            var r = stmt.executeQuery();
            if (r.next()) {
                return Diocesi.createFromDB(r);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

}


/*

welcomeText.setText("Welcome to JavaFX Application!");
        String url = "jdbc:mysql://localhost:3306/help";
        String username = "root";
        String password = "";
        try {
            System.out.println("Provo a connettermi al db");
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection connection = DriverManager.getConnection(url, username, password);
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("select * from responsabile where id=2");
            System.out.println(resultSet);
            if(resultSet.next()) {
                System.out.println("Ottengo i dati dal db:");
                while(resultSet.next()) {
                    System.out.println(resultSet.getInt(1)+ " " + resultSet.getString(2) + " " + resultSet.getString(3));
                }
            } else {
                System.out.println("Nessun dato presente.");
            }

            connection.close();
        } catch (Exception e) {
            System.out.println(e);
        }

 */