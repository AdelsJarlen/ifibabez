
//Integrasjonstesten skal:
//Opprette minimum en instans av hver eneste klasse og la disse inneholde nødvendige referanser til andre objekter.
//Skrive ut relevant informasjon om hvert enkelt objekt. (Her vil det lønne seg å ha overskrevet toString() metoden i alle klassene du har skrevet).

public class Integrasjonstest 
{
  public static void main(String [] args)
  { //Bruker de samme som i Test-Resept klassen:
    Narkotisk fentanyl = new Narkotisk("Fentanyl", 1500, 4.20,1 );
    Vanedannende imovane = new Vanedannende("imovane", 250, 20.0, 10);               
    Vanlig paracet = new Vanlig("Paracet", 39 , 500);
    Vanlig ibux = new Vanlig("Ibux", 45, 200);
    
    //Opprett lege og legespesialt:
    Lege lege = new Lege("Dr. Haukaas");        
    Spesialist spesialist = new Spesialist("Dr. Spesialist","1A2B3C");
    
    //Oppretter resepter, slik som i Test-Resept:
    HvitResept hvit = new HvitResept(paracet, lege, 101, 1);
    BlaResept bla = new BlaResept(imovane, spesialist, 202, 2);       
    P_Resept PResept = new P_Resept(ibux, lege, 303,2);
    MillResept millResept = new MillResept(fentanyl, spesialist, 404);
    //Skrives ut basert på toString() metodene som er opprettet


    //Tester at info fra test-metoden ovenfor fungerer:
    System.out.println(" ");
    System.out.println("Info om instansene som er opprettet i programmet: ");
    System.out.println(hvit + "\n");  //hvit resept med lege
    System.out.println(bla + "\n");   //bla resept med spesialist      
    System.out.println(PResept + "\n");//fra hvit resept - PResept med lege
    System.out.println(millResept + "\n");//fra hvit resept - Millresept med spesialist
    System.out.println(lege + "\n");  //info om legen
    System.out.println(spesialist+ "\n");//info om spesialist

  


  

// Forsokte aa opprette objektet som skal tegnes i oppgave E - datastrukturtegning:
//Legemidlet skal være narkotisk, resepten skal være en militærresept skrevet ut på dette 
//legemiddelet og legen er en spesialist (som har skrevet ute denne resepten).

  //Kode til datastrukturtegning som er levert som PDF: 

    Narkotisk oxycontin = new Narkotisk("Oxicontin", 499, 10, 10);  
    //Oppretter et legemiddel-objekt

    Spesialist spesialistlege2 = new Spesialist("Dr. Datastruktur" , "777");
    //Oppretter et legeobjekt - som er bygd på subklassen Spesialist

    MillResept dataStrukturResept = new MillResept(oxycontin, spesialistlege2, 1234);
    //Oppretter en resept basert på MillResept

    System.out.println("\nPrinter info om resepten fra oppgave E - Datastruktur: ");
    System.out.println(dataStrukturResept + "\n");
    
  
  }
  
 
}







    
