import junit.framework.TestCase;
import java.util.LinkedList;
import java.util.List;
import org.junit.*;
/**
 * A JUnit test case class.
 * Every method starting with the word "test" will be called when running
 * the test with JUnit.
 */
public class TestAutocomplete extends TestCase {
  
  /**
   * A test method.
   * (Replace "X" with a name describing the test.  You may write as
   * many "testSomething" methods in this class as you wish, and each
   * one will be called when running JUnit over this class.)
   */
  private AutocompleteProvider ar;
  private List<Candidate> thiList;
  private List<Candidate> neeList;
  private List<Candidate> thList;
  private Candidate cand;
  
  
  @Before
  public void setUp(){
    ar = new AutocompleteProvider();
    ar.train("The third thing that I need to tell you is that this thing does not think thoroughly.");
    thiList = ar.getWords("thi");
    neeList = ar.getWords("nee");
    thList = ar.getWords("th");
    cand = new Candidate("example");
  }
  
  public void testConfidence() {
    assertTrue(thiList.get(0).getConfidence() == 2);
    assertTrue(neeList.get(0).getConfidence() == 1);
    assertTrue(thList.get(6).getConfidence() == 1);
    assertTrue(thList.get(1).getConfidence() == 2);
  }
  
  public void testIncrement() {
    cand.increment();
    assertTrue(cand.getConfidence() == 2);
  }
  
  public void testEquals() {
    Candidate cand2 = new Candidate("example");
    assertTrue(cand2.equals(cand));
    cand2.increment();
    assertTrue(cand2.equals(cand));
    Candidate cand3 = new Candidate("test");
    assertFalse(cand3.equals(cand2));
  }
  
  public void testWordsAsString(){
    String s = "";
    for (int i = 0; i < thiList.size(); i++){
      s+=thiList.get(i).getWord() + " ";
    }
    s = s.substring(0, s.length()-1);
    assertTrue(s.equals(ar.getWordsAsString("thi")));
  }
  
  public void testAdditionalWords(){
    ar.train("zebra third zebra third third zebra zebra zebra zebra");
    assertTrue(ar.getWords("thi").get(0).getConfidence() == 4);
    assertTrue(ar.getWords("").get(0).getConfidence() == 6);
  }  
}
