package it.help.help.entity;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Objects;

public class Help {
    private static int id;
    private static int id_responsabile;
    private static String nome;
    private static String cognome;

    public Help(int id, int id_responsabile, String nome, String cognome) {
        this.id = id;
        this.id_responsabile = id_responsabile;
        this.nome = nome;
        this.cognome = cognome;
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
    public static String getCognome() {
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

    public static Help createFromDB(ResultSet row) throws SQLException {
        return new Help(
                row.getInt(1),
                row.getInt(2),
                row.getString(3),
                row.getString(4));
    }

}
