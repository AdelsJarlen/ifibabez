/* Klassedefinisjon for child-classen BlaaResept.
Arver alle fields og metoder fra parent-klassen Resept, 
men skal i tillegg ha et felt for en rabatt paa 75% og et felt
for aa holde styr paa reseptens farge.
Disse er final fordi de aldri skal endres.
*/

public class BlaaResept extends Resept {

    /* KLASSEVARIABLER */
    static final int rabatt = 75;
    static final String farge = "blaa";

    /* KONSTRUKTOER */
    public BlaaResept(Legemiddel legemiddel, Lege utskrivendeLege, Pasient pasient, int reit) {
        super(legemiddel, utskrivendeLege, pasient, reit); // henter variabler fra Resept
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
        return "BLAA RESEPT (ID: " + hentID() + "):" +
        "\n   Legemiddel: " + legemiddel.hentNavn() + 
        "\n   Utskrivende lege: " + utskrivendeLege.hentNavn() +
        "\n   Pasient-ID: " + hentPasientID() +
        "\n   Antall bruk igjen: " + hentReit() +
        "\n   Rabatt: " + hentRabatt() + "%" +
        "\n   Pris aa betale: " + prisAaBetale() + " kr";
    }
    
}
