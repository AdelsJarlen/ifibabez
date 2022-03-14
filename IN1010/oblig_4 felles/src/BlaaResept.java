/* Klassedefinisjon for child-classen BlaaResept.
Arver alle fields og metoder fra parent-klassen Resept, 
men skal i tillegg ha et felt for en rabatt paa 75% og et felt
for aa holde styr paa reseptens farge.
Disse er final fordi de aldri skal endres.
*/

public class BlaaResept extends Resept {

    /*******************/
    /* KLASSEVARIABLER */
    /*******************/

    static final int rabatt = 75;
    static final String farge = "blaa";

    /****************/
    /* KONSTRUKTOER */
    /****************/

    public BlaaResept(Legemiddel legemiddel, Lege utskrivendeLege, int pasientID, int reit) {
        super(legemiddel, utskrivendeLege, pasientID, reit); // henter variabler fra Resept
    }

    /***********/
    /* GETTERE */
    /***********/

    @Override
    public String farge() {
        return farge;
    }

    public int hentRabatt() {
        return rabatt;
    }

    /*****************/
    /* ANDRE METODER */
    /*****************/

    @Override
    public int prisAaBetale() {
        return legemiddel.hentPris() * (100-rabatt)/100;
    }

    // Bruker substring().toUpperCase() for aa faa stor bokstav paa farge
    @Override
    public String toString() {
        return "Reseptfarge: " + farge().substring(0, 1).toUpperCase() + farge().substring(1).toLowerCase() +
        "\nLegemiddel: " + legemiddel.hentNavn() + 
        "\nUtskrivende lege: " + utskrivendeLege.hentNavn() +
        "\nPasient-ID: " + hentPasientID() +
        "\nAntall bruk igjen: " + hentReit() +
        "\nRabatt: " + hentRabatt() + "%" +
        "\nPris aa betale: " + prisAaBetale() + " kr" +
        "\nObjekt-ID: " + hentID();
    }
    
}
