package studiplayer.audio;
import java.util.Map;

import studiplayer.basic.TagReader;

public class TaggedFile extends SampledFile {
    private String[] field = { "", "", "", "" };
    public TaggedFile() {
        super();
    }

    public TaggedFile(String s) throws NotPlayableException {
        super(s);
        readAndStoreTags();
    }

    public void readAndStoreTags() throws NotPlayableException {
        try {
            Map<String, Object> tag_map = TagReader.readTags(getPathname());
            if (tag_map.containsKey(null)) {
                title = "";
                author = "";
                album = "";
                duration = 0;
            } else {
                if (tag_map.containsKey("title") == false) {

                } else {
                    title = (String) tag_map.get("title");
                    title = title.trim();
                }
                if (tag_map.containsKey("author") == false) {

                } else {
                    author = (String) tag_map.get("author");
                    author = author.trim();
                }
                if (tag_map.containsKey("album") == false) {

                } else {
                    album = (String) tag_map.get("album");
                    album = album.trim();
                }
                if (tag_map.containsKey("duration") == false || tag_map.get("duration").equals(0)) {

                } else {
                    duration = (long) tag_map.get("duration");
                }
            }
        } catch (RuntimeException e) {
            throw new NotPlayableException(this.getPathname(), "AudioFile Tags failed");
        }

    }

    public String getAlbum() {
        return album;
    }

    public String toString() {
        if (album == "" || album == null) {
            return (super.toString() + " - " + getFormattedDuration());
        } else {
            return (super.toString() + " - " + album + " - " + getFormattedDuration());
        }
    }

    public String[] fields() {

        field[0] = this.author;
        field[1] = this.title;
        field[2] = this.album;
        field[3] = this.getFormattedDuration();
        return field;
    }
}
