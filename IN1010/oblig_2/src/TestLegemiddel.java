/* Testfil for Parent-Classen Legemiddel og dens Children
(Vanlig, Vanedannende og Narkotisk).

Inneholder enkle if-tester for alle metodene og verdiene i hver klasse, 
inkludert de child-spesifikke metodene hentNarkotisk() og hentVanedannende().

OBS: Jeg vet at tips 2 foreslaar at man skiller ut disse testene i egne metoder
og bruker spesifikke boolean-tester med testverdiene som parametre, men jeg
mener selv at det er helt unodvendig i dette tilfellet.*/

public class TestLegemiddel {
    public static void main(String[] args) {

        Vanlig paracet = new Vanlig("Paracet", 400.0, 29);
        Narkotisk morfin = new Narkotisk("Morfin", 1050.0, 750, 5);
        Vanedannende voltaren = new Vanedannende("Voltaren", 620.0, 225, 2);

        // Tester den statiske instansCounteren
        System.out.println("Det er " + Legemiddel.hentAntObjekter() + " legemidler totalt.");

        System.out.println("Starter testen...\n\n");        
        System.out.println("#####################################################\n");        

        /***************/
        /* FORSTE TEST */
        /***************/
        
        int sjekkTestVanlig = 0;

        // Tester klassen Vanlig
        System.out.println("## Test nr. [1/3] ##\n... tester legemiddelet '" 
        + paracet.hentNavn() + "' av klassen '" + paracet.hentKlassenavn() + "'.\n");

        // Test toString()
        System.out.println("Tester full utskrift av info om objektet: ");
        System.out.println("\n" + paracet.toString() + "\n");

        // Test navn
        if (paracet.hentNavn() == "Paracet") {
            System.out.println("Navnet er riktig!");
            sjekkTestVanlig++;
        } else {
            System.out.println("Oops! Navnet stemmer ikke.");
        }

        // Test virkestoff
        if (paracet.hentVirkestoff() == 400.0) {
            System.out.println("400.0 mg er korrekt mengde virkestoff!");
            sjekkTestVanlig++;
        } else {
            System.out.println("Oops! Det er feil mengde virkestoff.");
        }

        // Test pris
        if (paracet.hentPris() == 29) {
            System.out.println("29 kr er riktig pris!");
            sjekkTestVanlig++;
        } else {
            System.out.println("Oops! Det er feil pris.");
        }

        // Test objektID
        if (paracet.hentID() == 1) {
            System.out.println("1 er riktig objekt-ID!");
            sjekkTestVanlig++;
        } else {
            System.out.println("Oops! Det er feil ID.");
        }

        System.out.println("\n" + sjekkTestVanlig + " av 4 tester var vellykkede.");

        System.out.println("\n#####################################################");        

        /**************/
        /* ANDRE TEST */
        /**************/

        int sjekkTestNarkotisk = 0;

        // Tester klassen Narkotisk
        System.out.println("\n## Test nr. [2/3] ##\n... tester legemiddelet '" 
        + morfin.hentNavn() + "' av klassen '" + morfin.hentKlassenavn() + "'.\n");

        // Test toString()
        System.out.println("Tester full utskrift av info om objektet: ");
        System.out.println("\n" + morfin.toString() + "\n");

        // Test navn
        if (morfin.hentNavn() == "Morfin") {
            System.out.println("Navnet er riktig!");
            sjekkTestNarkotisk++;
        } else {
            System.out.println("Oops! Navnet stemmer ikke.");
        }

        // Test virkestoff
        if (morfin.hentVirkestoff() == 1050.0) {
            System.out.println("1050.0 mg er korrekt mengde virkestoff!");
            sjekkTestNarkotisk++;
        } else {
            System.out.println("Oops! Det er feil mengde virkestoff.");
        }

        // Test pris
        if (morfin.hentPris() == 750) {
            System.out.println("750 kr er riktig pris!");
            sjekkTestNarkotisk++;
        } else {
            System.out.println("Oops! Det er feil pris.");
        }

        // Test objektID
        if (morfin.hentID() == 2) {
            System.out.println("2 er korrekt. Objekt-IDen er riktig!");
            sjekkTestNarkotisk++;
        } else {
            System.out.println("Oops! Det er feil ID.");
        }

        // Test narkotisk
        if (morfin.hentNarkotisk() == 5) {
            System.out.println("Korrekt! Morfin er paa nivaa 5.");
            sjekkTestNarkotisk++;
        } else {
            System.out.println("Oops! Variabelen narkotisk er feil.");
        }

        System.out.println("\n" + sjekkTestNarkotisk + " av 5 tester var vellykkede.");

        System.out.println("\n#####################################################");        

        /***************/
        /* TREDJE TEST */
        /***************/

        int sjekkTestVanedannende = 0;

        // Tester klassen Vanedannende
        System.out.println("\n## Test nr. [3/3] ##\n... tester legemiddelet '" 
        + voltaren.hentNavn() + "' av klassen '" + voltaren.hentKlassenavn() + "'.\n");

        // Test toString()
        System.out.println("Tester full utskrift av info om objektet: ");
        System.out.println("\n" + voltaren.toString() + "\n");

        // Test navn
        if (voltaren.hentNavn() == "Voltaren") {
            System.out.println("Navnet er riktig!");
            sjekkTestVanedannende++;
        } else {
            System.out.println("Oops! Navnet stemmer ikke.");
        }

        // Test virkestoff
        if (voltaren.hentVirkestoff() == 620.0) {
            System.out.println("620.0 mg er korrekt mengde virkestoff!");
            sjekkTestVanedannende++;
        } else {
            System.out.println("Oops! Det er feil mengde virkestoff.");
        }

        // Test pris
        if (voltaren.hentPris() == 225) {
            System.out.println("225 kr er riktig pris!");
            sjekkTestVanedannende++;
        } else {
            System.out.println("Oops! Det er feil pris.");
        }

        // Test objektID
        if (voltaren.hentID() == 3) {
            System.out.println("3 er korrekt. Objekt-IDen er riktig!");
            sjekkTestVanedannende++;
        } else {
            System.out.println("Oops! Det er feil ID.");
        }

        // Test vanedannende
        if (voltaren.hentVanedannende() == 2) {
            System.out.println("Korrekt! Voltaren er paa nivaa 2.");
            sjekkTestVanedannende++;
        } else {
            System.out.println("Oops! Variabelen vanedannende er feil.");
        }
        
        System.out.println("\n" + sjekkTestVanedannende + " av 5 tester var vellykkede.");

        System.out.println("\n#####################################################");        

    }
}
