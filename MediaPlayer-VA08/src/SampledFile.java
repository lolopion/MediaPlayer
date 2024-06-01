import studiplayer.basic.*;

public abstract class SampledFile extends AudioFile {

    public SampledFile() {
        super();
    }

    public SampledFile(String s) {
        super(s);
    }

    public void play() {
        BasicPlayer.play(this.pathname);
    }

    public void stop() {
        BasicPlayer.stop();
    }

    public void togglePause() {
        BasicPlayer.togglePause();
    }

    public String getFormattedPosition() {
        return (timeFormatter(BasicPlayer.getPosition()));

    }

    public String getFormattedDuration() {
        return (timeFormatter(duration));
    }

    public static String timeFormatter(long microtime) {
        if (microtime < 0) {
            throw new RuntimeException("Negative time value provided");
        }
        int MaximaleZeit = 5999;
        microtime = microtime / (10 * 10 * 10 * 10 * 10 * 10);

        if (microtime > MaximaleZeit) {
            throw new RuntimeException("Time value exceeds allowed format");
        }
        int Minuten = (int) (microtime / 60);
        int Sekunden = (int) (microtime % 60);
        if (Minuten < 10 && Sekunden >= 10) {
            return ("0" + Minuten + ":" + Sekunden);
        }
        if (Minuten >= 10 && Sekunden < 10) {
            return (Minuten + ":0" + Sekunden);
        }
        if (Minuten < 10 && Sekunden < 10) {
            return ("0" + Minuten + ":0" + Sekunden);
        }
        if (Minuten >= 10 && Sekunden >= 10) {
            return (Minuten + ":" + Sekunden);
        } else {
            return "schlechter Musikgeschmack tststs";
        }
    }
}
