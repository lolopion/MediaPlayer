package studiplayer.audio;

import java.util.Comparator;

public class AlbumComparator implements Comparator<AudioFile> {
    public int compare(AudioFile af1, AudioFile af2) {
        if (!(af1 instanceof TaggedFile) && af2 instanceof TaggedFile) {
            return -1;
        } else if (!(af2 instanceof TaggedFile) && af1 instanceof TaggedFile) {
            return 1;
        } else if (!(af1 instanceof TaggedFile) && !(af2 instanceof TaggedFile)){
            return 0;
        } else {
            if (af1.equals(null) || af2.equals(null)) {
                throw new NullPointerException("AudioFile 1 or 2 is null");
            }
            TaggedFile tf1 = (TaggedFile) af1;
            TaggedFile tf2 = (TaggedFile) af2;
            String album1 = tf1.getAlbum();
            String album2 = tf2.getAlbum();
            if (album1 == null && !(album2 == null)) {
                return -1;
            } else if (!(album1 == null) && album2 == null) {
                return 1;
            } else if (album1 == null && album2 == null) {
                return 0;
            } else if (album1.compareTo(album2) > 0) {
                return 1;
            } else if (album1.compareTo(album2) < 0) {
                return -1;
            } else
                return 0;
        }
    }
}
