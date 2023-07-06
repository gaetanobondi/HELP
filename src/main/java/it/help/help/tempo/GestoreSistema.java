package it.help.help.tempo;

import it.help.help.entity.*;
import it.help.help.utils.DBMS;
import java.time.LocalDate;
import java.time.Month;
import java.util.Random;
import java.util.*;

public class GestoreSistema {
    public void checkSospensionePoli() throws Exception {
        Polo[] listaPoli = DBMS.queryGetAllPoli();
        for (Polo polo : listaPoli) {
            if(DBMS.queryScaricoSuperioreDueMesi(polo.getId())) {
                // sospendo il polo
                DBMS.querySospendiPolo(polo.getId());
            }
        }
    }

    public boolean checkSchemiDistribuzione() throws Exception {
        return DBMS.queryCheckSchemiDistribuzione();
    }

    public void eliminaSchemiDistribuzione() throws Exception {
        DBMS.queryEliminaSchemiDistribuzione();
    }
    public void eliminaRichiesteAdHoc() throws Exception {
        DBMS.queryEliminaRichiesteAdHoc();
    }
    public void azzeraMagazzini() throws Exception {
        DBMS.queryAzzeraMagazzini();
    }
    public void azzeraScorte() throws Exception {
        DBMS.queryAzzeraScorte();
    }

