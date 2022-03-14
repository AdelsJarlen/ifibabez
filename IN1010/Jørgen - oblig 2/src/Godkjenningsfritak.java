/* Offentlig grensesnitt for egenskapen godkjenningsfritak, som kan
gjelde for ulike typer helsepersonell som jobber med resepter.

Forteller klassen Spesialist at den maa ha en metode for aa hente
ut kontrollID-variabelen.*/

public interface Godkjenningsfritak {

    public String hentKontrollID();

}