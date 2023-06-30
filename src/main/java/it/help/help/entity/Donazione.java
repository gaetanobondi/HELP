package it.help.help.entity;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.Objects;

public class Donazione {
    private int id;
    private int codice_prodotto;
    private int id_azienda;
    private int quantità;
    private Date scadenza_prodotto;

    public Donazione(int id, int codice_prodotto, int id_azienda, int quantità, Date scadenza_prodotto) {
        this.id = id;
        this.codice_prodotto = codice_prodotto;
        this.id_azienda = id_azienda;
        this.quantità = quantità;
        this.scadenza_prodotto = scadenza_prodotto;
    }

    public Donazione() { }

    public int getId() {
        return id;
    }
    public int getCodiceProdotto() {
        return codice_prodotto;
    }
    public int getIdAzienda() {
        return id_azienda;
    }
    public int getQuantità() { return quantità; }
    public Date getScadenzaProdotto() {
        return scadenza_prodotto;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) return true;
        if (obj == null || obj.getClass() != this.getClass()) return false;
        var that = (Donazione) obj;
        return this.id == that.getId();
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }


    public static Donazione createFromDB(ResultSet row) throws SQLException {
        return new Donazione(
                row.getInt(1),
                row.getInt(2),
                row.getInt(3),
                row.getInt(4),
                row.getDate(5));
    }

}