    public void createPrevisioneDistribuzione() throws Exception {
        // Inizializza un insieme per tenere traccia dei membri unici
        Set<Membro> membriDiabetici = new HashSet<>();
        Set<Membro> membriIntollerantiLattosio = new HashSet<>();
        Set<Membro> membriCeliaci = new HashSet<>();
        Set<Membro> membriTutti = new HashSet<>();
        Set<Membro> membriSenzaBisogni = new HashSet<>();

        Membro[] listaMembri = DBMS.queryGetMembri();
        for (Membro membro : listaMembri) {
            // aggiungo ogni membro alla propria categoria
            if(membro.getCeliachia()) {
                membriCeliaci.add(membro);
            }
            if(membro.getDiabete()) {
                membriDiabetici.add(membro);
            }
            if(membro.getIntolleranzaLattosio()) {
                membriIntollerantiLattosio.add(membro);
            }
            if(!membro.getDiabete() && !membro.getCeliachia() && !membro.getIntolleranzaLattosio()) {
                membriSenzaBisogni.add(membro);
            }

            membriTutti.add(membro);
        }

        // adesso per ogni categoria controllo che ci siano almeno due alimenti
        int alimentiPerMembro = 10;
        Month currentMonth = LocalDate.now().getMonth();
        Donazione[] listaDonazioni = DBMS.queryGetDonazioniMeseCorrente(currentMonth.getValue());
        Set<Donazione> listaScorte = new HashSet<>();
        for (Donazione donazione : listaDonazioni) {
            listaScorte.add(donazione);
        }

        Iterator<Donazione> iterator = listaScorte.iterator();
        if(listaScorte != null) {
            while (iterator.hasNext()) {
                int totMembriVivere = 0;
                Donazione donazione = iterator.next();
                // ciclo per contare i membri che hanno diritto a ciascun vivere
                for (Membro membro : membriTutti) {
                    // per ogni membro devo verificare se può ritirare il vivere in base ai bisogni
                    Prodotto prodotto = DBMS.queryGetProdotto(donazione.getCodiceProdotto());
                    if((membro.getDiabete() && prodotto.getSenzaZucchero()) || !membro.getDiabete()) {
                        if((membro.getIntolleranzaLattosio() && prodotto.getSenzaLattosio()) || !membro.getIntolleranzaLattosio()) {
                            if((membro.getCeliachia() && prodotto.getSenzaGlutine() || !membro.getCeliachia())) {
                                totMembriVivere += 1;
                            }
                        }
                    }
                }

                // ciclo per distribuire i viveri
                if(totMembriVivere != 0) {
                    int quantitàMinima = alimentiPerMembro * totMembriVivere;
                    if(donazione.getQuantità() < quantitàMinima) {
                        Prodotto prodotto = DBMS.queryGetProdotto(donazione.getCodiceProdotto());
                        int dividedBy = 1;
                        if(prodotto.getSenzaZucchero()) {
                            Prodotto[] listaProdotti = DBMS.queryGetProdottiPer("senzaZucchero");
                            dividedBy += 1;
                        }
                        if(prodotto.getSenzaGlutine()) {
                            Prodotto[] listaProdotti = DBMS.queryGetProdottiPer("senzaGlutine");
                            dividedBy += 1;
                        }
                        if(prodotto.getSenzaLattosio()) {
                            Prodotto[] listaProdotti = DBMS.queryGetProdottiPer("senzaLattosio");
                            dividedBy += 1;
                        }

                        // divido in base alla categoria
                        int giustaQuantità = quantitàMinima / dividedBy;
                        if(prodotto.getSenzaZucchero()) {
                            Prodotto[] listaProdotti = DBMS.queryGetProdottiPer("senzaZucchero");
                            creaRichestaAdHoc(quantitàMinima, listaProdotti);
                        }
                        if(prodotto.getSenzaGlutine()) {
                            Prodotto[] listaProdotti = DBMS.queryGetProdottiPer("senzaGlutine");
                            creaRichestaAdHoc(quantitàMinima, listaProdotti);
                        }
                        if(prodotto.getSenzaLattosio()) {
                            Prodotto[] listaProdotti = DBMS.queryGetProdottiPer("senzaLattosio");
                            creaRichestaAdHoc(quantitàMinima, listaProdotti);
                        }
                    }
                }
            }
        } else {
            if(membriDiabetici.size() > 0) {
                int quantitàMinima = membriDiabetici.size() * alimentiPerMembro;
                System.out.println("Richieste perché non ci sono scorte:");
                // nessuna scorta presente
                Prodotto[] listaProdotti = DBMS.queryGetProdottiPer("senzaZucchero");
                creaRichestaAdHoc(quantitàMinima, listaProdotti);
            }

            if(membriCeliaci.size() > 0) {
                int quantitàMinima = membriCeliaci.size() * alimentiPerMembro;
                System.out.println("Richieste perché non ci sono scorte:");
                // nessuna scorta presente
                Prodotto[] listaProdotti = DBMS.queryGetProdottiPer("senzaGlutine");
                creaRichestaAdHoc(quantitàMinima, listaProdotti);
            }

            if(membriIntollerantiLattosio.size() > 0) {
                int quantitàMinima = membriIntollerantiLattosio.size() * alimentiPerMembro;
                System.out.println("Richieste perché non ci sono scorte:");
                // nessuna scorta presente
                Prodotto[] listaProdotti = DBMS.queryGetProdottiPer("senzaLattosio");
                creaRichestaAdHoc(quantitàMinima, listaProdotti);
            }

            if(membriSenzaBisogni.size() > 0) {
                int quantitàMinima = membriSenzaBisogni.size() * alimentiPerMembro;
                System.out.println("Richieste perché non ci sono scorte:");
                // nessuna scorta presente
                Prodotto[] listaProdotti = DBMS.queryGetProdotti();
                // Converti l'array in una lista
                List<Prodotto> listaProdottiList = new ArrayList<>(Arrays.asList(listaProdotti));
                creaRichestaAdHoc(quantitàMinima, listaProdotti);
            }
        }
    }

    public static List<List<Integer>> ottieniCombinazioni(int quantità, int[] range) {
        List<List<Integer>> result = new ArrayList<>();
        List<Integer> combination = new ArrayList<>();
        generaCombinazioni(quantità, range, 0, combination, result);
        return result;
    }

