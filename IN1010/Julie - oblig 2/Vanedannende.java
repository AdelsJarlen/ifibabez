public class Vanedannende extends Legemiddel {
     //arver egenskapene til Legemiddel-klassen
    
    private int vaneDanStyrke; //setter variabelen privat så den bare kan brukes i denne subklassen.
    
    
    public Vanedannende(String navn, int pris, double virkestoff, int styrke )
    {           
        super(navn,pris,virkestoff); //kaller superklassens konstruktør
        vaneDanStyrke = styrke; // legger til styrke som tillegg til konstruktøren fra superklassen        }
    }
    public int hentVanedannendeStyrke()
    {
        return vaneDanStyrke;        
    }
    
    @Override
    public String toString()
    {
        String vanedaninfo = super.toString(); // Stringen legemiddelInfo printes 
        vanedaninfo += "\nVanedannende styrke: " + hentVanedannendeStyrke() + "mg"; //legger til info om styrke
        return vanedaninfo;  //returnerer ny string med felles innhold
    }
    


}
