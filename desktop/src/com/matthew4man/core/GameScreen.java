package com.matthew4man.core;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.matthew4man.core.controllers.WorldController;
import com.matthew4man.core.helper.EntityContactListener;
import com.matthew4man.core.helper.TileMapHelper;
import com.matthew4man.core.objects.player.Player;
import org.lwjgl.opengl.GL20;

import static com.matthew4man.core.helper.Constants.PPM;

public class GameScreen extends ScreenAdapter {

    private OrthographicCamera camera;
    private SpriteBatch batch;
    private World world;
    private WorldController worldController;
    private Box2DDebugRenderer box2DDebugRenderer;

    private OrthogonalTiledMapRenderer orthogonalTiledMapRenderer;
    private TileMapHelper tileMapHelper;
    private EntityContactListener entityContactListener;

    private Player player;

    public GameScreen(OrthographicCamera camera) {
        this.setCamera(camera);
//        this.getCamera().zoom -= 0.5f;

        this.batch = new SpriteBatch();
        this.world = new World(new Vector2(0, -50f), false);
        this.worldController = new WorldController(this);
        this.box2DDebugRenderer = new Box2DDebugRenderer();

        this.tileMapHelper = new TileMapHelper(this);
        this.setOrthogonalTiledMapRenderer(tileMapHelper.setupMap());
        entityContactListener = new EntityContactListener();
        world.setContactListener(entityContactListener);
        entityContactListener.setPlayer(player);
    }

    private void update() {
        world.step(1 / 60f, 6, 2);
        cameraUpdate();

        batch.setProjectionMatrix(getCamera().combined);
        getOrthogonalTiledMapRenderer().setView(getCamera());
        player.update();

        if (player.getBody().getPosition().y < 0) {
            tileMapHelper.setupMap();
        }

        if (Gdx.input.isKeyPressed(Input.Keys.ESCAPE)) {
            Gdx.app.exit();
        }

        this.worldController.checkInput();
        this.worldController.performAction();
        this.worldController.checkEntityCollision();
    }

    private void cameraUpdate() {
        Vector3 position = getCamera().position;
//        position.x = Math.round(player.getBody().getPosition().x * PPM * 10) / 10f;
//        position.y = Math.round(player.getBody().getPosition().y * PPM * 10) / 10f;
//        position.x = 480;
//        position.y = 270;
        position.x = 960;
        position.y = 0;
        getCamera().position.set(position);
        getCamera().update();
    }

    @Override
    public void render(float delta) {
        this.update();

        Gdx.gl.glClearColor(0.07f,0,0.2f,1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        getOrthogonalTiledMapRenderer().render();

        batch.begin();
        player.render(batch);
        batch.end();

        box2DDebugRenderer.render(world, getCamera().combined.scl(PPM));
    }

    public World getWorld() {
        return this.world;
    }

    public void setPlayer(Player player) {
        this.player = player;
        this.worldController.setPlayer(player);
    }

    public OrthogonalTiledMapRenderer getOrthogonalTiledMapRenderer() {
        return orthogonalTiledMapRenderer;
    }

    public void setOrthogonalTiledMapRenderer(OrthogonalTiledMapRenderer orthogonalTiledMapRenderer) {
        this.orthogonalTiledMapRenderer = orthogonalTiledMapRenderer;
    }

    public OrthographicCamera getCamera() {
        return camera;
    }

    public void setCamera(OrthographicCamera camera) {
        this.camera = camera;
    }

    public EntityContactListener getContactListener() {
        return this.entityContactListener;
    }
}
