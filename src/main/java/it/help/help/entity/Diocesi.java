package it.help.help.entity;

import java.util.Objects;
import java.sql.*;

public class Diocesi {
    private static int id;
    private static int id_responsabile;
    private static String nome;
    private static String nome_responsabile;
    private static String cognome_responsabile;
    private static String prete;
    private static String indirizzo;
    private static int cellulare;
    private static boolean stato_account;
    private static Date date;

    public Diocesi(int id, int id_responsabile, String nome, String nome_responsabile, String cognome_responsabile, String prete, String indirizzo, int cellulare, boolean stato_account, Date date) {
        this.id = id;
        this.id_responsabile = id_responsabile;
        this.nome = nome;
        this.nome_responsabile = nome_responsabile;
        this.cognome_responsabile = cognome_responsabile;
        this.prete = prete;
        this.indirizzo = indirizzo;
        this.cellulare = cellulare;
        this.stato_account = stato_account;
        this.date = date;
    }

    public static int getId() {
        return id;
    }
    public static int getId_responsabile() {
        return id_responsabile;
    }

    public static String getNome() {
        return nome;
    }
    public static String getNome_responsabile() {
        return nome_responsabile;
    }
    public static String getCognome_responsabile() {
        return cognome_responsabile;
    }
    public static String getPrete() {
        return prete;
    }
    public static String getIndirizzo() {
        return indirizzo;
    }
    public static int getCellulare() {
        return cellulare;
    }
    public static boolean getStato_account() {
        return stato_account;
    }
    public static Date getDate() {
        return date;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) return true;
        if (obj == null || obj.getClass() != this.getClass()) return false;
        var that = (Diocesi) obj;
        return this.id == that.getId();
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "Diocesi[" +
                "id=" + id + ", " +
                "id_responsabile=" + id_responsabile + ", " +
                "nome=" + nome;
    }

    public static Diocesi createFromDB(ResultSet row) throws SQLException {
        return new Diocesi(
                row.getInt(1),
                row.getInt(2),
                row.getString(3),
                row.getString(4),
                row.getString(5),
                row.getString(6),
                row.getString(7),
                row.getInt(8),
                row.getBoolean(9),
                row.getDate(10));
    }

}
