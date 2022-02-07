package movietheatres;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalTime;
import java.util.*;
import java.util.Map.Entry;
import java.util.stream.Stream;

public class MovieTheatreService {

    private Map<String, List<Movie>> shows = new LinkedHashMap<>();

    public Map<String, List<Movie>> getShows() {
        return shows;
    }

    public void readFromFile(Path path) {
        try (BufferedReader br = Files.newBufferedReader(path)) {
            String line;
            while ((line = br.readLine()) != null) {
                populateTempMap(line);
            }
        } catch (IOException ioException) {
            throw new IllegalArgumentException("Can not read file!");
        }
    }

    private void populateTempMap(String line) {
        String place = line.split("-")[0];
        String name = line.split("-")[1].split(";")[0];
        LocalTime time = LocalTime.parse(line.split("-")[1].split(";")[1]);
        Movie movie = new Movie(name, time);
        if (shows.containsKey(place)) {
            List<Movie> values = shows.get(place);
            values.add(movie);
            values = new ArrayList<>(values.stream().sorted(Comparator.comparing(Movie::getStartTime)).toList());
            shows.put(place, values);
        } else {
            List<Movie> values = new ArrayList<>();
            values.add(movie);
            shows.put(place, values);
        }
    }

    public List<String> findMovie(String title) {
        /*List<String> result = new ArrayList<>();
        for (Entry<String, List<Movie>> entry : shows.entrySet()) {
            for (Movie movie : entry.getValue()) {
                if (movie.getTitle().equals(title) & !result.contains(entry.getKey())) {
                    result.add(entry.getKey());
                }
            }
        }
        return result;*/

        return shows.entrySet().stream()
                .filter(k -> k.getValue().stream().anyMatch(m -> m.getTitle().equals(title)))
                .map(e -> e.getKey())
                .toList();

    }

    public LocalTime findLatestShow(String title) {
        /*LocalTime result = LocalTime.of(0,0);
        for (Map.Entry<String, List<Movie>> entry : shows.entrySet()) {
            for (Movie movie : entry.getValue()) {
                if (movie.getTitle().equals(title) & movie.getStartTime().isAfter(result)) {
                    result = movie.getStartTime();
                }
            }
        }
        if (result.equals(LocalTime.of(0,0))) {
            throw new IllegalArgumentException("No theatre found!");
        } else {
            return result;
        }*/

        return shows.entrySet().stream().flatMap(m -> m.getValue().stream())
                .filter(m -> m.getTitle().equals(title))
                .sorted(Comparator.comparing(Movie::getStartTime).reversed())
                .findFirst().orElseThrow(() -> new IllegalArgumentException("Not found.")).getStartTime();
    }
}
