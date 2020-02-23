//package codeBreaker;
/* 
 * Name: Yvette Xie, Hilary Liu
 * Date: March 25, 2019
 * Assignment name: Code Breaker
 */
import java.util.*; // import the entire util library

public class codeBreaker {

 public static void main(String[] args) {
  final String VALID_CHARS = "GRBYOP";//declare and initialize all constants
  final int SIZE = 4;
  final int TRIES = 10;
  Scanner sc = new Scanner(System.in);//declare scanner
  String[] code = new String[SIZE];//declare arrays and arraylists
  ArrayList<String> input = new ArrayList<String>();
  ArrayList<String> clueList = new ArrayList<String>();
  String[][] guessFinal = new String[TRIES][SIZE];
  String[][] clueFinal = new String[TRIES][SIZE];
  boolean v,correct = true;//boolean for validation
  int turn = 0;//counter

  code = createCode(VALID_CHARS, SIZE);//get the generated code by calling the return method with valid characters and size of code as parameter

  for(int i=0;i<TRIES;i++)
  {
   String[] guess = new String[SIZE];//declare the temporary guess array
   String[] clue = new String[SIZE];//declare the temporary clue array
   correct = true;//set the final check boolean equals to true
   clueList.clear();//empty the temporary clue lists at the beginning of each guess
   input.clear();//empty the temporary guess lists at the beginning of each guess

   System.out.println("Guess " + (i+1));//user instruction
   System.out.println("Colours available:");
   System.out.println("G - green, R - red, B - blue, Y - yellow, O - orange, P - pink");
   System.out.println("If the clue list shows 'b', you put a right colour in the right place");
   System.out.println("If the clue list shows 'w', you put a right colour in a wrong place");
   System.out.println("you have 10 chances to break the code, good luck.");
   System.out.println("Please enter your guess of " +SIZE+ "-colour code only using the letters: " + VALID_CHARS);
   System.out.println("No space is needed in between; upper case only");
   String input1 = sc.nextLine();//get the user input
   for(int j = 0; j<input1.length();j++) {
    input.add(input1.substring(j, j+1));
   }//store the split characters in the user input into input arraylist
   guess = input.toArray(new String[input.size()]);//store the input arraylist to temporary guess array

   v = valid(guess,VALID_CHARS,SIZE);//check whether the input is valid or not using boolean v

   while (v == false){
    input.clear();//empty the temporary guess lists at the beginning of each guess
    System.out.println("Please enter your guess of " + SIZE + "-colour code again using the letters: " + VALID_CHARS);
    input1 = sc.nextLine();//get the user input
    for(int j = 0; j<input1.length();j++) {
     input.add(input1.substring(j, j+1));
    }//store the split characters in the user input into input arraylist
    guess = input.toArray(new String[input.size()]);//store the input arraylist to temporary guess array
    v = valid(guess,VALID_CHARS,SIZE);//check whether the input is valid or not using boolean v
   }//repeat if the input is not validated(when boolean v is false)

   turn++;//add 1 up to the counter

   clueList.addAll(findFullyCorrect(code,guess));//check if the user put a right colour in the right place, if so, add 'b's into the clue
   clueList.addAll(findColourCorrect(code,guess));//check if the user put a right colour in a wrong place, if so, add 'w's into the clue
   clue = clueList.toArray(new String[SIZE]);//add up all clues into temporary clue array

   for(int c = 0; c<SIZE;c++){
    guessFinal[i][c] = guess[c];//add temporary guess array to the final guess 2D array
    clueFinal[i][c] = clue[c];//add temporary clue array to the final clue 2D array
   }

   /*FOR TESTING (print out the randomly generated code)
    * for(int j = 0;j<SIZE;j++){
    System.out.print(code[j] + " ");
   }
   System.out.println();
    */ 

   displayGame(guessFinal,clueFinal);//display the game board(guess and clue)

   for(int r = 0; r<SIZE;r++){
    if(!guessFinal[i][r].equals(code[r])){
     correct = false;//if the temporary guess cannot match the code, make boolean correct equals false
     break;//exit the loop if the temporary guess cannot match the code
    }
   }

   if (correct || (turn==10 && correct == false)) {
    break;//if the final check is correct or the user have been guessed for 10 times, break the loop
   }
  }//end of tries

  if(correct){
   System.out.println("Congratulations! It took you " + turn + " guesses to find the code.");
  }//output this if the user get the code within 10 tries

  else{
   System.out.print("I'm sorry you lose. The correct code was ");//output this if the user did not get the code within 10 tries
   for(int i = 0;i<SIZE;i++){
    System.out.print(code[i] + " ");
   }//output the code to the user
  }
 }


 public static String[] createCode(String VALID_CHARS, int length){
  /**
   * Given a string of available characters, the method 
   * randomly generates a code with a fixed given length
   * by using random number generator and substring method
   * The code created is return to the main method 
   * as a string array
   *
   * @param avaliable characters, length of code
   * @return the randomly generated code as a string array
   */
  Random colour = new Random();// initialize random number generator
  String[] code = new String[length];//create string array for code

  for(int i = 0;i<length;i++){//the code will have a length of SIZE, therefore the letter generation repeats 4 times
   int number = colour.nextInt(VALID_CHARS.length());//System randomly pick a number between 0(inclusive) and the length of VALID_CHARS(exclusive)
   code[i] =VALID_CHARS.substring(number,number+1);//store the randomly picked character into the code array
  }
  return code;//return the randomly generated code

 }

