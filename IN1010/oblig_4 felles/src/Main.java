public class Main {
    public static void main(String[] args) throws Exception {
        Pasient julie = new Pasient("Julie Haukaas", "010198");
        Pasient synne = new Pasient("Synne Markmanrud", "260496");
        Pasient jorgen = new Pasient("Jorgen Osberg", "060598");

        // Lager tre legemidler (kopiert fra TestLegemiddel.java)
        Vanlig kortison = new Vanlig("Kortison", 800.0, 179);
        Narkotisk morfin = new Narkotisk("Morfin", 1050.0, 750, 5);
        Vanedannende voltaren = new Vanedannende("Voltaren", 620.0, 225, 2);

        // Lager et ekstra legemiddel som passer enda litt bedre til PResept
        Vanlig pPiller = new Vanlig("P-piller", 450.0, 259);

        // Lager en spesialistLege
        Spesialist spesialistLege = new Spesialist("Dr. Andresen", "kontrollString");

        // Lager 4 resepter med legemidlene og spesialisten
        HvitResept hvitResept = new HvitResept(kortison, spesialistLege, julie, 10);
        BlaaResept blaaResept = new BlaaResept(morfin, spesialistLege, synne, 1);
        MilResept militaerResept = new MilResept(voltaren, spesialistLege, julie);
        PResept pResept = new PResept(pPiller, spesialistLege, jorgen, 5);

        synne.leggTilResept(blaaResept);
        julie.leggTilResept(hvitResept);
        julie.leggTilResept(militaerResept);
        jorgen.leggTilResept(pResept);

        // System.out.println(julie + "\n\n" + synne + "\n\n" + jorgen);

        // System.out.println("Jorgen har resepten: " + jorgen.hentReseptliste().hent().hentLegemiddel().hentNavn());
    }
}
