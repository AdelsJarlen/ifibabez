/* INTEGRASJONSTEST
Dette hovedprogrammet tester alle klassene som er skrevet til prosjektet,
og sorger for aa kalle paa metoder som sjekker at de fungerer med hverandre
som forventet. */

public class Main {
    public static void main(String[] args) throws Exception {
        
        /******************************/
        /* OPPRETTE OG TESTE OBJEKTER */
        /******************************/

        // Lage legemidler
        Vanlig kortison = new Vanlig("Kortison", 800.0, 79);
        Narkotisk morfin = new Narkotisk("Morfin", 1050.0, 750, 5);
        Vanedannende voltaren = new Vanedannende("Voltaren", 620.0, 225, 2);
        Vanlig pPiller = new Vanlig("P-piller", 450.0, 259);

        // Lage to lege-objekter
        Lege vanligLege =  new Lege("Dr. Osberg");
        Spesialist spesialistLege = new Spesialist("Dr. Andresen", "kontrollString");

        // Lage en tilfeldig pasientID som kan brukes i reseptene
        int pasientID = 456;

        // Lage et objekt av hver resept
        HvitResept hvitResept = new HvitResept(kortison, vanligLege, pasientID, 10);
        BlaaResept blaaResept = new BlaaResept(morfin, spesialistLege, pasientID, 1);
        MilResept militaerResept = new MilResept(voltaren, vanligLege, pasientID);
        PResept pResept = new PResept(pPiller, spesialistLege, pasientID, 5);

        // Utskrift av alle objektene
        System.out.println("DETTE HOVEDPROGRAMMET SKAL TESTE HELSESYSTEMET I OBLIG 2");
        System.out.println("Det er " + Legemiddel.hentAntObjekter() + " legemidler og " +
        Resept.hentAntObjekter() + " resepter totalt. Disse er skrevet ut av legen " +
        vanligLege.hentNavn() + " eller spesialisten " + spesialistLege.hentNavn());

        // LEGEMIDLER
        System.out.println("\nLegemiddel 1: \n" + kortison.toString());
        System.out.println("\nLegemiddel 2: \n" + morfin.toString());
        System.out.println("\nLegemiddel 3: \n" + voltaren.toString());
        System.out.println("\nLegemiddel 4: \n" + pPiller.toString());

        // RESEPTER
        System.out.println("\nResept 1: \n" + hvitResept.toString());
        System.out.println("\nResept 2: \n" + blaaResept.toString());
        System.out.println("\nResept 3: \n" + militaerResept.toString());
        System.out.println("\nResept 4: \n" + pResept.toString());

        // LEGER
        System.out.println("\nLege 1: \n" + vanligLege.toString());
        System.out.println("\nLege 2: \n" + spesialistLege.toString());

    }
}
