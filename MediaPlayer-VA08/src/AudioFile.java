import java.io.File;

public abstract class AudioFile {
    String pathname;
    String filename;
    String author;
    String title;
    String album;
    long duration;

    public abstract void play();

    public abstract void togglePause();

    public abstract void stop();

    public abstract String getFormattedDuration();

    public abstract String getFormattedPosition();
    
    public abstract String[] fields();

    char separatorChar = System.getProperty("file.separator").charAt(0);

    public AudioFile() {
    }

    public AudioFile(String pathname) {
        boolean test;
        this.parsePathname(pathname);
        File file = new File(getPathname());
        test = file.canRead();
        if (test == false) {
            throw new RuntimeException("Ungültiger Dateipfad " + getPathname());
        } else {

        }
        this.parseFilename(this.getFilename());
    }

    public void parsePathname(String p) {
        pathname = p;
        int count = 0;
        if (pathname.isEmpty() == true || pathname.equals(null) == true) {

        } else {
            if (isWindows() == true) {
                String Windows_pathname_front;
                String Windows_drive_character;
                String Windows_pathname_end;
                pathname = pathname.replace('/', separatorChar);

                if (pathname.charAt(0) == '\\' && pathname.contains(":") == false
                        && Character.isAlphabetic(pathname.charAt(1)) == true && pathname.charAt(2) == separatorChar) {
                    Windows_pathname_front = pathname.substring(1, 1);
                    Windows_drive_character = pathname.substring(1, 2);
                    Windows_pathname_end = pathname.substring(3, pathname.length());
                    Windows_drive_character = Windows_drive_character.concat(":\\");
                    Windows_pathname_front = Windows_pathname_front.concat(Windows_drive_character);
                    pathname = Windows_pathname_front.concat(Windows_pathname_end);
                }
            } else {
                pathname = pathname.replace('\\', separatorChar);
                if (pathname.charAt(0) != separatorChar && pathname.contains(":") == true
                        && Character.isLetter(pathname.charAt(0)) == true) {
                    if (pathname.contains("home") == true) {
                    } else {
                        pathname = "/".concat(pathname);
                    }
                }
                if (pathname.contains(":") == true) {
                    pathname = pathname.replaceFirst(":", "/");
                }

            }

            for (int i = 0; i < pathname.length(); i++) {
                String separator_substring;
                String pathname_substring_front;
                String pathname_substring_end;

                if (pathname.charAt(i) == separatorChar) {
                    count++;
                }
                if (count > 0 && pathname.charAt(i) != separatorChar) {
                    count = 0;
                }

                if (pathname.charAt(i) == separatorChar && count >= 2) {
                    separator_substring = pathname.substring(i - count + 2, i);
                    pathname_substring_front = pathname.substring(0, i - count + 2);
                    pathname_substring_end = pathname.substring(i + 1, pathname.length());
                    separator_substring = separator_substring.replace(separatorChar, ' ');
                    separator_substring = separator_substring.replace(" ", "");
                    pathname = pathname_substring_front.concat(pathname_substring_end);
                    count = 0;
                    i = 0;
                }
            }

            int filenamePosition = pathname.lastIndexOf(separatorChar) + 1;
            filename = pathname.substring(filenamePosition);
        }
    }

    public String getFilename() {
        if (filename == null) {
            return "";
        } else if (pathname.endsWith("separatorChar") == true) {
            return "";
        } else if (pathname.isEmpty() == true) {
            return "";
        } else {
            return filename;
        }
    }

    public String getPathname() {
        if (pathname == null) {
            return "";
        } else if (pathname.isEmpty() == true) {
            return "";
        } else {
            return pathname;
        }
    }

    public void parseFilename(String f) {
        filename = f;
        if (filename.isEmpty() == true) {
            title = "";
            author = "";
        } else if (filename.charAt(0) == '.' && filename.contains("..mp3") == false) {
            title = "";
            author = "";
        } else if (filename.isBlank() == true) {
            title = "";
            author = "";
        } else {
            if (filename.contains(" - ") == false) {
                if (filename.contains(".") == false) {
                    author = "";
                    title = filename.substring(filename.indexOf("-"), filename.length());
                } else {
                    title = filename.substring(0, filename.lastIndexOf('.'));
                    title = title.trim();
                    author = "";
                }
            } else {
                if (filename.contains(" - ") == true) {
                    if (filename.contains(".") == false) {
                        author = "";
                        title = filename.substring(filename.indexOf("-") + 1, filename.length());
                        title = title.trim();
                    } else {
                        author = filename.substring(0, filename.indexOf(" - "));
                        author = author.trim();
                        title = filename.substring(filename.indexOf(" - ") + 2, filename.lastIndexOf('.'));
                        title = title.trim();
                    }
                } else {
                    author = filename.substring(0, filename.indexOf("-"));
                    author = author.trim();
                    title = filename.substring(filename.indexOf("-"), filename.lastIndexOf('.'));
                    title = title.trim();
                }
            }
        }
    }

    public String getAuthor() {
        return this.author;
    }

    public String getTitle() {
        return this.title;
    }

    public String toString() {
        if (getAuthor().equals("")) {
            return getTitle();
        } else {
            return getAuthor() + " - " + getTitle();
        }
    }

    private boolean isWindows() {
        return System.getProperty("os.name").toLowerCase().indexOf("win") >= 0;
    }

    public static void main(String[] args) {

//		AudioFile file1 = new AudioFile("Falco - Rock Me Amadeus.mp3");
//		System.out.println(file1.getPathname() + " - " + file1.getFilename());
//		System.out.println(file1.getAuthor());
//		System.out.println(file1.getTitle());
//

//		AudioFile file2 = new AudioFile("d:\\\\\\home\\\\Cro-Freiheit.mp3");
//		file2.parsePathname("d:\\\\\\home\\\\Cro-Freiheit.mp3");
//		System.out.println(file2.getPathname() + " - " + file2.getFilename());
    }
}
