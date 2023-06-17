package it.help.help.entity;

import java.util.Objects;
import java.sql.*;

public class Responsabile {
    private int id;
    private String nome;
    private String email;
    private String password;
    private boolean stato_account;
    private int type;

    public Responsabile(int id, String email, String password, int type, boolean stato_account,  String nome) {
        this.id = id;
        this.nome = nome;
        this.email = email;
        this.password = password;
        this.stato_account = stato_account;
        this.type = type;
    }

    public Responsabile() {}

    public int getId() {
        return id;
    }
    public String getNome() {
        return nome;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }
    public boolean getStatoAccount() {
        return stato_account;
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
                row.getBoolean(5),
                row.getString(6));
    }

}
