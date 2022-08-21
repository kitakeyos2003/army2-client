package effect;

import CLib.mGraphics;
import map.MM;
import coreLG.CCanvas;
import model.CRes;
import item.BM;
import screen.GameScr;
import player.PM;
import com.badlogic.gdx.math.Matrix4;
import screen.CScreen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import item.Bullet;

public class Camera {

    public static final byte FREE_MODE = 0;
    public static final byte PLAYER_MODE = 1;
    static final byte BULLET_MODE = 2;
    static final byte AIRPLANE_MODE = 3;
    static final byte TARGETPOINT_MODE = 4;
    static final byte TARGETPOINT_MODE_NORETRICT = 5;
    static final byte LAZER_MODE = 6;
    static final byte METEOR_MODE = 7;
    static final byte MISSILE_RAIN_MODE = 8;
    public static byte mode;
    public static int shaking;
    int vx;
    int vy;
    public static int x;
    public static int y;
    public static int startx;
    public static int starty;
    public static int cameraW;
    public static int cameraH;
    int player;
    Bullet bullet;
    int count;
    public int dx;
    public int dy;
    public static int dx2;
    public static int dy2;
    int indexX;
    int indexY;
    int setIndexX;
    int setIndexY;
    public static OrthographicCamera camera;
    int deltaY;

    public Camera() {
        this.vx = 15;
        this.vy = 15;
        this.count = 0;
        this.deltaY = 20;
        Camera.startx = 0;
        Camera.starty = 0;
    }

    public Camera(int x, int y) {
        this.vx = 15;
        this.vy = 15;
        this.count = 0;
        this.deltaY = 20;
        this.setxy(x, y);
        Camera.startx = x;
        Camera.starty = y;
        this.setFreeMode();
    }

    public Camera(int x, int y, int w, int h) {
        this.vx = 15;
        this.vy = 15;
        this.count = 0;
        this.deltaY = 20;
        this.setxy(x, y);
        Camera.startx = x;
        Camera.starty = y;
        Camera.cameraW = w;
        Camera.cameraH = h;
        this.setFreeMode();
    }

    public void init(OrthographicCamera _camera) {
        Camera.shaking = 0;
        this.setFreeMode();
    }

    public void setxy(int x, int y) {
        Camera.x = x;
        Camera.y = y;
    }

    public void setFreeMode() {
        Camera.mode = 0;
        Camera.dx2 = 0;
        Camera.dy2 = 0;
    }

    public void setPlayerMode(int target) {
        Camera.mode = 1;
        this.player = target;
    }

    public void setBulletMode(Bullet target) {
        Camera.mode = 2;
        this.bullet = target;
    }

    public void setMeteorMode(Bullet target) {
        Camera.mode = 7;
        this.bullet = target;
    }

    public void setAirPlaneMode() {
        Camera.mode = 3;
    }

    public void setLazerMode(Bullet target) {
        Camera.mode = 6;
        this.bullet = target;
    }

    public void setMRainMode(Bullet target) {
        Camera.mode = 8;
        this.bullet = target;
    }

    public void setTargetPointMode(int index_x, int index_y) {
        Camera.mode = 4;
        this.setIndexX = index_x - CScreen.w / 2;
        this.setIndexY = index_y - CScreen.h / 2;
    }

    public void setTargetPointModeNoRetrict(int index_x, int index_y) {
        Camera.mode = 5;
        this.setIndexX = index_x - CScreen.w / 2;
        this.setIndexY = index_y - CScreen.h / 2;
    }

    public void setZoom(float zoom) {
        Camera.camera.zoom = zoom;
    }

    public Matrix4 Combine() {
        return Camera.camera.combined;
    }

