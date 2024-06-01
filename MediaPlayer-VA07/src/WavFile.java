import studiplayer.basic.WavParamReader;

public class WavFile extends SampledFile {
   

    public WavFile() {
        super();
    }

    public WavFile(String s) {
        super(s);
        readAndSetDurationFromFile(pathname);
    }

    public static long computeDuration(long numberOfFrames, float frameRate) {
        if (frameRate == 0) {
            return 0;
        } else {
            return (numberOfFrames*(10*10*10*10*10*10) / (long) frameRate); 
        }
    }

    public void readAndSetDurationFromFile(String pathname) {
        long numberOfFrames;
        float frameRate;
        WavParamReader.readParams(pathname);
        numberOfFrames = WavParamReader.getNumberOfFrames();
        frameRate = WavParamReader.getFrameRate();
        duration = WavFile.computeDuration(numberOfFrames, frameRate);
    }
    
    public String toString() {
        return (super.toString() + " - " + getFormattedDuration());
    }
    
    public String[] fields() {
        String[] field = {"","","",""};
        field [0] = this.author;
        field [1] = this.title;
        field [2] = ""; //album
        field [3] = this.getFormattedDuration();
        return field;
    }
}
