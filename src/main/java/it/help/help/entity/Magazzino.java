package it.help.help.entity;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Objects;

public class Magazzino {
    private int id;
    private int type;
    private int id_proprietario;
    private int capienza_max;
    private int capienza_attuale;

    public Magazzino(int id, int type, int id_proprietario, int capienza_max, int capienza_attuale) {
        this.id = id;
        this.type = type;
        this.id_proprietario = id_proprietario;
        this.capienza_max = capienza_max;
        this.capienza_attuale = capienza_attuale;
    }

    public Magazzino() { }

    public int getId() {
        return id;
    }
    public int getType() {
        return type;
    }
    public int getIdProprietario() {
        return id_proprietario;
    }
    public int getCapienzaMax() { return capienza_max; }
    public int getCapienzaAttuale() {
        return capienza_attuale;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) return true;
        if (obj == null || obj.getClass() != this.getClass()) return false;
        var that = (Magazzino) obj;
        return this.id == that.getId();
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }


    public static Magazzino createFromDB(ResultSet row) throws SQLException {
        return new Magazzino(
                row.getInt(1),
                row.getInt(2),
                row.getInt(3),
                row.getInt(4),
                row.getInt(5));
    }

}
