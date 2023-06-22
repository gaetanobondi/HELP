package it.help.help.entity;

import java.util.Objects;
import java.sql.*;

public class Diocesi {
    private int id;
    private String nome;
    private String indirizzo;
    private int cellulare;
    private boolean stato_account;

    public Diocesi(int id, int id_responsabile, String nome, String indirizzo, int cellulare, boolean stato_account) {
        this.id = id;
        this.nome = nome;
        this.indirizzo = indirizzo;
        this.cellulare = cellulare;
        this.stato_account = stato_account;
    }

    public Diocesi() { }

    public int getId() {
        return id;
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

    public boolean getStato_account() {
        return stato_account;
    }


    @Override
    public boolean equals(Object obj) {
        if (obj == this) return true;
        if (obj == null || obj.getClass() != this.getClass()) return false;
        Diocesi that = (Diocesi) obj;
        return this.id == that.getId();
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    public static Diocesi createFromDB(ResultSet row) throws SQLException {
        return new Diocesi(
                row.getInt(1),
                row.getInt(2),
                row.getString(3),
                row.getString(4),
                row.getInt(5),
                row.getBoolean(6));
    }
}

