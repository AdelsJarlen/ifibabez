/* Klassedefinisjon for child-classen PResept.
Arver alle fields og metoder fra parent-klassen HvitResept,
som igjen arver alt fra Resept. 
Men skal i tillegg ha et felt for en rabatt paa 108kr og et felt
for aa holde styr paa reseptens type.
Disse variablene er final fordi de aldri skal endres.
*/

public class PResept extends HvitResept {

    /*******************/
    /* KLASSEVARIABLER */
    /*******************/

    static final String reseptType = "P-resept";
    static final int rabatt = 108;

    /****************/
    /* KONSTRUKTOER */
    /****************/

    public PResept(Legemiddel legemiddel, Lege utskrivendeLege, Pasient pasient, int reit) {
        super(legemiddel, utskrivendeLege, pasient, reit);
    }

    /***********/
    /* GETTERE */
    /***********/

    public int hentRabatt() {
        return rabatt;
    }

    public String hentReseptType() {
        return reseptType;
    }
    
    /*****************/
    /* ANDRE METODER */
    /*****************/

    // Returnerer 0 hvis totalen er mindre enn 0, ellers rabattert pris
    @Override
    public int prisAaBetale() {
        return Math.max(0, (legemiddel.hentPris() - hentRabatt()));
    }

    // Bruker substring().toUpperCase() for aa faa stor bokstav paa farge. P-resept er alltid med stor P uansett
    @Override
    public String toString() {
        return "Reseptfarge: " + farge().substring(0, 1).toUpperCase() + farge().substring(1).toLowerCase() +
        "\nType: " + hentReseptType() +
        "\nLegemiddel: " + legemiddel.hentNavn() + 
        "\nUtskrivende lege: " + utskrivendeLege.hentNavn() +
        "\nPasient-ID: " + hentPasientID() +
        "\nAntall bruk igjen: " + hentReit() +
        "\nRabatt: " + hentRabatt() + " kr" +
        "\nPris aa betale: " + prisAaBetale() + " kr" +
        "\nObjekt-ID: " + hentID();
    }
}
