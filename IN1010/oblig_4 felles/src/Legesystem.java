import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.NoSuchElementException;
import java.util.Scanner;

public class Legesystem {

    protected Stabel<Resept> resepter = new Stabel<>();
    protected IndeksertListe<Pasient> pasienter = new IndeksertListe<>();
    protected Prioritetskoe<Lege> leger = new Prioritetskoe<>();
    protected IndeksertListe<Legemiddel> legemidler = new IndeksertListe<>();

    public void lesFraFil(String filnavn) throws FileNotFoundException, UlovligUtskrift, NoSuchElementException {

        File data = new File(filnavn);
        Scanner scanner = new Scanner(data);

        /* LESER INN ALLE SEKSJONENE I TXT-FILEN */

        Koe<String> pasienterTxt = new Koe<>();
        Koe<String> legemidlerTxt = new Koe<>();
        Koe<String> legerTxt = new Koe<>();
        Koe<String> resepterTxt = new Koe<>();

        // kjoerer saa lenge filen har flere linjer
        while (scanner.hasNextLine()) {
            
            String linje = scanner.nextLine(); // lagrer foerste linje

            if (linje.contains("Pasient")) {
                linje = scanner.nextLine();
                while (!(linje.contains("#"))) {
                    pasienterTxt.leggTil(linje);
                    if (scanner.hasNextLine()) {
                        linje = scanner.nextLine(); // gaar bare videre hvis filen har flere linjer
                    } else {
                        break;
                    }
                }
            }

            if (linje.contains("Legemidler")) {
                linje = scanner.nextLine();
                while (!(linje.contains("#"))) {
                    legemidlerTxt.leggTil(linje);
                    if (scanner.hasNextLine()) {
                        linje = scanner.nextLine();
                    } else {
                        break;
                    }
                }
            }

            if (linje.contains("Leger")) {
                linje = scanner.nextLine();
                while (!(linje.contains("#"))) {
                    legerTxt.leggTil(linje);
                    if (scanner.hasNextLine()) {
                        linje = scanner.nextLine();
                    } else {
                        break;
                    }
                }
            }

            if (linje.contains("Resepter")) {
                linje = scanner.nextLine();
                while (!(linje.contains("#"))) {
                    resepterTxt.leggTil(linje);
                    if (scanner.hasNextLine()) {
                        linje = scanner.nextLine();
                    } else {
                        break;
                    }
                }
            }
        }

        for (String pasient_linje : pasienterTxt) {
            String[] info = pasient_linje.split(",");
            pasienter.leggTil(new Pasient(info[0], info[1]));
        }

        for (String legemiddel_linje : legemidlerTxt) {
            if (legemiddel_linje.contains("narkotisk")) {
                String[] info = legemiddel_linje.split(",");
                String navn = info[0];
                double virkestoff = Double.parseDouble(info[3]);
                int pris = Integer.parseInt(info[2]);
                int styrke = Integer.parseInt(info[4]);
                legemidler.leggTil(new Narkotisk(navn, virkestoff, pris, styrke));
            }

            if (legemiddel_linje.contains("vanedannende")) {
                String[] info = legemiddel_linje.split(",");
                String navn = info[0];
                double virkestoff = Double.parseDouble(info[3]);
                int pris = Integer.parseInt(info[2]);
                int styrke = Integer.parseInt(info[4]);
                legemidler.leggTil(new Vanedannende(navn, virkestoff, pris, styrke));
            }

            if (legemiddel_linje.contains("vanlig")) {
                String[] info = legemiddel_linje.split(",");
                String navn = info[0];
                double virkestoff = Double.parseDouble(info[3]);
                int pris = Integer.parseInt(info[2]);
                legemidler.leggTil(new Vanlig(navn, virkestoff, pris));
            }
        }

        for (String lege_linje : legerTxt) {
            String[] info = lege_linje.split(",");
            int takk_for_mellomrom_bitches = Integer.parseInt(info[1].trim());
            if (takk_for_mellomrom_bitches == 0) { // maatte legge til mellomrom fordi dere suger
                leger.leggTil(new Lege(info[0]));
            } else {
                leger.leggTil(new Spesialist(info[0], info[1]));  
            }  
        }

        for (String resept_linje : resepterTxt) {
            if (resept_linje.contains("hvit")) {
                String[] info = resept_linje.split(",");
                int legemiddelNr = Integer.parseInt(info[0]);
                int pasientID = Integer.parseInt(info[2]);
                int reit = Integer.parseInt(info[4]);
                String legenavn = info[1];
                Legemiddel lm = legemidler.hent(legemiddelNr-1);
                Pasient pasient = pasienter.hent(pasientID-1);
                boolean skrevetUt = false;

                for (Lege lege : leger) {
                    if (lege.hentNavn().equals(legenavn)) {
                        lege.skrivHvitResept(lm, pasient, reit);
                        resepter.leggTil(new HvitResept(lm, lege, pasient, reit));
                        skrevetUt = true;
                    }
                }

                if (skrevetUt == false) {
                    throw new NoSuchElementException("Legen " + legenavn + " finnes ikke.");
                }
            }

            if (resept_linje.contains("blaa")) {
                String[] info = resept_linje.split(",");
                int legemiddelNr = Integer.parseInt(info[0]);
                int pasientID = Integer.parseInt(info[2]);
                int reit = Integer.parseInt(info[4]);
                String legenavn = info[1];
                Legemiddel lm = legemidler.hent(legemiddelNr-1);
                Pasient pasient = pasienter.hent(pasientID-1);
                boolean skrevetUt = false;

                for (Lege lege : leger) {
                    if (lege.hentNavn().equals(legenavn)) {
                        lege.skrivBlaaResept(lm, pasient, reit);
                        resepter.leggTil(new BlaaResept(lm, lege, pasient, reit));
                        skrevetUt = true;
                    }
                }

                if (skrevetUt == false) {
                    throw new NoSuchElementException("Legen " + legenavn + " finnes ikke.");
                }

            }

            if (resept_linje.contains("militaer")) {
                String[] info = resept_linje.split(",");
                int legemiddelNr = Integer.parseInt(info[0]);
                int pasientID = Integer.parseInt(info[2]);
                String legenavn = info[1];
                Legemiddel lm = legemidler.hent(legemiddelNr-1);
                Pasient pasient = pasienter.hent(pasientID-1);
                boolean skrevetUt = false;

                for (Lege lege : leger) {
                    if (lege.hentNavn().equals(legenavn)) {
                        lege.skrivMilResept(lm, pasient);
                        resepter.leggTil(new MilResept(lm, lege, pasient));
                        skrevetUt = true;
                    }
                }

                if (skrevetUt == false) {
                    throw new NoSuchElementException("Legen " + legenavn + " finnes ikke.");
                }

            }

            if (resept_linje.contains(",p,")) {
                String[] info = resept_linje.split(",");
                int legemiddelNr = Integer.parseInt(info[0]);
                int pasientID = Integer.parseInt(info[2]);
                int reit = Integer.parseInt(info[4]);
                String legenavn = info[1];
                Legemiddel lm = legemidler.hent(legemiddelNr-1);
                Pasient pasient = pasienter.hent(pasientID-1);
                boolean skrevetUt = false;

                for (Lege lege : leger) {
                    if (lege.hentNavn().equals(legenavn)) {
                        lege.skrivPResept(lm, pasient, reit);
                        resepter.leggTil(new PResept(lm, lege, pasient, reit));
                        skrevetUt = true;
                    }
                }

                if (skrevetUt == false) {
                    throw new NoSuchElementException("Legen " + legenavn + " finnes ikke.");
                }

            }
            scanner.close();
        }
    }

