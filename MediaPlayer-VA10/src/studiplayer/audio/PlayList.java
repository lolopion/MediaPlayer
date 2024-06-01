package studiplayer.audio;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Collections;
import java.util.LinkedList;
import java.util.Scanner;
import java.io.File;

public class PlayList extends LinkedList<AudioFile> {
    int currentIndex = 0;
    boolean shufflemode = false;

    public PlayList() {
    }

    public PlayList(String pathname) {
        super();
        String copyofpathname = pathname.toLowerCase();
        if (copyofpathname.endsWith(".m3u")) {
            this.loadFromM3U(pathname);
        } else {
            throw new RuntimeException();
        }
    }

    public void setCurrent(int current) {
        currentIndex = current;
    }

    public int getCurrent() {
        return currentIndex;
    }

    public void changeCurrent() {
        if (currentIndex >= this.size() - 1) {
            currentIndex = 0;
            if (shufflemode == true) {
                this.setRandomOrder(true);
            } else {
                
            }
        } else if (currentIndex <= this.size() - 1) {
            currentIndex++;
        }
    }

    public AudioFile getCurrentAudioFile() {
        if (this.isEmpty() == true) {
            return null;
        } else if (currentIndex >= this.size()) {
            return null;
        } else if (this.get(getCurrent()) == null) {
            return null;
        } else {
            return this.get(getCurrent());
        }
    }

    public void setRandomOrder(boolean randomOrder) {
        if (randomOrder == true) {
            Collections.shuffle(this);
            shufflemode = true;
        } else {
            shufflemode = false;
        }
    }

    public void sort(SortCriterion order) {
        if (order == SortCriterion.TITLE) {
            Collections.sort(this, new TitleComparator());
        } else if (order == SortCriterion.AUTHOR) {
            Collections.sort(this, new AuthorComparator());
        } else if (order == SortCriterion.ALBUM) {
            Collections.sort(this, new AlbumComparator());
        } else if (order == SortCriterion.DURATION){
            Collections.sort(this, new DurationComparator());
        }
    }
    public void saveAsM3U(String pathname) {
        FileWriter AudioFileWriter = null;
        String linesep = System.getProperty("line.separator");
        try {
            AudioFileWriter = new FileWriter(pathname);
            for(AudioFile audioFile : this) {
               AudioFileWriter.write(audioFile.pathname + linesep); 
            }            
        } catch (IOException e) {
            throw new RuntimeException();
        } finally {
            try {
                AudioFileWriter.close();
            } catch (Exception e) {

            }
        }
    }

    public void loadFromM3U(String pathname) {
        this.clear();
        currentIndex = 0;
        Scanner scanner = null;
        String line;
        try {
            scanner = new Scanner(new File(pathname));
            while (scanner.hasNextLine()) {
                line = scanner.nextLine();
                if (line.isBlank() == false && line.startsWith("#") == false) {
                    try {
                        this.add(AudioFileFactory.getInstance(line));
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            try {
                scanner.close();
            } catch (Exception e) {

            }
        }
    }
}
