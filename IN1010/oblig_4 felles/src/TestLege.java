public class TestLege {
    public static void main(String[] args) throws UlovligUtskrift {
        Lege bob = new Lege("Dr. Bob Kåre Ytrearne");
        Lege yasmin = new Lege("Dr. Yasmin Urdula");
        Lege aese = new Lege("Dr. Æse Bergeland");
        Lege zara = new Lege("Dr. Zara Zulu");

        Pasient julie = new Pasient("Julie Haukaas", "010198");
        Pasient synne = new Pasient("Synne Markmanrud", "260496");
        Pasient jorgen = new Pasient("Jorgen Osberg", "060598");

        /* TEST AV */
        zara.nyResept(new HvitResept(new Vanedannende("Zoritox", 200, 245, 67), zara, julie, 3));
        zara.nyResept(new HvitResept(new Vanlig("Paracet", 1000, 199), zara, synne, 2));
        zara.nyResept(new BlaaResept(new Narkotisk("Morfex", 3, 798, 200), zara, jorgen, 25));

        Legemiddel narko = new Narkotisk("Morfex", 3, 798, 200);
        Legemiddel vanlig = new Vanlig("Paracet", 1000, 199);
        Legemiddel vane = new Vanedannende("Zoritox", 200, 245, 67);

        /* TEST AV COMPARETO */
        // System.out.println(bob.compareTo(yasmin));
        // System.out.println(yasmin.compareTo(bob));
        // System.out.println(bob.compareTo(bob));
        // System.out.println(aese.compareTo(zara));
        // System.out.println(zara.hentResepter());

        /* TEST AV */
        // Tester om metodene i oppgave D3 funker: //
        bob.skrivHvitResept(narko, jorgen, 3);

        bob.skrivMilResept(narko, julie);


        bob.skrivBlaaResept(vanlig, synne, 3);
        bob.skrivHvitResept(vane, jorgen, 3);
        bob.skrivMilResept(vane, jorgen);
        bob.skrivPResept(vane, synne, 1);
        System.out.println(bob.printResepter());
        bob.skrivBlaaResept(narko, julie, 3); 

    }
}
