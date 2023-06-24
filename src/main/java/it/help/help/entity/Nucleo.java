package it.help.help.entity;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Objects;

public class Nucleo {
    private int id;
    private int id_polo;
    private String cognome;
    private int reddito;

    public Nucleo(int id, int id_polo, String cognome, int reddito) {
        this.id = id;
        this.id_polo = id_polo;
        this.cognome = cognome;
        this.reddito = reddito;
    }

    public Nucleo() { }

    public int getId() {
        return id;
    }
    public String getCognome() {
        return cognome;
    }
    public int getReddito() {
        return reddito;
    }
    public int getIdPolo() { return id_polo; }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) return true;
        if (obj == null || obj.getClass() != this.getClass()) return false;
        var that = (Nucleo) obj;
        return this.id == that.getId();
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }


    public static Nucleo createFromDB(ResultSet row) throws SQLException {
        return new Nucleo(
                row.getInt(1),
                row.getInt(2),
                row.getString(3),
                row.getInt(4));
    }

}
