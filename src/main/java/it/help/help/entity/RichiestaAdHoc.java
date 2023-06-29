package it.help.help.entity;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Objects;

public class RichiestaAdHoc {
    private int id;
    private int codice_prodotto;
    private int quantità;

    public RichiestaAdHoc(int id, int codice_prodotto, int quantità) {
        this.id = id;
        this.codice_prodotto = codice_prodotto;
        this.quantità = quantità;
    }

    public RichiestaAdHoc() { }

    public int getId() {
        return id;
    }
    public int getCodiceProdotto() {
        return codice_prodotto;
    }
    public int getQuantità() {
        return quantità;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) return true;
        if (obj == null || obj.getClass() != this.getClass()) return false;
        var that = (RichiestaAdHoc) obj;
        return this.id == that.getId();
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }


    public static RichiestaAdHoc createFromDB(ResultSet row) throws SQLException {
        return new RichiestaAdHoc(
                row.getInt(1),
                row.getInt(2),
                row.getInt(3));
    }

}
