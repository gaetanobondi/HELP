package it.help.help.entity;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Objects;

public class Segnalazione {
    private int id;
    private int id_polo;
    private int tipo_soggetto;
    private int id_soggetto;
    private int codice_prodotto;
    private int quantità_ricevuta;
    private Date data;

    public Segnalazione(int id, int id_polo, int tipo_soggetto, int id_soggetto, int codice_prodotto, int quantità_ricevuta, Date data) {
        this.id = id;
        this.id_polo = id_polo;
        this.tipo_soggetto = tipo_soggetto;
        this.id_soggetto = id_soggetto;
        this.codice_prodotto = codice_prodotto;
        this.quantità_ricevuta = quantità_ricevuta;
        this.data = data;
    }

    public Segnalazione() { }

    public int getId() {
        return id;
    }
    public int getPolo() {
        return id_polo;
    }
    public int getTipoSoggetto() {
        return tipo_soggetto;
    }
    public int getIdSoggetto() {
        return id_soggetto;
    }
    public int getCodiceProdotto() {
        return codice_prodotto;
    }
    public int getQuantitàRicevuta() { return quantità_ricevuta; }
    public Date getData() {
        return data;
    }


    @Override
    public boolean equals(Object obj) {
        if (obj == this) return true;
        if (obj == null || obj.getClass() != this.getClass()) return false;
        var that = (Segnalazione) obj;
        return this.id == that.getId();
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }


    public static Segnalazione createFromDB(ResultSet row) throws SQLException {
        return new Segnalazione(
                row.getInt(1),
                row.getInt(2),
                row.getInt(3),
                row.getInt(4),
                row.getInt(5),
                row.getInt(6),
                row.getDate(7));
    }

}
