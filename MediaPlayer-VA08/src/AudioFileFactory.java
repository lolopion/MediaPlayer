
public class AudioFileFactory {
    public static AudioFile getInstance(String pathname) {
        String copyofpathname;
        copyofpathname = pathname.toLowerCase();
        if(copyofpathname.endsWith(".ogg") == true || copyofpathname.endsWith(".mp3") == true) {
            return new TaggedFile(pathname);
        } else if (copyofpathname.endsWith(".wav") == true) {
            return new WavFile(pathname);
        } else {
            throw new RuntimeException("Unknow suffix for AudioFile: \""+ pathname + "\"");
        }
      
    }
}
