package studiplayer.audio;

import studiplayer.basic.WavParamReader;

public class WavFile extends SampledFile {

    public WavFile() {
        super();
    }

    public WavFile(String s) throws NotPlayableException {
        super(s);
        readAndSetDurationFromFile(pathname);
    }

    public static long computeDuration(long numberOfFrames, float frameRate) {
        if (frameRate == 0) {
            return 0;
        } else {
            return (numberOfFrames * (10 * 10 * 10 * 10 * 10 * 10) / (long) frameRate);
        }
    }

    public void readAndSetDurationFromFile(String pathname) throws NotPlayableException {
        long numberOfFrames;
        float frameRate;
        try {
            WavParamReader.readParams(pathname);

            numberOfFrames = WavParamReader.getNumberOfFrames();
            frameRate = WavParamReader.getFrameRate();
            duration = WavFile.computeDuration(numberOfFrames, frameRate);
        } catch (RuntimeException e) {
            throw new NotPlayableException(pathname, "Could not read pathname");
        }
    }

    public String toString() {
        return (super.toString() + " - " + getFormattedDuration());
    }

    public String[] fields() {
        String[] field = { "", "", "", "" };
        field[0] = this.author;
        field[1] = this.title;
        field[2] = ""; // album
        field[3] = this.getFormattedDuration();
        return field;
    }
}
