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

    public MilResept(Legemiddel legemiddel, Lege utskrivendeLege, Pasient pasient) {
        super(legemiddel, utskrivendeLege, pasient, 3); // henter variabler fra HvitResept, men setter default verdi for reit
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
    
    @Override
    public String toString() {
        return "MILITAERRESEPT (hvit) (ID: " + hentID() + "):" +
        "\n   Legemiddel: " + legemiddel.hentNavn() + 
        "\n   Utskrivende lege: " + utskrivendeLege.hentNavn() +
        "\n   Pasient-ID: " + hentPasientID() +
        "\n   Antall bruk igjen: " + hentReit() +
        "\n   Rabatt: " + hentRabatt() + "%" +
        "\n   Pris aa betale: " + prisAaBetale() + " kr";
    }
}
