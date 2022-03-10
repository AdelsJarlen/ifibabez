class Prioritetskoe <T extends Comparable<T>> extends Lenkeliste<T> {
    
    @Override
    public void leggTil(T x){ //skal legge de i sortert rekkefølge
        Node a = forste;
        Node forrige = forste;


        if (storrelse() == 0){ //dersom listen er tom 
            forste = new Node(x);
            antiBruk++;    //økes pga ny ble lagt inn
            return;
        }

        while (!(a == null)) { //gaar igjennom elementene
          
            if (x.compareTo(a.data) > 0) { //hvis x er større, gå videre i liste
                forrige = a;
                a = a.neste;
            }

            else { //legger til element i lenken, foran a
                antiBruk++;
                if (forrige == a){ //hvis det bare er ett element i listen
                    forste = new Node(x);
                    forste.neste = a;
                    return;
                }

                forrige.neste = new Node(x);
                Node nye = forrige.neste;
                nye.neste = a;
                return;
            }
        }
        antiBruk++; 
        forrige.neste = new Node(x); //hvis x er størst, legg inn på slutten
        //kjøres om while-løkken er ferdig og x er større enn alle de andre. 
    }



}


//   F2: Sørg for at listen din kommer gjennom testene i TestPrioritetskoe.java.
// - Ja, den kjører feilfritt 