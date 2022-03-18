/* Klassedefinisjon for child-classen Spesialist.
Arver alle fields og metoder fra parent-klassen Lege, 
men skal i tillegg implementere metoden som kreves i interfacet
Godjenningsfritak. Har derfor et felt for aa holde styr paa 
kontrollID.
*/

public class Spesialist extends Lege implements Godkjenningsfritak {

    /*******************/
    /* KLASSEVARIABLER */
    /*******************/

    public String kontrollID;

    /****************/
    /* KONSTRUKTOER */
    /****************/

    public Spesialist(String navn, String kontrollID) {
        super(navn);

        this.kontrollID = kontrollID;
    }

    /***********/
    /* GETTERE */
    /***********/

    @Override
    public String hentKontrollID() {
        return kontrollID;
    }

    /*****************/
    /* ANDRE METODER */
    /*****************/

    @Override
    public String toString() {
        return "Navn: " + hentNavn() + " (Kontroll-ID: " + hentKontrollID() + ")";
    }
}
