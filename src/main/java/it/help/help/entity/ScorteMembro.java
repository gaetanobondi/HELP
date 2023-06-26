package it.help.help.entity;

import java.util.Objects;

public class ScorteMembro {
    private Scorte scorte;
    private Membro membro;
    private int id_nucleo;
    private int id_polo;
    private int id_diocesi;

    public ScorteMembro(Scorte scorte, Membro membro, int id_nucleo, int id_polo, int id_diocesi) {
        this.scorte = scorte;
        this.membro = membro;
        this.id_nucleo = id_nucleo;
        this.id_polo = id_polo;
        this.id_diocesi = id_diocesi;
    }

    public Scorte getScorte() {
        return scorte;
    }

    public Membro getMembro() {
        return membro;
    }
    public int getIdNucleo() {
        return id_nucleo;
    }
    public int getIdPolo() {
        return id_polo;
    }
    public int getIdDiocesi() {
        return id_diocesi;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ScorteMembro that = (ScorteMembro) o;
        return Objects.equals(scorte, that.scorte) && Objects.equals(membro, that.membro);
    }

    @Override
    public int hashCode() {
        return Objects.hash(scorte, membro);
    }
}

