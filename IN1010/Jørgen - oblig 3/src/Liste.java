/* OVERDORDNET INTERFACE FOR ALLE LISTER I OBLIGEN
- metoden stoerrelse() skal returnere antall elementer i listen
- leggTil() skal legge til et element sist i listen
- hent() skal returnere det foerste elementet i listen uten aa endre det
- fjern() skal slette det foerste elementet i listen og returnere det */

interface Liste <T> {
    int stoerrelse ();
    void leggTil (T x);
    T hent ();
    T fjern ();
}