    private static void generaCombinazioni(int quantità, int[] range, int start, List<Integer> combination, List<List<Integer>> result) {
        if (quantità == 0) {
            result.add(new ArrayList<>(combination));
            return;
        }

        for (int i = start; i < range.length; i++) {
            if (quantità >= range[i]) {
                combination.add(range[i]);
                generaCombinazioni(quantità - range[i], range, i, combination, result);
                combination.remove(combination.size() - 1);
            }
        }
    }
    public void creaRichestaAdHoc(int quantitàMinima, Prodotto[] listaProdotti) throws Exception {
        // Converti l'array in una lista
        List<Prodotto> listaProdottiList = new ArrayList<>(Arrays.asList(listaProdotti));
        // Randomizza la lista
        Collections.shuffle(listaProdottiList);

        int[] coins = {25, 20, 10, 2, 5, 1, 15};

        List<List<Integer>> combinations = ottieniCombinazioni(quantitàMinima, coins);

        Random r = new Random();
        int i = r.nextInt(combinations.size());

        Prodotto lastProdotto = null;
        for (int combination : combinations.get(i)) {
            if (!listaProdottiList.isEmpty()) {
                lastProdotto = listaProdottiList.remove(0);
            }
            System.out.println("Richiesta ad-hoc: " + combination + " di prodotto con codice " + lastProdotto.getCodice());
            DBMS.querySalvaRichiesta(lastProdotto.getCodice(), combination);
        }
    }

