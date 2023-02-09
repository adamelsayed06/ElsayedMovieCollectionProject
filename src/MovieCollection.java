import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.Collections;

public class MovieCollection {
  private ArrayList<Movie> movies;
  private Scanner scanner;

  public MovieCollection(String fileName) {
    importMovieList(fileName);
    scanner = new Scanner(System.in);
  }

  public ArrayList<Movie> getMovies() {
    return movies;
  }

  public void menu() {
    String menuOption = "";

    System.out.println("Welcome to the movie collection!");
    System.out.println("Total: " + movies.size() + " movies");

    while (!menuOption.equals("q")) {
      System.out.println("------------ Main Menu ----------");
      System.out.println("- search (t)itles");
      System.out.println("- search (k)eywords");
      System.out.println("- search (c)ast");
      System.out.println("- see all movies of a (g)enre");
      System.out.println("- list top 50 (r)ated movies");
      System.out.println("- list top 50 (h)igest revenue movies");
      System.out.println("- (q)uit");
      System.out.print("Enter choice: ");
      menuOption = scanner.nextLine();

      if (menuOption.equals("t")) {
        searchTitles();
      } else if (menuOption.equals("c")) {
        searchCast();
      } else if (menuOption.equals("k")) {
        searchKeywords();
      } else if (menuOption.equals("g")) {
        listGenres();
      } else if (menuOption.equals("r")) {
        listHighestRated();
      } else if (menuOption.equals("h")) {
        listHighestRevenue();
      } else if (menuOption.equals("q")) {
        System.out.println("Goodbye!");
      } else {
        System.out.println("Invalid choice!");
      }
    }
  }

  private void importMovieList(String fileName) {
    try {
      movies = new ArrayList<Movie>();
      FileReader fileReader = new FileReader(fileName);
      BufferedReader bufferedReader = new BufferedReader(fileReader);
      String line = bufferedReader.readLine();

      while ((line = bufferedReader.readLine()) != null) {
        // get data from the columns in the current row and split into an array
        String[] movieFromCSV = line.split(",");

        String title = movieFromCSV[0];
        String cast = movieFromCSV[1];
        String director = movieFromCSV[2];
        String tagline = movieFromCSV[3];
        String keywords = movieFromCSV[4];
        String overview = movieFromCSV[5];
        int runtime = Integer.parseInt(movieFromCSV[6]);
        String genres = movieFromCSV[7];
        double userRating = Double.parseDouble(movieFromCSV[8]);
        int year = Integer.parseInt(movieFromCSV[9]);
        int revenue = Integer.parseInt(movieFromCSV[10]);

        Movie nextMovie = new Movie(title, cast, director, tagline, keywords, overview, runtime, genres, userRating, year, revenue);

        movies.add(nextMovie);

        /* TASK 1: FINISH THE CODE BELOW */
        // using the movieFromCSV array,
        // obtain the title, cast, director, tagline,
        // keywords, overview, runtime (int), genres,
        // user rating (double), year (int), and revenue (int)


        // create a Movie object with the row data:


        // add the Movie to movies:


      }
      bufferedReader.close();
    } catch(IOException exception) {
      System.out.println("Unable to access " + exception.getMessage());
    }
  }

  private void searchTitles() {
    System.out.print("Enter a title search term: ");
    String searchTerm = scanner.nextLine();

    // prevent case sensitivity
    searchTerm = searchTerm.toLowerCase();

    // arraylist to hold search results
    ArrayList<Movie> results = new ArrayList<Movie>();

    // search through ALL movies in collection
    for (int i = 0; i < movies.size(); i++) {
      String movieTitle = movies.get(i).getTitle();
      movieTitle = movieTitle.toLowerCase();

      if (movieTitle.indexOf(searchTerm) != -1) {
        //add the Movie objest to the results list
        results.add(movies.get(i));
      }
    }

    if (results.size() > 0) {
      // sort the results by title
      sortResults(results);

      // now, display them all to the user
      for (int i = 0; i < results.size(); i++) {
        String title = results.get(i).getTitle();

        // this will print index 0 as choice 1 in the results list; better for user!
        int choiceNum = i + 1;
        System.out.println("" + choiceNum + ". " + title);
      }

      System.out.println("Which movie would you like to learn more about?");
      System.out.print("Enter number: ");
      int choice = scanner.nextInt();
      scanner.nextLine();
      Movie selectedMovie = results.get(choice - 1);
      displayMovieInfo(selectedMovie);
      System.out.println("\n ** Press Enter to Return to Main Menu **");
      scanner.nextLine();
    } else {
      System.out.println("\nNo movie titles match that search term!");
      System.out.println("** Press Enter to Return to Main Menu **");
      scanner.nextLine();
    }
  }

  private void sortResults(ArrayList<Movie> listToSort) {
    for (int j = 1; j < listToSort.size(); j++) {
      Movie temp = listToSort.get(j);
      String tempTitle = temp.getTitle();

      int possibleIndex = j;
      while (possibleIndex > 0 && tempTitle.compareTo(listToSort.get(possibleIndex - 1).getTitle()) < 0) {
        listToSort.set(possibleIndex, listToSort.get(possibleIndex - 1));
        possibleIndex--;
      }
      listToSort.set(possibleIndex, temp);
    }
  }
  
  private void displayMovieInfo(Movie movie) {
    System.out.println();
    System.out.println("Title: " + movie.getTitle());
    System.out.println("Tagline: " + movie.getTagline());
    System.out.println("Runtime: " + movie.getRuntime() + " minutes");
    System.out.println("Year: " + movie.getYear());
    System.out.println("Directed by: " + movie.getDirector());
    System.out.println("Cast: " + movie.getCast());
    System.out.println("Overview: " + movie.getOverview());
    System.out.println("User rating: " + movie.getUserRating());
    System.out.println("Box office revenue: " + movie.getRevenue());
  }

