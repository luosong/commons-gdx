package com.gemserk.commons.gdx.games;

import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;

public class SpatialPhysicsImpl implements Spatial {
	
	private final Vector2 position = new Vector2();
	
	private float angle = 0f;
	
	private final Body body;
	
	private float w, h;
	
	@Override
	public float getX() {
		return body.getTransform().getPosition().x;
	}

	@Override
	public float getY() {
		return body.getTransform().getPosition().y;
	}

	@Override
	public void setPosition(float x, float y) {
		position.set(x, y);
		body.setTransform(position, angle * MathUtils.degreesToRadians);
	}

	@Override
	public float getAngle() {
		// floatValue.value = (float) (body.getAngle() * 180f / Math.PI);
		this.angle = body.getAngle() * MathUtils.radiansToDegrees;
		return angle;
	}

	@Override
	public void setAngle(float angle) {
		this.angle = angle;
		// TODO: fix this to update the position if it wasn't correctly set yet.
		body.getTransform().setRotation(angle * MathUtils.degreesToRadians);
	}

	@Override
	public float getWidth() {
		return w;
	}

	@Override
	public float getHeight() {
		return h;
	}

	@Override
	public void setSize(float width, float height) {
		this.w = width;
		this.h = height;
		// TODO: do resizing of the body?
	}

	public SpatialPhysicsImpl(Body body, float w, float h) {
		this.body = body;
		this.w = w;
		this.h = h;
	}
	
	public SpatialPhysicsImpl(Body body, Spatial spatial) {
		this.body = body;
		set(spatial);
	}
	
	@Override
	public void set(Spatial spatial) {
		setPosition(spatial.getX(), spatial.getY());
		setSize(spatial.getWidth(), spatial.getHeight());
		setAngle(spatial.getAngle());
	}

	@Override
	public Vector2 getPosition() {
		position.set(body.getTransform().getPosition());
		return position;
	}

}
