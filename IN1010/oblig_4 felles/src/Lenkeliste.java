import java.util.Iterator;


abstract class Lenkeliste <T> implements Liste<T> { 
    
    
        protected int antiBruk;
        protected Node forste;
        protected Node siste; 
    
        class Node{  //oppretter en Node-klasse 
            T data;
            Node neste = null;
            Node forrige = null;
            
        
            Node(T data) { 
              this.data = data; }
        }
    

     //indre klasse for iterator
    class LenkelisteIterator implements Iterator<T>{
    //Hvordan skal dette implementeres egentlig?


    //Instansvariabler
    protected Node p = forste;

    //sjekk om element eksisterer
    public boolean hasNext(){
      if( p != null ){
        return true;
      }
      else{
        return false;
      }
    }

    //returner neste element
    public T next(){

      Node temp = p;

      //flytter peker til neste node slik at hasNext() kan sjekke
      p = p.neste;

      //returner data fra elementet vi startet med i metoden
      return temp.data;
    }


    public void remove(){}

  }


    public int storrelse(){
        return antiBruk;
    }   //returnere hvor mange elementer det er i listen.

   

    public void leggTil(T x)
    {   Node nodeEn = new Node(x); //Oppretter først en ny node

        //legger til første node dersom det ikke er noen i listen i starten
        if(forste == null)
        {  forste = nodeEn;
        }
    
        else {  //legg node til i slutten av lenken

            Node a = forste;  // lager Node a som utgangspunkt
            while(a.neste != null){
                a = a.neste;   // gå videre dersom "neste" peker på en node
            }

            a.neste = nodeEn;
            } // lagrer noden i enden av lenken.

        antiBruk++; //oppdatterer antallet når Node er lagt til
        return;
    }
    

    public T hent() throws UgyldigListeindeks{
        if (forste == null) {throw new UgyldigListeindeks(-1); }
        return forste.data;
    } //returnere det 1. elementet i listen, uten at det fjernes fra listen.
  

   
    public T fjern() throws UgyldigListeindeks{
        if (antiBruk == 0) {throw new UgyldigListeindeks(0); }
        Node a = forste;    //lagrer 1. node
        forste = a.neste;
        antiBruk--;        // fjerner en node fra total-antall
        return a.data;    // returnerer den første noden
    } 

     //iterator objekt
    public Iterator<T> iterator(){
    LenkelisteIterator iterator = new LenkelisteIterator();
    return iterator;
    }


    @Override
    public String toString(){ //overskriver toString metode til Object, returnerer leselig strenge for lenkeliste
        String nodeinfo = " ";
        Node nodeEn = forste;
        for (int i = 0; i<storrelse(); i++){
            nodeinfo += nodeEn.data + "-->";
            nodeEn = nodeEn.neste;
        }
        return nodeinfo; //Skriver ut lesbar string med nodeinfo
    }
        
}


