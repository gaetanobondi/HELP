package it.help.help.entity;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Objects;

public class Scorte {
    private int id;
    private int codice_prodotto;
    private int id_magazzino;
    private int quantità;
    private Date scadenza_prodotto;

    public Scorte(int id, int codice_prodotto, int id_magazzino, int quantità, Date scadenza_prodotto) {
        this.id = id;
        this.codice_prodotto = codice_prodotto;
        this.id_magazzino = id_magazzino;
        this.quantità = quantità;
        this.scadenza_prodotto = scadenza_prodotto;
    }

    public Scorte() { }

    public int getId() {
        return id;
    }
    public int getCodiceProdotto() {
        return codice_prodotto;
    }
    public int getIdMagazzino() {
        return id_magazzino;
    }
    public int getQuantità() { return quantità; }
    public Date getScadenzaProdotto() {
        return scadenza_prodotto;
    }

    public void setQuantità(int quantità) {
        this.quantità = quantità;
    }


    @Override
    public boolean equals(Object obj) {
        if (obj == this) return true;
        if (obj == null || obj.getClass() != this.getClass()) return false;
        var that = (Scorte) obj;
        return this.id == that.getId();
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }


    public static Scorte createFromDB(ResultSet row) throws SQLException {
        return new Scorte(
                row.getInt(1),
                row.getInt(2),
                row.getInt(3),
                row.getInt(4),
                row.getDate(5));
    }

}
