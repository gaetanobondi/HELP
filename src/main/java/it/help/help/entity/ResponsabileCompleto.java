package it.help.help.entity;

import it.help.help.entity.*;

public class ResponsabileCompleto {
    private final Responsabile responsabile;
    private final Diocesi diocesi;
    private final AziendaPartner aziendaPartner;

    public ResponsabileCompleto(Responsabile responsabile, Diocesi diocesi) {
        this.responsabile = responsabile;
        this.diocesi = diocesi;
        this.aziendaPartner = null;
    }

    public ResponsabileCompleto(Responsabile responsabile, AziendaPartner aziendaPartner) {
        this.responsabile = responsabile;
        this.aziendaPartner = aziendaPartner;
        this.diocesi = null;
    }

    public Responsabile getResponsabile() {
        return responsabile;
    }

    public Diocesi getDiocesi() {
        return diocesi;
    }

    public AziendaPartner getAziendaPartner() {
        return aziendaPartner;
    }

}
