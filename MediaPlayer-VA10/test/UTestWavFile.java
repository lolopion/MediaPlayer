import static org.junit.Assert.assertEquals;
import org.junit.Test;

import studiplayer.audio.WavFile;

public class UTestWavFile {
    @Test
    public void test_computeDuration_01() throws Exception {
        assertEquals("Wrong duration", 2000000L, WavFile.computeDuration(88200L, 44100.0f));
    }

    public void test_readAndSetDurationFromFile_02() throws Exception {
        WavFile wf = new WavFile();
        wf.parsePathname("audiofiles/wellenmeister - tranquility.wav");
        wf.readAndSetDurationFromFile(wf.getPathname());
        assertEquals("Wrong time format", "02:21", wf.getFormattedDuration());
    }
}
