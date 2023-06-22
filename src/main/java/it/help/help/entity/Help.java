package it.help.help.entity;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Objects;

public class Help {
    private int id;
    private String indirizzo;
    private int cellulare;

    public Help(int id, String indirizzo, int cellulare) {
        this.id = id;
        this.indirizzo = indirizzo;
        this.cellulare = cellulare;
    }

    public Help() { }

    public int getId() {
        return id;
    }
    public String getIndirizzo() {
        return indirizzo;
    }
    public int getCellulare() {
        return cellulare;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) return true;
        if (obj == null || obj.getClass() != this.getClass()) return false;
        var that = (Responsabile) obj;
        return this.id == that.getId();
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }


    public Help createFromDB(ResultSet row) throws SQLException {
        return new Help(
                row.getInt(1),
                row.getString(2),
                row.getInt(3));
    }

}
