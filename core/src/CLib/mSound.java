package CLib;

import model.CRes;

public class mSound {

    public static float volumeSound;
    public static float volumeMusic;
    private static final int MAX_VOLUME = 10;
    public static int[] soundID;
    public static int CurMusic;
    public static boolean isMusic;
    public static boolean isSound;
    public static SoundSystem[] music;
    public static SoundSystem[] sound;
    public static boolean isEnableSound;
    public static int sizeMusic;
    public static int sizeSound;

    public static void init() {
        mSound.music = new SoundSystem[mSound.sizeMusic];
        for (int i = 0; i < mSound.music.length; ++i) {
            mSound.music[i] = new SoundSystem(new StringBuilder(String.valueOf(i)).toString(), true);
        }
        mSound.sound = new SoundSystem[mSound.sizeSound];
        for (int i = 0; i < mSound.sound.length; ++i) {
            boolean isLOOP = false;
            mSound.sound[i] = new SoundSystem(new StringBuilder(String.valueOf(i)).toString(), isLOOP);
        }
        System.gc();
    }

    public static int getSoundPoolSource(int index, String fileName) {
        return index;
    }

    public static void playSound(int id, float volume, int index) {
        if (!mSound.isSound) {
            return;
        }
        if (mSound.sound != null && mSound.sound[id] != null) {
            try {
                mSound.sound[id].play(volume);
            } catch (IllegalStateException e) {
                e.printStackTrace();
            }
        }
    }

    public static void SetLoopSound(int id, float volume, int loop) {
    }

    public static void UnSetLoopAll() {
    }

    public static void playMus(int id, float volume, boolean isLoop) {
        if (!mSound.isMusic) {
            return;
        }
        if (mSound.music != null) {
            for (int i = 0; i < mSound.music.length; ++i) {
                if (mSound.music[i] != null && mSound.music[i].isPlaying() && i != id) {
                    mSound.music[i].pause();
                }
            }
        }
        if (id < 0 || id > mSound.music.length - 1) {
            return;
        }
        try {
            mSound.music[id].setVolume(volume, volume);
            mSound.music[id].setLooping(isLoop);
            mSound.music[id].play(volume);
        } catch (IllegalStateException e) {
            e.printStackTrace();
        }
    }

    public static void pauseMusic(int id) {
    }

    public static void pauseCurMusic() {
        for (int i = 0; i < mSound.music.length; ++i) {
            if (mSound.music[i].isPlaying()) {
                mSound.music[i].pause();
                mSound.CurMusic = i;
            }
        }
    }

    public static void resumeMusic(int id) {
    }

    public static void resumeAll() {
    }

    public static void releaseAll() {
    }

    public static void stopAll() {
    }

    public static void stopSoundAll() {
        if (mSound.sound != null) {
            for (int i = 0; i < mSound.sound.length; ++i) {
                if (mSound.sound[i] != null) {
                    mSound.sound[i].stop();
                }
            }
        }
    }

    public static void setVolume(int id, int index, int soundVolume) {
    }

    public static void setVolume(int volumeSound) {
        CRes.saveRMSInt("sound", volumeSound);
        mSound.volumeSound = volumeSound / 100.0f;
    }

    static {
        mSound.volumeSound = 0.0f;
        mSound.volumeMusic = 0.0f;
        mSound.CurMusic = -1;
        mSound.isMusic = true;
        mSound.isSound = true;
        mSound.isEnableSound = true;
        mSound.sizeMusic = 1;
        mSound.sizeSound = 3;
    }
}
