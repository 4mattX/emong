package com.matthew4man.core.helper;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.matthew4man.core.objects.player.Entity;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static com.matthew4man.core.helper.Constants.PPM;

public class EntityHelperService {

    private World world;

    public EntityHelperService(World world) {
        this.world = world;
    }

    public List<Object> createEntity(float playerX, float playerY, float clickX, float clickY) {

        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        bodyDef.position.set(playerX, playerY);
        bodyDef.fixedRotation = true;

        String uuid = (UUID.randomUUID()).toString();
        Body body = world.createBody(bodyDef);

        PolygonShape shape = new PolygonShape();
        shape.setAsBox(5 / PPM, 5 / PPM);

        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = shape;
        fixtureDef.friction = 0;
        Fixture fixture = body.createFixture(fixtureDef);
        fixture.setSensor(true);
        fixture.setUserData("spore-orb:" + uuid);


        body.setLinearVelocity(findVelocityVector(playerX, playerY, clickX, clickY));

        shape.dispose();

        List<Object> idEntityList = new ArrayList<>();

        idEntityList.add(uuid);
        idEntityList.add(new Entity(world, body, null));

        return idEntityList;
    }

    public Vector2 findVelocityVector(float playerX, float playerY, float clickX, float clickY) {
        float velocityX = clickX - playerX;
        float velocityY = clickY - playerY;

        float newVelocityX = Math.abs(velocityX);
        float newVelocityY = Math.abs(velocityY);

        while ((Math.abs(newVelocityX) + Math.abs(newVelocityY)) / 2 < 5) {
            newVelocityX *= 1.1;
            newVelocityY *= 1.1;
        }

        newVelocityX *= (velocityX < 0) ? -1 : 1;
        newVelocityY *= (velocityY < 0) ? -1 : 1;

        return new Vector2(newVelocityX * 1.2f, newVelocityY * 2.1f);
    }

}