    private Scanner tastatur = new Scanner(System.in);

    public void kommandoer() {
        System.out.println("******** LEGESYSTEM ********");
        System.out.println("1. Skriv ut fullstendig oversikt over pasienter");
        System.out.println("2. Skriv ut fullstendig oversikt over leger");
        System.out.println("3. Skriv ut fullstendig oversikt over legemidler");
        System.out.println("4. Skriv ut fullstendig oversikt over resepter");
        System.out.println("5. Legg til ny pasient");
        System.out.println("6. Legg til ny lege");
        System.out.println("7. Legg til ny legemiddel");
        System.out.println("8. Legg til ny resept");
        System.out.println("9. Legg til fra fil");
        System.out.println("10. Bruke en resept fra listen til en pasient");
        System.out.println("11. Skriv ut statistikk");
        System.out.println("12. Skriv alle data til .txt-fil");
        System.out.println("0. Avslutt");

        System.out.print("\nSkriv inn kommando (tall):   ");
    }

    public void kommandoloekke() throws UlovligUtskrift, FileNotFoundException {
        int inputFraBruker = -1;
    
        while(inputFraBruker != 0){
            if(inputFraBruker == 1){
            //print ut fullstendig oversikt over pasienter
                if (pasienter.stoerrelse() == 0) {
                    System.out.println("Det er ingen pasienter i systemet ennaa.");
                } else {
                    System.out.println(pasienter);
                }

            //print ut fullstendig oversikt over leger
            } else if (inputFraBruker == 2){
                if (leger.stoerrelse() == 0) {
                    System.out.println("Det er ingen leger i systemet ennaa.");
                } else {
                    System.out.println(leger);
                }

            //print ut fullstendig oversikt over legemidler
            } else if (inputFraBruker == 3){
                if (legemidler.stoerrelse() == 0) {
                    System.out.println("Det er ingen legemidler i systemet ennaa.");
                } else {
                    System.out.println(legemidler);
                }

            //print ut fullstendig oversikt over resepter
            } else if (inputFraBruker == 4){
                if (resepter.stoerrelse() == 0) {
                    System.out.println("Det er ingen resepter i systemet ennaa.");
                } else {
                    System.out.println(resepter);
                }
            
            // legg til eller opprett pasient
            } else if (inputFraBruker == 5){
                System.out.print("\nSkriv inn pasientens fulle navn:    ");
                String pasientnavn = tastatur.nextLine();
                System.out.print("Skriv inn pasientens personnummer:    ");
                String personnr = tastatur.nextLine();

                pasienter.leggTil(new Pasient(pasientnavn, personnr));

                System.out.println("\n... La til pasienten " + pasientnavn + ".");
                
            // legg til eller opprett lege
            } else if (inputFraBruker == 6){
                System.out.print("\nSkriv inn legens navn:    ");
                String legenavn = tastatur.nextLine();
                System.out.print("Er legen spesialist? (j/n)   ");
                String svar = tastatur.nextLine();

                if (svar.equals("j")){
                    System.out.print("Skriv inn legens kontrollID:   ");
                    String kontrollID = tastatur.nextLine();
                    leger.leggTil(new Spesialist(legenavn, kontrollID));
                }
                else {
                    leger.leggTil(new Lege(legenavn));
                }

                System.out.println("\n... La til legen " + legenavn + ".");

            // legg til eller opprett legemiddel
            } else if (inputFraBruker == 7) {
                System.out.print("Hva slags legemiddel er det?\n1. Narkotisk\n2. Vanedannende\n3. Vanlig\nSkriv inn et tall:    ");
                String type = tastatur.nextLine();
                System.out.print("Skriv inn legemiddelnavn:   ");
                String legemiddelnavn = tastatur.nextLine();
                System.out.print("Skriv inn virkestoff (mg):   ");
                String virkestoffString = tastatur.nextLine();
                Double virkestoff = null;
                
                try {
                    virkestoff = Double.parseDouble(virkestoffString);
                } catch (Exception e) { 
                    System.err.println(virkestoffString + " er ikke et gyldig flyttall. Proev igjen.\n");
                } 
                
                if (virkestoff == null) {
                    kommandoloekke();
                }

                System.out.print("Skriv inn pris (NOK):   ");
                int pris = Integer.parseInt(tastatur.nextLine());

                // Narkotisk
                if (type.equals("1")){
                    System.out.print("Skriv inn narkotisk styrke:    ");
                    int styrke = Integer.parseInt(tastatur.nextLine());

                    legemidler.leggTil(new Narkotisk(legemiddelnavn, virkestoff, pris, styrke));
                    System.out.println("\n... La til legemiddelet " + legemiddelnavn + ".");
                }
                // Vanedannende
                else if (type.equals("2")){
                    System.out.print("Skriv inn narkotisk styrke:    ");
                    int styrke = Integer.parseInt(tastatur.nextLine());

                    legemidler.leggTil(new Vanedannende(legemiddelnavn, virkestoff, pris, styrke));
                    System.out.println("\n... La til legemiddelet " + legemiddelnavn + ".");
                }
                // Vanlig legemiddel
                else if (type.equals("3")){
                    legemidler.leggTil(new Vanlig(legemiddelnavn, virkestoff, pris));
                    System.out.println("\n... La til legemiddelet " + legemiddelnavn + ".");
                }

                else {
                    System.out.println(type + " er ikke et gyldig valg. Proev igjen.");
                    kommandoer(); // viser listen med kommandoer igjen (hovedmeny)
                }

            // legg til eller opprett resept
            } else if(inputFraBruker == 8){
                spoerOmReseptinfo(); // sjekker detaljene for hver 
            
            } else if (inputFraBruker == 9) {
                System.out.print("Skriv inn filnavn:   ");
                String brukervalg = tastatur.nextLine();
                lesFraFil(brukervalg);
                System.out.println("\n... La inn elementer fra fil: " + brukervalg);

            // bruke gitt resept fra liste til gitt pasient
            } else if (inputFraBruker == 10) {
                Pasient minPasient;

                int teller = 1;
                for (Pasient pasient : pasienter) {
                    System.out.println(teller + ". " + pasient.enkelString());
                    teller++;
                }

                System.out.print("Skriv inn et tall:   ");
                int pasientvalg = Integer.parseInt(tastatur.nextLine());
                minPasient = pasienter.hent(pasientvalg-1);

                if (minPasient.hentReseptliste().stoerrelse() == 0) {
                    System.out.println(minPasient.hentNavn() + " har ingen resepter.");
                } else {

                    System.out.println("Valgt pasient: " + minPasient.enkelString() + "\n");

                    // velg resept
                    System.out.println("Hvilken resept vil du bruke?");
                    System.out.println(pasienter.hent(pasientvalg - 1).reseptlisteString());
                    
                    System.out.print("Skriv inn tall:   ");
                    int reseptvalg = Integer.parseInt(tastatur.nextLine());

                    Resept minResept = minPasient.hentReseptliste().hent(reseptvalg-1);
                    if (minResept.hentReit() > 0) {
                        minResept.bruk();
                        System.out.println("\n... Du har naa brukt resepten paa " + minResept.hentLegemiddel().hentNavn()
                        + " for pasient " + minPasient.hentNavn() + ". Resepten har " + minResept.hentReit() + 
                        " reit igjen.");
                    } else {
                        System.out.println("Kunne ikke bruke resept paa " + minResept.hentLegemiddel().hentNavn() + " (ingen reit igjen)");
                    }
                }

            } else if (inputFraBruker == 11) {
                System.out.println("1. Vis resepter paa vanedannende legemidler");
                System.out.println("2. Vis resepter paa narkotiske legemidler");
                System.out.println("3. Vis oversikt over mulig misbruk av narkotika");

                System.out.print("\nSkriv inn et tall:   ");
                int input = Integer.parseInt(tastatur.nextLine());

                if (input == 1) {
                    int teller = 0;
                    for (Resept resept : resepter) {
                        
                        if (resept.hentLegemiddel() instanceof Vanedannende) {
                            teller++;
                        }
                    }
                    System.out.println("Det er totalt " + teller + " resepter paa vanedannende legemidler i legesystemet.");
                } else if (input == 2) {
                    int teller = 0;
                    for (Resept resept : resepter) {
                        
                        if (resept.hentLegemiddel() instanceof Narkotisk) {
                            teller++;
                        }
                    }
                    System.out.println("Det er totalt " + teller + " resepter paa narkotiske legemidler i legesystemet.");
                } else if (input == 3) {
                    System.out.println("\nLeger som har skrevet ut resept paa narkotiske legemidler:");
                    for (Resept resept : resepter) {
                        if (resept.hentLegemiddel() instanceof Narkotisk) {
                            System.out.println("- " + resept.hentLege().hentNavn() + " har skrevet ut resept paa " + resept.hentLegemiddel().hentNavn() + ".\n");
                        }
                    }
                    System.out.println("\nPasienter som har minst en gyldig resept paa narkotiske legemidler:");
                    for (Resept resept : resepter) {
                        if (resept.hentLegemiddel() instanceof Narkotisk && resept.hentReit() > 0) {
                            System.out.println("- " + resept.hentPasient().hentNavn() + " har gyldig resept paa " + resept.hentLegemiddel().hentNavn() + ".\n");
                        }
                    }
                } else {
                    kommandoloekke(); // kjoerer hovedmenyen og kommandosjekken paa nytt
                }
            } else if(inputFraBruker == 12) {
                System.out.print("Skriv inn filnavn ('d' for aa bruke default):   ");
                String brukervalg = tastatur.nextLine();
                if (!(brukervalg.equals("d"))) {
                    System.out.println("\n... Lagret data til fil: " + brukervalg);
                    skrivTilFil(brukervalg);
                } else {
                    System.out.println("\n... Lagret data til fil: data_fra_legesystem.txt");
                    skrivTilFil();
                }
            }
            System.out.print("\nVil du fortsette? (j/n)   ");
            String brukervalg = tastatur.nextLine();
            if (brukervalg.equals("j")) {
                kommandoer();
                inputFraBruker = Integer.parseInt(tastatur.nextLine());
            } else if (brukervalg.equals("n")) {
                inputFraBruker = 0;
            } else {
                System.out.println("Ugyldig input. Avslutter programmet.");
                inputFraBruker = 0;
            }
        }
    }

