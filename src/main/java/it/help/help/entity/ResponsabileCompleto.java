package it.help.help.entity;

import it.help.help.entity.*;

public class ResponsabileCompleto {
    private final Responsabile responsabile;
    private final Diocesi diocesi;

    public ResponsabileCompleto(Responsabile responsabile, Diocesi diocesi) {
        this.responsabile = responsabile;
        this.diocesi = diocesi;
    }

    public Responsabile getResponsabile() {
        return responsabile;
    }

    public Diocesi getDiocesi() {
        return diocesi;
    }

}
