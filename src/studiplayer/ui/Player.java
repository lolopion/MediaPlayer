package studiplayer.ui;

import java.net.URL;
import java.util.List;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import studiplayer.audio.AudioFile;
import studiplayer.audio.NotPlayableException;
import studiplayer.audio.PlayList;

public class Player extends Application {
    Button playButton = createButton("play.png");
    Button pauseButton = createButton("pause.png");
    Button stopButton = createButton("stop.png");
    Button nextButton = createButton("next.png");
    Button editorButton = createButton("pl_editor.png");
    private PlayList playList;
    private String playListPathname;
    final String StartTime = "00:00";
    final String currentSong = "Current song: ";
    public final static String DEFAULT_PLAYLIST = "playlists/DefaultPlayList.m3u";
    private Label songDescription;
    private Label playTime;
    private volatile boolean stopped = true;
    String play_Time = "";
    String song_Description = "";
    private PlayListEditor playListEditor;
    private boolean editorVisible;
    private PlayerThread playerThread;
    private TimerThread timerThread;
    Stage aktualisieren = new Stage();

    public Player() {
    }

    private class PlayerThread extends Thread {
        public PlayerThread() {
            super("Player");
        }

        public void run() {
            // <- hier algorithmik, die parallel ausgeführt wird
            while (!stopped && playList != null && !playList.isEmpty()) {
                try {
                    playList.getCurrentAudioFile().play();
                    updateSongInfo(playList.getCurrentAudioFile());
                } catch (NotPlayableException e) {
                    e.printStackTrace();
                }
                if (!stopped) {
                    playList.changeCurrent();
                    updateSongInfo(playList.getCurrentAudioFile());
                } else {

                }
            }
        }
    }

    private class TimerThread extends Thread {
        public TimerThread() {
            super("Timer");
        }

        public void run() {
            while (!stopped && playList != null && !playList.isEmpty()) {
                updateSongInfo(playList.getCurrentAudioFile());
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {

                }
            }
        }
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        // Objekt wird erzeugt mithilfe der Kommandozeile
        setButtonStates(false, true, true, false, false);
        editorVisible = false;
        List<String> parameters = getParameters().getRaw();
        if (!parameters.isEmpty()) {
            try {
                playList = new PlayList(parameters.get(0));
                playListPathname = parameters.get(0);
            } catch (Exception e) {
                throw new RuntimeException("m3u file with the pathname: " + parameters.get(0) + " doesnt exist!");
            }
        } else {
            playList = new PlayList(DEFAULT_PLAYLIST);
            playListPathname = DEFAULT_PLAYLIST;
        }
        Pane pane1 = new HBox();
        BorderPane mainPane = new BorderPane();
        songDescription = new Label("");
        playTime = new Label("");
        mainPane.setTop(songDescription);
        pane1.getChildren().add(playTime);
        updateSongInfo(playList.getCurrentAudioFile());
        playListEditor = new PlayListEditor(this, this.playList);
        mainPane.setCenter(pane1);
        Scene scene1 = new Scene(mainPane, 700, 90);
        primaryStage.setScene(scene1);
        primaryStage.setTitle(currentSong + song_Description);
        aktualisieren = primaryStage;
        pane1.getChildren().add(playButton);
        playButton.setOnAction(e -> {
            playCurrentSong();
            setButtonStates(true, false, false, false, false);
        });
        pane1.getChildren().add(pauseButton);
        pauseButton.setOnAction(e -> {
            pauseCurrentSong();
            setButtonStates(true, false, false, false, false);
        });
        pane1.getChildren().add(stopButton);
        stopButton.setOnAction(e -> {
            int curr = playList.getCurrent();
            stopCurrentSong();
            playList.setCurrent(curr);
            updateSongInfo(playList.getCurrentAudioFile());
            setButtonStates(false, true, true, false, false);
        });
        pane1.getChildren().add(nextButton);
        nextButton.setOnAction(e -> {
            if (!stopped) {
                playList.getCurrentAudioFile().stop();
                // warum ist das so???
//                playList.changeCurrent();
                playCurrentSong();
                setButtonStates(true, false, false, false, false);
            } else {
                playList.changeCurrent();
                playCurrentSong();
                setButtonStates(true, false, false, false, false);
            }
        });
        pane1.getChildren().add(editorButton);
        editorButton.setOnAction(e -> {
            if (editorVisible) {
                editorVisible = false;
                playListEditor.hide();
            } else {
                editorVisible = true;
                playListEditor.show();
            }
        });
        primaryStage.show();
    }

