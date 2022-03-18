/* Klassedefinisjon for child-classen Vanlig.
Arver alle fields og metoder fra parent-klassen Legemiddel, 
og har ingen spesielle felter.
*/

public class Vanlig extends Legemiddel {

    /*******************/
    /* KLASSEVARIABLER */
    /*******************/

    protected String klassenavn = getClass().getSimpleName().toLowerCase();
    
    /****************/
    /* KONSTRUKTOER */
    /****************/

    public Vanlig(String navn, double virkestoff, int pris) {
        super(navn, virkestoff, pris);
    }
    
    /***********/
    /* GETTERE */
    /***********/

    public String hentKlassenavn() {
        return klassenavn;
    }

    /*****************/
    /* ANDRE METODER */
    /*****************/
    
    @Override
    public String toString() {
        return "Navn: " + hentNavn() + 
        "\nVirkestoff: " + hentVirkestoff() + " mg" +
        "\nPris: " + hentPris() + " kr" +
        "\nType legemiddel: " + hentKlassenavn() +
        "\nObjekt-ID: " + hentID();
    }
}
