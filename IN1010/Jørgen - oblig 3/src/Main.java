public class Main {
    public static void main(String[] args) throws Exception {

        // // Test 1
        // Prioritetskoe<String> k = new Prioritetskoe<>();

        // System.out.println("Test 1 fasit: []" );
        // System.out.println(k.toString());

        // Test 2
        Prioritetskoe<String> k = new Prioritetskoe<>();
        k.leggTil("Anne"); k.leggTil("Berit"); k.leggTil("Chris");

        System.out.println("Test 2 fasit: [Anne, Berit, Chris]");
        System.out.println(k.toString());

        System.out.println("Anne".compareTo("Chris"));

        // // Test 3
        // Prioritetskoe<String> k = new Prioritetskoe<>();
        // k.leggTil("B");  
        // k.leggTil("G");  
        // k.leggTil("C");  
        // k.leggTil("A");
        // k.leggTil("F");
        // k.leggTil("E");
        // k.leggTil("H");
        
        // // k.leggTil("Z");  k.leggTil("X");
        // // k.fjern();
        // System.out.println(k.toString());

    }
}
