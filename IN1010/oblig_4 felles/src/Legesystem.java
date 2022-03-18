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
        System.out.println("\n******** LEGESYSTEM ********");
        System.out.println("1. Skriv ut fullstendig oversikt over pasienter");
        System.out.println("2. Skriv ut fullstendig oversikt over leger");
        System.out.println("3. Skriv ut fullstendig oversikt over legemidler");
        System.out.println("4. Skriv ut fullstendig oversikt over resepter");
        System.out.println("5. Legg til ny pasient");
        System.out.println("6. Legg til ny lege");
        System.out.println("7. Legg til ny legemiddel");
        System.out.println("8. Legg til ny resept");
        System.out.println("9. Bruke en resept fra listen til en pasient");
        System.out.println("10. Skriv ut statistikk");
        System.out.println("11. Skriv alle data til fil");
        System.out.println("0. Avslutt");

        System.out.print("\nSkriv inn et tall:   ");
    }

    public void kommandoloekke() throws UlovligUtskrift, FileNotFoundException {
        int inputFraBruker = -1;
    
        while(inputFraBruker != 0){
            if(inputFraBruker == 1){
            //print ut fullstendig oversikt over pasienter
                System.out.println(pasienter);

            //print ut fullstendig oversikt over leger
            } else if(inputFraBruker == 2){
                System.out.println(leger);

            //print ut fullstendig oversikt over legemidler
            } else if(inputFraBruker == 3){
                System.out.println(legemidler);

            //print ut fullstendig oversikt over resepter
            } else if(inputFraBruker == 4){
                for (Lege lege : leger) {
                    lege.printResepter();
                }
            
            // legg til eller opprett pasient
            } else if(inputFraBruker == 5){
                System.out.print("\nSkriv inn pasientens fulle navn:    ");
                String pasientnavn = tastatur.nextLine();
                System.out.print("Skriv inn pasientens personnummer:    ");
                String personnr = tastatur.nextLine();

                pasienter.leggTil(new Pasient(pasientnavn, personnr));
                
                // System.out.println(pasienter);

            // legg til eller opprett lege
            } else if(inputFraBruker == 6){
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

                System.out.println(leger);

            // legg til eller opprett legemiddel
            } else if(inputFraBruker == 7){
                System.out.print("Hva slags legemiddel er det?\n1. Narkotisk\n2. Vanedannende\n3. Vanlig\n Skriv inn et tall:    ");
                String type = tastatur.nextLine();
                System.out.print("\nSkriv inn legemiddelnavn:   ");
                String legemiddelnavn = tastatur.nextLine();
                System.out.print("\nSkriv inn virkestoff (mg):   ");
                String virkestoffString = tastatur.nextLine();
                Double virkestoff = null;
                try {
                    virkestoff = Double.parseDouble(virkestoffString);
                } catch (Exception e) { 
                    System.err.println("Ikke gyldig flyttall!\n");
                } 
                
                if (virkestoff == null) {
                    kommandoloekke();
                }

                System.out.print("\nSkriv inn pris (NOK):   ");
                int pris = Integer.parseInt(tastatur.nextLine());

                // Narkotisk
                if (type.equals("1")){
                    System.out.print("Skriv inn narkotisk styrke:    ");
                    int styrke = Integer.parseInt(tastatur.nextLine());

                    legemidler.leggTil(new Narkotisk(legemiddelnavn, virkestoff, pris, styrke));
                }
                // Vanedannende
                else if (type.equals("2")){
                    System.out.print("Skriv inn narkotisk styrke:    ");
                    int styrke = Integer.parseInt(tastatur.nextLine());

                    legemidler.leggTil(new Vanedannende(legemiddelnavn, virkestoff, pris, styrke));
                }
                // Vanlig legemiddel
                else if (type.equals("3")){
                    legemidler.leggTil(new Vanlig(legemiddelnavn, virkestoff, pris));
                }
                else{
                    System.out.println("Ikke et gyldig valg!");
                    kommandoer();
                }

            // legg til eller opprett resept
            } else if(inputFraBruker == 8){
                spoerOmReseptinfo();

            // bruke gitt resept fra liste til gitt pasient
            } else if(inputFraBruker == 9) {
                Pasient minPasient;

                int teller = 1;
                for (Pasient pasient : pasienter) {
                    System.out.println(teller + ". " + pasient.enkelString());
                    teller++;
                }

                System.out.print("Skriv inn et tall:   ");
                int pasientvalg = Integer.parseInt(tastatur.nextLine());
                minPasient = pasienter.hent(pasientvalg-1);

                System.out.println("Pasient valgt er " + pasienter.hent(pasientvalg-1).enkelString() + ")\n");

                // velg resept
                System.out.println("Hvilken resept vil du bruke?");
                System.out.println(pasienter.hent(pasientvalg - 1).reseptlisteString());
                
                System.out.print("Skriv inn tall:   ");
                int reseptvalg = Integer.parseInt(tastatur.nextLine());

                // Resept minResept = minPasient.hentReseptliste().hent(reseptvalg-1);
                
                minPasient.hentReseptliste().hent(reseptvalg-1).bruk();

                if (minPasient.hentReseptliste().hent(reseptvalg-1).hentReit() > 0) {
                    System.out.println("\nDu har brukt: " + pasienter.hent(pasientvalg - 1).reseptlisteString());
                } else {
                    System.out.println("Kunne ikke bruke resept paa " + minPasient.hentReseptliste().hent(reseptvalg-1).hentLegemiddel().hentNavn() + " (ingen reit igjen)");
                }

            } else if (inputFraBruker == 10) {
                System.out.println("1. Vis resepter paa vanedannende legemidler");
                System.out.println("2. Vis resepter paa narkotiske legemidler");
                System.out.println("3. Oversikt over mulig misbruk av narkotika");

                System.out.print("\nSkriv inn et tall:   ");
                int input = Integer.parseInt(tastatur.nextLine());

                if (input == 1) {
                    int teller = 0;
                    for (Resept resept : resepter) {
                        
                        if (resept.hentLegemiddel() instanceof Vanedannende) {
                            teller++;
                        }
                    }
                    System.out.println("Totalt antall resepter paa vanedannende legemidler: " + teller);
                } else if (input == 2) {
                    int teller = 0;
                    for (Resept resept : resepter) {
                        
                        if (resept.hentLegemiddel() instanceof Narkotisk) {
                            teller++;
                        }
                    }
                    System.out.println("Totalt antall resepter paa narkotiske legemidler: " + teller);
                } else if (input == 3) {
                    System.out.println("\nLeger som har skrevet ut resept paa narkotiske legemidler:");
                    for (Resept resept : resepter) {
                        if (resept.hentLegemiddel() instanceof Narkotisk) {
                            System.out.println("- " + resept.hentLege().hentNavn() + " har skrevet ut resept paa " + resept.hentLegemiddel().hentNavn() + ".\n");
                        }
                    }
                    System.out.println("\nPasienter som har minst én gyldig resept paa narkotiske legemidler:");
                    for (Resept resept : resepter) {
                        if (resept.hentLegemiddel() instanceof Narkotisk && resept.hentReit() > 0) {
                            System.out.println("- " + resept.hentPasient().hentNavn() + " har gyldig resept paa " + resept.hentLegemiddel().hentNavn() + ".\n");
                        }
                    }
                } else {
                    kommandoloekke();
                }
            } else if(inputFraBruker == 11) {
                skrivTilFil();
            }
          kommandoer();
          inputFraBruker = Integer.parseInt(tastatur.nextLine());
        }
    }

    public void spoerOmReseptinfo() throws UlovligUtskrift, FileNotFoundException{

        // VELG LEGE

        System.out.println("Velg en lege fra listen:   ");
        System.out.println(leger);
        System.out.print("Skriv inn et tall:   ");
        int legevalg = Integer.parseInt(tastatur.nextLine());
        IndeksertListe<Lege> mineleger = new IndeksertListe<>();
        
        for (Lege lege : leger) {
            mineleger.leggTil(lege);
        }

        Lege minLege = mineleger.hent(legevalg-1);

        System.out.println(minLege);

        // VELG PASIENT

        System.out.println("Velg en pasient fra listen:   ");
        System.out.println(pasienter);
        System.out.print("Skriv inn et tall:   ");
        int pasientvalg = Integer.parseInt(tastatur.nextLine());

        Pasient minPasient = pasienter.hent(pasientvalg-1);

        System.out.println(minPasient);

        // VELG LEGEMIDDEL

        System.out.println("Velg en legemiddel fra listen:   ");
        System.out.println(legemidler);
        System.out.print("Skriv inn et tall:   ");
        int legemiddelvalg = Integer.parseInt(tastatur.nextLine());

        Legemiddel mittlegemiddel = legemidler.hent(legemiddelvalg-1);

        // sjekke hvilken type legemiddel

        System.out.println(mittlegemiddel);

        
        // VELG RESEPT

        System.out.println("Hva slags resept vil du skrive ut?\n1. hvit\n2. blaa\n3. militaer\n4. p");
        System.out.print("Skriv inn et tall:    ");

        int reseptvalg = Integer.parseInt(tastatur.nextLine());
        
        // hvit resept
        if (reseptvalg == 1) {
            System.out.print("Hvor mange reit:    ");
            int minreit = Integer.parseInt(tastatur.nextLine());
            boolean fortsett = true;

            try {
                minLege.skrivHvitResept(mittlegemiddel, minPasient, minreit);
            } catch (Exception e) { 
                fortsett = false;
                System.err.println(minLege.hentNavn() + " har ikke lov aa skrive ut " + mittlegemiddel.hentNavn() + "\n");
            } 
            
            if (!fortsett) {
                kommandoloekke();
            }
            

            minLege.printResepter();

        // blaa resept
        } else if (reseptvalg == 2) {
            System.out.print("Hvor mange reit:    ");
            int minreit = Integer.parseInt(tastatur.nextLine());

            minLege.skrivBlaaResept(mittlegemiddel, minPasient, minreit);

            minLege.printResepter();
        
        // militaer resept
        } else if (reseptvalg == 3) {

            minLege.skrivMilResept(mittlegemiddel, minPasient);

            minLege.printResepter();

        // p resept
        } else if (reseptvalg == 4) {
            System.out.print("Hvor mange reit:    ");
            int minreit = Integer.parseInt(tastatur.nextLine());

            minLege.skrivPResept(mittlegemiddel, minPasient, minreit);

            minLege.printResepter();

        } else {
            System.out.println("Prøv igjen...");
            kommandoloekke();
        }
    }

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
}
