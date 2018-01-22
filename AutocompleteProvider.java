import java.util.LinkedList;
import java.util.List;

/**
 * Class for using a tree of AlphabetNodes to provide autocomplete suggestions
 */
public class AutocompleteProvider{
  
  private AlphabetNode root;
  
  /**
   * Constructor
   * Initializes root using the 0-argument AlphabetNode constructor
   */
  public AutocompleteProvider(){
    this.root = new AlphabetNode();
  }
  
  /**
   * Gives autocomplete suggestions based on the letters inputted and words used to train the AlphabetNode tree
   * @param String of an incomplete word, ideally the beginning of one or more words inserted in the AlphabetNode tree
   * If not, a message is printed and suggestions are given anyways
   * @return a List<Candidate> of suggestions which begin with the inputted String, sorted by confidence in highest to lowest
   */
  public List<Candidate> getWords(String fragment){
    AlphabetNode preLetters = root;
    while (fragment.length() > 0){
      int index = preLetters.getIndex(fragment.charAt(0));
      try{
      preLetters = preLetters.getChild(index);
      } catch(IndexOutOfBoundsException e){
        System.out.println("Nothing beginning with your fragment.");
        break;
      }
      fragment = fragment.substring(1);
    }
    LinkedList<Candidate> retList = new LinkedList<Candidate>();
    return preLetters.orderedTraversal(retList);
  }
  
  /**
   * A second version of getWords that returns a String
   * @param String of an incomplete word, ideally the beginning of one or more words inserted in the AlphabetNode tree
   * If not, a message is printed and suggestions are given anyways
   * @return a String of suggested words, sorted by confidence, to be used in a println statement
   */
  public String getWordsAsString(String fragment){
    List<Candidate> suggestions = getWords(fragment);
    String s = "";
    for (int i = 0; i < suggestions.size()-1; i++){
      s+=suggestions.get(i).getWord()+" ";
    }
    s+=suggestions.get(suggestions.size()-1).getWord();
    return s;
  }
  
  /**
   * Fills out the AlphabetNode tree with words from the inputted String. Everything is made lowercase and all non-
   * English letters are removed
   * @param String to train the AlphabetNode tree; ideally a sentence as words are determined by spaces
   */
  public void train(String passage){
    String[] words = passage.split(" ");
    for (int i = 0; i < words.length; i++){
      words[i] = words[i].toLowerCase();
      if ((words[i].charAt(words[i].length()-1) < 97) || (words[i].charAt(words[i].length()-1) > 122))
        words[i] = words[i].substring(0, words[i].length()-1);
      root.insert(words[i], new Candidate(words[i]));
    }
  }
}