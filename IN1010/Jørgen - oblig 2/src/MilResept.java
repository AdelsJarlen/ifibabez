/* Klassedefinisjon for child-classen MilResept.
Arver alle fields og metoder fra parent-klassen HvitResept,
som igjen arver alt fra Resept. 
Men skal i tillegg ha et felt for en rabatt paa 100% og et felt
for aa holde styr paa reseptens type.
Disse variablene er final fordi de aldri skal endres.
*/

public class MilResept extends HvitResept {

    /*******************/
    /* KLASSEVARIABLER */
    /*******************/

    static final int rabatt = 100;
    static final String reseptType = "militaerresept";

    /****************/
    /* KONSTRUKTOER */
    /****************/

    public MilResept(Legemiddel legemiddel, Lege utskrivendeLege, int pasientID) {
        super(legemiddel, utskrivendeLege, pasientID, 3); // henter variabler fra HvitResept, men setter default verdi for reit
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

    // Generell prisfunksjon, ganger med 100% rabatt = 0kr
    // Beholder utregningen selv om det er 0 i tilfelle man vil gjore endringer
    @Override
    public int prisAaBetale() {
        return legemiddel.hentPris() * (100-rabatt)/100;
    }
    
    // Bruker substring().toUpperCase() for aa faa stor bokstav paa farge og paa reseptType
    @Override
    public String toString() {
        return "Reseptfarge: " + farge().substring(0, 1).toUpperCase() + farge().substring(1).toLowerCase() +
        "\nType: " + hentReseptType().substring(0,1).toUpperCase() + hentReseptType().substring(1).toLowerCase() +
        "\nLegemiddel: " + legemiddel.hentNavn() + 
        "\nUtskrivende lege: " + utskrivendeLege.hentNavn() +
        "\nPasient-ID: " + hentPasientID() +
        "\nAntall bruk igjen: " + hentReit() +
        "\nRabatt: " + hentRabatt() + "%" +
        "\nPris aa betale: " + prisAaBetale() + " kr" +
        "\nObjekt-ID: " + hentID();
    }
}