    /* KJOERES HVIS BRUKER VIL LEGGE TIL EN HELT NY RESEPT */
    // henter detaljer fra bruker og oppretter riktig reseptobjekt
    public void spoerOmReseptinfo() throws UlovligUtskrift, FileNotFoundException {

        // VELG LEGE //
        // skriver ut listen over leger og henter input som int
        System.out.println("Velg en lege fra listen:   ");
        System.out.println(leger); // skriver ut prioritetskoen med leger
        System.out.print("Skriv inn et tall:   ");
        int legevalg = Integer.parseInt(tastatur.nextLine()); // parser inten fra bruker

        // lager ny liste med legene for aa kunne hente paa indeks
        IndeksertListe<Lege> mineleger = new IndeksertListe<>();
        
        // loekke for aa legge til alle
        for (Lege lege : leger) {
            mineleger.leggTil(lege);
        }

        Lege minLege = mineleger.hent(legevalg-1); // henter valgt lege
        System.out.println("Valgt lege: " + minLege); // 

        // VELG PASIENT //
        // skriver ut liste med alle pasienter (forenklet toString())
        System.out.println("Velg en pasient fra listen:   ");

        System.out.println("####################");
        int teller = 1;
        for (Pasient pasient : pasienter) {
            System.out.println(teller + ". " + pasient.enkelString());
            teller++;
        }
        System.out.println("####################");

        System.out.print("Skriv inn et tall:   ");
        int pasientvalg = Integer.parseInt(tastatur.nextLine()); // parser int fra bruker

        Pasient minPasient = pasienter.hent(pasientvalg-1); // henter paa indeks

        System.out.println("Valgt pasient: " + minPasient.hentNavn());

        // VELG LEGEMIDDEL //
        // skriver ut liste over legemidler
        System.out.println("Velg et legemiddel fra listen:   ");
        System.out.println(legemidler); // skriver ut full liste med legemidler
        System.out.print("Skriv inn et tall:   ");
        int legemiddelvalg = Integer.parseInt(tastatur.nextLine()); // parser int fra bruker

        Legemiddel mittlegemiddel = legemidler.hent(legemiddelvalg-1); // henter paa indeks

        // sjekke hvilken type legemiddel
        System.out.println("Valgt legemiddel: \n" + mittlegemiddel.hentNavn());

        
        // VELG RESEPT // 
        System.out.println("Hva slags resept vil du skrive ut?\n1. Hvit\n2. Blaa\n3. Militaer\n4. P-resept");
        System.out.print("Skriv inn et tall:    ");

        int reseptvalg = Integer.parseInt(tastatur.nextLine()); // parser fra brukeren
        
        // hvit resept
        if (reseptvalg == 1) {
            System.out.print("Hvor mange reit:    ");
            int minreit = Integer.parseInt(tastatur.nextLine());
            boolean fortsett = true;

            try {
                minLege.skrivHvitResept(mittlegemiddel, minPasient, minreit);
            } catch (Exception e) { 
                fortsett = false;
                System.err.println(minLege.hentNavn() + " har ikke lov til aa skrive ut " + mittlegemiddel.hentNavn() + ".\n");
            } 
            
            if (!fortsett) {
                kommandoloekke();
            }
            
            System.out.println("\n... La til hvit resept paa " + mittlegemiddel.hentNavn() + " for " + 
            minPasient.hentNavn() + " (" + minreit + " reit).");
            

        // blaa resept
        } else if (reseptvalg == 2) {
            System.out.print("Hvor mange reit:    ");
            int minreit = Integer.parseInt(tastatur.nextLine()); // leser input som int

            minLege.skrivBlaaResept(mittlegemiddel, minPasient, minreit); // skriver ut resepten fra Lege-objektet

            System.out.println("\n... La til blaa resept paa " + mittlegemiddel.hentNavn() + " for " + 
            minPasient.hentNavn() + " (" + minreit + " reit).");
        
        // militaer resept
        } else if (reseptvalg == 3) {
            // har ikke reit, trenger bare lm og pasient
            minLege.skrivMilResept(mittlegemiddel, minPasient);

            System.out.println("\n... La til militaerresept paa " + mittlegemiddel.hentNavn() + " for " + minPasient.hentNavn() + ".");

        // p resept
        } else if (reseptvalg == 4) {
            System.out.print("Hvor mange reit:    ");
            int minreit = Integer.parseInt(tastatur.nextLine()); // leser input fra bruker som int

            minLege.skrivPResept(mittlegemiddel, minPasient, minreit); // skriver ut fra Lege-objektet

            System.out.println("\n... La til P-resept paa " + mittlegemiddel.hentNavn() + " for " + 
            minPasient.hentNavn() + " (" + minreit + " reit).");

        } else {
            System.out.println("Noe gikk galt. Proev igjen...");
            kommandoloekke(); // kjoerer hovedmeny og kommandosjekk paa nytt
        }
    }

