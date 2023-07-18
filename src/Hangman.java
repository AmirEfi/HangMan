import java.util.Arrays;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Hangman {
    String username;
    String password;
    int numOfPlayer = 0;
    int score = 0;

    public static void clearScr() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }

    public static void delay(){
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void FirstMenu(){
        System.out.println("Welcome to the hangman!");
        System.out.println("1. Signup");
        System.out.println("2. Login");
        System.out.println("0. Exit");
        System.out.print("Enter a number please: ");
    }

    public static void LoginMenu(){
        System.out.println("1. Start Game");
        System.out.println("2. Show Leaderboard");
        System.out.println("0. Back");
        System.out.print("Enter a number please: ");
    }

    public static void FormOfGame(int lives) {

        boolean one = false, two = false, three = false, four = false, five = false, six = false, seven = false;

        switch (lives) {
            case 0: one = two = three = four = five = six = seven = true; break;
            case 1: one = two = three = four = five = six = true; break;
            case 2: one = two = three = four = five = true; break;
            case 3: one = two = three = four = true; break;
            case 4: one = two = three = true; break;
            case 5: one = two = true; break;
            case 6: one = true; break;
        }

        for (int i = 0; i < 4; i++)
            System.out.print("-");
        System.out.println();

        for (int i = 0; i < 5; i++) {
            System.out.print("|");

            if (i == 0 && one) {
                System.out.print("  |");

                if (lives == 0)
                    System.out.println();
            }

            if (i == 1 && two) {
                System.out.print("  O");

                if (lives == 0)
                    System.out.println();
            }

            if (i == 2 && three)
                System.out.print(" /");

            if (i == 2 && four)
                System.out.print("|");

            if (i == 2 && five) {
                System.out.print("\\");

                if (lives == 0)
                    System.out.println();
            }
            if (i == 3 && six)
                System.out.print(" / ");

            if (i == 3 && seven)
                System.out.println("\\");

            if (!five || !six || !seven)
                System.out.println();
        }
    }

    public static void Game(Hangman player){
        clearScr();
        Scanner input = new Scanner(System.in);

        String[] words = {"tehran", "pizza", "banana", "new york", "advanced programming", "michael jordan",
                "lionel messi", "apple", "macaroni", "university", "intel", "kitten", "python", "java",
                "data structures", "algorithm", "assembly", "basketball", "hockey", "leader", "javascript",
                "toronto", "united states of america", "psychology", "chemistry", "breaking bad", "physics",
                "abstract classes", "linux kernel", "january", "march", "time travel", "twitter", "instagram",
                "dog breeds", "strawberry", "snow", "game of thrones", "batman", "ronaldo", "soccer",
                "hamburger", "italy", "greece", "albert einstein", "hangman", "clubhouse", "call of duty",
                "science", "theory of languages and automata"};

        String wordToGuess = words[(int) (Math.random() * words.length)];
        char[] prevGuessed = new char[50];
        char[] BlankOfGuess = new char[wordToGuess.length()];
        int letterNum = 0;

        for(int i = 0; i < wordToGuess.length(); i++) {

            if(wordToGuess.charAt(i) == ' ')
                BlankOfGuess[i] = ' ';

            else {
                BlankOfGuess[i] = '-';
                letterNum++;
            }
        }

        int lives = 7;
        int indexPrevGuess = 0;
        int remaining = wordToGuess.length();
        int wrongAboveNine = 0;
        char guess;
        boolean firstRnd = true;

        System.out.println("The characters of the word that you have to guess is like below: ");
        for(int i = 0; i < wordToGuess.length();i++)
            System.out.print(BlankOfGuess[i] + " ");
        System.out.println();
        System.out.println();

        while(lives > 0 && remaining > 0) {

            boolean prevGuess = true;
            boolean correctAns = false;

            FormOfGame(lives);

            if(firstRnd)
                System.out.print("Guess a letter (You can use a hint just for one time by typing '.') : ");
            else
                System.out.print("Guess a letter: ");

            guess = input.findWithinHorizon(".",0).charAt(0);

            if(guess == '.' && player.score >= 10) {
                firstRnd = false;
                boolean correctHint = true;

                while (correctHint) {
                    boolean checkHint = false;
                    guess = wordToGuess.charAt((int) (Math.random() * wordToGuess.length()));

                    for (int i = 0; i < wordToGuess.length(); i++)
                        checkHint = (guess == BlankOfGuess[i]);
                    correctHint = checkHint;
                }
                player.score -= 10;
            }

            else if(guess == '.'){
                firstRnd = false;

                System.out.println("Your score is not enough, Need 10 scores for hint.");

                delay();

                clearScr();

                continue;
            }

            for(int i=0; i < indexPrevGuess; i++){

                if(prevGuessed[i] == guess){
                    clearScr();
                    System.out.println("The letter was chosen before, Try another!");
                    prevGuess = false;
                }
            }

            if(prevGuess)
                prevGuessed[indexPrevGuess++] = guess;
            else
                continue;

            boolean correctGuess = (wordToGuess.indexOf(guess) != -1);
            clearScr();

            if(correctGuess) {
                System.out.println("Your guess is correct!");

                delay();

                clearScr();

                for (int i = 0; i < wordToGuess.length(); i++) {

                    if (wordToGuess.charAt(i) == guess) {
                        remaining--;
                        BlankOfGuess[i] = guess;

                        if(remaining == 0){
                            System.out.println("Congrats! You guessed the word correctly!");
                            System.out.println("The word was '" + wordToGuess + "'.");

                            correctAns = true;
                            player.score += 5;

                            delay();
                            break;
                        }
                    }
                }
                if(remaining != 0){

                    for (int i = 0; i < wordToGuess.length(); i++){
                        System.out.print(BlankOfGuess[i] + " ");
                    }

                }
                System.out.println();
            }
            else{
                clearScr();
                if(letterNum > 9)
                    wrongAboveNine++;
                else
                    lives--;

                if(wrongAboveNine == 2){
                    lives--;
                    wrongAboveNine = 0;
                }

                if(lives == 0){
                    System.out.println("Your opportunities are over!");
                    System.out.println("'GAME OVER'");

                    FormOfGame(lives);

                    delay();
                    break;
                }

                if(letterNum > 9)
                    System.out.println("Sorry, your guess is wrong. you have " + lives + " more opportunities! (1 live = 2 wrong letter)");
                else
                    System.out.println("Sorry, your guess is wrong. you have " + lives + " more opportunities!");

                delay();
            }

            if(!correctAns) {
                System.out.print("Previous guessed letters: ");

                for (int i = 0; i < indexPrevGuess; i++)
                    System.out.print(prevGuessed[i]);
                System.out.println();
            }
        }
    }
    public static void sort(int scr[])
    {
        int n = scr.length;
        for (int i = 1; i < n; ++i) {
            int key = scr[i];
            int j = i - 1;

            /* Move elements of arr[0..i-1], that are
               greater than key, to one position ahead
               of their current position */
            while (j >= 0 && scr[j] > key) {
                scr[j + 1] = scr[j];
                j = j - 1;
            }
            scr[j + 1] = key;
        }
    }

    public static boolean login(Hangman players[],int numPlayer, int indexPlayer){

        Scanner input = new Scanner(System.in);
        int num = input.nextInt();
        int[] scores = new int[numPlayer];
        String[] names = new String[numPlayer];

        switch (num) {
            case 0:
                return false;

            case 1:
                Game(players[indexPlayer]);
                break;

            case 2:
                clearScr();
                int runOfBoard = 1;

                for (int i = 0; i < numPlayer; i++) {
                    scores[i] = players[i].score;
                    names[i] = players[i].username;
                }
                Arrays.sort(scores);

                while(runOfBoard == 1) {

                    for (int i = scores.length-1; i >= 0; i--) {

                        for (int j = 0; j < numPlayer; j++) {
                            if(scores[i] == players[j].score)
                                System.out.println(players[j].username + " ----- " + players[j].score);
                        }

                    }
                    System.out.println();

                    for (int i = 0; i < 70; i++)
                        System.out.print("-");
                    System.out.println();
                    System.out.print("Enter 0 to back to the last menu: ");

                    runOfBoard = input.nextInt();
                }
                break;

            default:
                clearScr();
                System.out.println("Your chosen number is not correct!");
                delay();
        }
        return true;
    }

    public static void entrance(Hangman player, Hangman players[], int numPlayer){
        Scanner input = new Scanner(System.in);
        int num = input.nextInt();

        switch (num){
            case 0:
                System.exit(0);
                break;

            case 1:
                clearScr();
                System.out.print("Enter your username: ");
                player.username = input.next();
                boolean sameID = false;

                for(int i = 0; i < numPlayer; i++){

                    if(players[i].username.equals(player.username)){

                        System.out.println("Sorry, This username had taken, Try another one.");
                        sameID = true;

                        delay();

                        break;
                    }
                }
                if(sameID)
                    break;

                System.out.print("Enter your password: ");
                player.password = input.next();
                System.out.println();

                String rgxPassword = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&#])[A-Za-z\\d@$!%*?&#]{6,}$";
                Pattern patternPass = Pattern.compile(rgxPassword);
                Matcher matcher = patternPass.matcher(player.password);

                if(matcher.matches()){
                    clearScr();

                    System.out.println("You registered successfully!");

                    delay();

                    player.numOfPlayer++;
                }
                else{
                    clearScr();

                    System.out.println("Your chosen password is not correct, please sign up again!");

                    delay();
                }
                break;

            case 2:
                clearScr();

                if(numPlayer == 0){
                    System.out.println("First, you have to sign up!");
                    delay();
                    break;
                }

                else {
                    System.out.print("Enter your username: ");
                    String id = input.next();
                    int indexOfPlayer = 0;
                    boolean signedUp = false;

                    for(int i = 0; i < numPlayer; i++){

                        if(players[i].username.equals(id)){
                            signedUp = true;
                            indexOfPlayer = i;
                            break;
                        }

                    }

                    if(signedUp){
                        System.out.print("Enter your password: ");
                        String pass = input.next();

                        if(players[indexOfPlayer].password.equals(pass)){
                            clearScr();
                            boolean loginRun = true;
                            System.out.println("You logged in to your account successfully!");
                            delay();

                            while(loginRun) {
                                clearScr();
                                LoginMenu();
                                loginRun = login(players, numPlayer, indexOfPlayer);
                            }

                        }
                        else {
                            clearScr();
                            System.out.println("Your password is not correct!");
                            System.out.println("Please login again.");
                            delay();
                        }
                    }
                    else{
                        clearScr();
                        System.out.println("Sorry, first you have to sign up then try login!");
                        delay();
                    }
                }
            break;

            default:
                clearScr();
                System.out.println("Your chosen number is not correct!");
                delay();
        }
    }
}
class Test extends Hangman{

    public static void main(String[] arg ){

        Hangman players[] = new Hangman[100];
        int numPlayer = 0;

        for(int i = 0; i < players.length; i++)
            players[i] = new Hangman();

        while (true) {
                clearScr();

                FirstMenu();

                entrance(players[numPlayer], players, numPlayer);

                if(players[numPlayer].numOfPlayer != 0)
                    numPlayer++;
        }
    }
}