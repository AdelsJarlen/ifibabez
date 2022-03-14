/* Klassedefinisjon for Parent-classen Lege.
Er IKKE abstract fordi det skal vaere mulig aa opprette objekter av klassen, 
altsaa vanlige leger. Men hvis legen er spesialist skal det i stedet opprettes
et objekt av child-klassen Spesialist.
*/

public class Lege {

    /*******************/
    /* KLASSEVARIABLER */
    /*******************/

    private String navn;

    /****************/
    /* KONSTRUKTOER */
    /****************/

    public Lege(String navn) {
        this.navn = navn;
    }

    /***********/
    /* GETTERE */
    /***********/

    public String hentNavn() {
        return navn;
    }

    /*****************/
    /* ANDRE METODER */
    /*****************/

    @Override
    public String toString() {
        return "Navn: " + hentNavn();
    }
}
