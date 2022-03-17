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
                                // lege.printResepter();
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

    public void kommandoer() {
        System.out.println("******** LEGESYSTEM ********");
        System.out.println("1. Skriv ut fullstendig oversikt \n   over pasienter, leger, legemidler og resepter.");
        System.out.println("2. Opprett eller legg til nye elementer i systemet");
        System.out.println("3. Bruke en gitt resept fra listen til en pasient");
        System.out.println("4. Skriv ut forskjellige former for statistikk");
        System.out.println("5. Skriv alle data til fil");
        System.out.println("0. Avslutt");
    }

    public void 
