import java.io.File;
import java.io.FileNotFoundException;
import java.util.NoSuchElementException;
import java.util.Scanner;

public class alternativtLegesystem {
    
    protected IndeksertListe<Resept> utskrevneResepter;
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
                        skrevetUt = true;
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
                        lege.printResepter();
                    }
                }

                if (skrevetUt == false) {
                    throw new NoSuchElementException("Legen " + legenavn + " finnes ikke.");
                }

            }
        scanner.close();
        }       
    }
}
