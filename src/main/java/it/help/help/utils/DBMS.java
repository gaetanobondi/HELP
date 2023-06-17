package it.help.help.utils;

import it.help.help.entity.*;

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
    public static void queryRegistraResponsabile(String email, String password, int type) throws Exception {
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
                System.out.println("Registrato correttamente");
            } else {
                System.out.println("Errore");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void queryRegistraPolo(int id_diocesi, String nome, String email, String password) throws Exception {
        connect();
        // Ottenere la data di oggi
        LocalDate today = LocalDate.now();
        // Convertire LocalDate in java.sql.Date
        java.sql.Date date = java.sql.Date.valueOf(today);

        String query = "INSERT INTO polo (email, password, type, nome, date, id_diocesi) VALUES (?,?,?,?,?,?)";
        try (PreparedStatement stmt = connection.prepareStatement(query, PreparedStatement.RETURN_GENERATED_KEYS)) {
            stmt.setString(1, email);
            stmt.setString(2, password);
            stmt.setInt(3, 2);
            stmt.setString(4, nome);
            stmt.setDate(5, date);
            stmt.setInt(6, id_diocesi);
            // return stmt.executeUpdate() > 0;

            int rowsAffected = stmt.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Polo registrato correttamente");
            } else {
                System.out.println("Errore");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
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
                Responsabile responsabile = Responsabile.createFromDB(r);
                // MainUtils.responsabileLoggato = responsabile.createFromDB(r);
                return responsabile;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static boolean queryDonazioniEffettuate(int id_responsabile) throws Exception {
        connect();
        var query = "SELECT * FROM donazione WHERE id_azienda = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query, PreparedStatement.RETURN_GENERATED_KEYS)) {
            stmt.setInt(1, id_responsabile);
            var r = stmt.executeQuery();
            if (r.next()) {
                Responsabile responsabile = new Responsabile();
                responsabile = responsabile.createFromDB(r);
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public static Diocesi getDiocesi(int id_responsabile) throws Exception {
        connect();
        var query = "SELECT * FROM diocesi WHERE id_responsabile = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query, PreparedStatement.RETURN_GENERATED_KEYS)) {
            stmt.setInt(1, id_responsabile);
            var r = stmt.executeQuery();
            if (r.next()) {
                Diocesi diocesi = new Diocesi();
                return MainUtils.diocesiLoggata = diocesi.createFromDB(r);
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
                Help help = new Help();
                return MainUtils.helpLoggato = help.createFromDB(r);
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
                AziendaPartner aziendaPartner = new AziendaPartner();
                MainUtils.aziendaPartnerLoggata = aziendaPartner.createFromDB(r);
                return aziendaPartner;
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
                Polo polo = new Polo();
                return MainUtils.poloLoggato = polo.createFromDB(r);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void accettaRichiesta(int id_responsabile, String tabella) throws Exception {
        connect();
        var query = "UPDATE "+tabella+" SET stato_account = ? WHERE id_responsabile = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query, PreparedStatement.RETURN_GENERATED_KEYS)) {
            stmt.setInt(1, 1);
            stmt.setInt(2, id_responsabile);
            var r = stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static Responsabile getResponsabile(int id_responsabile) throws Exception {
        connect();
        var query = "SELECT * FROM responsabile WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query, PreparedStatement.RETURN_GENERATED_KEYS)) {
            stmt.setInt(1, id_responsabile);
            var r = stmt.executeQuery();
            if (r.next()) {
                Responsabile responsabile = Responsabile.createFromDB(r);
                return  responsabile;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static Diocesi[] getRichiesteDiocesi() throws Exception {
        connect();
        String query = "SELECT * FROM diocesi WHERE stato_account = ? ORDER BY date ASC";
        List<Diocesi> richiesteDiocesi = new ArrayList<>();

        try (PreparedStatement stmt = connection.prepareStatement(query, PreparedStatement.RETURN_GENERATED_KEYS)) {
            stmt.setInt(1, 0);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Diocesi diocesi = Diocesi.createFromDB(rs);
                richiesteDiocesi.add(diocesi);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return richiesteDiocesi.toArray(new Diocesi[0]);
    }

    public static AziendaPartner[] getRichiesteAziendePartner() throws Exception {
        connect();
        String query = "SELECT * FROM azienda_partner WHERE stato_account = ? ORDER BY date ASC";
        List<AziendaPartner> richiesteAziende = new ArrayList<>();

        try (PreparedStatement stmt = connection.prepareStatement(query, PreparedStatement.RETURN_GENERATED_KEYS)) {
            stmt.setInt(1, 0);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                AziendaPartner aziendaPartner = AziendaPartner.createFromDB(rs);
                richiesteAziende.add(aziendaPartner);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return richiesteAziende.toArray(new AziendaPartner[0]);
    }

    public static void queryModificaDati(int id_responsabile, String nomeTabellaDB, HashMap<String, Object> dati) throws Exception {
        connect();
        StringBuilder queryBuilder = new StringBuilder("UPDATE " + nomeTabellaDB + " SET ");
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
        if(nomeTabellaDB.equals("responsabile")) {
            queryBuilder.append(" WHERE id = ?");
        } else {
            queryBuilder.append(" WHERE id_responsabile = ?");
        }
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