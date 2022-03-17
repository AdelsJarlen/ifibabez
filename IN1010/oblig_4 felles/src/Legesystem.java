import java.io.File;
import java.io.FileNotFoundException;
import java.util.NoSuchElementException;
import java.util.Scanner;

public class Legesystem {
    
    // maa finne ut hvordan reseptlisten i Pasient.java skal oppdateres
    // skal pasient legge til resept, eller skal resept legge til paa pasient?

    protected IndeksertListe<Resept> utskrevneResepter;
    protected Stabel<Resept> resepter;
    protected IndeksertListe<Pasient> pasienter = new IndeksertListe<>();
    protected Prioritetskoe<Lege> leger = new Prioritetskoe<>();
    protected IndeksertListe<Legemiddel> legemidler = new IndeksertListe<>();

    public void lesFraFil(String filnavn) throws FileNotFoundException, UlovligUtskrift, NoSuchElementException {

        File data = new File(filnavn);
        Scanner scanner = new Scanner(data);

        String linje = scanner.nextLine();

        while (linje != null) {
            
            if (linje.contains("Pasient")) {
                String pasient_linje = scanner.nextLine();
                
                while (pasient_linje != null) {
                    if (pasient_linje.contains("#")) {
                        linje = pasient_linje;
                        break;
                    }
                    String[] info = pasient_linje.split(",");
                    pasienter.leggTil(new Pasient(info[0], info[1]));
                    pasient_linje = scanner.nextLine();
                }

                // System.out.println(pasienter);
            } 

            if (linje.contains("Legemidler")) {
                String legemiddel_linje = scanner.nextLine();
                
                while (legemiddel_linje != null) {
                    if (legemiddel_linje.contains("#")) {
                        linje = legemiddel_linje;
                        break;
                    }

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

                    legemiddel_linje = scanner.nextLine();
                }

                // System.out.println(legemidler);
                
            }

            if (linje.contains("Leger")) {
                String lege_linje = scanner.nextLine();
                
                while (lege_linje != null) {
                    if (lege_linje.contains("#")) {
                        linje = lege_linje;
                        break;
                    }

                    String[] info = lege_linje.split(",");
                    
                    int takk_for_mellomrom_bitches = Integer.parseInt(info[1].trim());

                    if (takk_for_mellomrom_bitches == 0) { // maatte legge til mellomrom fordi dere suger
                        leger.leggTil(new Lege(info[0]));
                    } else {
                        leger.leggTil(new Spesialist(info[0], info[1]));  
                    }  

                    lege_linje = scanner.nextLine();
                }

                // System.out.println(leger);
            } 

            if (linje.contains("Resepter")) {
                String resept_linje = scanner.nextLine();
                
                while (resept_linje != null) {
                    if (resept_linje.contains("#")) {
                        linje = resept_linje;
                        break;
                    }

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
                                skrevetUt = true;
                                //lege.printResepter();
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
                                skrevetUt = true;
                                // lege.printResepter();
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
                                skrevetUt = true;
                                // lege.printResepter();
                            }
                        }

                        if (skrevetUt == false) {
                            throw new NoSuchElementException("Legen " + legenavn + " finnes ikke.");
                        }

                    }

                    if (resept_linje.contains("p")) {
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
                                skrevetUt = true;
                                // lege.printResepter();
                            }
                        }

                        if (skrevetUt == false) {
                            throw new NoSuchElementException("Legen " + legenavn + " finnes ikke.");
                        }

                    }
                    

                    System.out.println(resept_linje);

                    if (scanner.hasNextLine()) {
                        resept_linje = scanner.nextLine();
                    } else {
                        break;
                    }
                    
                }

                // System.out.println(legemidler.toString());
                
            }

            // System.out.println(linje);
            
            if (scanner.hasNextLine()) {
                linje = scanner.nextLine();
            } else {
                break;
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
        System.out.println("5. Opprett eller legg til ny pasient");
        System.out.println("6. Opprett eller legg til ny lege");
        System.out.println("7. Opprett eller legg til ny legemiddel");
        System.out.println("8. Opprett eller legg til ny resept");
        System.out.println("9. Bruke en gitt resept fra listen til en pasient");
        System.out.println("10. Skriv ut forskjellige former for statistikk");
        System.out.println("11. Skriv alle data til fil");
        System.out.println("0. Avslutt");

        System.out.print("\nSkriv inn et tall:   ");
    }

    public void kommandoloekke() throws UlovligUtskrift{
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
                Double virkestoff = Double.parseDouble(tastatur.nextLine());
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
            }
        //     } else if(inputFraBruker == 9){
        //     finnMestPopulaereFag();
        //   } else if(inputFraBruker == 10){
        //     finnMestArbeidsommeStudent();
        //   } else if(inputFraBruker == 11){
        //     skrivUtAlleFagMedTilhorendeStudenter();
        //   }
          kommandoer();
          inputFraBruker = Integer.parseInt(tastatur.nextLine());
        }
    }


    public void spoerOmReseptinfo() throws UlovligUtskrift{

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

            minLege.skrivHvitResept(mittlegemiddel, minPasient, minreit);

            minLege.printResepter();

        // blaa resept
        } else if (reseptvalg == 2) {
        
        // militaer resept
        } else if (reseptvalg == 3) {

        // p resept
        } else if (reseptvalg == 4) {

        } else {
            System.out.println("Prøv igjen...");
            kommandoloekke();
        }
    }
}