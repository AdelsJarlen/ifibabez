/* Klassedefinisjon for parent-klassen Resept.
Er abstract fordi det ikke skal vaere mulig aa opprette objekter av den, 
bare av children-klassene (HvitResept, BlaaResept, MilResept og PResept).
*/

abstract class Resept {
    
    /*******************/
    /* KLASSEVARIABLER */
    /*******************/

    protected Legemiddel legemiddel;
    protected Lege utskrivendeLege;
    protected Pasient pasient;
    protected int reit;

    // Static variabel for aa telle antall instanser som blir opprettet
    // og for aa gi hver instans en unik ID (okes med 1 hver gang konstruktoren kalles)
    protected static int antObjekter;
    protected int objektID;

    /****************/
    /* KONSTRUKTOER */
    /****************/

    public Resept(Legemiddel legemiddel, Lege utskrivendeLege, Pasient pasient, int reit) {
        this.legemiddel = legemiddel;
        this.utskrivendeLege = utskrivendeLege;
        this.pasient = pasient;
        this.reit = reit;

        antObjekter++;
        objektID = antObjekter; // gir unik objektID for resepten
    }

    /***********/
    /* GETTERE */
    /***********/

    public int hentId() {
        return objektID;
    }

    public Legemiddel hentLegemiddel() {
        return legemiddel;
    }

    public Lege hentLege() {
        return utskrivendeLege;
    }

    public int hentPasientID() {
        return pasient.hentID();
    }

    public int hentReit() {
        return reit;
    }

    public int hentID() {
        return objektID;
    }

    public static int hentAntObjekter() {
        return antObjekter;
    }

    /***********/
    /* SETTERE */
    /***********/

    // Lager egen metode for aa redusere Reit i tilfelle man vil bruke resepten
    // flere ganger paa samme tid
    public void brukReit(int antGanger) {
        reit -= antGanger;
    }

    /*****************/
    /* ANDRE METODER */
    /*****************/

    public boolean bruk() {

        if (hentReit() == 0) {
            return false; // hvis resepten ikke har flere bruk igjen, reit == 0
        } else {
            brukReit(1); // bruker resepten 1 gang
            return true;
        }
    }

    /*********************/
    /* ABSTRAKTE METODER */
    /*********************/

    abstract public String farge();
    
    abstract public int prisAaBetale();
}
