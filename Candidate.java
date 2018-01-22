/**
 * Class for tying a String word to how often it's used
 */
public class Candidate{
  
  private String word;
  private int count;
  
  /**
   * Constructor
   * @param a String representing a word for the Candidate to hold
   * Count is always initialized to one
   */
  public Candidate(String word){
    this.word = word;
    this.count = 1;
  }
  
  /**
   * Returns the word of this Candidate
   * @return the Candidate's word
   */
  public String getWord(){
    return word;
  }
  
  /**
   * Returns the confidence of this Candidate, represented by its count variable
   * @return int for the frequency of the word associated with this Candidate in the training data
   */
  public Integer getConfidence(){
    return count;
  }
  
  /**
   * Increments count by one
   */
  public void increment(){
    count++;
  }
  
  /**
   * Candidates are compared soley based on their String word, not confidence
   * Candidates can be compared to Strings
   * @param a polymorphic Object with a runtime type of String or Candidate
   * @returns true if they are equal, false if not
   */
  @Override
  public boolean equals(Object o){
    if ((o instanceof Candidate) || (o instanceof String)){
      if (o instanceof Candidate)
        return ((Candidate)o).word.equalsIgnoreCase(this.word);
      else
        return ((String)o).equalsIgnoreCase(this.word);
    }
    else
      return false;
  }
}