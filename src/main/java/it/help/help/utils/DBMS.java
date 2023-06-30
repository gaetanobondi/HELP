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
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void querySalvaSegnalazione(int id_polo, int tipo_soggetto, int id_soggetto, int codice_prodotto, int quantità_ricevuta) throws Exception {
        connect();
        // Ottenere la data di oggi
        LocalDate today = LocalDate.now();
        // Convertire LocalDate in java.sql.Date
        java.sql.Date date = java.sql.Date.valueOf(today);

        String query = "INSERT INTO segnalazione (id_polo, tipo_soggetto, id_soggetto, codice_prodotto, quantità_ricevuta, data) VALUES (?,?,?,?,?,?)";
        try (PreparedStatement stmt = connection.prepareStatement(query, PreparedStatement.RETURN_GENERATED_KEYS)) {
            stmt.setInt(1, id_polo);
            stmt.setInt(2, tipo_soggetto);
            stmt.setInt(3, id_soggetto);
            stmt.setInt(4, codice_prodotto);
            stmt.setInt(5, quantità_ricevuta);
            stmt.setDate(6, date);
            // return stmt.executeUpdate() > 0;

            int rowsAffected = stmt.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Segnalato correttamente");
            } else {
                System.out.println("Errore");
            }
            connection.close();
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
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public static void querySalvaRichiesta(int codice_prodotto, int quantità) throws Exception {
        connect();
        // Ottenere la data di oggi
        LocalDate today = LocalDate.now();
        // Convertire LocalDate in java.sql.Date
        java.sql.Date date = java.sql.Date.valueOf(today);

        // Controllare se il record esiste già
        String checkQuery = "SELECT quantità FROM richiesta_ad_hoc WHERE codice_prodotto = ?";
        try (PreparedStatement checkStmt = connection.prepareStatement(checkQuery)) {
            checkStmt.setInt(1, codice_prodotto);
            ResultSet resultSet = checkStmt.executeQuery();
            if (resultSet.next()) {
                // Il record esiste già, incrementa la quantità
                int existingQuantity = resultSet.getInt("quantità");
                int newQuantity = existingQuantity + quantità;

                String updateQuery = "UPDATE richiesta_ad_hoc SET quantità = ? WHERE codice_prodotto = ?";
                try (PreparedStatement updateStmt = connection.prepareStatement(updateQuery)) {
                    updateStmt.setInt(1, newQuantity);
                    updateStmt.setInt(2, codice_prodotto);
                    int rowsAffected = updateStmt.executeUpdate();
                    if (rowsAffected > 0) {
                        System.out.println("Quantità aggiornata correttamente");
                    } else {
                        System.out.println("Errore nell'aggiornamento della quantità");
                    }
                }
            } else {
                // Il record non esiste, inserisci un nuovo record
                String insertQuery = "INSERT INTO richiesta_ad_hoc (codice_prodotto, quantità, data) VALUES (?,?,?)";
                try (PreparedStatement insertStmt = connection.prepareStatement(insertQuery)) {
                    insertStmt.setInt(1, codice_prodotto);
                    insertStmt.setInt(2, quantità);
                    insertStmt.setDate(3, date);

                    int rowsAffected = insertStmt.executeUpdate();
                    if (rowsAffected > 0) {
                        System.out.println("Record inserito correttamente");
                    } else {
                        System.out.println("Errore nell'inserimento del record");
                    }
                }
            }
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    // 0 => diocesi
    // 1 => polo
    // 2 => nucleo
    public static void querySalvaSchemaDistribuzione(int type, int id_type, int codice_prodotto, int quantità) throws Exception {
        connect();

        // Query di ricerca preliminare
        String selectQuery = "SELECT quantità FROM schema_distribuzione WHERE type = ? AND id_type = ? AND codice_prodotto = ?";

        try (PreparedStatement selectStmt = connection.prepareStatement(selectQuery)) {
            selectStmt.setInt(1, type);
            selectStmt.setInt(2, id_type);
            selectStmt.setInt(3, codice_prodotto);

            ResultSet resultSet = selectStmt.executeQuery();

            if (resultSet.next()) {
                // Record già presente, esegui un'istruzione UPDATE
                int quantitàEsistente = resultSet.getInt("quantità");
                int nuovaQuantità = quantitàEsistente + quantità;

                String updateQuery = "UPDATE schema_distribuzione SET quantità = ? WHERE type = ? AND id_type = ? AND codice_prodotto = ?";

                try (PreparedStatement updateStmt = connection.prepareStatement(updateQuery)) {
                    updateStmt.setInt(1, nuovaQuantità);
                    updateStmt.setInt(2, type);
                    updateStmt.setInt(3, id_type);
                    updateStmt.setInt(4, codice_prodotto);

                    int rowsAffected = updateStmt.executeUpdate();

                    if (rowsAffected > 0) {
                        System.out.println("Aggiornato correttamente");
                    } else {
                        System.out.println("Errore durante l'aggiornamento");
                    }
                }
            } else {
                // Nessun record corrispondente, esegui un'istruzione INSERT
                String insertQuery = "INSERT INTO schema_distribuzione (type, id_type, codice_prodotto, quantità) VALUES (?, ?, ?, ?)";

                try (PreparedStatement insertStmt = connection.prepareStatement(insertQuery)) {
                    insertStmt.setInt(1, type);
                    insertStmt.setInt(2, id_type);
                    insertStmt.setInt(3, codice_prodotto);
                    insertStmt.setInt(4, quantità);

                    int rowsAffected = insertStmt.executeUpdate();

                    if (rowsAffected > 0) {
                        System.out.println("Inserito correttamente");
                    } else {
                        System.out.println("Errore durante l'inserimento");
                    }
                }
            }
            connection.close();
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
            connection.close();
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
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void queryCaricaViveri(int codice_prodotto, int id_magazzino, int quantità, Date data_scadenza) throws Exception {
        connect();
        String query = "INSERT INTO scorte (codice_prodotto, id_magazzino, quantità, scadenza_prodotto) VALUES (?,?,?,?)";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, codice_prodotto);
            stmt.setInt(2, id_magazzino);
            stmt.setInt(3, quantità);
            stmt.setDate(4, data_scadenza);
            // return stmt.executeUpdate() > 0;

            int rowsAffected = stmt.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Registrato correttamente");
            } else {
                System.out.println("Errore");
            }
            connection.close();
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
            connection.close();
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
            connection.close();
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
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public static Scorte[] queryGetScortePer(String type) throws Exception {
        connect();
        var query = "SELECT * FROM scorte INNER JOIN prodotto ON scorte.codice_prodotto = prodotto.codice INNER JOIN magazzino ON scorte.id_magazzino = magazzino.id WHERE prodotto."+type+" = 1 AND magazzino.type = 0";
        List<Scorte> listaScorte = new ArrayList<>();

        try (PreparedStatement stmt = connection.prepareStatement(query, PreparedStatement.RETURN_GENERATED_KEYS)) {
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Scorte scorte = new Scorte(rs.getInt(1), rs.getInt(2), rs.getInt(3), rs.getInt(4), rs.getDate(5));
                listaScorte.add(scorte);
            }
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return listaScorte.toArray(new Scorte[0]);
    }

    // type 0 => mensile
    // 1 => trimestrale
    // 2 => annuale
    public static Segnalazione[] queryGetSegnalazioni(int type_interval, int interval) throws Exception {
        connect();
        var query = "";
        switch (type_interval) {
            case 0:
                query = "SELECT * FROM segnalazione WHERE MONTH(data) = " + interval;
                break;
            case 1:
                query = "SELECT * FROM segnalazione WHERE MONTH(data) = " + interval;
                break;
            case 2:
                // Calcolo dei mesi corrispondenti al trimestre
                int startMonth = (interval - 1) * 3 + 1;
                int endMonth = interval * 3;

                // Creazione della parte della query per il trimestre
                StringBuilder trimesterQuery = new StringBuilder();
                trimesterQuery.append("MONTH(data) >= ").append(startMonth);
                trimesterQuery.append(" AND MONTH(data) <= ").append(endMonth);

                // Composizione della query completa
                query = "SELECT * FROM segnalazione WHERE YEAR(data) = ? AND (" + trimesterQuery.toString() + ")";
                break;
        }
        List<Segnalazione> listaSegnalazioni = new ArrayList<>();

        try (PreparedStatement stmt = connection.prepareStatement(query, PreparedStatement.RETURN_GENERATED_KEYS)) {
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Segnalazione segnalazione = Segnalazione.createFromDB(rs);
                listaSegnalazioni.add(segnalazione);
            }
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return listaSegnalazioni.toArray(new Segnalazione[0]);
    }

    public static Scorte[] queryGetScortePerTutti() throws Exception {
        connect();
        var query = "SELECT * FROM scorte INNER JOIN prodotto ON scorte.codice_prodotto = prodotto.codice INNER JOIN magazzino ON scorte.id_magazzino = magazzino.id WHERE prodotto.senzaZucchero = 0 AND prodotto.senzaGlutine = 0 AND prodotto.senzaLattosio = 0 AND magazzino.type = 0";
        List<Scorte> listaScorte = new ArrayList<>();

        try (PreparedStatement stmt = connection.prepareStatement(query, PreparedStatement.RETURN_GENERATED_KEYS)) {
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Scorte scorte = new Scorte(rs.getInt(1), rs.getInt(2), rs.getInt(3), rs.getInt(4), rs.getDate(5));
                listaScorte.add(scorte);
            }
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return listaScorte.toArray(new Scorte[0]);
    }
    public static Prodotto[] queryGetProdottiPer(String type) throws Exception {
        connect();
        var query = "SELECT * FROM prodotto WHERE "+type+" = 1";
        List<Prodotto> listaProdotti = new ArrayList<>();

        try (PreparedStatement stmt = connection.prepareStatement(query, PreparedStatement.RETURN_GENERATED_KEYS)) {
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Prodotto prodotto = Prodotto.createFromDB(rs);
                listaProdotti.add(prodotto);
            }
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return listaProdotti.toArray(new Prodotto[0]);
    }

    public static SchemaDistribuzione[] queryGetSchemiDistribuzione(int type, int id_type) throws Exception {
        connect();
        var query = "SELECT * FROM schema_distribuzione WHERE type = ? && id_type = ?";
        List<SchemaDistribuzione> schemiDistribuzione = new ArrayList<>();

        try (PreparedStatement stmt = connection.prepareStatement(query, PreparedStatement.RETURN_GENERATED_KEYS)) {
            stmt.setInt(1, type);
            stmt.setInt(2, id_type);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                SchemaDistribuzione schemaDistribuzione = SchemaDistribuzione.createFromDB(rs);
                schemiDistribuzione.add(schemaDistribuzione);
            }
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return schemiDistribuzione.toArray(new SchemaDistribuzione[0]);
    }


    public static SchemaDistribuzione[] queryGetSchemiDistribuzione(int type) throws Exception {
        connect();
        var query = "SELECT * FROM schema_distribuzione WHERE type = ?";
        List<SchemaDistribuzione> schemiDistribuzione = new ArrayList<>();

        try (PreparedStatement stmt = connection.prepareStatement(query, PreparedStatement.RETURN_GENERATED_KEYS)) {
            stmt.setInt(1, type);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                SchemaDistribuzione schemaDistribuzione = SchemaDistribuzione.createFromDB(rs);
                schemiDistribuzione.add(schemaDistribuzione);
            }
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return schemiDistribuzione.toArray(new SchemaDistribuzione[0]);
    }



    public static boolean queryControllaEsistenzaEmail(String email) throws Exception {
        connect();
        var query = "SELECT * FROM responsabile WHERE email = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, email);
            var r = stmt.executeQuery();
            if (r.next()) {
                connection.close();
                return true;
            }
            connection.close();
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
                connection.close();
                return true;
            }
            connection.close();
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
            connection.close();
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
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public static Diocesi queryGetDiocesi(int id) throws Exception {
        connect();
        var query = "SELECT * FROM diocesi WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query, PreparedStatement.RETURN_GENERATED_KEYS)) {
            stmt.setInt(1, id);
            var r = stmt.executeQuery();
            if (r.next()) {
                Diocesi diocesi = new Diocesi();
                return diocesi.createFromDB(r);
            }
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static Diocesi getDiocesiById(int id) throws Exception {
        connect();
        var query = "SELECT * FROM diocesi WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query, PreparedStatement.RETURN_GENERATED_KEYS)) {
            stmt.setInt(1, id);
            var r = stmt.executeQuery();
            if (r.next()) {
                Diocesi diocesi = new Diocesi();
                return diocesi.createFromDB(r);
            }
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static boolean queryGetStatoAccount(String nome_tabella, int id) throws Exception {
        connect();
        var query = "SELECT stato_account FROM "+nome_tabella+" WHERE id = ? && stato_account = 1";
        try (PreparedStatement stmt = connection.prepareStatement(query, PreparedStatement.RETURN_GENERATED_KEYS)) {
            stmt.setInt(1, id);
            var r = stmt.executeQuery();
            if (r.next()) {
                return true;
            }
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public static boolean queryGetStatoSospensione(int id) throws Exception {
        connect();
        var query = "SELECT stato_sospensione FROM polo WHERE id = ? && stato_sospensione = 1";
        try (PreparedStatement stmt = connection.prepareStatement(query, PreparedStatement.RETURN_GENERATED_KEYS)) {
            stmt.setInt(1, id);
            var r = stmt.executeQuery();
            if (r.next()) {
                return true;
            }
            connection.close();
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
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static AziendaPartner queryGetAziendaPartner(int id) throws Exception {
        connect();
        var query = "SELECT * FROM azienda WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query, PreparedStatement.RETURN_GENERATED_KEYS)) {
            stmt.setInt(1, id);
            var r = stmt.executeQuery();
            if (r.next()) {
                AziendaPartner aziendaPartner = new AziendaPartner();
                return aziendaPartner.createFromDB(r);
            }
            connection.close();
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
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static Polo queryGetPoloById(int id) throws Exception {
        connect();
        var query = "SELECT * FROM polo WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query, PreparedStatement.RETURN_GENERATED_KEYS)) {
            stmt.setInt(1, id);
            var r = stmt.executeQuery();
            if (r.next()) {
                Polo polo = new Polo();
                return MainUtils.poloLoggato = polo.createFromDB(r);
            }
            connection.close();
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
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public static void queryModificaPassword(String email, String password) throws Exception {
        connect();
        var query = "UPDATE responsabile SET password = ? WHERE email = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query, PreparedStatement.RETURN_GENERATED_KEYS)) {
            stmt.setString(1, password);
            stmt.setString(2, email);
            var r = stmt.executeUpdate();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void querySospendiPolo(int id) throws Exception {
        connect();
        var query = "UPDATE polo SET stato_sospensione = 1 WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, id);
            var r = stmt.executeUpdate();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public static void queryRipristinaPolo(int id) throws Exception {
        connect();
        var query = "UPDATE polo SET stato_sospensione = 0 WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, id);
            var r = stmt.executeUpdate();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static Responsabile getResponsabile(int type, int id_lavoro) throws Exception {
        connect();
        var query = "SELECT * FROM responsabile WHERE type = ? && id_lavoro = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query, PreparedStatement.RETURN_GENERATED_KEYS)) {
            stmt.setInt(1, type);
            stmt.setInt(2, id_lavoro);
            var r = stmt.executeQuery();
            if (r.next()) {
                Responsabile responsabile = Responsabile.createFromDB(r);
                return  responsabile;
            }
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static Prodotto queryGetProdotto(int codice_prodotto) throws Exception {
        connect();
        var query = "SELECT * FROM prodotto WHERE codice = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query, PreparedStatement.RETURN_GENERATED_KEYS)) {
            stmt.setInt(1, codice_prodotto);
            var r = stmt.executeQuery();
            if (r.next()) {
                Prodotto prodotto = Prodotto.createFromDB(r);
                return prodotto;
            }
            connection.close();
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
            connection.close();
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
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public static void queryEliminaRichiestaAdHoc(int id) throws Exception {
        connect();
        var query = "DELETE FROM richiesta_ad_hoc WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query, PreparedStatement.RETURN_GENERATED_KEYS)) {
            stmt.setInt(1, id);
            var r = stmt.executeUpdate();
            connection.close();
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
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return richiesteDiocesi.toArray(new Diocesi[0]);
    }
    public static Donazione[] queryGetAllDonazioni() throws Exception {
        connect();
        String query = "SELECT * FROM donazione ORDER BY date DESC";
        List<Donazione> listaDonazioni = new ArrayList<>();

        try (PreparedStatement stmt = connection.prepareStatement(query, PreparedStatement.RETURN_GENERATED_KEYS)) {
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Donazione donazione = Donazione.createFromDB(rs);
                listaDonazioni.add(donazione);
            }
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return listaDonazioni.toArray(new Donazione[0]);
    }
    public static Donazione[] queryGetAllDonazioni(int id_azienda) throws Exception {
        connect();
        String query = "SELECT * FROM donazione WHERE id_azienda = ? ORDER BY date DESC";
        List<Donazione> listaDonazioni = new ArrayList<>();

        try (PreparedStatement stmt = connection.prepareStatement(query, PreparedStatement.RETURN_GENERATED_KEYS)) {
            stmt.setInt(1, id_azienda);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Donazione donazione = Donazione.createFromDB(rs);
                listaDonazioni.add(donazione);
            }
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return listaDonazioni.toArray(new Donazione[0]);
    }
    public static Magazzino[] queryGetMagazzini(int type, int id_proprietario) throws Exception {
        connect();
        String query = "SELECT * FROM magazzino WHERE type = ? && id_proprietario = ?";
        List<Magazzino> listaMagazzini = new ArrayList<>();

        try (PreparedStatement stmt = connection.prepareStatement(query, PreparedStatement.RETURN_GENERATED_KEYS)) {
            stmt.setInt(1, type);
            stmt.setInt(2, id_proprietario);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Magazzino magazzino = Magazzino.createFromDB(rs);
                listaMagazzini.add(magazzino);
            }
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return listaMagazzini.toArray(new Magazzino[0]);
    }

    public static Scorte[] queryGetScorte(int id_magazzino) throws Exception {
        connect();
        String query = "SELECT * FROM scorte WHERE id_magazzino = ?";
        List<Scorte> listaScorte = new ArrayList<>();

        try (PreparedStatement stmt = connection.prepareStatement(query, PreparedStatement.RETURN_GENERATED_KEYS)) {
            stmt.setInt(1, id_magazzino);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Scorte scorte = Scorte.createFromDB(rs);
                listaScorte.add(scorte);
            }
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return listaScorte.toArray(new Scorte[0]);
    }

    public static Diocesi[] queryGetAllDiocesi() throws Exception {
        connect();
        String query = "SELECT * FROM diocesi";
        List<Diocesi> listaDiocesi = new ArrayList<>();

        try (PreparedStatement stmt = connection.prepareStatement(query, PreparedStatement.RETURN_GENERATED_KEYS)) {
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Diocesi diocesi = Diocesi.createFromDB(rs);
                listaDiocesi.add(diocesi);
            }
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return listaDiocesi.toArray(new Diocesi[0]);
    }

    public static Polo[] queryGetAllPoli(int id_diocesi) throws Exception {
        connect();
        String query = "SELECT * FROM polo WHERE id_diocesi = ?";
        List<Polo> listaPoli = new ArrayList<>();

        try (PreparedStatement stmt = connection.prepareStatement(query, PreparedStatement.RETURN_GENERATED_KEYS)) {
            stmt.setInt(1, id_diocesi);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Polo polo = Polo.createFromDB(rs);
                listaPoli.add(polo);
            }
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return listaPoli.toArray(new Polo[0]);
    }
    public static Polo[] queryGetAllPoli() throws Exception {
        connect();
        String query = "SELECT * FROM polo";
        List<Polo> listaPoli = new ArrayList<>();

        try (PreparedStatement stmt = connection.prepareStatement(query, PreparedStatement.RETURN_GENERATED_KEYS)) {
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Polo polo = Polo.createFromDB(rs);
                listaPoli.add(polo);
            }
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return listaPoli.toArray(new Polo[0]);
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
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return listaProdotti.toArray(new Prodotto[0]);
    }

    public static RichiestaAdHoc[] queryGetRichiesteAdHoc() throws Exception {
        connect();
        String query = "SELECT * FROM richiesta_ad_hoc";
        List<RichiestaAdHoc> listaRichieste = new ArrayList<>();

        try (PreparedStatement stmt = connection.prepareStatement(query, PreparedStatement.RETURN_GENERATED_KEYS)) {
            // stmt.setInt(1, 0);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                RichiestaAdHoc richiestaAdHoc = RichiestaAdHoc.createFromDB(rs);
                listaRichieste.add(richiestaAdHoc);
            }
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return listaRichieste.toArray(new RichiestaAdHoc[0]);
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
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return listaMembri.toArray(new Membro[0]);
    }

    public static Nucleo[] getNuclei(int id_polo) throws Exception {
        connect();
        String query = "SELECT * FROM nucleo WHERE id_polo = ? ORDER BY reddito ASC";
        List<Nucleo> listaNuclei = new ArrayList<>();

        try (PreparedStatement stmt = connection.prepareStatement(query, PreparedStatement.RETURN_GENERATED_KEYS)) {
            stmt.setInt(1, id_polo);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Nucleo nucleo = Nucleo.createFromDB(rs);
                listaNuclei.add(nucleo);
            }
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return listaNuclei.toArray(new Nucleo[0]);
    }

    public static Nucleo getNucleo(int id) throws Exception {
        connect();
        String query = "SELECT * FROM nucleo WHERE id = ?";

        try (PreparedStatement stmt = connection.prepareStatement(query, PreparedStatement.RETURN_GENERATED_KEYS)) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                return Nucleo.createFromDB(rs);
            }
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
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
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return richiesteAziende.toArray(new AziendaPartner[0]);
    }

    public static void queryModificaDati(int id, String nomeTabellaDB, HashMap<String, Object> dati) throws Exception {
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
        values.add(id);

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
            connection.close();
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