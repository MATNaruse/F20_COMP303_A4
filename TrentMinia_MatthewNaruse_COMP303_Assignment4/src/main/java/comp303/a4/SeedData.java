package comp303.a4;

import java.util.ArrayList;
import java.util.List;

import comp303.a4.entities.Movie;

public class SeedData {
    public static void loadMovieData(List<Movie> list) {
        list.add(new Movie(
            "Movie Name",
            "Director",
            "English",
            "Genre",
            "This movie will knock your socks off or some shit",
            "PG-18",
            200,
            "img/img1.jpg"
        ));
        list.add(new Movie(
            "Tenet",
            "Christopher Nolan",
            "English",
            "Action, Sci-Fi",
            "A secret agent embarks on a dangerous, time-bending mission to prevent the start of World War III.",
            "PG-13",
            150,
            "img/img1.jpg"
        ));
        list.add(new Movie(
            "Jingle Jangle: A Christmas Journey",
            "Christopher Nolan",
            "English",
            "Musical, Fantasy",
            "Decades after his apprentice betrays him, a once joyful toy maker finds new hope when his bright young " +
                "granddaughter appears on his doorstep.",
            "PG",
            122,
            "img/img1.jpg"
        ));
    }
}
