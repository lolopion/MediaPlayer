import static org.junit.Assert.assertEquals;

import org.junit.Test;

import studiplayer.audio.AudioFile;

public class UTestAudioFile {

    @Test

    public void test_parsePathname_03() throws Exception {
//        EmulateOtherOs.emulateLinux();
        AudioFile af = new AudioFile();
        af.parsePathname("/my-tmp/file.mp3");
        char sepchar = java.io.File.separatorChar;
		AudioFile ag = new AudioFile();
		ag.parsePathname("/d//audio//CroFreiheit.mp3");

        assertEquals("Pathname stored incorrectly", sepchar + "my-tmp" + sepchar + "file.mp3", af.getPathname());
        assertEquals("Returned filename is incorrect", "file.mp3", af.getFilename());
		assertEquals("Pathname stored incorrectly", "d:" + sepchar + "audio" + sepchar + "CroFreiheit.mp3",
                ag.getPathname());
        assertEquals("Returned filename is incorrect", "CroFreiheit.mp3",
                ag.getFilename());

    }

    private boolean isWindows() {
        return System.getProperty("os.name").toLowerCase().indexOf("win") >= 0;
    }

    public void test_parseFilename_38() throws Exception {
//    	EmulateOtherOs.emulateLinux();
        AudioFile af = new AudioFile();
        AudioFile ag = new AudioFile();
        af.parsePathname("/tmp/test/   A.U.T.H.O.R  -  T.I.T.E.L   .EXTENSION");
        af.parseFilename(af.getFilename());

        assertEquals("Filename stored incorrectly", "   A.U.T.H.O.R  -  T.I.T.E.L   .EXTENSION", af.getFilename());
        assertEquals("Author stored incorrectly", "A.U.T.H.O.R", af.getAuthor());
        assertEquals("Title stored incorrectly", "T.I.T.E.L", af.getTitle());

        ag.parsePathname("d:\\\\\\home\\\\Cro-Messias BesterMann-FREIHEIT-OneLove.mp3");
        ag.parseFilename(ag.getFilename());

        assertEquals("Filename stored incorrectly", "Cro-Messias BesterMann-FREIHEIT-OneLove.mp3", ag.getFilename());
        assertEquals("Author stored incorrectly", "Cro-Messias BesterMann", ag.getAuthor());
        assertEquals("Title stored incorrectly", "FREIHEIT-OneLove", ag.getTitle());

    }

    public void test_toString_e() throws Exception {
//    	EmulateOtherOs.emulateLinux();
        AudioFile af = new AudioFile("d:\\home\\Cro-Freiheit.mp3");

        assertEquals("Pathname stored incorrectly", "d:\\home\\Cro-Freiheit.mp3", af.getPathname());
        assertEquals("Filename stored incorrectly", "Cro-Freiheit.mp3", af.getFilename());
        assertEquals("String stored incorrectly", "Cro - Freiheit", af.toString());

    }

}
