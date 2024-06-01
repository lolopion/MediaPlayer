package studiplayer.audio;

import java.util.Comparator;

public class DurationComparator implements Comparator<AudioFile> {
    public int compare(AudioFile af1, AudioFile af2) {
        if (!(af1 instanceof SampledFile)) {
            return -1;
        } else if (!(af2 instanceof SampledFile)) {
            return 1;
        } else {
            if (af1 == null || af2 == null) {
                throw new NullPointerException("AudioFile 1 or 2 is null");
            }
            SampledFile sf1 = (SampledFile) af1;
            SampledFile sf2 = (SampledFile) af2;
            String duration1 = sf1.getFormattedDuration();
            String duration2 = sf2.getFormattedDuration();
            if (duration1 == null && !(duration2 == null)) {
                return -1;
            } else if (!(duration1 == null) && duration2 == null) {
                return 1;
            } else if ((duration1 == null) && duration2 == null) {
                return 0;
            } else if (duration1.compareTo(duration2) > 0) {
                return 1;
            } else if (duration1.compareTo(duration2) < 0) {
                return -1;
            } else
                return 0;
        }
    }
}