    /* SKRIVER ALL INFOEN FRA LISTE-OBJEKTENE TIL EN NY TXT-FIL */
    // bruker PrintWriter for aa lagre innholdet i listene leger, pasienter, legemidler og resepter
    // til en ny txt-fil. Standard filnavn er data_fra_legesystem.txt.
    public void skrivTilFil() throws FileNotFoundException {
        File ny_fil = new File("data_fra_legesystem.txt");
        PrintWriter pw = new PrintWriter(ny_fil);

        pw.println("# Pasienter (navn, fnr)");
        for (Pasient pasient : pasienter) {
            pw.println(pasient.hentNavn() + "," + pasient.hentFnr());
        }
        pw.println("# Legemidler (navn,type,pris,virkestoff,[styrke])");
        for (Legemiddel lm : legemidler) {
            if (lm instanceof Vanedannende) {
                pw.println(lm.hentNavn() + "," + lm.hentKlassenavn() + "," 
                + lm.hentPris() + "," + ((int) lm.hentVirkestoff()) + "," + ((Vanedannende) lm).hentStyrke());
            } else if (lm instanceof Narkotisk) {
                pw.println(lm.hentNavn() + "," + lm.hentKlassenavn() + "," + lm.hentPris() + "," + ((int) lm.hentVirkestoff()) + "," + ((Narkotisk) lm).hentStyrke());
            } else {
                pw.println(lm.hentNavn() + "," + lm.hentKlassenavn() + "," + lm.hentPris() + "," + ((int) lm.hentVirkestoff()));
            }
        }
        pw.println("# Leger (navn,kontrollid / 0 hvis vanlig lege)");
        for (Lege lege : leger) {
            if (lege instanceof Spesialist) {
                pw.println(lege.hentNavn() + "," + ((Spesialist) lege).hentKontrollID());
            } else {
                pw.println(lege.hentNavn() + ",0");
            }
        }
        pw.println("# Resepter (legemiddelNummer,legeNavn,pasientID,type,[reit])");
        for (Resept resept : resepter) {
            if (resept instanceof MilResept) {
                pw.println(resept.hentLegemiddel().hentID() + "," + resept.hentLege().hentNavn() + "," +
                resept.hentPasientID() + ",militaer");
            } else if (resept instanceof PResept) {
                pw.println(resept.hentLegemiddel().hentID() + "," + resept.hentLege().hentNavn() + "," +
                resept.hentPasientID() + ",p," + resept.hentReit());
            } else {
                pw.println(resept.hentLegemiddel().hentID() + "," + resept.hentLege().hentNavn() + "," +
                resept.hentPasientID() + "," + resept.farge() + "," + resept.hentReit());
            }
        }
        pw.close();
    }

