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
        list.add(new Movie(
            "Godmothered",
            "Sharon Maguire",
            "English",
            "Comedy, Fantasy",
            "An inexperienced fairy godmother-in-training tries to prove that people still need " +
                " fairy godmothers.",
            "PG",
            110,
            "img/img1.jpg"
        ));
        list.add(new Movie(
            "The Phenomenon",
            "James Fox",
            "English",
            "Documentary",
            "This documentary examines unidentified aerial phenomenon. With testimony from" + 
                " high-ranking government officials, and NASA Astronauts, Senator Harry Reid says it 'makes the" +
                " incredible credible.'",
            "Unrated",
            100,
            "img/img1.jpg"
        ));
        list.add(new Movie(
            "Freaky",
            "Christopher Landon",
            "English",
            "Comedy, Horror, Thriller",
            "After swapping bodies with a deranged serial killer, a young girl in high school discovers she has less" +
                " than 24 hours before the change becomes permanent. ",
            "R",
            102,
            "img/img1.jpg"
        ));
        list.add(new Movie(
            "Fatman",
            "Eshom Nelms, Ian Nelms",
            "English",
            "Action, Comedy, Fantasy",
            "A rowdy, unorthodox Santa Claus is fighting to save his declining business. Meanwhile, Billy, a" +
                " neglected and precocious 12 year old, hires a hit man to kill Santa after receiving a lump" +
                " of coal in his stocking. ",
            "R",
            100,
            "img/img1.jpg"
        ));
        list.add(new Movie(
            "Let Him Go",
            "Thomas Bezucha",
            "English",
            "Genre",
            "A retired sheriff and his wife, grieving over the death of their son, set out to find their" +
                " only grandson.",
            "R",
            113,
            "img/img1.jpg"
        ));
        list.add(new Movie(
            "Mulan",
            "Niki Caro",
            "English",
            "Action, Adveture, Drama",
            "A young Chinese maiden disguises herself as a male warrior in order to save her father.",
            "PG-13",
            115,
            "img/img1.jpg"
        ));
        list.add(new Movie(
            "Ghosts of War",
            "Eric Bress",
            "English",
            "Horror, Thriller, War",
            "Five American soldiers assigned to hold a French Chateau near the end of World War II. This unexpected respite quickly descends into madness when they encounter a supernatural enemy more terrifying than anything seen on the battlefield.",
            "R",
            94,
            "img/img1.jpg"
        ));
        list.add(new Movie(
            "Rudolph the Red-Nosed Reindeer",
            "Larry Roemer",
            "English",
            "Animation, Adventure, Comedy",
            "A misfit reindeer and his friends look for a place that will accept them.",
            "G",
            47,
            "img/img1.jpg"
        ));
    }
}
