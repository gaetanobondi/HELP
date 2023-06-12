package it.help.help.entity;
import java.sql.*;
import java.util.Objects;

public class AziendaPartner {
    private final int id;
    private final int id_responsabile;
    private final String nome;
    private final String nome_responsabile;
    private final String cognome_responsabile;
    private final String indirizzo;
    private final int cellulare;
    private final String viveri_prodotto;
    private final boolean stato_account;
    private final Date date;

    public AziendaPartner(int id, int id_responsabile, String nome, String nome_responsabile, String cognome_responsabile, String indirizzo, int cellulare, String viveri_prodotto, boolean stato_account, Date date) {
        this.id = id;
        this.id_responsabile = id_responsabile;
        this.nome = nome;
        this.nome_responsabile = nome_responsabile;
        this.cognome_responsabile = cognome_responsabile;
        this.indirizzo = indirizzo;
        this.cellulare = cellulare;
        this.viveri_prodotto = viveri_prodotto;
        this.stato_account = stato_account;
        this.date = date;
    }

    public int getId() {
        return id;
    }
    public int getIdResponsabile() {
        return id_responsabile;
    }
    public String getNome() {
        return nome;
    }
    public String getNomeResponsabile() {
        return nome_responsabile;
    }
    public String getCognomeResponsabile() {
        return cognome_responsabile;
    }
    public String getIndirizzo() {
        return indirizzo;
    }
    public int getCellulare() {
        return cellulare;
    }
    public String getViveriProdotto() {
        return viveri_prodotto;
    }
    public boolean getStatoAccount() {
        return stato_account;
    }
    public Date getDate() {
        return date;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) return true;
        if (obj == null || obj.getClass() != this.getClass()) return false;
        var that = (AziendaPartner) obj;
        return this.id == that.getId();
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    public static AziendaPartner createFromDB(ResultSet row) throws SQLException {
        return new AziendaPartner(
                row.getInt(1),
                row.getInt(2),
                row.getString(3),
                row.getString(4),
                row.getString(5),
                row.getString(6),
                row.getInt(7),
                row.getString(8),
                row.getBoolean(9),
                row.getDate(10));
    }

}