    public void generazioneSchemiDistribuzione() throws Exception {
        // Inizializza un insieme per tenere traccia dei prodotti unici
        Set<Scorte> scorteDiabetici = new HashSet<>();
        Set<Scorte> scorteIntollerantiLattosio = new HashSet<>();
        Set<Scorte> scorteCeliaci = new HashSet<>();

        // Inizializza un insieme per tenere traccia dei membri unici
        Set<Membro> membriDiabetici = new HashSet<>();
        Set<Membro> membriIntollerantiLattosio = new HashSet<>();
        Set<Membro> membriCeliaci = new HashSet<>();

        Membro[] listaMembri = DBMS.queryGetMembri();
        for (Membro membro : listaMembri) {
            // aggiungo ogni membro alla propria categoria
            if(membro.getCeliachia()) {
                membriCeliaci.add(membro);
            }
            if(membro.getDiabete()) {
                membriDiabetici.add(membro);
            }
            if(membro.getIntolleranzaLattosio()) {
                membriIntollerantiLattosio.add(membro);
            }
        }

        HashMap<ScorteMembro, Scorte> membriCheHannoRitirato = new HashMap<>();

        Scorte[] listaScorte = null;

        Magazzino[] listaMagazziniHelp = DBMS.queryGetMagazzini(0, 1);
        for (Magazzino magazzino : listaMagazziniHelp) {
            if(magazzino.getCapienzaAttuale() > 0) {
                // se c'è qualcosa in magazzino controllo cosa è
                listaScorte = DBMS.queryGetScorte(magazzino.getId());
                for (Scorte scorte : listaScorte) {
                    Prodotto prodotto = DBMS.queryGetProdotto(scorte.getCodiceProdotto());
                    if (prodotto.getSenzaZucchero()) {
                        scorteDiabetici.add(scorte);
                    }
                    if (prodotto.getSenzaLattosio()) {
                        scorteIntollerantiLattosio.add(scorte);
                    }
                    if (prodotto.getSenzaGlutine()) {
                        scorteCeliaci.add(scorte);
                    }

                    // scortePerTutti.add(scorte);

                    int totMembriVivere = 0;
                    // Scorte scorte = iterator.next();
                    // ciclo per contare i membri che hanno diritto a ciascun vivere
                    for (Membro membro : listaMembri) {
                        // per ogni membro devo verificare se può ritirare il vivere in base ai bisogni
                        // Prodotto prodotto = DBMS.queryGetProdotto(scorte.getCodiceProdotto());
                        if((membro.getDiabete() && prodotto.getSenzaZucchero()) || !membro.getDiabete()) {
                            if((membro.getIntolleranzaLattosio() && prodotto.getSenzaLattosio()) || !membro.getIntolleranzaLattosio()) {
                                if((membro.getCeliachia() && prodotto.getSenzaGlutine() || !membro.getCeliachia())) {
                                    Nucleo nucleo = DBMS.queryGetNucleo(membro.getIdNucleo());
                                    Polo polo = DBMS.queryGetPolo(nucleo.getIdPolo());
                                    Diocesi diocesi = DBMS.queryGetDiocesi(polo.getId_diocesi());
                                    // controllo se posso distribuire
                                    if(!polo.getStato_sospensione() && diocesi.getStato_account()) {
                                        totMembriVivere += 1;
                                    }
                                }
                            }
                        }
                    }
                    System.out.println(totMembriVivere + " membri devono ritirare il prodotto " + scorte.getCodiceProdotto());

                    // ciclo per distribuire i viveri
                    if(totMembriVivere != 0) {
                        int giustaQuantità = (int) Math.ceil((double)scorte.getQuantità() / (double)totMembriVivere);
                        Scorte scortaMembro = new Scorte(scorte.getId(), scorte.getCodiceProdotto(), scorte.getIdMagazzino(), giustaQuantità, scorte.getScadenzaProdotto());
                        for (Membro membro : listaMembri) {
                            // per ogni membro devo verificare se può ritirare il vivere in base ai bisogni
                            // Prodotto prodotto = DBMS.queryGetProdotto(scorte.getCodiceProdotto());
                            if((membro.getDiabete() && prodotto.getSenzaZucchero()) || !membro.getDiabete()) {
                                if((membro.getIntolleranzaLattosio() && prodotto.getSenzaLattosio()) || !membro.getIntolleranzaLattosio()) {
                                    if((membro.getCeliachia() && prodotto.getSenzaGlutine() || !membro.getCeliachia())) {
                                        if(scorte.getQuantità() >= giustaQuantità) {
                                            scorte.setQuantità(scorte.getQuantità() - giustaQuantità);
                                            Nucleo nucleo = DBMS.queryGetNucleo(membro.getIdNucleo());
                                            Polo polo = DBMS.queryGetPolo(nucleo.getIdPolo());
                                            Diocesi diocesi = DBMS.queryGetDiocesi(polo.getId_diocesi());
                                            // controllo se posso distribuire
                                            if(!polo.getStato_sospensione() && diocesi.getStato_account()) {
                                                ScorteMembro key = new ScorteMembro(scortaMembro, membro, membro.getIdNucleo(), polo.getId(), diocesi.getId());
                                                membriCheHannoRitirato.put(key, scortaMembro);
                                                System.out.println(membro.getNome() + " ha ritirato " + scortaMembro.getQuantità() + " di " + scortaMembro.getCodiceProdotto());
                                                System.out.println("Totale scorte di Help: " + scorteTotali(listaMagazziniHelp, listaScorte));
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }


        System.out.println("Totale scorte di Help: " + scorteTotali(listaMagazziniHelp, listaScorte));

        // iterando membri che hanno ritirato posso risalire alle quantità da assegnare a ciascuna diocesi, polo, nucleo
        // Creazione della mappa per memorizzare gli ID e le scorte
        Map<Integer, ArrayList<Scorte>> scortePerIdNucleo = new HashMap<>();
        Map<Integer, ArrayList<Scorte>> scortePerIdPolo = new HashMap<>();
        Map<Integer, ArrayList<Scorte>> scortePerIdDiocesi = new HashMap<>();
        for (HashMap.Entry<ScorteMembro, Scorte> entry : membriCheHannoRitirato.entrySet()) {
            ScorteMembro key = entry.getKey();

            Scorte scorte = key.getScorte();
            Membro membro = key.getMembro();
            int id_nucleo = key.getIdNucleo();
            int id_polo = key.getIdPolo();
            int id_diocesi = key.getIdDiocesi();

            // ottengo il nucleo
            Nucleo nucleo = DBMS.queryGetNucleo(membro.getIdNucleo());
            // Aggiunta degli IDNucleo e delle scorte alla mappa
            aggiungiScorte(scortePerIdNucleo, id_nucleo, scorte);
            aggiungiScorte(scortePerIdPolo, id_polo, scorte);
            aggiungiScorte(scortePerIdDiocesi, id_diocesi, scorte);
        }

        for (Map.Entry<Integer, ArrayList<Scorte>> entry : scortePerIdDiocesi.entrySet()) {
            int idDiocesi = entry.getKey();
            ArrayList<Scorte> scorteDiocesi = entry.getValue();

            System.out.println("ID Diocesi: " + idDiocesi);
            Map<Integer, Integer> sommaPerCodiceProdotto = new HashMap<>();
            for (Scorte scorte : scorteDiocesi) {
                int codiceProdotto = scorte.getCodiceProdotto();
                int quantita = scorte.getQuantità();

                if (sommaPerCodiceProdotto.containsKey(codiceProdotto)) {
                    int sommaQuantita = sommaPerCodiceProdotto.get(codiceProdotto);
                    sommaPerCodiceProdotto.put(codiceProdotto, sommaQuantita + quantita);
                } else {
                    sommaPerCodiceProdotto.put(codiceProdotto, quantita);
                }
            }

            // Stampa delle somme per ciascun codice prodotto
            for (Map.Entry<Integer, Integer> sumEntry : sommaPerCodiceProdotto.entrySet()) {
                int codiceProdotto = sumEntry.getKey();
                int sommaQuantita = sumEntry.getValue();
                System.out.println("Codice Prodotto: " + codiceProdotto + ", Somma Quantità: " + sommaQuantita);
                DBMS.querySalvaSchemaDistribuzione(0, idDiocesi, codiceProdotto, sommaQuantita);
            }
            System.out.println();
        }

        for (Map.Entry<Integer, ArrayList<Scorte>> entry : scortePerIdPolo.entrySet()) {
            int idPolo = entry.getKey();
            ArrayList<Scorte> scortePolo = entry.getValue();

            System.out.println("ID Polo: " + idPolo);
            Map<Integer, Integer> sommaPerCodiceProdotto = new HashMap<>();
            for (Scorte scorte : scortePolo) {
                int codiceProdotto = scorte.getCodiceProdotto();
                int quantita = scorte.getQuantità();

                if (sommaPerCodiceProdotto.containsKey(codiceProdotto)) {
                    int sommaQuantita = sommaPerCodiceProdotto.get(codiceProdotto);
                    sommaPerCodiceProdotto.put(codiceProdotto, sommaQuantita + quantita);
                } else {
                    sommaPerCodiceProdotto.put(codiceProdotto, quantita);
                }
            }

            // Stampa delle somme per ciascun codice prodotto
            for (Map.Entry<Integer, Integer> sumEntry : sommaPerCodiceProdotto.entrySet()) {
                int codiceProdotto = sumEntry.getKey();
                int sommaQuantita = sumEntry.getValue();
                System.out.println("Codice Prodotto: " + codiceProdotto + ", Somma Quantità: " + sommaQuantita);
                DBMS.querySalvaSchemaDistribuzione(1, idPolo, codiceProdotto, sommaQuantita);
            }
            System.out.println();

            Polo[] listaPoli = DBMS.queryGetAllPoli();
            for (Polo polo : listaPoli) {
                distribuzioneMagazzinoPolo(polo.getId());
            }
        }

        // Iterazione sulle scorte di ciascun nucleo
        for (Map.Entry<Integer, ArrayList<Scorte>> entry : scortePerIdNucleo.entrySet()) {
            int idNucleo = entry.getKey();
            ArrayList<Scorte> scorteNucleo = entry.getValue();

            System.out.println("ID Nucleo: " + idNucleo);
            Map<Integer, Integer> sommaPerCodiceProdotto = new HashMap<>();
            for (Scorte scorte : scorteNucleo) {
                int codiceProdotto = scorte.getCodiceProdotto();
                int quantita = scorte.getQuantità();

                if (sommaPerCodiceProdotto.containsKey(codiceProdotto)) {
                    int sommaQuantita = sommaPerCodiceProdotto.get(codiceProdotto);
                    sommaPerCodiceProdotto.put(codiceProdotto, sommaQuantita + quantita);
                } else {
                    sommaPerCodiceProdotto.put(codiceProdotto, quantita);
                }
            }

            // Stampa delle somme per ciascun codice prodotto
            for (Map.Entry<Integer, Integer> sumEntry : sommaPerCodiceProdotto.entrySet()) {
                int codiceProdotto = sumEntry.getKey();
                int sommaQuantita = sumEntry.getValue();
                System.out.println("Codice Prodotto: " + codiceProdotto + ", Somma Quantità: " + sommaQuantita);
                DBMS.querySalvaSchemaDistribuzione(2, idNucleo, codiceProdotto, sommaQuantita);
            }
            System.out.println();
        }
    }

    private void distribuzioneMagazzinoPolo(int id_polo) throws Exception {
        // Inizializza un insieme per tenere traccia dei prodotti unici
        Set<Scorte> scorteDiabetici = new HashSet<>();
        Set<Scorte> scorteIntollerantiLattosio = new HashSet<>();
        Set<Scorte> scorteCeliaci = new HashSet<>();
        Set<Scorte> scortePerTutti = new HashSet<>();

        int scorteTotali = 0;
        Scorte[] listaScorte = null;

        boolean atLeastOne = false;
        Magazzino[] listaMagazziniPolo = DBMS.queryGetMagazzini(2, id_polo);
        for (Magazzino magazzino : listaMagazziniPolo) {
            if(magazzino.getCapienzaAttuale() > 0) {
                atLeastOne = true;
                // se c'è qualcosa in magazzino controllo cosa è
                listaScorte = DBMS.queryGetScorte(magazzino.getId());
                for (Scorte scorte : listaScorte) {
                    scorteTotali += scorte.getQuantità();
                    Prodotto prodotto = DBMS.queryGetProdotto(scorte.getCodiceProdotto());
                    if (prodotto.getSenzaZucchero()) {
                        scorteDiabetici.add(scorte);
                    }
                    if (prodotto.getSenzaLattosio()) {
                        scorteIntollerantiLattosio.add(scorte);
                    }
                    if (prodotto.getSenzaGlutine()) {
                        scorteCeliaci.add(scorte);
                    }

                    scortePerTutti.add(scorte);
                }
            }
        }

        if(atLeastOne) {
            // Inizializza un insieme per tenere traccia dei membri unici
            Set<Membro> membriDiabetici = new HashSet<>();
            Set<Membro> membriIntollerantiLattosio = new HashSet<>();
            Set<Membro> membriCeliaci = new HashSet<>();
            Set<Membro> membriTutti = new HashSet<>();

            int totMembri = 0;

            Nucleo[] listaNuclei = DBMS.queryGetNuclei(id_polo);
            int countNuclei = listaNuclei.length;
            for (Nucleo nucleo : listaNuclei) {
                Membro[] listaMembri = DBMS.queryGetMembri(nucleo.getId());
                totMembri += listaMembri.length;
                for (Membro membro : listaMembri) {
                    // aggiungo ogni membro alla propria categoria
                    if(membro.getCeliachia()) {
                        membriCeliaci.add(membro);
                    }
                    if(membro.getDiabete()) {
                        membriDiabetici.add(membro);
                    }
                    if(membro.getIntolleranzaLattosio()) {
                        membriIntollerantiLattosio.add(membro);
                    }
                    membriTutti.add(membro);
                }
            }

            HashMap<ScorteMembro, Scorte> membriCheHannoRitirato = new HashMap<>();

            System.out.println("Totale scorte del polo " + id_polo + ": " + scorteTotali(listaMagazziniPolo, listaScorte));

            Iterator<Scorte> iterator = scortePerTutti.iterator();
            while (iterator.hasNext()) {
                int totMembriVivere = 0;
                Scorte scorte = iterator.next();
                // ciclo per contare i membri che hanno diritto a ciascun vivere
                for (Membro membro : membriTutti) {
                    // per ogni membro devo verificare se può ritirare il vivere in base ai bisogni
                    Prodotto prodotto = DBMS.queryGetProdotto(scorte.getCodiceProdotto());
                    if((membro.getDiabete() && prodotto.getSenzaZucchero()) || !membro.getDiabete()) {
                        if((membro.getIntolleranzaLattosio() && prodotto.getSenzaLattosio()) || !membro.getIntolleranzaLattosio()) {
                            if((membro.getCeliachia() && prodotto.getSenzaGlutine() || !membro.getCeliachia())) {
                                totMembriVivere += 1;
                            }
                        }
                    }
                }
                System.out.println(totMembriVivere + " membri devono ritirare il prodotto " + scorte.getCodiceProdotto());

                // ciclo per distribuire i viveri
                if(totMembriVivere != 0) {
                    int giustaQuantità = (int) Math.ceil((double)scorte.getQuantità() / (double)totMembriVivere);
                    Scorte scortaMembro = new Scorte(scorte.getId(), scorte.getCodiceProdotto(), scorte.getIdMagazzino(), giustaQuantità, scorte.getScadenzaProdotto());
                    for (Membro membro : membriTutti) {
                        // per ogni membro devo verificare se può ritirare il vivere in base ai bisogni
                        Prodotto prodotto = DBMS.queryGetProdotto(scorte.getCodiceProdotto());
                        if((membro.getDiabete() && prodotto.getSenzaZucchero()) || !membro.getDiabete()) {
                            if((membro.getIntolleranzaLattosio() && prodotto.getSenzaLattosio()) || !membro.getIntolleranzaLattosio()) {
                                if((membro.getCeliachia() && prodotto.getSenzaGlutine() || !membro.getCeliachia())) {
                                    if(scorte.getQuantità() >= giustaQuantità) {
                                        scorte.setQuantità(scorte.getQuantità() - giustaQuantità);
                                        Nucleo nucleo = DBMS.queryGetNucleo(membro.getIdNucleo());
                                        Polo polo = DBMS.queryGetPolo(nucleo.getIdPolo());
                                        Diocesi diocesi = DBMS.queryGetDiocesi(polo.getId_diocesi());
                                        ScorteMembro key = new ScorteMembro(scortaMembro, membro, membro.getIdNucleo(), polo.getId(), diocesi.getId());
                                        membriCheHannoRitirato.put(key, scortaMembro);
                                        System.out.println(membro.getNome() + " ha ritirato " + scortaMembro.getQuantità() + " di " + scortaMembro.getCodiceProdotto());
                                        System.out.println("Totale scorte del polo " + id_polo + ": " + scorteTotali(listaMagazziniPolo, listaScorte));
                                    }
                                }
                            }
                        }
                    }

                    // Creazione della mappa per memorizzare gli ID e le scorte
                    Map<Integer, ArrayList<Scorte>> scortePerIdNucleo = new HashMap<>();

                    for (HashMap.Entry<ScorteMembro, Scorte> entry : membriCheHannoRitirato.entrySet()) {
                        ScorteMembro key = entry.getKey();
                        Scorte scortaMembro2 = entry.getValue();

                        // Esegui le operazioni desiderate con la chiave e il valore
                        // Ad esempio, puoi ottenere lo Scorte e il Membro dalla chiave:
                        Scorte scorte2 = key.getScorte();
                        Membro membro = key.getMembro();
                        int id_nucleo = key.getIdNucleo();
                        int id_polo2 = key.getIdPolo();
                        int id_diocesi = key.getIdDiocesi();

                        // E puoi fare qualcosa con lo Scorte associato:
                        int quantità = scortaMembro2.getQuantità();
                        int codiceProdotto = scortaMembro2.getCodiceProdotto();

                        // Puoi anche fare qualcosa con lo Scorte e il Membro originali:
                        // int quantitàOriginale = scorte.getQuantità();
                        // int codiceProdottoOriginale = scorte.getCodiceProdotto();

                        // Esegui le operazioni desiderate con le variabili ottenute sopra

                        // ottengo il nucleo
                        Nucleo nucleo = DBMS.queryGetNucleo(membro.getIdNucleo());
                        // Aggiunta degli IDNucleo e delle scorte alla mappa
                        aggiungiScorte(scortePerIdNucleo, id_nucleo, scorte2);
                    }

                    // Iterazione sulle scorte di ciascun nucleo
                    for (Map.Entry<Integer, ArrayList<Scorte>> entry : scortePerIdNucleo.entrySet()) {
                        int idNucleo = entry.getKey();
                        ArrayList<Scorte> scorteNucleo = entry.getValue();

                        System.out.println("ID Nucleo: " + idNucleo);
                        // for (Scorte scorte : scorteNucleo) {
                        // System.out.println("Scorte: " + scorte.getQuantità());
                        // }
                        Map<Integer, Integer> sommaPerCodiceProdotto = new HashMap<>();
                        for (Scorte scorte2 : scorteNucleo) {
                            int codiceProdotto = scorte2.getCodiceProdotto();
                            int quantita = scorte2.getQuantità();

                            if (sommaPerCodiceProdotto.containsKey(codiceProdotto)) {
                                int sommaQuantita = sommaPerCodiceProdotto.get(codiceProdotto);
                                sommaPerCodiceProdotto.put(codiceProdotto, sommaQuantita + quantita);
                            } else {
                                sommaPerCodiceProdotto.put(codiceProdotto, quantita);
                            }
                        }

                        // Stampa delle somme per ciascun codice prodotto
                        for (Map.Entry<Integer, Integer> sumEntry : sommaPerCodiceProdotto.entrySet()) {
                            int codiceProdotto = sumEntry.getKey();
                            int sommaQuantita = sumEntry.getValue();
                            System.out.println("Codice Prodotto: " + codiceProdotto + ", Somma Quantità: " + sommaQuantita);
                            DBMS.querySalvaSchemaDistribuzione(2, idNucleo, codiceProdotto, sommaQuantita);
                        }
                        System.out.println();
                    }
                }
            }
        } else {
            System.out.println("Il polo " + id_polo + " non ha rimanenze di magazzino.");
        }
    }


    // Metodo di utilità per aggiungere le scorte a un ID esistente o crearne uno nuovo
    private static void aggiungiScorte(Map<Integer, ArrayList<Scorte>> map, int id, Scorte scorte) {
        if (map.containsKey(id)) {
            map.get(id).add(scorte);
        } else {
            ArrayList<Scorte> listaScorte = new ArrayList<>();
            listaScorte.add(scorte);
            map.put(id, listaScorte);
        }
    }

    public int scorteTotali(Magazzino[] magazzini, Scorte[] allScorte) throws Exception {
        int scorteTotali = 0;

        Magazzino[] listaMagazziniHelp = magazzini;
        for (Magazzino magazzino : listaMagazziniHelp) {
            if(magazzino.getCapienzaAttuale() > 0) {
                // se c'è qualcosa in magazzino controllo cosa è
                Scorte[] listaScorte = allScorte;
                for (Scorte scorte : listaScorte) {
                    scorteTotali += scorte.getQuantità();
                }
            }
        }

        return scorteTotali;
    }

}
