import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Legesystem {
    
    // maa finne ut hvordan reseptlisten i Pasient.java skal oppdateres
    // skal pasient legge til resept, eller skal resept legge til paa pasient?

    protected IndeksertListe<Resept> utskrevneResepter;
    protected Stabel<Resept> resepter;
    protected Koe<Pasient> pasienter = new Koe<>();
    protected Koe<Lege> leger = new Koe<>();
    protected Koe<Legemiddel> legemidler = new Koe<>();

    public void lesFraFil(String filnavn) throws FileNotFoundException {

        File data = new File(filnavn);
        Scanner scanner = new Scanner(data);

        while (scanner.hasNextLine()) {
            String linje = scanner.nextLine();
            
            if (linje.contains("Pasient")) {
                String pasient_linje = scanner.nextLine();
                
                while (scanner.hasNextLine()) {
                    if (pasient_linje.contains("#")) {
                        linje = pasient_linje;
                        break;
                    }
                    String[] info = pasient_linje.split(",");
                    pasienter.leggTil(new Pasient(info[0], info[1]));
                    pasient_linje = scanner.nextLine();
                }

                System.out.println(pasienter);
            } 

            if (linje.contains("Legemidler")) {
                String legemiddel_linje = scanner.nextLine();
                
                while (scanner.hasNextLine()) {
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

                System.out.println(legemidler);
                
            }

            if (linje.contains("Leger")) {
                String lege_linje = scanner.nextLine();
                
                while (scanner.hasNextLine()) {
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

                System.out.println(leger);
            } 

            // System.out.println(linje);

            if (linje.contains("Resepter")) {
                String resept_linje = scanner.nextLine();
                
                while (scanner.hasNextLine()) {
                    if (resept_linje.contains("#")) {
                        linje = resept_linje;
                        break;
                    }

                    if (resept_linje.contains("hvit")) {
                        String[] info = resept_linje.split(",");
                        String legemiddelNr = info[0];
                        String lege = info[1];
                        int pasientID = Integer.parseInt(info[2]);
                        int reit = Integer.parseInt(info[4]);

                        // (legemiddelNr, lege, pasientID, reit));
                    }

                    if (resept_linje.contains("vanedannende")) {
                        String[] info = resept_linje.split(",");
                        String navn = info[0];
                        double virkestoff = Double.parseDouble(info[3]);
                        int pris = Integer.parseInt(info[2]);
                        int styrke = Integer.parseInt(info[4]);
                        legemidler.leggTil(new Vanedannende(navn, virkestoff, pris, styrke));
                    }

                    if (resept_linje.contains("vanlig")) {
                        String[] info = resept_linje.split(",");
                        String navn = info[0];
                        double virkestoff = Double.parseDouble(info[3]);
                        int pris = Integer.parseInt(info[2]);
                        legemidler.leggTil(new Vanlig(navn, virkestoff, pris));
                    }

                    resept_linje = scanner.nextLine();
                }

                // System.out.println(legemidler.toString());
                
            }

        }
    }

}
