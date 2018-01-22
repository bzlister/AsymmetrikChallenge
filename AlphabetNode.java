import java.util.LinkedList;
import java.util.ArrayList;

/** 
  * Class for the data structure used to store training data
  * Roughly equivalent to a 26-ary tree
  */
public class AlphabetNode {
  
  public char marker;//Single letter used to designate what the words in the node's subtree begin with
  private Candidate word;//AlphabetNodes with a Candidate have had it inserted to that position in the tree 
  private ArrayList<AlphabetNode> children;//should not be longer than 26. Results in a tree data structure of AlphabetNodes
  
  /**
   * Constructor
   * @param a lowercase letter, acting as a precursor for words in the subtree. Use when inserting a word
   */
  public AlphabetNode(char marker){
    this.marker = marker;
    this.children = new ArrayList<AlphabetNode>();
  }
  
  /**
   * Constructor
   * Takes no parameters. Use when creating the root of a new tree
   */
  public AlphabetNode(){
    this.children = new ArrayList<AlphabetNode>();
  }
  
  /**
   * Inserts a new word into a marker-designated position in the tree
   * The letters of the String word are taken off and used to traverse the tree
   * When the String is entirely gone, the current (returned by 'this') node is the one to set the Candidate to
   * @param a String version of the word, and a Candidate-wrapped version of the word
   */
  public void insert(String word, Candidate finalWord){
    if (word.length() > 0){
      int index = this.getIndex(word.charAt(0));
      try{
      if (word.charAt(0) == this.children.get(index).marker)
        this.children.get(index).insert(word.substring(1), finalWord);
      else{
        AlphabetNode newNode = new AlphabetNode(word.charAt(0));
        this.children.add(index, newNode);
        newNode.insert(word.substring(1), finalWord);
      }
      } catch (IndexOutOfBoundsException e){
        AlphabetNode newNode = new AlphabetNode(word.charAt(0));
        this.children.add(index, newNode);
        newNode.insert(word.substring(1), finalWord);
      }
    }
    else{
      if (finalWord.equals(this.word))
        this.word.increment();
      else
        this.word = finalWord;
    }
  }
  
  /**
   * Gets the current AlphabetNode's nth child
   * @param integer index corresponding to the position of the desired AlphabetNode
   * @return the AlphabetNode which is the current AlphabetNode's nth child
   */
  public AlphabetNode getChild(int index){
    return this.children.get(index);
  }
  
  /**
   * Returns a List of all Candidates in the subtree, sorted by confidence
   * @param LinkedList<Candidate> The method recursivley mutates the parameter, so an empty LinkedList is needed
   * when the method is first called
   * @return LinkedList<Candidate> the initially empty LinkedList, now filled with Candidates and sorted
   */
  public LinkedList<Candidate> orderedTraversal(LinkedList<Candidate> retList){
    if (this.word != null){
      int i = 0;
      while((i < retList.size()) && (this.word.getConfidence() < retList.get(i).getConfidence()))
        i++;
      retList.add(i, this.word);
    }
    for (int j = 0; j < this.children.size(); j++){
      retList = this.children.get(j).orderedTraversal(retList);
    }
    return retList;
  }
  
  /**
   * Finds the current AlphabetNode's child with the same marker as the parameter
   * @param A char for finding the corresponding AlphabetNode
   * @return the index of the matched AlphabetNode in its parents' list of children
   */
  public int getIndex(char c){
    int i = 0;
    if (c < 97)
      c+=32;
    /*
     * I actually believe this is ok to search in this manner. If every node had a 26-element array of children allowing 
     * for constant-time access, the space complexity would be prohibitive. In the way I chose, nodes are only added when
     * a new word is inserted, meaning that even an AlphabetNode in even a highly-trained tree has few children (there are
     * no English words beginning with 'zk' for instance)
     */
    while ((i < this.children.size()) && (c != this.children.get(i).marker))
      i++;
    return i;
  }
}