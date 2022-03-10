class Stabel <T> extends Lenkeliste <T>  {
    
    @Override
    public void leggTil(T x) {
        super.leggTil(x);       //kaller på Lenkeliste sin metode
        Node ny = new Node(x);
        ny.neste = forste;
        forste = ny;
    } // gjort slik som vist på forelesning


 
}

//   C2: Sørg for at listen din kommer gjennom testene i TestStabel.java før du går videre til Del D.
// - Ja, den kjører feilfritt :-) 

