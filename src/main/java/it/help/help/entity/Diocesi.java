package it.help.help.entity;

import java.util.Objects;
import java.sql.*;

public class Diocesi {
    private final int id;
    private final int id_responsabile;
    private final String nome;
    private final String nome_responsabile;
    private final String cognome_responsabile;
    private final String prete;
    private final String indirizzo;
    private final int cellulare;
    private final boolean stato_account;
    private final Date date;

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

    public int getId() {
        return id;
    }
    public int getId_responsabile() {
        return id_responsabile;
    }

    public String getNome() {
        return nome;
    }
    public String getNome_responsabile() {
        return nome_responsabile;
    }
    public String getCognome_responsabile_responsabile() {
        return cognome_responsabile;
    }
    public String getPrete() {
        return prete;
    }
    public String getIndirizzo() {
        return indirizzo;
    }
    public int getCellulare() {
        return cellulare;
    }
    public boolean getStato_account() {
        return stato_account;
    }
    public Date getDate() {
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
