import java.util.ArrayList;
import java.util.stream.Collectors;

/**
 * Created by euan on 28/02/2016.
 */
public class LatticeState extends SearchState{
    private WordH wordH;
    private int i;
    private int transitionCost;

    public LatticeState(WordH wordH, int i,WordLattice lat) {
        this.wordH = wordH;
        this.i = i;
        //uncomment if using search4 as framework (for A*)
        /*this.estRemCost = lat.getEndTime()-wordH.getEnd();*/
    }

    @Override
    public int getLocalCost() {
        return wordH.getCost() + transitionCost;
    }


    @Override
    boolean goalP(Search searcher) {
        if(searcher instanceof  LatticeSearch){
            WordLattice lat = ((LatticeSearch) searcher).getLatt();
            return wordH.getEnd() == lat.getEndTime();
        }
        return false;
    }

    @Override
    ArrayList<SearchState> getSuccessors(Search searcher) {
        ArrayList<SearchState> list = new ArrayList<SearchState>();
        if(searcher instanceof  LatticeSearch){
            LatticeSearch latticeSearch = (LatticeSearch) searcher;
            WordLattice lat = latticeSearch.getLatt();

            for(WordH word : lat.wordsAtT(this.wordH.getEnd())){
                LatticeState state = new LatticeState(word,i+1,lat);
                state.transitionCost = latticeSearch.getBg().getCost(wordH.getWord(),word.getWord());
                list.add(state);
            }
        }
        return list;
    }

    @Override
    boolean sameState(SearchState n2) {
        if(n2 instanceof LatticeState){
            LatticeState other = (LatticeState) n2;
            return this.wordH == other.wordH && this.i == other.i;
        }
        return false;
    }

    @Override
    public String toString() {
        return wordH.toString() + " " + i;
    }
}