  private void searchKeywords() {
    System.out.print("Enter a keyword search term: ");
    String searchTerm = scanner.nextLine();

    searchTerm = searchTerm.toLowerCase();

    ArrayList<Movie> results = new ArrayList<Movie>();

    for (int i = 0; i < movies.size(); i++) {
      String movieKeyword = movies.get(i).getKeywords();
      movieKeyword = movieKeyword.toLowerCase();

      if (movieKeyword.indexOf(searchTerm) != -1) {
        results.add(movies.get(i));
      }
    }

    if (results.size() > 0) {
      sortResults(results);

      for (int i = 0; i < results.size(); i++) {
        String title = results.get(i).getTitle();

        int choiceNum = i + 1;
        System.out.println("" + choiceNum + ". " + title);
      }

      System.out.println("Which movie would you like to learn more about?");
      System.out.print("Enter number: ");
      int choice = scanner.nextInt();
      scanner.nextLine();
      Movie selectedMovie = results.get(choice - 1);
      displayMovieInfo(selectedMovie);
      System.out.println("\n ** Press Enter to Return to Main Menu **");
      scanner.nextLine();
    } else {
      System.out.println("\nNo movie keywords match that search term!");
      System.out.println("** Press Enter to Return to Main Menu **");
      scanner.nextLine();
    }

  }

  private void searchCast() {
    String castMember = "";
    int k = 0;
    System.out.print("Enter an actor's name: ");
    String searchTerm = scanner.nextLine();

    searchTerm = searchTerm.toLowerCase();
    ArrayList<String> list = new ArrayList<String>();
    ArrayList<Movie> selectedMoviess = new ArrayList<Movie>();

    for (int i = 0; i < movies.size(); i++) {
      castMember += movies.get(i).getCast() + "|";
    }

    castMember = castMember.toLowerCase();
    String[] results = castMember.split("\\|");

    ArrayList<String> newResults = new ArrayList<String>();

    for(int i = 0; i < results.length; i++){
      newResults.add(results[i]);
    }
    Collections.sort(newResults);

    ArrayList<String> tempResults = new ArrayList<String>();
    ArrayList<String> finalResults = new ArrayList<String>();

    for(int i = 0; i < results.length; i++){
      if(!(tempResults.contains(newResults.get(i)))){
        tempResults.add(newResults.get(i));
      }
    }

    for(int j = 0; j < tempResults.size();j++) {
      if(tempResults.get(j).indexOf(searchTerm) != -1){
        finalResults.add(tempResults.get(j));
      }
    }

    if(finalResults.size() > 0){
      for (int i = 0; i < finalResults.size(); i++){
        String cast = finalResults.get(i);
        int choiceNum = i + 1;
        System.out.println("" + choiceNum + ". " + cast);
      }

      System.out.println("Which actor's movies would you like to see?");
      int choice = scanner.nextInt();
      String selectedMember = finalResults.get(choice - 1);

      for(int i = 0; i < movies.size(); i++){
        if(movies.get(i).getCast().toLowerCase().indexOf(selectedMember) != -1){
          String selectedMovie = movies.get(i).getTitle();
          selectedMoviess.add(movies.get(i));

          int choiceNum = k + 1;
          System.out.println("" + choiceNum + ". " + selectedMovie);
          k++;
        }


      }

      System.out.println("Which movie would you like to learn more about?");
      System.out.print("Enter number: ");
      int choice2 = scanner.nextInt();
      scanner.nextLine();



      Movie selectedMovie = selectedMoviess.get(choice - 1);
      displayMovieInfo(selectedMovie);
      System.out.println("\n ** Press Enter to Return to Main Menu **");
      scanner.nextLine();

    }



    
  }
  
  private void listGenres() {
    String genres = "";
    ArrayList <String> genresNoDuplicates = new ArrayList<String>();
    ArrayList <String> moviesThatFit = new ArrayList <String>();


      for (int i = 0; i < movies.size(); i++) {
        genres += movies.get(i).getGenres() + "|";
      }
      String[] genresArray = genres.split("\\|");

      for(int i = 0; i < genresArray.length; i++){
        if(!(genresNoDuplicates.contains(genresArray[i]))) {
          genresNoDuplicates.add(genresArray[i]);
        }
      }

      Collections.sort(genresNoDuplicates);

      for(int i = 0; i < genresNoDuplicates.size(); i++){
        int choiceNum = i + 1;
        System.out.println("" + choiceNum + ". " + genresNoDuplicates.get(i));
      }

    System.out.println("Choose which genre you'd like to search in");
      String selectedGenre = scanner.nextLine();

      for(int i = 0; i < movies.size(); i++){
        if(movies.get(i).getGenres().contains(selectedGenre)){
          moviesThatFit.add(movies.get(i).getTitle());
        }
      }

      for(int i = 0; i < moviesThatFit.size(); i++){
        int choiceNum = i + 1;
        System.out.println("" + choiceNum + ". " + moviesThatFit.get(i));
      }

    System.out.println("Which movie would you like to learn more about?");
    System.out.print("Enter number: ");
    int choice2 = scanner.nextInt();
    scanner.nextLine();



    for(int i = 0; i < movies.size(); i++){
      if(moviesThatFit.get(choice2 - 1).equals(movies.get(i).getTitle())){
        displayMovieInfo(movies.get(i));
      }

      System.out.println("\n ** Press Enter to Return to Main Menu **");
      scanner.nextLine();
    }




    }

  
  private void listHighestRated() {
    /* TASK 6: IMPLEMENT ME */
  }
  
  private void listHighestRevenue() {
    /* TASK 6: IMPLEMENT ME */
  }
}