public class TestLege {
    public static void main(String[] args) throws UlovligUtskrift {
        
        /* OPPRETTER LEGE-OBJEKTER */
        Lege bob = new Lege("Dr. Bob Kåre Ytrearne");
        Lege yasmin = new Lege("Dr. Yasmin Urdula");
        Lege aese = new Lege("Dr. Æse Bergeland");
        Lege zara = new Lege("Dr. Zara Zulu");

        /* OPPRETTER PASIENT-OBJEKTER */
        Pasient julie = new Pasient("Julie Haukaas", "010198");
        Pasient synne = new Pasient("Synne Markmanrud", "260496");
        Pasient jorgen = new Pasient("Jorgen Osberg", "060598");

        /* TEST AV METODEN nyResept() */
        zara.nyResept(new HvitResept(new Vanedannende("Zoritox", 200, 245, 67), zara, julie, 3));
        zara.nyResept(new HvitResept(new Vanlig("Paracet", 1000, 199), zara, synne, 2));
        zara.nyResept(new BlaaResept(new Narkotisk("Morfex", 3, 798, 200), zara, jorgen, 25));

        /* OPPRETTER LEGEMIDDEL-OBJEKTER */
        Legemiddel narko = new Narkotisk("Morfex", 3, 798, 200);
        Legemiddel vanlig = new Vanlig("Paracet", 1000, 199);
        Legemiddel vane = new Vanedannende("Zoritox", 200, 245, 67);

        /* TEST AV compareTo */
        // System.out.println(bob.compareTo(yasmin));
        // System.out.println(yasmin.compareTo(bob));
        // System.out.println(bob.compareTo(bob));
        // System.out.println(aese.compareTo(zara));
        // System.out.println(zara.hentResepter());

        /* TEST AV DE FIRE skrivResept-METODENE */
        bob.skrivBlaaResept(vanlig, synne, 3);
        bob.skrivHvitResept(vane, jorgen, 3);
        bob.skrivMilResept(vane, jorgen);
        bob.skrivPResept(vane, synne, 1);
        bob.skrivBlaaResept(narko, julie, 3);

        bob.printResepter(); // skriver ut reseptene for aa sjekke
        
        /* TEST AV ULOVLIG UTSKRIFT */
        bob.skrivHvitResept(narko, jorgen, 3); // skal throwe UlovligUtskrift-exception og bryte kjoeringen
        bob.skrivMilResept(narko, julie);
    }
}
