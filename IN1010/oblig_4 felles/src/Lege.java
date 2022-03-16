/* Klassedefinisjon for Parent-classen Lege.
Er IKKE abstract fordi det skal vaere mulig aa opprette objekter av klassen, 
altsaa vanlige leger. Men hvis legen er spesialist skal det i stedet opprettes
et objekt av child-klassen Spesialist.
*/

public class Lege implements Comparable<Lege>{

    /*******************/
    /* KLASSEVARIABLER */
    /*******************/

    private String navn;
    private IndeksertListe<Resept> utskrevneResepter = new IndeksertListe<>();

    /****************/
    /* KONSTRUKTOER */
    /****************/

    public Lege(String navn) {
        this.navn = navn;
    }

    /***********/
    /* METODER */
    /***********/

    public String hentNavn() {
        return navn;
    }

    @Override
    public String toString() {
        return "Navn: " + hentNavn();
    }

    /**
     * Sammenlikner to strenger basert på hver karakters unicode-verdi. 
     * Metoden fungerer ikke pålitelig med navn som inneholder spesialkarakterer som 'æ, ø og å'.
     * return et heltall med differansen mellom unicode-verdien til Lege 'a' subtrahert 'b'.
     */
    @Override
    public int compareTo(Lege lege) {
        String a = this.navn.toLowerCase();
        String b = lege.navn.toLowerCase();

        return a.compareTo(b);
    }

    public void nyResept(Resept ny_resept){
        this.utskrevneResepter.leggTil(ny_resept);
    }

    public IndeksertListe<Resept> hentResepter() {
        return utskrevneResepter;
    }

    public String printResepter() {
        return utskrevneResepter.toString();
    }

    public void sjekkLovligUtskrift(Legemiddel legemiddel) throws UlovligUtskrift {
        if (legemiddel instanceof Narkotisk) {
            System.out.println("Narkotiske legemidler kan kun skrives ut paa blaa resept.");
            throw new UlovligUtskrift(this, legemiddel);
        }
    }
    
    public HvitResept skrivHvitResept(Legemiddel legemiddel, Pasient pasient, int reit) throws UlovligUtskrift {
        sjekkLovligUtskrift(legemiddel);
        HvitResept ny_resept = new HvitResept(legemiddel, this, pasient, reit);
        nyResept(ny_resept);
        return ny_resept;
    }

    public MilResept skrivMilResept(Legemiddel legemiddel, Pasient pasient) throws UlovligUtskrift {
        sjekkLovligUtskrift(legemiddel);
        MilResept ny_resept = new MilResept(legemiddel, this, pasient);
        nyResept(ny_resept);
        return ny_resept;
    }

    public PResept skrivPResept(Legemiddel legemiddel, Pasient pasient, int reit) throws UlovligUtskrift {
        sjekkLovligUtskrift(legemiddel);
        PResept ny_resept = new PResept(legemiddel, this, pasient, reit);
        nyResept(ny_resept);
        return ny_resept;
    }

    public BlaaResept skrivBlaaResept(Legemiddel legemiddel, Pasient pasient, int reit) throws UlovligUtskrift {
        if ((legemiddel instanceof Narkotisk) && !(this instanceof Spesialist)) {
            throw new UlovligUtskrift(this, legemiddel);
        }
        BlaaResept ny_resept = new BlaaResept(legemiddel, this, pasient, reit);
        nyResept(ny_resept);
        return ny_resept;
       
    }
        
}
