package it.help.help.entity;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Objects;

public class SchemaDistribuzione {
    private int id;
    private int type;
    private int id_type;
    private int codice_prodotto;
    private int quantità;

    public SchemaDistribuzione(int id, int type, int id_type, int codice_prodotto, int quantità) {
        this.id = id;
        this.type = type;
        this.id_type = id_type;
        this.codice_prodotto = codice_prodotto;
        this.quantità = quantità;
    }

    public SchemaDistribuzione() { }

    public int getId() {
        return id;
    }
    public int getCodiceProdotto() {
        return codice_prodotto;
    }
    public int getType() {
        return type;
    }
    public int getIdType() {
        return id_type;
    }
    public int getQuantità() { return quantità; }


    @Override
    public boolean equals(Object obj) {
        if (obj == this) return true;
        if (obj == null || obj.getClass() != this.getClass()) return false;
        var that = (SchemaDistribuzione) obj;
        return this.id == that.getId();
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }


    public static SchemaDistribuzione createFromDB(ResultSet row) throws SQLException {
        return new SchemaDistribuzione(
                row.getInt(1),
                row.getInt(2),
                row.getInt(3),
                row.getInt(4),
                row.getInt(5));
    }

}
