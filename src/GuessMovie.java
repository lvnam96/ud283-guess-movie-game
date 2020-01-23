import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

public class GuessMovie {
    ArrayList<String> films;
    String selectedFilm;
    String[] placeholder;
    ArrayList<Character> wrongLetters = new ArrayList<Character>();
    boolean hasWon = false;

    public static void main(String[] args) {
        GuessMovie game = new GuessMovie();
        game.init();
    }

    GuessMovie () {
        this.films = this.getFilmsFromFile();
        this.selectedFilm = this.getRandomFilm();
        this.placeholder = new String[this.selectedFilm.length()];
        for (int i = 0; i < this.selectedFilm.length(); i++) {
            this.placeholder[i] = "_";
        }
    }

    private void init () {
        Scanner scanner = new Scanner(System.in);

        int chances = 10;
        while (chances > 0) {
            this.printPlaceholder();
            char letter;
            try {
                letter = scanner.nextLine().charAt(0);
            } catch (InputMismatchException e) {
                System.out.println("Please type only ONE LETTER:");
                letter = scanner.nextLine().charAt(0);
            }
            if (this.selectedFilm.contains(Character.toString(letter))) {
                this.hasWon = true;
                System.out.println("Correct!");
                //edit this.placeholder
//                char[] chars = this.selectedFilm.toCharArray();
                for (int i = 0; i < this.placeholder.length; i++) {
                    if (this.selectedFilm.charAt(i) == letter) {
                        this.placeholder[i] = Character.toString(letter);
                    } else if (this.placeholder[i] == "_") {
                        this.hasWon = false;
                    }
                }
                // check hasWon
                if (this.hasWon) {
                    System.out.println("You won!!! The movie's name is: " + this.selectedFilm);
                    return;
                }
                continue;
            }

            this.wrongLetters.add(letter);
            this.printWrongLetters();
            chances--;
            this.printTurnsLeft(chances);
        }

        System.out.println("You lose!");
    }

    private void printPlaceholder () {
        System.out.println("Film's name: " + String.join("", this.placeholder));
    }

    private void printWrongLetters () {
        if (this.wrongLetters.size() > 0) {
            StringBuilder sb = new StringBuilder();
            for (char s: this.wrongLetters) {
                sb.append(s);
            }
            System.out.println(String.format(
                    "You have guessed %d wrong letter%s: %s",
                    this.wrongLetters.size(),
                    this.wrongLetters.size() > 1 ? "s" : "",
                    String.join(", ", sb.toString()),
                    10 - this.wrongLetters.size()

            ));
        } else {
            System.out.println(String.format("You have guessed 0 wrong letter."));
        }
    }

    private void printTurnsLeft (int chances) {
        System.out.println(String.format("%d turns left.", chances));
    }

    private ArrayList<String> getFilmsFromFile () {
        ArrayList<String> films = new ArrayList<String>();
        Scanner fileReader;
        try {
            File file = new File("movies.txt");
            fileReader = new Scanner(file);

            while (fileReader.hasNextLine()) {
                films.add(fileReader.nextLine());
            }
        } catch (FileNotFoundException e) {
            System.out.println("File not found!");
        }
        return films;
    }

    private String getRandomFilm () {
        int randomIndex = (int)(Math.random() * this.films.size());
        return this.films.get(randomIndex);
    }
}
