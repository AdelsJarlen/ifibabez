/* Klassedefinisjon for child-classen Vanedannende.
Arver alle fields og metoder fra parent-klassen Legemiddel, 
men skal i tillegg ha et felt for aa holde styr paa 
hvor sterkt vanedannende legemiddelet er.
*/

public class Vanedannende extends Legemiddel {

    /*******************/
    /* KLASSEVARIABLER */
    /*******************/

    protected int styrke;
    protected String klassenavn = getClass().getSimpleName().toLowerCase();

    /****************/
    /* KONSTRUKTOER */
    /****************/

    public Vanedannende(String navn, double virkestoff, int pris, int styrke) {
        super(navn, virkestoff, pris);

        this.styrke = styrke;
    }

    /***********/
    /* GETTERE */
    /***********/

    public String hentKlassenavn() {
        return klassenavn;
    }

    public int hentStyrke() {
        return styrke;
    }

    /*****************/
    /* ANDRE METODER */
    /*****************/

    @Override
    public String toString() {
        return "Navn: " + hentNavn() + 
        "\n   Virkestoff: " + hentVirkestoff() + " mg" +
        "\n   Pris: " + hentPris() + " kr" +
        "\n   Objekt-ID: " + hentID() +
        "\n   Vanedannende styrke: " + hentStyrke();
    }
    
}
