package it.help.help.entity;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Objects;

public class Help {
    private int id;
    private int id_responsabile;
    private String nome;
    private String cognome;

    public Help(int id, int id_responsabile, String nome, String cognome) {
        this.id = id;
        this.id_responsabile = id_responsabile;
        this.nome = nome;
        this.cognome = cognome;
    }

    public Help() { }

    public int getId() {
        return id;
    }
    public int getId_responsabile() {
        return id_responsabile;
    }
    public String getNome() {
        return nome;
    }
    public String getCognome() {
        return cognome;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) return true;
        if (obj == null || obj.getClass() != this.getClass()) return false;
        var that = (Responsabile) obj;
        return this.id == that.getId();
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "Help[" +
                "id=" + id + ", " +
                "id_responsabile=" + id_responsabile + ", " +
                "nome=" + nome;
    }

    public Help createFromDB(ResultSet row) throws SQLException {
        return new Help(
                row.getInt(1),
                row.getInt(2),
                row.getString(3),
                row.getString(4));
    }

}
