package com.gemserk.commons.gdx.graphics;

import java.nio.FloatBuffer;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.glutils.ImmediateModeRenderer;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

public class ImmediateModeRendererUtils {

	private static final ImmediateModeRenderer renderer = new ImmediateModeRenderer();

	private static final Vector2 tmp = new Vector2();

	private static final Vector2 angleTmp = new Vector2(1, 0);

	public static void drawSolidCircle(Circle circle, float angle, Color color) {
		drawSolidCircle(circle.x, circle.y, circle.radius, angle, color);
	}

	public static void drawSolidCircle(Vector2 center, float radius, float axisAngle, Color color) {
		drawSolidCircle(center.x, center.y, radius, axisAngle, color);
	}

	public static void drawSolidCircle(float x, float y, float radius, float axisAngle, Color color) {
		angleTmp.set(1, 0);
		angleTmp.rotate(axisAngle);
		drawSolidCircle(x, y, radius, angleTmp, color);
	}

	public static void drawSolidCircle(Vector2 center, float radius, Vector2 axis, Color color) {
		drawSolidCircle(center.x, center.y, radius, axis, color);
	}

	public static void drawSolidCircle(float x, float y, float radius, Vector2 axis, Color color) {
		drawSolidCircle(x, y, radius, color);
		drawLine(x, y, x + axis.x * radius, y + axis.y * radius, color);
	}

	public static void drawSolidCircle(Vector2 center, float radius, Color color) {
		drawSolidCircle(center.x, center.y, radius, color);
	}

	public static void drawSolidCircle(float x, float y, float radius, Color color) {
		renderer.begin(GL10.GL_LINE_LOOP);
		{
			float angle = 0;
			float angleInc = 2 * (float) Math.PI / 20;
			for (int i = 0; i < 20; i++, angle += angleInc) {
				tmp.set((float) Math.cos(angle) * radius + x, (float) Math.sin(angle) * radius + y);
				renderer.color(color.r, color.g, color.b, color.a);
				renderer.vertex(tmp.x, tmp.y, 0);
			}
		}
		renderer.end();
	}

	public static void drawLine(Vector2 p0, Vector2 p1, Color color) {
		drawLine(p0.x, p0.y, p1.x, p1.y, color);
	}

	public static void drawLine(float x0, float y0, float x1, float y1, Color color) {
		renderer.begin(GL10.GL_LINES);
		{
			renderer.color(color.r, color.g, color.b, color.a);
			renderer.vertex(x0, y0, 0);
			renderer.color(color.r, color.g, color.b, color.a);
			renderer.vertex(x1, y1, 0);
		}
		renderer.end();
	}

	public static void drawHorizontalAxis(float y, Color color) {
		drawHorizontalAxis(y, 10000, color);
	}

	public static void drawVerticalAxis(float x, Color color) {
		drawVerticalAxis(x, 10000, color);
	}

	public static void drawHorizontalAxis(float y, float length, Color color) {
		drawLine(-length, y, length, y, color);
	}

	public static void drawVerticalAxis(float x, float length, Color color) {
		drawLine(x, -length, x, length, color);
	}

	public static void drawRectangle(Rectangle r, Color color) {
		drawRectangle(r.x, r.y, r.x + r.getWidth(), r.y + r.getHeight(), color);
	}

	public static void drawRectangle(float x0, float y0, float x1, float y1, Color color) {
		renderer.begin(GL10.GL_LINE_LOOP);
		{
			renderer.color(color.r, color.g, color.b, color.a);
			renderer.vertex(x0, y0, 0f);

			renderer.color(color.r, color.g, color.b, color.a);
			renderer.vertex(x0, y1, 0f);

			renderer.color(color.r, color.g, color.b, color.a);
			renderer.vertex(x1, y1, 0f);

			renderer.color(color.r, color.g, color.b, color.a);
			renderer.vertex(x1, y0, 0f);
		}
		renderer.end();
	}

	public static void drawPolygon(Vector2[] vertices, float x, float y, float angle, Color color) {
		GL10 gl = Gdx.graphics.getGL10();
		gl.glPushMatrix();

		gl.glTranslatef(x, y, 0f);
		gl.glRotatef(angle, 0f, 0f, 1f);

		renderer.begin(GL10.GL_LINE_LOOP);
		for (int i = 0; i < vertices.length; i++) {
			Vector2 v = vertices[i];
			renderer.color(color.r, color.g, color.b, color.a);
			renderer.vertex(v.x, v.y, 0);
		}
		renderer.end();

		gl.glPopMatrix();
	}

	public static void draw(Triangulator triangulator, float x, float y, float angle, Color color) {
		GL10 gl = Gdx.graphics.getGL10();

		gl.glPushMatrix();
		gl.glTranslatef(x, y, 0f);
		gl.glRotatef(angle, 0f, 0f, 1f);

		renderer.begin(GL10.GL_TRIANGLES);
		for (int i = 0; i < triangulator.getTriangleCount(); i++) {
			for (int p = 0; p < 3; p++) {
				float[] pt = triangulator.getTrianglePoint(i, p);
				renderer.color(color.r, color.g, color.b, color.a);
				renderer.vertex(pt[0], pt[1], 0f);
			}
		}
		renderer.end();

		gl.glPopMatrix();
	}

	public static void draw(int primitiveType, int count, FloatBuffer verticesBuffer) {
		GL10 gl = Gdx.gl10;
		gl.glEnableClientState(GL10.GL_VERTEX_ARRAY);
		gl.glVertexPointer(3, GL10.GL_FLOAT, 0, verticesBuffer);
		gl.glDrawArrays(primitiveType, 0, count);
	}

	public static void draw(int primitiveType, int count, FloatBuffer verticesBuffer, FloatBuffer colorsBuffer) {
		GL10 gl = Gdx.gl10;

		gl.glPolygonMode(GL10.GL_FRONT_AND_BACK, GL10.GL_LINE);
		
		gl.glEnableClientState(GL10.GL_VERTEX_ARRAY);
		gl.glVertexPointer(3, GL10.GL_FLOAT, 0, verticesBuffer);

		if (colorsBuffer != null) {
			gl.glEnableClientState(GL10.GL_COLOR_ARRAY);
			gl.glColorPointer(4, GL10.GL_FLOAT, 0, colorsBuffer);
		}

		gl.glDrawArrays(primitiveType, 0, count);

		if (colorsBuffer != null) {
			gl.glDisableClientState(GL10.GL_COLOR_ARRAY);
			gl.glColorPointer(4, GL10.GL_FLOAT, 0, colorsBuffer);
		}
	}

}