    public void skrivTilFil(String filnavn) throws FileNotFoundException {
        if (!(filnavn.contains(".txt"))) {
            System.out.println("Filen maa vaere en .txt-fil");
            return;
        }
        File ny_fil = new File(filnavn);
        PrintWriter pw = new PrintWriter(ny_fil);

        pw.println("# Pasienter (navn, fnr)");
        for (Pasient pasient : pasienter) {
            pw.println(pasient.hentNavn() + "," + pasient.hentFnr());
        }
        pw.println("# Legemidler (navn,type,pris,virkestoff,[styrke])");
        for (Legemiddel lm : legemidler) {
            if (lm instanceof Vanedannende) {
                pw.println(lm.hentNavn() + "," + lm.hentKlassenavn() + "," 
                + lm.hentPris() + "," + ((int) lm.hentVirkestoff()) + "," + ((Vanedannende) lm).hentStyrke());
            } else if (lm instanceof Narkotisk) {
                pw.println(lm.hentNavn() + "," + lm.hentKlassenavn() + "," + lm.hentPris() + "," + ((int) lm.hentVirkestoff()) + "," + ((Narkotisk) lm).hentStyrke());
            } else {
                pw.println(lm.hentNavn() + "," + lm.hentKlassenavn() + "," + lm.hentPris() + "," + ((int) lm.hentVirkestoff()));
            }
        }
        pw.println("# Leger (navn,kontrollid / 0 hvis vanlig lege)");
        for (Lege lege : leger) {
            if (lege instanceof Spesialist) {
                pw.println(lege.hentNavn() + "," + ((Spesialist) lege).hentKontrollID());
            } else {
                pw.println(lege.hentNavn() + ",0");
            }
        }
        pw.println("# Resepter (legemiddelNummer,legeNavn,pasientID,type,[reit])");
        for (Resept resept : resepter) {
            if (resept instanceof MilResept) {
                pw.println(resept.hentLegemiddel().hentID() + "," + resept.hentLege().hentNavn() + "," +
                resept.hentPasientID() + ",militaer");
            } else if (resept instanceof PResept) {
                pw.println(resept.hentLegemiddel().hentID() + "," + resept.hentLege().hentNavn() + "," +
                resept.hentPasientID() + ",p," + resept.hentReit());
            } else {
                pw.println(resept.hentLegemiddel().hentID() + "," + resept.hentLege().hentNavn() + "," +
                resept.hentPasientID() + "," + resept.farge() + "," + resept.hentReit());
            }
        }
        pw.close();
    }

    public void kjoer() throws UlovligUtskrift, FileNotFoundException {
        System.out.println("#####################################\n" +
                           "## Velkommen til vaart legesystem! ##\n" +
                           "#####################################");
        kommandoloekke();
    }
}