package it.help.help.entity;

import java.util.Objects;
import java.sql.*;

public class Responsabile {
    private int id;
    private String nome;
    private String cognome;
    private String email;
    private String password;
    private int type;
    private int id_lavoro;

    public Responsabile(int id, String email, String password, int type, String nome, String cognome, int id_lavoro) {
        this.id = id;
        this.nome = nome;
        this.cognome = cognome;
        this.email = email;
        this.password = password;
        this.id_lavoro = id_lavoro;
        this.type = type;
    }

    public Responsabile() {}

    public int getId() {
        return id;
    }
    public String getNome() {
        return nome;
    }
    public String getCognome() {
        return cognome;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }
    public int getIdLavoro() {
        return id_lavoro;
    }

    public int getType() {
        return type;
    }


    @Override
    public boolean equals(Object obj) {
        if (obj == this) return true;
        if (obj == null || obj.getClass() != this.getClass()) return false;
        var that = (Responsabile) obj;
        return this.email.compareToIgnoreCase(that.getEmail()) == 0;
    }

    @Override
    public int hashCode() {
        return Objects.hash(email, password, type);
    }

    @Override
    public String toString() {
        return "Responsabile[" +
                "email=" + email + ", " +
                "password=" + password + ", " +
                "password=" + password;
    }

    public static Responsabile createFromDB(ResultSet row) throws SQLException {
        return new Responsabile(
                row.getInt(1),
                row.getString(2),
                row.getString(3),
                row.getInt(4),
                row.getString(5),
                row.getString(6),
                row.getInt(7));
    }

}
