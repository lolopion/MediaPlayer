package studiplayer.audio;

import java.util.Comparator;

public class AuthorComparator implements Comparator<AudioFile> {
    public int compare(AudioFile af1, AudioFile af2) {
        String author1 = af1.getAuthor();
        String author2 = af2.getAuthor();
        if (af1 == null || af2 == null) {
            throw new NullPointerException("AudioFile 1 or 2 is null");
        }
        if (author1 == null && !(author2 == null)) {
            return -1;
        } else if (!(author1 == null) && author2 == null) {
            return 1;
        } else if ((author1 == null) && author2 == null) {
            return 0;
        } else if (author1.compareTo(author2) > 0) {
            return 1;
        } else if (author1.compareTo(author2) < 0) {
            return -1;
        } else
            return 0;

    }
}
