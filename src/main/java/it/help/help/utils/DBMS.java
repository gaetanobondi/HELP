package it.help.help.utils;

import it.help.help.entity.Responsabile;

import java.util.*;

import java.sql.*;
import it.help.help.entity.*;
import java.time.LocalDate;
import java.util.HashMap;

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

        String query = "INSERT INTO responsabile (email, password, type, date) VALUES (?,?,?,?)";
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
                    int lastID;
                    ResponsabileCompleto responsabileCompleto;
                    Responsabile responsabile = queryControllaCredenzialiResponsabile(email, password);
                    switch (type) {
                        case 0:
                            break;
                        case 1:
                            // DIOCESI
                            lastID = queryRegistraDiocesi(id);
                            Diocesi diocesi = getDiocesi(lastID);
                            responsabileCompleto = new ResponsabileCompleto(responsabile, diocesi);
                            return responsabileCompleto;
                        case 2:
                            break;
                        case 3:
                            // AZIENDA PARTNER
                            lastID = queryRegistraAziendaPartner(id);
                            AziendaPartner aziendaPartner = getAziendaPartner(lastID);
                            responsabileCompleto = new ResponsabileCompleto(responsabile, aziendaPartner);
                            return responsabileCompleto;
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
        String query = "INSERT INTO diocesi (id_responsabile, stato_account, date) VALUES (?,?,?)";
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

    private static int queryRegistraAziendaPartner(int id_responsabile) throws Exception {
        connect();
        // Ottenere la data di oggi
        LocalDate today = LocalDate.now();
        // Convertire LocalDate in java.sql.Date
        java.sql.Date date = java.sql.Date.valueOf(today);
        String query = "INSERT INTO azienda_partner (id_responsabile, stato_account, date) VALUES (?,?,?)";
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

    private static int queryRegistraPolo(int id_responsabile, int id_diocesi) throws Exception {
        connect();
        // Ottenere la data di oggi
        LocalDate today = LocalDate.now();
        // Convertire LocalDate in java.sql.Date
        java.sql.Date date = java.sql.Date.valueOf(today);
        String query = "INSERT INTO polo (id_responsabile, id_diocesi, date) VALUES (?,?,?)";
        try (PreparedStatement stmt = connection.prepareStatement(query, PreparedStatement.RETURN_GENERATED_KEYS)) {
            stmt.setInt(1, id_responsabile);
            stmt.setInt(2, id_diocesi);
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
        var query = "SELECT * FROM responsabile WHERE email = ?";
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
        var query = "SELECT * FROM responsabile WHERE email = ? and password = ?";
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

    public static Diocesi getDiocesi(int id_responsabile) throws Exception {
        connect();
        var query = "SELECT * FROM diocesi WHERE id_responsabile = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query, PreparedStatement.RETURN_GENERATED_KEYS)) {
            stmt.setInt(1, id_responsabile);
            var r = stmt.executeQuery();
            if (r.next()) {
                return Diocesi.createFromDB(r);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static Help getHelp(int id_responsabile) throws Exception {
        connect();
        var query = "SELECT * FROM help WHERE id_responsabile = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query, PreparedStatement.RETURN_GENERATED_KEYS)) {
            stmt.setInt(1, id_responsabile);
            var r = stmt.executeQuery();
            if (r.next()) {
                return Help.createFromDB(r);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static AziendaPartner getAziendaPartner(int id_responsabile) throws Exception {
        connect();
        var query = "SELECT * FROM azienda_partner WHERE id_responsabile = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query, PreparedStatement.RETURN_GENERATED_KEYS)) {
            stmt.setInt(1, id_responsabile);
            var r = stmt.executeQuery();
            if (r.next()) {
                return AziendaPartner.createFromDB(r);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static Polo getPolo(int id_responsabile) throws Exception {
        connect();
        var query = "SELECT * FROM polo WHERE id_responsabile = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query, PreparedStatement.RETURN_GENERATED_KEYS)) {
            stmt.setInt(1, id_responsabile);
            var r = stmt.executeQuery();
            if (r.next()) {
                return Polo.createFromDB(r);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void queryModificaDati(int id_responsabile, String tabella, HashMap<String, Object> dati) throws Exception {
        connect();
        StringBuilder queryBuilder = new StringBuilder("UPDATE " + tabella + " SET ");
        List<Object> values = new ArrayList<>();

        // Costruisci la clausola SET della query e raccogli i valori da aggiornare
        for (Map.Entry<String, Object> entry : dati.entrySet()) {
            String campo = entry.getKey();
            Object valore = entry.getValue();
            queryBuilder.append(campo).append(" = ?, ");
            values.add(valore);
        }
        queryBuilder.delete(queryBuilder.length() - 2, queryBuilder.length()); // Rimuovi l'ultima virgola

        // Aggiungi la clausola WHERE alla query (assumendo che ci sia un campo "id")
        queryBuilder.append(" WHERE id_responsabile = ?");
        values.add(id_responsabile);

        String query = queryBuilder.toString();
        try (PreparedStatement stmt = connection.prepareStatement(query, PreparedStatement.RETURN_GENERATED_KEYS)) {
            // Imposta i valori nella query
            int i = 1;
            for (Object valore : values) {
                stmt.setObject(i, valore);
                i++;
            }

            // Esegui la query di aggiornamento
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
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