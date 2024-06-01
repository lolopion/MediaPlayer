import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;
import java.util.Map;
import org.junit.Test;

import studiplayer.audio.TaggedFile;
import studiplayer.basic.TagReader;

public class UTestTaggedFile {
    @Test
    public void test_play_01() throws Exception {
        TaggedFile tf = new TaggedFile("audiofiles/Rock 812.mp3");
        tf.play();
    }

    @Test
    public void test_togglePause_02() throws Exception {
        TaggedFile tf = new TaggedFile("beethoven-ohne-album.mp3");
        tf.play();
        tf.togglePause();
    }

    @Test
    public void test_getFormattedPosition_03() throws Exception {

    }

    @Test
    public void test_getFormattedDuration_04() throws Exception {

    }

    @Test
    public void test_stop_05() throws Exception {
        TaggedFile tf = new TaggedFile("audiofiles/Rock 812.mp3");
        tf.play();
        tf.stop();
    }

    @Test
    public void test_timeFormatter_06() throws Exception {
        assertEquals("Wrong time Format", "05:05", TaggedFile.timeFormatter(305862000L));
    }

    @Test
    public void test_timeFormatter_07() throws Exception {
        try {
            TaggedFile.timeFormatter(-1L);
            fail("Time value underflows format; expecting exception");
        } catch (RuntimeException e) {

        }
    }

    @Test
    public void test_readTags_08() throws Exception {
        TaggedFile tf = new TaggedFile("audiofiles/Rock 812.mp3");
        Map<String, Object> tag_map = TagReader.readTags(tf.getPathname());
        for (String key : tag_map.keySet()) {
            System.out.printf("\nKey: %s\n", key);
            System.out.printf("Type of value: %s\n", tag_map.get(key).getClass().toString());
            System.out.println("Value: " + tag_map.get(key));
        }
    }

    @Test
    public void test_readAndStoreTags_09() throws Exception {
        TaggedFile tf = new TaggedFile("audiofiles/Rock 812.mp3");
        tf.readAndStoreTags();
        assertEquals("Wrong author", "Eisbach", tf.getAuthor());
        assertEquals("Wrong title", "Rock 812", tf.getTitle());
        assertEquals("Wrong album", "The Sea, the Sky", tf.getAlbum());
        assertEquals("Wrong time format", "05:31", tf.getFormattedDuration());
    }
}
