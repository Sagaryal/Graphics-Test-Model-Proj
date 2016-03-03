package gra29;

import java.io.IOException;
import java.nio.IntBuffer;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.lwjgl.BufferUtils;
import org.lwjgl.LWJGLException;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.lwjgl.util.vector.Vector3f;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.util.glu.GLU.*;

public class Main {
	
	private Model m;
	private Vector3f location, rotation;
	private List<Point> points;
	private int width = 1024;
	private int height = 768;
	private String modelPath = "res/icosphere.obj";
	

	public static void main(String args[]) {
		new Main().run();
	}
	
	public void run() {
		try {
			Display.setDisplayMode(new DisplayMode(width, height));
			Display.setTitle("Model Viewer");
			Display.create();
		} catch(LWJGLException e) {
			e.printStackTrace();
			Display.destroy();
			System.exit(1);
		}
		
		points = new ArrayList<Point>();
		Random rand = new Random();
		for(int i = 0; i < 10000; i++) {
			points.add(new Point(rand.nextFloat() - 0.5f, rand.nextFloat() - 0.5f, -rand.nextFloat() * 200));
		}
		location = new Vector3f(0f, 0f, 0f);
		rotation = new Vector3f(0f, 0f, 0f);
		
		try {
			m = Model.getModel(modelPath);
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		
		glMatrixMode(GL_PROJECTION);
		glLoadIdentity();
		gluPerspective(30f, (float) (width / height), 0.3f, 100f);
		glMatrixMode(GL_MODELVIEW);
		glEnable(GL_DEPTH_TEST);
		glEnable(GL_TEXTURE_2D);
	 
		while(!Display.isCloseRequested()) {
			input();
			render();
			Display.update();
			Display.sync(60);
		}
		
		Display.destroy();
		System.exit(0);
	}	
	
	public void input() {
		boolean up = Keyboard.isKeyDown(Keyboard.KEY_W);
		boolean down = Keyboard.isKeyDown(Keyboard.KEY_S);
		boolean left = Keyboard.isKeyDown(Keyboard.KEY_A);
		boolean right = Keyboard.isKeyDown(Keyboard.KEY_D);
		boolean flyUp = Keyboard.isKeyDown(Keyboard.KEY_E);
		boolean flyDown = Keyboard.isKeyDown(Keyboard.KEY_Q);
		boolean speedUp = Keyboard.isKeyDown(Keyboard.KEY_LSHIFT);
		boolean slowDown = Keyboard.isKeyDown(Keyboard.KEY_LCONTROL);
		float walkspeed = 0.15f;
		if(Mouse.isButtonDown(0)) {
			float mx = Mouse.getDX();
			float my = Mouse.getDY();
			mx *= 0.25f;
			my *= 0.25f;
			rotation.y += mx;
			if(rotation.y > 360) {
				rotation.y -= 360;
			}
			rotation.x -= my;
			if(rotation.x > 85) {
				rotation.x = 85;
			}
			if(rotation.x < -85) {
				rotation.x = -85;
			}
		}
		
		if(speedUp && !slowDown) {
			walkspeed = 0.25f;
		}
		if(slowDown && !speedUp) {
			walkspeed = 0.10f;
		}
		
		if(up && !down) {
			float cz = (float) (walkspeed * 2 * Math.cos(Math.toRadians(rotation.y)));
			float cx = (float) (walkspeed * Math.sin(Math.toRadians(rotation.y)));
			location.z += cz;
			location.x -= cx;
		}
		
		if(down &&!up) {
			float cz = (float) (walkspeed * 2 * Math.cos(Math.toRadians(rotation.y)));
			float cx = (float) (walkspeed * Math.sin(Math.toRadians(rotation.y)));
			location.z -= cz;
			location.x += cx;
		}
		
		if(right && !left) {
			float cz = (float) (walkspeed * 2 * Math.sin(Math.toRadians(rotation.y + 90)));
			float cx = (float) (walkspeed * Math.cos(Math.toRadians(rotation.y)));
			location.z += cz;
			location.x -= cx;
		}
		
		if(left && !right) {
			float cz = (float) (walkspeed * 2 * Math.sin(Math.toRadians(rotation.y + 90)));
			float cx = (float) (walkspeed * Math.cos(Math.toRadians(rotation.y)));
			location.z -= cz;
			location.x += cx;
		}
		
		if(flyUp && !flyDown) {
			location.y -= walkspeed;
		}
		
		if(flyDown && ! flyUp) {
			location.y += walkspeed;
		}
		
	}
	
	public void render() {
		glPushMatrix();
		glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
		glRotatef(rotation.x, 1f, 0f, 0f);
		glRotatef(rotation.y, 0f, 1f, 0f);
		glRotatef(rotation.z, 0f, 0f, 1f);
		glTranslatef(location.x, location.y, location.z);
		//for(Point p: points) {
	//		p.render();
//		}
		m.render();
		
		glPopMatrix();
	}
	
	private class Point {

		float x, y, z;
		public Point(float x, float y, float z) {
			this.x = x;
			this.y = y;
			this.z = z;
		}
		
		public void render() {
			glBegin(GL_POINTS);
			glVertex3f(x, y, z);
			glEnd();
		}
	}
}
