class IndeksertListe <T> extends Lenkeliste<T> { 

    //Setter inn x i listen i posisjon pos der 0<=pos<=stoerrelse().
    public void leggTil (int pos, T x) {  
        if (pos < 0 || pos > antiBruk) //hvis gitt pos er større enn 0 eller større enn antiBruk
        throw new UgyldigListeindeks(pos);

    
        Node nodeTo = new Node(x); //lager ny node
        Node tmp = forste;  //oppretter temporary som utgangspunkt.


        if(pos == 0){
        nodeTo.neste = forste;
        forste = nodeTo;  }

        else {  // går videre i lenken hvis "tmp" peker på en node
        
            for(int i = 0; i < pos-1; i++){
                tmp = tmp.neste; }
            
            nodeTo.neste = tmp.neste;  // Peker til objekt etter gitt posisjon

            tmp.neste = nodeTo; // lagre temp-noden som neste-peker for den nye noden i gitt posisjonen
            }
        
        antiBruk++; //Øker ant noder i lenken
        return;
    }
    

   
    
    //Kaller på Ugyldighetsklassen fra oppgaveteksten, lovlig pos er 0<=pos<stoerrelse().
    public void sett (int pos, T x) {  
        if (pos < 0 || pos >= storrelse()|| forste == null)
        throw new UgyldigListeindeks(pos);
        
            Node a = forste;
        
            for(int i = 0; 
                i < pos; 
                i++) {
                a = a.neste; }
        
            a.data = x;
            return;
        } // erstatter elementet i pos med x. 
    
   
   
    //Skal hente elementet i gitt posisjon der 0<=pos<stoerrelse()
    public T hent (int pos) {
 
        if (pos < 0 || pos >= storrelse() || forste == null)
        throw new UgyldigListeindeks(pos);

        Node tmp = forste;

        for(int i = 0; i < pos; i++){
        tmp = tmp.neste;
        }

        return tmp.data;
    
    }
   
   
    //fjerner elementet i pos der 0<=pos<stoerrelse(), og returnere det.
     public T fjern(int pos){
        if (pos < 0 || pos >= storrelse() || forste == null)
        throw new UgyldigListeindeks(pos);

        Node tmp = forste;
        Node forrige = forste;

        // dersom første node skal fjernes
        if(pos == 0){
        forste = tmp.neste;
        }

        else {  //stopper på pos før den pos som fjernes
       
        for(int i = 0; i < pos-1; i++){
            tmp = tmp.neste; }

        forrige = tmp.neste;  // lagrer noden som fjernes

      
        if(forrige.neste != null){  // forrige node må peke til den neste 
            tmp.neste = forrige.neste;  }

        else{
            tmp.neste = null; }

        tmp = forrige; // noden som er fjernet er nå tmp
        }

        antiBruk--; //reduserer antalliBruk da vi har fjernet en. 
       
        return tmp.data;  //returner data til tmp som vi fjernet. 
    }
    


} 


// E2: Sjekk klassen med testene i TestIndeksertListe.java.
// - Ja, den kjører feilfritt :-) 





    
    