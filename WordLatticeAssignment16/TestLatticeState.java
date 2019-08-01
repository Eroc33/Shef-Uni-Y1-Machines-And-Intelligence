import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static junit.framework.TestCase.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * Created by euan on 28/02/2016.
 */
public class TestLatticeState {
    LatticeSearch searcher;
    WordH wordA;
    WordH wordB;
    WordH wordC;
    WordLattice latt;

    @Before
    public void setup(){
        latt = new WordLattice();
        wordA = new WordH("a",0,1,1);
        latt.getAllWordHs().add(wordA);
        wordB = new WordH("b",1,2,2);
        latt.getAllWordHs().add(wordB);
        wordC = new WordH("c",2,3,3);
        latt.getAllWordHs().add(wordC);
        String [] vocab= {"a","b","c"};
        int[][] costs = {{1,2,3},
                         {4,5,6},
                         {7,8,9}};
        LM bg = new LM(vocab,costs);
        searcher = new LatticeSearch(latt,bg);
    }

    //Test that on LatticeState goalP returns true if and only if the word ends where the lattice does
    @Test
    public void goalPWorks(){
        LatticeState state = new LatticeState(new WordH("*start*",0,0,0),0,latt);
        assertTrue(state.goalP(searcher));
        state = new LatticeState(new WordH("*start*",0,-5,0),0,latt);
        assertFalse(state.goalP(searcher));
    }

    @Test
    public void getSuccessorsWorks()
    {
        //Test that the only successors to a state are ones setup to come directly after it
        LatticeState state = new LatticeState(new WordH("*start*",0,0,0),0,latt);
        List<SearchState> successors = state.getSuccessors(searcher);
        assertTrue(successors.size() == 1);
        assertTrue(successors.get(0).sameState(new LatticeState(wordA,1,latt)));
        assertFalse(successors.get(0).sameState(new LatticeState(wordC,1,latt)));

        //Test that the end state has no successors
        state = new LatticeState(new WordH("*end*",3,3,0),0,latt);
        successors = state.getSuccessors(searcher);
        assertTrue(successors.size() == 0);
    }

    //Test that LatticeState.sameState only returns true when the same word and position are contained.
    @Test
    public void sameStateWorks(){
        assertTrue(new LatticeState(wordA,0,latt).sameState(new LatticeState(wordA,0,latt)));
        assertFalse(new LatticeState(wordA,0,latt).sameState(new LatticeState(wordA,1,latt)));
        assertFalse(new LatticeState(wordA,0,latt).sameState(new LatticeState(wordB,0,latt)));
        assertFalse(new LatticeState(wordA,0,latt).sameState(new LatticeState(wordB,1,latt)));
    }

}