    public Button createButton(String iconfile) {
        Button button = null;
        try {
            URL url = getClass().getResource("/icons/" + iconfile);
            Image icon = new Image(url.toString());
            ImageView imageView = new ImageView(icon);
            imageView.setFitHeight(48);
            imageView.setFitWidth(48);
            button = new Button("", imageView);
            button.setContentDisplay(ContentDisplay.GRAPHIC_ONLY);
        } catch (Exception e) {
            System.out.println("Image " + "icons/" + iconfile + " not found!");
            System.exit(-1);
        }
        return button;
    }

    public void playCurrentSong() {
        if (playList != null && !playList.isEmpty()) {
            stopped = false;
            playerThread = new PlayerThread();
            playerThread.start();
            timerThread = new TimerThread();
            timerThread.start();
        } else {
            System.out.println("PlayList ist leer");
        }
    }

    private void pauseCurrentSong() {
        if (playList == null || playList.isEmpty()) {
            System.out.println("PlayList ist leer");
        } else {
            playList.getCurrentAudioFile().togglePause();
            if (stopped) {
                stopped = false;
            } else {
                stopped = true;
            }
        }
    }

    private void stopCurrentSong() {
        if (playList != null && !playList.isEmpty()) {
            playList.getCurrentAudioFile().stop();
            stopped = true;
            play_Time = StartTime;
            Platform.runLater(() -> {
                playTime.setText(play_Time);
            });
            playerThread.stop();
        } else {
            System.out.println("PlayList ist leer");
        }
    }

    private void updateSongInfo(AudioFile af) {
        if (af == null) {
            play_Time = "--:--";
            song_Description = "no current song";
            Platform.runLater(() -> {
                playTime.setText(play_Time);
                songDescription.setText(song_Description);
                aktualisieren.setTitle(song_Description);
            });
        } else if (af != null) {
            song_Description = af.toString();
            play_Time = af.getFormattedPosition();
            Platform.runLater(() -> {
                playTime.setText(play_Time);
                songDescription.setText(song_Description);
                aktualisieren.setTitle(song_Description);
            });
        }
    }

    private void refreshUI() {
        Platform.runLater(() -> {
            if (playList != null && playList.size() > 0) {
                updateSongInfo(playList.getCurrentAudioFile());
                setButtonStates(false, true, true, false, false);
            } else {
                updateSongInfo(null);
                setButtonStates(true, true, true, true, false);
            }
        });
    }

    private void setButtonStates(boolean playButtonState, boolean pauseButtonState, boolean stopButtonState,
            boolean nextButtonState, boolean editorButtonState) {
        playButton.setDisable(playButtonState);
        pauseButton.setDisable(pauseButtonState);
        stopButton.setDisable(stopButtonState);
        nextButton.setDisable(nextButtonState);
        editorButton.setDisable(editorButtonState);
    }

    public void setEditorVisible(boolean visible) {
        editorVisible = visible;
    }

    public String getPlayListPathname() {
        return playListPathname;
    }

    public void setPlayList(String Pathname) {
        if (Pathname != null && !Pathname.isBlank()) {
            playList = new PlayList(Pathname);
            refreshUI();
        } else {
            playList = new PlayList(DEFAULT_PLAYLIST);
            refreshUI();
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
