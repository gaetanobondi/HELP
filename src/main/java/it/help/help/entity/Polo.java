package it.help.help.entity;

import java.util.Objects;
import java.sql.*;

public class Polo {
    private int id;
    private String nome;
    private boolean stato_sospensione;
    private String indirizzo;
    private int cellulare;
    private int id_diocesi;
    private Date date;

    public Polo(int id, String nome, boolean stato_sospensione, String indirizzo, int cellulare, int id_diocesi, Date date) {
        this.id = id;
        this.nome = nome;
        this.stato_sospensione = stato_sospensione;
        this.indirizzo = indirizzo;
        this.cellulare = cellulare;
        this.id_diocesi = id_diocesi;
        this.date = date;
    }

    public Polo() { }

    public int getId() {
        return id;
    }
    public int getId_diocesi() {
        return id_diocesi;
    }
    public String getNome() {
        return nome;
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
        var that = (Polo) obj;
        return this.id == that.getId();
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }


    public static Polo createFromDB(ResultSet row) throws SQLException {
        return new Polo(
                row.getInt(1),
                row.getString(2),
                row.getBoolean(3),
                row.getString(4),
                row.getInt(5),
                row.getInt(6),
                row.getDate(7));
    }

}
