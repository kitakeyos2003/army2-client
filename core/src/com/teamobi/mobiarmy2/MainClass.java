package com.teamobi.mobiarmy2;

import CLib.EasingFunction;
import com.badlogic.gdx.InputProcessor;
import CLib.LibSysTem;
import CLib.mImage;
import CLib.Image;
import model.IAction2;
import model.FilePack;
import coreLG.CCanvas;
import coreLG.CONFIG;
import java.io.InputStream;
import java.io.FileNotFoundException;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import com.badlogic.gdx.Gdx;
import CLib.mGraphics;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.ApplicationAdapter;

public class MainClass extends ApplicationAdapter {

    SpriteBatch batch;
    Texture img;
    MyInputProcessor inputProcessor;
    private InputMultiplexer inputMultiplexer;
    public static Point mousePoint;
    public static Point mousePointOld;
    ShapeRenderer shapeRenderer;
    OrthographicCamera camera;
    private Sprite sprite;
    public static Texture texture;
    public static SpriteBatch _spriteBachl;
    private mGraphics _graphic;
    public static int zoomlevel;
    public static int scaleX;
    public static int scaleY;
    public static float speed;
    public static long timeDeltaTem;
    public static long timeDelta;
    int width;
    int height;
    int errrender;

    public MainClass() {
        this.errrender = 0;
    }

    public void create() {
        this._graphic = new mGraphics(new SpriteBatch());
        this.shapeRenderer = new ShapeRenderer();
        this.batch = new SpriteBatch();
        this.camera = new OrthographicCamera();
        this.sprite = new Sprite(new Texture(Gdx.files.internal("badlogic.jpg")));
        try {
            this.onCheckDataInputStreamAnyFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
        MainClass._spriteBachl = new SpriteBatch();
    }

    private void onCheckData() {
        InputStream input = null;
        try {
            input = new FileInputStream("D:\\testout.txt");
            DataInputStream inst = new DataInputStream(input);
            int count = input.available();
            byte[] ary = new byte[count];
            inst.read(ary);
            byte[] array;
            for (int length = (array = ary).length, i = 0; i < length; ++i) {
                byte bt = array[i];
                char k = (char) bt;
                System.out.print(String.valueOf(k) + "-");
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e2) {
            e2.printStackTrace();
        }
    }

    private void onCheckDataInputStream() {
        try {
            FilePack filePack = new FilePack(CCanvas.getClassPathConfig(String.valueOf(CONFIG.PATH_EFFECT) + "effect"));
            if (filePack != null) {
                filePack.loadImage("ex3.png", new IAction2() {
                    @Override
                    public void perform(Object object) {
                        mImage _mImage = new mImage((Image) object);
                        MainClass.texture = _mImage.image.texture;
                    }
                });
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void onCheckDataInputStreamAnyFile() throws IOException {
        InputStream input = null;
        DataInputStream dis = null;
        try {
            input = Gdx.files.local(String.valueOf(LibSysTem.res) + "/barMove_x2.png").read();
            dis = new DataInputStream(input);
            int count = input.available();
            byte[] arr = new byte[count];
            dis.read(arr);
            byte[] array;
            for (int length = (array = arr).length, i = 0; i < length; ++i) {
                byte bt = array[i];
                char k = (char) bt;
                System.out.print(String.valueOf(k) + "-");
            }
        } catch (IOException ex) {
            return;
        } finally {
            dis.close();
        }
        dis.close();
    }

    public void render() {
        Gdx.gl.glClearColor(0.0f, 0.0f, 0.0f, 1.0f);
        this.errrender = 1;
        Gdx.gl.glClear(16384);
        this.errrender = 111;
        Gdx.gl.glEnable(3042);
        this.errrender = 112;
        Gdx.gl.glBlendFunc(770, 771);
        this.errrender = 114;
        Gdx.gl.glDisable(3042);
        this.errrender = 2;
        this._graphic.g.setProjectionMatrix(this.camera.combined);
        this.errrender = 3;
        this.errrender = 4;
        this.camera.update();
        this.errrender = 5;
        this.batch.begin();
        this.errrender = 10;
        this.batch.draw(MainClass.texture, 0.0f, 0.0f);
        this.errrender = 11;
        this.batch.end();
        this.errrender = 12;
    }

    public void dispose() {
        if (this.batch != null) {
            this.batch.dispose();
        }
        if (this.img != null) {
            this.img.dispose();
        }
    }

    public void initaliseInputProcessors() {
        this.inputMultiplexer = new InputMultiplexer();
        Gdx.input.setInputProcessor(this.inputMultiplexer);
        this.inputProcessor = new MyInputProcessor();
        this.inputMultiplexer.addProcessor(this.inputProcessor);
    }

    static {
        MainClass.zoomlevel = 1;
        MainClass.scaleX = 1;
        MainClass.scaleY = 1;
        MainClass.speed = 1.0f;
    }

    public class Point {

        public float x;
        public float y;

        public Point(float x, float y) {
            this.x = x;
            this.y = y;
        }
    }

    class MyInputProcessor implements InputProcessor {

        public boolean keyDown(int keycode) {
            if (keycode == 29) {
                ++MainClass.speed;
                return false;
            }
            return false;
        }

        public boolean keyUp(int keycode) {
            return false;
        }

        public boolean keyTyped(char character) {
            return false;
        }

        public boolean touchDown(int screenX, int screenY, int pointer, int button) {
            float scrX = EasingFunction.EaseInBack(MainClass.mousePointOld.x, MainClass.mousePoint.y, 1.0f * MainClass.speed);
            float scrY = EasingFunction.EaseInBack(MainClass.mousePointOld.y, MainClass.mousePoint.y, 1.0f * MainClass.speed);
            MainClass.mousePoint = new Point(scrX, scrY);
            return false;
        }

        public boolean touchUp(int screenX, int screenY, int pointer, int button) {
            MainClass.mousePointOld = MainClass.mousePoint;
            return false;
        }

        public boolean touchDragged(int screenX, int screenY, int pointer) {
            return false;
        }

        public boolean mouseMoved(int screenX, int screenY) {
            return false;
        }

        public boolean scrolled(float amountX, float amountY) {
            return false;
        }
    }
}
