import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Random;

public class App {
    public static void main(String[] args) throws FileNotFoundException {
        
        File Words= new File("words.txt");
        Scanner WordsInput = new Scanner(Words);
        Scanner userInput =  new Scanner(System.in);
        Random rand= new Random();
        String[] AllWords=new String[3000]; //Because we have 3000 words in the file
        ArrayList<Character> UsedLetters = new ArrayList<>();
        String RandomWordInStr;
        int RandomIndex=rand.nextInt(3001);
        int Attempts=5;
        for(int i=0;i<3000;i++){
            String wrd=WordsInput.nextLine();
            AllWords[i]=wrd;
        }
        WordsInput.close();
        
        RandomWordInStr=AllWords[RandomIndex];
        char[] RandomWord=new char[RandomWordInStr.length()];
        char[] CoveredRandomWord=new char[RandomWordInStr.length()];

        for(int i=0;i<RandomWordInStr.length();i++){
            RandomWord[i]=RandomWordInStr.charAt(i);
            CoveredRandomWord[i]='*';
        }

        System.out.println("I have chosen the random word and you have to guess it in 5 attempts.");
        System.out.println("Enter just one letter on each turn and click enter to submit. Good luck!");
        System.out.print("Here is your word:");
        for(int i=0;i<CoveredRandomWord.length;i++){
            System.out.print(CoveredRandomWord[i]+" ");
        }        
        System.out.println();
        System.out.println("Attemps:"+Attempts);
        System.out.println();



        while (true) {
            System.out.print("Your guess: ");
            char user = ' ';

            try {
                String inputLine = userInput.nextLine();
                
                // Check if the input is empty or a sequence of characters
                if(inputLine.length()==0){
                    throw new IllegalArgumentException();
                }
                else if (inputLine.length() > 1) {
                    throw new ArrayIndexOutOfBoundsException();
                }

                user = inputLine.charAt(0);
                
                if (!Character.isLetter(user)) {
                    throw new InputMismatchException();
                }

                if (Character.isUpperCase(user)) {
                    user = Character.toLowerCase(user);
                }
            } 
            catch (InputMismatchException e) {
                System.out.println("Please, enter a valid letter");
                UpdateGuess(CoveredRandomWord, Attempts);
                continue;
            } 
            
            catch(ArrayIndexOutOfBoundsException e) {
                System.out.println("Please, enter a single letter, not a sentence!");
                UpdateGuess(CoveredRandomWord, Attempts);
                continue;
            } 
            
            catch(IllegalArgumentException e) {
                System.out.println("Enter something! input can't not be empty!");
                UpdateGuess(CoveredRandomWord, Attempts);
                continue;
            }

            boolean correctGuess = false;
        
            for (int i = 0; i < RandomWord.length; i++) {
                if (user == RandomWord[i]) {
                    CoveredRandomWord[i] = RandomWord[i];
                    correctGuess = true; 
                }
            }
            if(correctGuess==false){
                //System.out.println("Wrong guess!");
                if(!CheckLetterRepeat(UsedLetters, user)){
                    Attempts--;
                    System.out.println("Wrong guess!");
                }else{
                    System.out.println("Wrong guess! but I will not take out your attempt, becaues you have tried this letter already");
                    
                }
                UsedLetters.add(user);
            }else{
                System.out.println("Correct!");
            }


            UpdateGuess(CoveredRandomWord, Attempts);
            
            
            if(GameWin(CoveredRandomWord)==CoveredRandomWord.length){
                System.out.println("YOU WON!");
                System.out.println("The word was:"+RandomWordInStr);
                break;                
            }

            if(Attempts==0){
                System.out.println("GAME OVER!");
                System.out.println("The word was:"+RandomWordInStr);
                break;
            }            
        }

    }



    public static int GameWin(char[] a){
        int cnt=0;
        for(int i=0;i<a.length;i++){
            if(a[i]!='*'){
                cnt++;
            }
        }
        return cnt;
    }

    public static void UpdateGuess(char[] CoveredRandomWord,int Attempts){
        System.out.println("Attempts:"+Attempts);
        System.out.print("Word:");
        for(int i=0;i<CoveredRandomWord.length;i++){
            System.out.print(CoveredRandomWord[i]+" ");
        }             
        System.out.println();
        System.out.println();
        
    }

    public static boolean CheckLetterRepeat(ArrayList<Character> usedletters, char user){
        for(int i=0;i<usedletters.size();i++){
            if(usedletters.get(i).charValue()==user){
                return true;
            }
        }
        return false;
    }


}
