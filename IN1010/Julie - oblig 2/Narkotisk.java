public class Narkotisk extends Legemiddel { 
    //arver egenskapene til Legemiddel-klassen
    
    private int narkStyrke; //setter variabelen privat så den bare kan brukes i denne subklassen.


    public Narkotisk(String navn, int pris, double virkestoff, int styrke )
    {
        super(navn,pris,virkestoff); //kaller superklassens konstruktør
        narkStyrke = styrke; // legger til styrke som tillegg til konstruktøren fra superklassen
    }

    public int hentNarkotiskStyrke()
    {
        return narkStyrke;
    }

    @Override
    public String toString()
    {
        String narkoinfo = super.toString(); // Stringen legemiddelInfo printes 
        narkoinfo += "\nNarkotisk styrke: " + hentNarkotiskStyrke() + "mg"; //legger til info om styrke
        return narkoinfo;  //returnerer ny string med felles innhold
    }

}

