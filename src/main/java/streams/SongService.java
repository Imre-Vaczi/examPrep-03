package streams;

import java.time.LocalDate;
import java.util.*;

public class SongService {

    private List<Song> songs = new ArrayList<>();

    public List<Song> getSongs() {
        return List.copyOf(songs);
    }

    public void addSong(Song song) {
        if (song == null) {
            throw new IllegalArgumentException("Song can not be null!");
        }
        songs.add(song);
    }

    public Optional<Song> shortestSong() {
        return songs.stream()
                .min(Comparator.comparing(Song::getLength));
    }

    public List<Song> findSongByTitle(String titleToLookFor) {
        return songs.stream()
                .filter(s -> s.getTitle().equals(titleToLookFor))
                .toList();
    }

    public boolean isPerformerInSong(Song songToSearchIn, String performerToLookFor) {
        return songToSearchIn.getPerformers()
                .stream().anyMatch(s -> s.equals(performerToLookFor));
    }

    public List<String> titlesBeforeDate(LocalDate before) {
        return songs.stream()
                .filter(s -> s.getRelease().isBefore(before))
                .map(s -> s.getTitle())
                .toList();
    }
}
