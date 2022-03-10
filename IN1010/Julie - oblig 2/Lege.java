public class Lege {
    
  protected String navn;
  //protected så bare klassen og de som reffererer til objektet kan beytte den
    

//Konstruktoren i Lege tar kun inn en String med legens navn
  Lege(String navn)
  {
    this.navn = navn;
  } //Input navn settes som legens navn


// Lege skal ha en metode for a hente ut navnet til legen.
  public String hentNavn()
  {
    return navn;
  }

  
  public String toString()
  {
    String legeinfo = navn;
    return legeinfo;
  }

  
    
}
