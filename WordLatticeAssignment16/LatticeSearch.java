/**
 * Created by euan on 28/02/2016.
 */
public class LatticeSearch extends Search{
    private WordLattice latt;
    private LM bg;

    public LatticeSearch(WordLattice latt, LM bg) {

        this.latt = latt;
        this.bg = bg;
    }


    public WordLattice getLatt() {
        return latt;
    }

    public LM getBg() {
        return bg;
    }
}
