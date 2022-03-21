/* Klassedefinisjon for child-classen Vanlig.
Arver alle fields og metoder fra parent-klassen Legemiddel, 
og har ingen spesielle felter.
*/

public class Vanlig extends Legemiddel {

    /* KLASSEVARIABLER */
    protected String klassenavn = getClass().getSimpleName().toLowerCase();
    
    /* KONSTRUKTOER */
    public Vanlig(String navn, double virkestoff, int pris) {
        super(navn, virkestoff, pris);
    }
    
    /* GETTERE */
    public String hentKlassenavn() {
        return klassenavn;
    }

    /* ANDRE METODER */
    @Override
    public String toString() {
        return "Navn: " + hentNavn() + 
        "\n   Virkestoff: " + hentVirkestoff() + " mg" +
        "\n   Pris: " + hentPris() + " kr" +
        "\n   Type legemiddel: " + hentKlassenavn() +
        "\n   Objekt-ID: " + hentID();
    }
}
