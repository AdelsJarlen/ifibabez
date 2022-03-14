/* Klassedefinisjon for child-classen Vanedannende.
Arver alle fields og metoder fra parent-klassen Legemiddel, 
men skal i tillegg ha et felt for aa holde styr paa 
hvor sterkt vanedannende legemiddelet er.
*/

public class Vanedannende extends Legemiddel {

    /*******************/
    /* KLASSEVARIABLER */
    /*******************/

    protected int vanedannende;
    protected String klassenavn = getClass().getSimpleName();

    /****************/
    /* KONSTRUKTOER */
    /****************/

    public Vanedannende(String navn, double virkestoff, int pris, int vanedannende) {
        super(navn, virkestoff, pris);

        this.vanedannende = vanedannende;
    }

    /***********/
    /* GETTERE */
    /***********/

    public String hentKlassenavn() {
        return klassenavn;
    }

    public int hentVanedannende() {
        return vanedannende;
    }

    /*****************/
    /* ANDRE METODER */
    /*****************/

    @Override
    public String toString() {
        return "Navn: " + hentNavn() + 
        "\nVirkestoff: " + hentVirkestoff() + " mg" +
        "\nPris: " + hentPris() + " kr" +
        "\nObjekt-ID: " + hentID() +
        "\nVanedannende styrke: " + hentVanedannende();
    }
    
}
