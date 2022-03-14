/* Klassedefinisjon for child-classen Narkotisk.
Arver alle fields og metoder fra parent-klassen Legemiddel, 
men skal i tillegg ha 
*/

public class Narkotisk extends Legemiddel {

    /*******************/
    /* KLASSEVARIABLER */
    /*******************/

    protected int narkotisk;
    protected String klassenavn = getClass().getSimpleName();

    /****************/
    /* KONSTRUKTOER */
    /****************/

    public Narkotisk(String navn, double virkestoff, int pris, int narkotisk) {
        super(navn, virkestoff, pris); // henter variabler fra parent-klassen

        // Definerer narkotisk styrke som egen variabel
        this.narkotisk = narkotisk;
    }
    
    /***********/
    /* GETTERE */
    /***********/
    
    public String hentKlassenavn() {
        return klassenavn;
    }

    public int hentNarkotisk() {
        return narkotisk;
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
        "\nObjekt-ID: " + hentID() +
        "\nNarkotisk styrke: " + hentNarkotisk();
    }
}
