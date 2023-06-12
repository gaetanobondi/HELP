package it.help.help.entity;

import java.util.Objects;
import java.sql.*;

public class Responsabile {
    private final int id;
    private final String email;
    private final String password;
    private final int type;

    public Responsabile(int id, String email, String password, int type) {
        this.id = id;
        this.email = email;
        this.password = password;
        this.type = type;
    }

    public int getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
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
                row.getInt(4));
    }

}
