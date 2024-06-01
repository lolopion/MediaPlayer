package studiplayer.audio;

import java.util.Comparator;

public class TitleComparator implements Comparator<AudioFile> {
    public int compare(AudioFile af1, AudioFile af2) {
        if (af1 == null || af2 == null) {
            throw new NullPointerException("AudioFile 1 or 2 is null");
        }
        String title1 = af1.getTitle();
        String title2 = af2.getTitle();
        if (title1 == null && !(title2 == null)) {
            return -1;
        } else if (!(title1 == null) && title2 == null) {
            return 1;
        } else if (title1 == null && title2 == null) {
            return 0;
        } else if (title1.compareTo(title2) > 0) {
            return 1;
        } else if (title1.compareTo(title2) < 0) {
            return -1;
        } else
            return 0;
    }
}
