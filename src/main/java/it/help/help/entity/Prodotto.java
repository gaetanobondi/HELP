package it.help.help.entity;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Objects;

public class Prodotto {
    private int codice;
    private String tipo;
    private boolean senzaGlutine;
    private boolean senzaLattosio;
    private boolean senzaZucchero;

    public Prodotto(int codice, String tipo, boolean senzaGlutine, boolean senzaLattosio, boolean senzaZucchero) {
        this.codice = codice;
        this.tipo = tipo;
        this.senzaGlutine = senzaGlutine;
        this.senzaLattosio = senzaLattosio;
        this.senzaZucchero = senzaZucchero;
    }

    public Prodotto() { }

    public int getCodice() {
        return codice;
    }
    public String getTipo() {
        return tipo;
    }
    public boolean getSenzaGlutine() {
        return senzaGlutine;
    }
    public boolean getSenzaLattosio() {
        return senzaLattosio;
    }
    public boolean getSenzaZucchero() {
        return senzaZucchero;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) return true;
        if (obj == null || obj.getClass() != this.getClass()) return false;
        var that = (Prodotto) obj;
        return this.codice == that.getCodice();
    }

    @Override
    public int hashCode() {
        return Objects.hash(codice);
    }


    public static Prodotto createFromDB(ResultSet row) throws SQLException {
        return new Prodotto(
                row.getInt(1),
                row.getString(2),
                row.getBoolean(3),
                row.getBoolean(4),
                row.getBoolean(5));
    }

}