 public static boolean valid(String[] guess, String code, int length){
  /**
   * Return the boolean v to indicate if the guess is valid. The method 
   * compares the characters in guess to the available characters
   * as well as the length of the guess to the fix length of code

   * @param user's guess as a string array, a string containing the code, the length of the code
   * @return boolean v to indicated if the input is valid or not
   */

  boolean v = false;//declare and set a boolean v to false
  int i = 0;//initialize a counter

  while(guess.length ==length && i < length){//repeat if the length of code is the ruled length and counter is less than the ruled length
   v = false;//initialize the boolean each time
   for(int a = 0; a < code.length();a++){
    if(guess[i].equals(code.substring(a, a+1))){
     v = true;
    }
   }//check if the characters in counter place of the code is a ruled valid characters, if so, set the boolean v to true
   i++;//add 1 up to counter
   if (v == false){
    break;//if the boolean v is false, exit the loop
   }
  }
  return v;//return the boolean value 
 }

 public static ArrayList<String> findFullyCorrect(String[] code, String[] guess){
  /**
   * Given the user's guess and the code , the method 
   * check if the user put a right colour in the right place, add 'b's to the black arraylist
   * by using for loop, arraylist and equalsIgnoreCase method
   * The code created is return to the main method 
   * as a string arraylist
   *
   * @param user's guess as a string array, randomly generated code as a string array
   * @return arraylist black, which contains the number of a right colour in the right place
   */
  ArrayList<String> black = new ArrayList<String>();//declare an arraylist

  for(int i = 0; i<code.length; i++){
   if (guess[i].equalsIgnoreCase(code[i])){
    black.add("b");//if the user put a right colour in the right place, add a 'b' to the black arraylist
   }
  }
  return black;//return the value in arraylist
 }

 public static ArrayList<String> removeFullyCorrect(String[] code, String[] guess){
  /**
   * Given the user's guess and the code , the method 
   * check if the user put a right colour in the right place, remove the value from the arraylist
   * by using for loop, arraylist and equals method
   * The code created is return to the findColourCorrect method 
   * as a string arraylist
   *
   * @param user's guess as a string array, randomly generated code as a string array
   * @return arraylist code1, which contains the user guess after remove the fully correct ones
   */
  ArrayList<String> code1 = new ArrayList<String>();//declare an arraylist
  for(int i = 0; i<code.length; i++) {
   code1.add(code[i]);//copy the code to the arraylist
  }

  for(int i = guess.length-1; i>=0 ;i--){
   if (guess[i].equals(code[i])){
    code1.remove(i);
   }
  }//remove the 'b' labeled characters in the arraylist
  return code1;//return the values in arraylist
 }

 public static ArrayList<String> findColourCorrect(String[] code, String[] guess){
  /**
   * Given the user's guess and the code , the method 
   * check if the user put a right colour in a wrong place, add 'w's to the black arraylist
   * by using for loop, arraylist, equalsIgnoreCase and removeFullyCorrect method
   * The code created is return to the main method 
   * as a string arraylist
   *
   * @param user's guess as a string array, randomly generated code as a string array
   * @return arraylist white, which contains the number of a right colour in a wrong place
   */
  ArrayList<String> charLeft = new ArrayList<String>();//declare an arraylist called charLeft
  charLeft = removeFullyCorrect(code,guess);//get the values after remove fully corrects
  ArrayList<String> white = new ArrayList<String>();//declare an arraylist called white

  for(int i = 0;i<guess.length;i++){
   for(int a = 0;a<charLeft.size();a++){
    if(guess[i].equalsIgnoreCase(charLeft.get(a))){
     white.add("w");//if the user put a right colour in a wrong place, add a 'w' to the white arraylist
    }
   }
  }
  return white;//return the value in arraylist
 }

 public static void displayGame(String[][] clue, String[][] guess){
  /**
   * Given the user's guesses and the clues , the method 
   * displays the game board, which includes guesses and clues to user
   * by using for loop, scanner
   * The code created is to display the game board in the main 
   *
   * @param user's guesses as a 2D string array, user's clues as a 2D string array
   * @return prints out the user's guess and the clue
   */

  System.out.println("Guess" + " \t"+ "Clue");
  System.out.println("*****************");

  for(int i = 0; i<guess.length; i++) {
   for(int j = 0; j<clue[j].length; j++) {
    System.out.print(((clue[i][j] == null) ? " " : clue[i][j])+" ");
   }//display the guess 2D array, if it is null, change it to a space
   System.out.print("\t");//output a tab
   for(int j = 0; j<guess[j].length; j++) {
    System.out.print(((guess[i][j] == null) ? " " : guess[i][j])+" ");
   }//display the clue 2D array, if it is null, change it to a space
   System.out.println();//change to next line
  }

 }

}


