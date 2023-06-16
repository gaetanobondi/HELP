package it.help.help.entity;

import java.util.Objects;
import java.sql.*;

public class Polo {
    private int id;
    private int id_responsabile;
    private int id_diocesi;
    private String nome;
    private String nome_responsabile;
    private String cognome_responsabile;
    private String indirizzo;
    private int cellulare;
    private boolean stato_sospensione;
    private Date date;

    public Polo(int id, int id_responsabile, int id_diocesi, String nome, String nome_responsabile, String cognome_responsabile, String indirizzo, int cellulare, boolean stato_sospensione, Date date) {
        this.id = id;
        this.id_responsabile = id_responsabile;
        this.id_diocesi = id_diocesi;
        this.nome = nome;
        this.nome_responsabile = nome_responsabile;
        this.cognome_responsabile = cognome_responsabile;
        this.indirizzo = indirizzo;
        this.cellulare = cellulare;
        this.stato_sospensione = stato_sospensione;
        this.date = date;
    }

    public Polo() { }

    public int getId() {
        return id;
    }
    public int getId_responsabile() {
        return id_responsabile;
    }
    public int getId_diocesi() {
        return id_diocesi;
    }
    public String getNome() {
        return nome;
    }
    public String getNome_responsabile() {
        return nome_responsabile;
    }
    public String getCognome_responsabile() {
        return cognome_responsabile;
    }
    public String getIndirizzo() {
        return indirizzo;
    }
    public int getCellulare() {
        return cellulare;
    }
    public boolean getStato_sospensione() {
        return stato_sospensione;
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
        return "Polo[" +
                "id=" + id + ", " +
                "id_responsabile=" + id_responsabile + ", " +
                "nome=" + nome;
    }

    public Polo createFromDB(ResultSet row) throws SQLException {
        return new Polo(
                row.getInt(1),
                row.getInt(2),
                row.getInt(3),
                row.getString(4),
                row.getString(5),
                row.getString(6),
                row.getString(7),
                row.getInt(8),
                row.getBoolean(9),
                row.getDate(10));
    }

}
