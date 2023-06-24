package it.help.help.utils;

import it.help.help.entity.*;

import java.util.*;

import java.sql.*;
import it.help.help.entity.*;
import java.time.LocalDate;
import java.sql.Date;
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
    public static void queryRegistraResponsabile(String email, String password, int type, int id_lavoro) throws Exception {
        connect();
        // Ottenere la data di oggi
        LocalDate today = LocalDate.now();
        // Convertire LocalDate in java.sql.Date
        java.sql.Date date = java.sql.Date.valueOf(today);

        String query = "INSERT INTO responsabile (email, password, type, id_lavoro, date) VALUES (?,?,?,?,?)";
        try (PreparedStatement stmt = connection.prepareStatement(query, PreparedStatement.RETURN_GENERATED_KEYS)) {
            stmt.setString(1, email);
            stmt.setString(2, password);
            stmt.setInt(3, type);
            stmt.setInt(4, id_lavoro);
            stmt.setDate(5, date);
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

    public static void queryRegistraNucleo(int id_polo, String cognome, int reddito) throws Exception {
        connect();
        String query = "INSERT INTO nucleo (id_polo, cognome, reddito) VALUES (?,?,?)";
        try (PreparedStatement stmt = connection.prepareStatement(query, PreparedStatement.RETURN_GENERATED_KEYS)) {
            stmt.setInt(1, id_polo);
            stmt.setString(2, cognome);
            stmt.setInt(3, reddito);
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

    public static void queryRegistraMembro(String codice_fiscale, int id_nucleo, String nome, String cognome, Date data_nascita, String indirizzo, boolean celiachia, boolean intolleranza_lattosio, boolean diabete) throws Exception {
        connect();
        String query = "INSERT INTO membro (codice_fiscale, id_nucleo, nome, cognome, data_nascita, indirizzo, celiachia, intolleranza_lattosio, diabete) VALUES (?,?,?,?,?,?,?,?,?)";
        try (PreparedStatement stmt = connection.prepareStatement(query, PreparedStatement.RETURN_GENERATED_KEYS)) {
            stmt.setString(1, codice_fiscale);
            stmt.setInt(2, id_nucleo);
            stmt.setString(3, nome);
            stmt.setString(4, cognome);
            stmt.setDate(5, data_nascita);
            stmt.setString(6, indirizzo);
            stmt.setBoolean(7, celiachia);
            stmt.setBoolean(8, intolleranza_lattosio);
            stmt.setBoolean(9, diabete);
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

    public static void querySalvaDonazione(int id_azienda, int codice_prodotto, int quantità, Date data_scadenza) throws Exception {
        connect();
        // Ottenere la data di oggi
        LocalDate today = LocalDate.now();
        // Convertire LocalDate in java.sql.Date
        java.sql.Date date = java.sql.Date.valueOf(today);

        String query = "INSERT INTO donazione (codice_prodotto, id_azienda, quantità, scadenza_prodotto, date) VALUES (?,?,?,?,?)";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, codice_prodotto);
            stmt.setInt(2, id_azienda);
            stmt.setInt(3, quantità);
            stmt.setDate(4, data_scadenza);
            stmt.setDate(5, date);
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

    public static int queryRegistraPolo(int id_diocesi, String nome, String indirizzo, int cellulare) throws Exception {
        connect();
        // Ottenere la data di oggi
        LocalDate today = LocalDate.now();
        // Convertire LocalDate in java.sql.Date
        java.sql.Date date = java.sql.Date.valueOf(today);

        String query = "INSERT INTO polo (id_diocesi, nome, indirizzo, cellulare, date) VALUES (?,?,?,?,?)";
        try (PreparedStatement stmt = connection.prepareStatement(query, PreparedStatement.RETURN_GENERATED_KEYS)) {
            stmt.setInt(1, id_diocesi);
            stmt.setString(2, nome);
            stmt.setString(3, indirizzo);
            stmt.setInt(4, cellulare);
            stmt.setDate(5, date);
            // return stmt.executeUpdate() > 0;

            int rowsAffected = stmt.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Polo registrato correttamente");
                ResultSet generatedKeys = stmt.getGeneratedKeys();
                if (generatedKeys.next()) {
                    return generatedKeys.getInt(1);
                }
            } else {
                System.out.println("Errore");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public static int queryRegistraDiocesi() throws Exception {
        connect();
        // Ottenere la data di oggi
        LocalDate today = LocalDate.now();
        // Convertire LocalDate in java.sql.Date
        java.sql.Date date = java.sql.Date.valueOf(today);
        String query = "INSERT INTO diocesi (date) VALUES (?)";
        try (PreparedStatement stmt = connection.prepareStatement(query, PreparedStatement.RETURN_GENERATED_KEYS)) {
            stmt.setDate(1, date);
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

    public static int queryRegistraAziendaPartner() throws Exception {
        connect();
        // Ottenere la data di oggi
        LocalDate today = LocalDate.now();
        // Convertire LocalDate in java.sql.Date
        java.sql.Date date = java.sql.Date.valueOf(today);
        String query = "INSERT INTO azienda (date) VALUES (?)";
        try (PreparedStatement stmt = connection.prepareStatement(query, PreparedStatement.RETURN_GENERATED_KEYS)) {
            stmt.setDate(1, date);
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

    public static boolean queryControllaEsistenzaMembro(String codice_fiscale) throws Exception {
        connect();
        var query = "SELECT * FROM membro WHERE codice_fiscale = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, codice_fiscale);
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
                // MainUtils.responsabileLoggato = responsabile.createFromDB(r);
                // return responsabile;
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
                return diocesi.createFromDB(r);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static boolean getStatoAccount(String nome_tabella, int id) throws Exception {
        connect();
        var query = "SELECT stato_account FROM "+nome_tabella+" WHERE id = ? && stato_account = 1";
        try (PreparedStatement stmt = connection.prepareStatement(query, PreparedStatement.RETURN_GENERATED_KEYS)) {
            stmt.setInt(1, id);
            var r = stmt.executeQuery();
            if (r.next()) {
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public static Help queryGetHelp(int id) throws Exception {
        connect();
        var query = "SELECT * FROM help WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query, PreparedStatement.RETURN_GENERATED_KEYS)) {
            stmt.setInt(1, id);
            var r = stmt.executeQuery();
            if (r.next()) {
                Help help = new Help();
                return help.createFromDB(r);
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

    public static void accettaRichiesta(int id, String tabella) throws Exception {
        connect();
        var query = "UPDATE "+tabella+" SET stato_account = ? WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query, PreparedStatement.RETURN_GENERATED_KEYS)) {
            stmt.setInt(1, 1);
            stmt.setInt(2, id);
            var r = stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static Responsabile getResponsabile(int id_lavoro) throws Exception {
        connect();
        var query = "SELECT * FROM responsabile WHERE id_lavoro = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query, PreparedStatement.RETURN_GENERATED_KEYS)) {
            stmt.setInt(1, id_lavoro);
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

    public static void queryEliminaNucleo(int id) throws Exception {
        connect();
        var query = "DELETE FROM nucleo WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, id);
            var r = stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        var query2 = "DELETE FROM membro WHERE id_nucleo = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query2)) {
            stmt.setInt(1, id);
            var r = stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void queryEliminaMembro(String codice_fiscale) throws Exception {
        connect();
        var query = "DELETE FROM membro WHERE codice_fiscale = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query, PreparedStatement.RETURN_GENERATED_KEYS)) {
            stmt.setString(1, codice_fiscale);
            var r = stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
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

    public static Prodotto[] queryGetProdotti() throws Exception {
        connect();
        String query = "SELECT * FROM prodotto";
        List<Prodotto> listaProdotti = new ArrayList<>();

        try (PreparedStatement stmt = connection.prepareStatement(query, PreparedStatement.RETURN_GENERATED_KEYS)) {
            // stmt.setInt(1, 0);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Prodotto prodotto = Prodotto.createFromDB(rs);
                listaProdotti.add(prodotto);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return listaProdotti.toArray(new Prodotto[0]);
    }

    public static Membro[] getMembri(int id_nucleo) throws Exception {
        connect();
        String query = "SELECT * FROM membro WHERE id_nucleo = ?";
        List<Membro> listaMembri = new ArrayList<>();

        try (PreparedStatement stmt = connection.prepareStatement(query, PreparedStatement.RETURN_GENERATED_KEYS)) {
            stmt.setInt(1, id_nucleo);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Membro membro = Membro.createFromDB(rs);
                listaMembri.add(membro);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return listaMembri.toArray(new Membro[0]);
    }

    public static Nucleo[] getNuclei(int id_polo) throws Exception {
        connect();
        String query = "SELECT * FROM nucleo WHERE id_polo = ?";
        List<Nucleo> listaNuclei = new ArrayList<>();

        try (PreparedStatement stmt = connection.prepareStatement(query, PreparedStatement.RETURN_GENERATED_KEYS)) {
            stmt.setInt(1, id_polo);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Nucleo nucleo = Nucleo.createFromDB(rs);
                listaNuclei.add(nucleo);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return listaNuclei.toArray(new Nucleo[0]);
    }

    public static AziendaPartner[] getRichiesteAziendePartner() throws Exception {
        connect();
        String query = "SELECT * FROM azienda WHERE stato_account = ? ORDER BY date ASC";
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
        queryBuilder.append(" WHERE id = ?");
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