    public void update() {
        Camera.startx = Camera.x;
        Camera.starty = Camera.y;
        this.indexX = 0;
        this.indexY = 0;
        switch (Camera.mode) {
            case 1: {
                if (PM.p[this.player] == null) {
                    break;
                }
                if (PM.p[this.player].look == 0) {
                    this.indexX = PM.p[this.player].x - 40 - CScreen.w / 2;
                } else if (PM.p[this.player].look == 2) {
                    this.indexX = PM.p[this.player].x + 40 - CScreen.w / 2;
                }
                this.indexY = PM.p[this.player].y - CScreen.h / 2 - 12;
                if (!GameScr.pm.isYourTurn()) {
                    this.checkIndex(4);
                    break;
                }
                if (PM.p[this.player].getState() != 1) {
                    this.checkIndex(4);
                    break;
                }
                break;
            }
            case 2: {
                if (this.bullet == null) {
                    break;
                }
                if (this.bullet.paintCount >= this.bullet.yPaint.length) {
                    break;
                }
                if (this.bullet.type == 37 && (this.bullet.angle == -90 || this.bullet.angle == 270) && this.bullet.yPaint[this.bullet.paintCount] <= -300) {
                    break;
                }
                this.indexX = this.bullet.xPaint[this.bullet.paintCount] - CScreen.w / 2;
                this.indexY = this.bullet.yPaint[this.bullet.paintCount] - CScreen.h / 2;
                if (this.bullet.type != 37) {
                    this.checkIndex(2);
                    break;
                }
                this.checkIndex(1);
                break;
            }
            case 3: {
                this.indexX = BM.airPlaneX - CScreen.w / 4;
                this.indexY = BM.airPlaneY - CScreen.h / 2;
                this.checkIndex(2);
                break;
            }
            case 6: {
                this.indexX = this.bullet.xPaint[this.bullet.paintCount] - CScreen.w / 2;
                this.indexY = this.bullet.yPaint[this.bullet.paintCount] - CScreen.h / 3;
                this.checkIndex(1);
                break;
            }
            case 7: {
                this.indexX = this.bullet.xPaint[this.bullet.paintCount] - CScreen.w / 2;
                this.indexY = this.bullet.yPaint[this.bullet.paintCount] - CScreen.h / 3;
                this.checkIndex(1);
                break;
            }
            case 8: {
                this.indexX = PM.getCurPlayer().x - CScreen.w / 2;
                this.indexY = this.bullet.yPaint[this.bullet.paintCount] - CScreen.h / 2;
                this.checkIndex(2);
                break;
            }
            case 4: {
                this.indexX = this.setIndexX;
                this.indexY = this.setIndexY;
                this.checkIndex(2);
                break;
            }
            case 5: {
                this.indexX = this.setIndexX;
                this.indexY = this.setIndexY;
                this.checkIndex(3);
                break;
            }
        }
        if (Camera.shaking != 0) {
            if (Camera.shaking == 1) {
                Camera.x += CRes.random(-5, 5);
                Camera.y += CRes.random(-5, 5);
            } else if (Camera.shaking == 2) {
                Camera.x += CRes.random(-20, 20);
                Camera.y += CRes.random(-20, 20);
            } else if (Camera.shaking == 3) {
                if (CCanvas.gameTick % 3 == 0) {
                    Camera.y += this.deltaY;
                } else {
                    Camera.y -= this.deltaY;
                }
                if (this.count % 2 == 0) {
                    --this.deltaY;
                }
                if (this.deltaY < 0) {
                    this.deltaY = 0;
                }
            }
            ++this.count;
            if (this.count > ((Camera.shaking != 3) ? 10 : 30)) {
                this.deltaY = 20;
                Camera.shaking = 0;
                this.count = 0;
            }
        }
        if (Camera.mode != 5) {
            restrict(0, MM.mapWidth, -1000, MM.mapHeight);
        }
    }

    public void mainLoop() {
        switch (Camera.mode) {
            case 0: {
                Camera.x += Camera.dx2;
                if (Camera.dx2 != 0) {
                    Camera.dx2 = 0;
                }
                Camera.y += Camera.dy2;
                if (Camera.dy2 != 0) {
                    Camera.dy2 = 0;
                    break;
                }
                break;
            }
        }
        if (Camera.mode != 5) {
            restrict(0, MM.mapWidth, -1000, MM.mapHeight);
        }
    }

    void checkIndex(int rangeBit) {
        if (Camera.x != this.indexX) {
            this.dx = Camera.x - this.indexX;
            Camera.x -= this.dx >> rangeBit;
        }
        if (Camera.y != this.indexY) {
            this.dy = Camera.y - this.indexY;
            Camera.y -= this.dy >> rangeBit;
        }
    }

    public static void restrict(int left, int right, int up, int down) {
        if (Camera.y < up) {
            Camera.y = up;
        }
        if (Camera.y > down - CScreen.h) {
            Camera.y = down - CScreen.h;
            Camera.starty = Camera.y;
        }
        if (Camera.x < left) {
            Camera.x = left;
        }
        if (Camera.x > right - CScreen.w) {
            Camera.x = right - CScreen.w;
        }
    }

    public static void translate(mGraphics g) {
        g.translate(-Camera.x, -Camera.y);
    }

    public static void reTranslate(mGraphics g) {
        g.translate(-g.getTranslateX(), -g.getTranslateY());
    }

    public static String getMode() {
        if (Camera.mode == 0) {
            return "FREE_MODE";
        }
        if (Camera.mode == 1) {
            return "PLAYER_MODE";
        }
        if (Camera.mode == 2) {
            return "BULLET_MODE";
        }
        if (Camera.mode == 3) {
            return "AIRPLANE_MODE";
        }
        if (Camera.mode == 4) {
            return "TARGETPOINT_MODE";
        }
        if (Camera.mode == 5) {
            return "TARGETPOINT_MODE_NORETRICT";
        }
        if (Camera.mode == 6) {
            return "LAZER_MODE";
        }
        if (Camera.mode == 7) {
            return "METEOR_MODE";
        }
        if (Camera.mode == 8) {
            return "MISSILE_RAIN_MODE";
        }
        return "NONE";
    }

    public void onClearCamera() {
        this.bullet = null;
        this.setFreeMode();
    }

    static {
        Camera.shaking = 0;
        Camera.x = 0;
        Camera.y = 0;
        Camera.startx = 0;
        Camera.starty = 0;
        Camera.cameraW = 0;
        Camera.cameraH = 0;
    }
}
