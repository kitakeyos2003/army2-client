package CLib;

import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.Gdx;
import coreLG.CCanvas;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;

public class SoundSystem {

    public Sound objSound;
    public Music objMusic;
    public boolean isLoop;

    public SoundSystem(String name, boolean isLoop) {
        this.isLoop = isLoop;
        if (name.contains(".ogg")) {
            throw new IllegalArgumentException("ONLY SUPPORT FILE.WAV AMD ");
        }
        try {
            if (CCanvas.isPc()) {
                if (isLoop) {
                    String nameT = "res/music/" + name + ".mp3";
                    FileHandle fileHandle = Gdx.files.local(nameT);
                    this.objMusic = Gdx.audio.newMusic(fileHandle);
                } else {
                    String nameT = "res/sound/" + name + ".wav";
                    FileHandle fileHandle = Gdx.files.local(nameT);
                    this.objSound = Gdx.audio.newSound(Gdx.files.local(nameT));
                }
            } else if (isLoop) {
                String nameT = "res/music/" + name + ".mp3";
                FileHandle fileHandle = Gdx.files.internal(nameT);
                this.objMusic = Gdx.audio.newMusic(fileHandle);
            } else {
                String nameT = "res/sound/" + name + ".wav";
                FileHandle fileHandle = Gdx.files.internal(nameT);
                this.objSound = Gdx.audio.newSound(fileHandle);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void play(float volume) {
        if (this.isLoop) {
            if (this.objMusic != null) {
                this.objMusic.setVolume(volume);
                this.objMusic.play();
            }
        } else if (this.objSound != null) {
            this.objSound.play(volume);
        }
    }

    public boolean isPlaying() {
        return this.objMusic != null && this.objMusic.isPlaying();
    }

    public void pause() {
        if (this.isLoop) {
            if (this.objMusic != null) {
                this.objMusic.pause();
            }
        } else if (this.objSound != null) {
            this.objSound.pause();
        }
    }

    public void stop() {
        if (this.isLoop) {
            if (this.objMusic != null) {
                this.objMusic.stop();
            }
        } else if (this.objSound != null) {
            this.objSound.stop();
        }
    }

    public void setLooping(boolean isLoop2) {
        if (this.objMusic != null) {
            this.objMusic.setLooping(isLoop2);
        }
    }

    public void setVolume(float volume, float volume2) {
        if (this.objMusic != null) {
            this.objMusic.setVolume(volume2);
        }
    }
}
