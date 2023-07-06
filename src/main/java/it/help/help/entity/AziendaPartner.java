package it.help.help.entity;
import java.sql.*;
import java.util.Objects;

public class AziendaPartner {
    private int id;
    private String nome;
    private String indirizzo;
    private int cellulare;
    private boolean stato_account;

    public AziendaPartner(int id, String nome, boolean stato_account, String indirizzo, int cellulare) {
        this.id = id;
        this.nome = nome;
        this.indirizzo = indirizzo;
        this.cellulare = cellulare;
        this.stato_account = stato_account;
    }

    public AziendaPartner() { }

    public int getId() {
        return id;
    }
    public String getNome() {
        return nome;
    }
    public String getIndirizzo() {
        return indirizzo;
    }
    public int getCellulare() {
        return cellulare;
    }
    public boolean getStatoAccount() {
        return stato_account;
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
                row.getString(2),
                row.getBoolean(3),
                row.getString(4),
                row.getInt(5));
    }

}
