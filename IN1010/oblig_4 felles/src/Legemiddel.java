/* Klassedefinisjon for Parent-classen Legemiddel.
Er abstract fordi det ikke skal opprettes instanser av den, den brukes bare
for aa gi videre metoder og fields til sine children (Vanedannende, Narkotisk og Vanlig).
*/

abstract class Legemiddel {

    /* KLASSEVARIABLER */
    // Beskyttede instansvariabler som skal arves av alle legemidler
    // Virkestoff i milligram (float), pris i hele kroner (int), navn som String
    protected String navn;
    protected double virkestoff;
    protected int pris;

    // Static variabel for aa telle antall instanser som blir opprettet
    // og for aa gi hver instans en unik ID (okes med 1 hver gang konstruktoren kalles)
    protected static int antObjekter;
    protected int objektID;

    /* KONSTRUKTOER */
    public Legemiddel(String navn, double virkestoff, int pris) {
        this.navn = navn;
        this.virkestoff = virkestoff;
        this.pris = pris;

        antObjekter++;
        objektID = antObjekter; // gir unik objektID for legemidlet
    }

    /* GETTERE */
    public String hentNavn() {
        return navn;
    }

    public double hentVirkestoff() {
        return virkestoff;
    }

    public int hentPris() {
        return pris;
    }

    public int hentID() {
        return objektID;
    }

    public static int hentAntObjekter() {
        return antObjekter;
    }

    /* ABSTRAKTE METODER */
    abstract public String hentKlassenavn();
}