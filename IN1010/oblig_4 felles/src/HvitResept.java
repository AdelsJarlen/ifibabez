/* Klassedefinisjon for child-classen HvitResept.
Arver alle fields og metoder fra parent-klassen Resept, 
men skal i tillegg ha et felt som holder styr paa fargen paa resepten.
Dette feltet er final fordi fargen skal vaere den samme for alle hvite resepter
og dens children.
*/
public class HvitResept extends Resept {

    /* KLASSEVARIABLER */
    static final String farge = "hvit";

    /* KONSTRUKTOER */
    public HvitResept(Legemiddel legemiddel, Lege utskrivendeLege, Pasient pasient, int reit) {
        super(legemiddel, utskrivendeLege, pasient, reit); // henter variabler fra Resept
    }

    /* GETTERE */
    @Override
    public String farge() {
        return farge;
    }

    /* ANDRE METODER */
    // Returnerer bare pris fordi ordinaere hvite resepter ikke gir rabatt
    @Override
    public int prisAaBetale() {
        return legemiddel.hentPris();
    }

    @Override
    public String toString() {
        return "HVIT RESEPT (ID: " + hentID() + "):" +
        "\n   Legemiddel: " + legemiddel.hentNavn() + 
        "\n   Utskrivende lege: " + utskrivendeLege.hentNavn() +
        "\n   Pasient-ID: " + hentPasientID() +
        "\n   Antall bruk igjen: " + hentReit() +
        "\n   Pris aa betale: " + prisAaBetale() + " kr";
    }
}
