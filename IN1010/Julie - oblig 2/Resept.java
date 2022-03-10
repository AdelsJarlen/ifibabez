abstract class Resept 
{   
    // Instansvariabler fra oppgavetekst:
    protected int ID;
    protected Legemiddel legemiddel;
    protected Lege utskrivingsLege;
    protected int pasientID;
    protected int reit;

    protected static int tellAntall = 0;
    // private for å brukes i klassen og ikke endres utenfor.


    protected Resept(Legemiddel legemiddel, Lege utskrivingsLege, int pasientID, int reit)
    {
        this.legemiddel = legemiddel;
        this.utskrivingsLege = utskrivingsLege;
        this.pasientID = pasientID;
        this.reit = reit;
        tellAntall +=1; //Når id opprettes øker antallet med 1 
        ID = tellAntall;
    }

    public int hentID()
    {
        return ID;
    }

    public Legemiddel hentLegemiddel()
    {
        return legemiddel;
    }

    public Lege hentLege()
    {
        return utskrivingsLege;
    }

    public int hentPasientID()
    {
        return pasientID;
    }

    public int hentReit()
    {
        return reit;
    }

    public boolean bruk() 
    {//returnerer true/false basert på om resepten er brukt opp. 
        if (reit > 0) //dersom false skal komme opp ved "0" har jeg ">", og ikke "=>"
        {
            reit-= 1;
            return true;
        }
        else 
        { return false;
        }
    }

    abstract public String farge(); //Senere skal resepten være bla eller hvit

    abstract public int prisAaBetale();



    public String toString()
    {   //Lager utskriftsvennlig string av info:
        String reseptInfo = "\nInfo om resept: " + 
                            "\nLegemiddel: " + hentLegemiddel() +
                            "\nLege: " + hentLege() +
                            "\nPasientID: " + hentPasientID() +
                            "\nReit: " + hentReit();
    return reseptInfo;
    }


}

