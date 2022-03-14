/* Testfil for Parent-Classen Resept og dens Children
(HvitResept, BlaaResept og MilResept + PResept).

Inneholder enkle if-tester for alle metodene og verdiene i hver klasse, 
inkludert de child-spesifikke metodene hentNarkotisk() og hentVanedannende().

OBS: Jeg vet at tips 2 foreslaar at man skiller ut disse testene i egne metoder
og bruker spesifikke boolean-tester med testverdiene som parametre, men jeg
mener selv at det er unodvendig ekstrakode i dette tilfellet.*/

public class TestResepter {
    public static void main(String[] args) {

        // Lager tre legemidler (kopiert fra TestLegemiddel.java)
        Vanlig kortison = new Vanlig("Kortison", 800.0, 179);
        Narkotisk morfin = new Narkotisk("Morfin", 1050.0, 750, 5);
        Vanedannende voltaren = new Vanedannende("Voltaren", 620.0, 225, 2);

        // Lager et ekstra legemiddel som passer enda litt bedre til PResept
        Vanlig pPiller = new Vanlig("P-piller", 450.0, 259);

        // Lager en spesialistLege
        Spesialist spesialistLege = new Spesialist("Dr. Andresen", "kontrollString");

        // Vi ser for oss en pasient og finner paa en ID
        int pasientID = 123;

        // Lager 4 resepter med legemidlene og spesialisten
        HvitResept hvitResept = new HvitResept(kortison, spesialistLege, pasientID, 10);
        BlaaResept blaaResept = new BlaaResept(morfin, spesialistLege, pasientID, 1);
        MilResept militaerResept = new MilResept(voltaren, spesialistLege, pasientID);
        PResept pResept = new PResept(pPiller, spesialistLege, pasientID, 5);

        // Tester den statiske instansCounteren i Resept
        System.out.println("\nDet er " + Resept.hentAntObjekter() + " resepter totalt.");

        System.out.println("Starter testen...\n");        
        System.out.println("#####################################################\n");        

        /***************/
        /* FORSTE TEST */
        /***************/
        
        int sjekkTestHvitResept = 0;

        // Tester klassen HvitResept
        System.out.println("## Test nr. [1/4] ##\n... tester resepten '" 
        + hvitResept.farge() + "' for legemiddelet '" + hvitResept.hentLegemiddel().hentNavn() + "'.\n");

        // Test toString()
        System.out.println("Tester full utskrift av info om objektet (og dermed ogsaa getterne): ");
        System.out.println(hvitResept.toString() + "\n");
        sjekkTestHvitResept++;

        // Test farge()
        if (hvitResept.farge() == "hvit") {
            System.out.println("Hvit er riktig farge!");
            sjekkTestHvitResept++;
        } else {
            System.out.println("Oops! Det er feil farge.");
        }

        // Test prisAaBetale()
        if (hvitResept.prisAaBetale() == hvitResept.hentLegemiddel().hentPris()) {
            System.out.println("179 kr er riktig pris!");
            sjekkTestHvitResept++;
        } else {
            System.out.println("Oops! Det er feil pris.");
        }

        // Test objektID
        if (hvitResept.hentID() == 1) {
            System.out.println("1 er riktig objekt-ID!");
            sjekkTestHvitResept++;
        } else {
            System.out.println("Oops! Det er feil ID.");
        }

        // Test bruk()
        if (hvitResept.bruk()) {
            System.out.println("Resepten kan brukes. Reit er naa: " + hvitResept.hentReit());
            sjekkTestHvitResept++;
        } else {
            System.out.println("Resepten er allerede brukt opp. Reit = " + hvitResept.hentReit());
        }

        System.out.println("\n" + sjekkTestHvitResept + " av 5 tester var vellykkede.");

        System.out.println("\n#####################################################\n");        

        /**************/
        /* ANDRE TEST */
        /**************/

        int sjekkTestBlaaResept = 0;

        // Tester klassen BlaaResept
        System.out.println("## Test nr. [2/4] ##\n... tester resepten '" 
        + blaaResept.farge() + "' for legemiddelet '" + blaaResept.hentLegemiddel().hentNavn() + "'.\n");

        // Test toString()
        System.out.println("Tester full utskrift av info om objektet (vha. getterne): ");
        System.out.println("\n" + blaaResept.toString() + "\n");
        sjekkTestBlaaResept++;

        // Test farge()
        if (blaaResept.farge() == "blaa") {
            System.out.println("Blaa er riktig farge!");
            sjekkTestBlaaResept++;
        } else {
            System.out.println("Oops! Det er feil farge.");
        }

        // Test prisAaBetale()
        if (blaaResept.prisAaBetale() == 187) {
            System.out.println("188 kr er riktig rabattert pris!");
            sjekkTestBlaaResept++;
        } else {
            System.out.println("Oops! Det er feil pris.");
        }

        // Test objektID
        if (blaaResept.hentID() == 2) {
            System.out.println("2 er riktig objektID!");
            sjekkTestBlaaResept++;
        } else {
            System.out.println("Oops! Det er feil ID.");
        }

        // Test bruk()
        if (blaaResept.bruk()) {
            System.out.println("Resepten kan brukes. Reit er naa: " + blaaResept.hentReit());
            sjekkTestBlaaResept++;
        } else {
            System.out.println("Resepten er allerede brukt opp. Reit = " + blaaResept.hentReit());
        }

        System.out.println("\n" + sjekkTestBlaaResept + " av 5 tester var vellykkede.");

        System.out.println("\n#####################################################\n");        

        /***************/
        /* TREDJE TEST */
        /***************/

        int sjekkTestMilResept = 0;

        // Tester klassen MilResept
        System.out.println("## Test nr. [3/4] ##\n... tester resepten '" 
        + militaerResept.farge() + "' for legemiddelet '" + militaerResept.hentLegemiddel().hentNavn() + "'.\n");

        // Test toString()
        System.out.println("Tester full utskrift av info om objektet (vha. getterne): ");
        System.out.println("\n" + militaerResept.toString() + "\n");
        sjekkTestMilResept++;

        // Test farge()
        if (militaerResept.farge() == "hvit") {
            System.out.println("Hvit er riktig farge!");
            sjekkTestMilResept++;
        } else {
            System.out.println("Oops! Det er feil farge.");
        }

        // Test prisAaBetale()
        if (militaerResept.prisAaBetale() == 0) {
            System.out.println("0 kr er riktig rabattert pris!");
            sjekkTestMilResept++;
        } else {
            System.out.println("Oops! Det er feil pris.");
        }

        // Test objektID
        if (militaerResept.hentID() == 3) {
            System.out.println("3 er riktig objektID!");
            sjekkTestMilResept++;
        } else {
            System.out.println("Oops! Det er feil ID.");
        }

        // Test bruk()
        if (militaerResept.bruk()) {
            System.out.println("Resepten kan brukes. Reit er naa: " + militaerResept.hentReit());
            sjekkTestMilResept++;
        } else {
            System.out.println("Resepten er allerede brukt opp. Reit = " + militaerResept.hentReit());
        }
        
        System.out.println("\n" + sjekkTestMilResept + " av 5 tester var vellykkede.");

        System.out.println("\n#####################################################\n");  
        
        /***************/
        /* FJERDE TEST */
        /***************/

        int sjekkTestPResept = 0;

        // Tester klassen MilResept
        System.out.println("## Test nr. [3/4] ##\n... tester resepten '" 
        + pResept.farge() + "' for legemiddelet '" + pResept.hentLegemiddel().hentNavn() + "'.\n");

        // Test toString()
        System.out.println("Tester full utskrift av info om objektet (vha. getterne): ");
        System.out.println("\n" + pResept.toString() + "\n");
        sjekkTestPResept++;

        // Test farge()
        if (pResept.farge() == "hvit") {
            System.out.println("Hvit er riktig farge!");
            sjekkTestPResept++;
        } else {
            System.out.println("Oops! Det er feil farge.");
        }

        // Test prisAaBetale()
        if (pResept.prisAaBetale() == 151) {
            System.out.println("151 kr er riktig rabattert pris!");
            sjekkTestPResept++;
        } else {
            System.out.println("Oops! Det er feil pris.");
        }

        // Test objektID
        if (pResept.hentID() == 4) {
            System.out.println("4 er riktig objektID!");
            sjekkTestPResept++;
        } else {
            System.out.println("Oops! Det er feil ID.");
        }

        // Test bruk()
        if (pResept.bruk()) {
            System.out.println("Resepten kan brukes. Reit er naa: " + pResept.hentReit());
            sjekkTestPResept++;
        } else {
            System.out.println("Resepten er allerede brukt opp. Reit = " + pResept.hentReit());
        }
        
        System.out.println("\n" + sjekkTestPResept + " av 5 tester var vellykkede.");

        System.out.println("\n#####################################################");       

    }
}
