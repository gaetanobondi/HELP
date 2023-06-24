package it.help.help.entity;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.Objects;

public class Membro {
    private String codice_fiscale;
    private int id_nucleo;
    private String nome;
    private String cognome;
    private Date data_nascita;
    private String indirizzo;
    private boolean celiachia;
    private boolean intolleranza_lattosio;
    private boolean diabete;

    public Membro(String codice_fiscale, int id_nucleo, String nome, String cognome, Date data_nascita, String indirizzo, boolean celiachia, boolean intolleranza_lattosio, boolean diabete) {
        this.codice_fiscale = codice_fiscale;
        this.id_nucleo = id_nucleo;
        this.nome = nome;
        this.cognome = cognome;
        this.data_nascita = data_nascita;
        this.indirizzo = indirizzo;
        this.celiachia = celiachia;
        this.intolleranza_lattosio = intolleranza_lattosio;
        this.diabete = diabete;
    }

    public Membro() { }

    public String getCodiceFiscale() {
        return codice_fiscale;
    }
    public int getIdNucleo() { return id_nucleo; }
    public String getNome() {
        return nome;
    }
    public String getCognome() {
        return cognome;
    }
    public Date getDataNascita() {
        return data_nascita;
    }
    public String getIndirizzo() {
        return indirizzo;
    }
    public boolean getCeliachia() {
        return celiachia;
    }
    public boolean getIntolleranzaLattosio() {
        return intolleranza_lattosio;
    }
    public boolean getDiabete() {
        return diabete;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) return true;
        if (obj == null || obj.getClass() != this.getClass()) return false;
        var that = (Nucleo) obj;
        return this.codice_fiscale.equals(codice_fiscale);
    }

    @Override
    public int hashCode() {
        return Objects.hash(codice_fiscale);
    }


    public static Membro createFromDB(ResultSet row) throws SQLException {
        return new Membro(
                row.getString(1),
                row.getInt(2),
                row.getString(3),
                row.getString(4),
                row.getDate(5),
                row.getString(6),
                row.getBoolean(7),
                row.getBoolean(8),
                row.getBoolean(9));
    }

}
