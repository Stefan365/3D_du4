/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Stefan Veres
 */
public class Pom {
/* Trieda reprezentujuca zoznam, ktoreho kazdy prvok je ineho typu
    */
    
    private String meno; //meno zadavatela
    private String priezvisko;//priezvisko zadavatela
    private int rokN; //rok narodenia zadavatela
    private boolean jeMuz; //indikacia pohlavia 

    public Pom() {
    }
    public Pom(String meno, String priezv, int rok, boolean muz) {
        this.meno = meno;
        this.priezvisko = priezv;
        this.rokN = rok;
        this.jeMuz = muz;
    }

    public String getMeno() {
        return this.meno;
    }

    public String getPriezvisko() {
        return this.priezvisko;
    }

    public int getRokN() {
        return this.rokN;
    }

    public boolean getPohlavie() {
        return this.jeMuz;
    }
}
