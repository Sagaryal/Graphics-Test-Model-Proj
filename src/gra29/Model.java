package gra29;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.lwjgl.util.glu.Sphere;
import org.lwjgl.util.vector.Vector3f;

import static org.lwjgl.opengl.GL11.*;

import org.newdawn.slick.opengl.Texture;
import org.newdawn.slick.opengl.TextureLoader;

public class Model {
	
	public List<Vector3f> verts;
	public List<Vector3f> norms;
	public List<Face> faces;
	
	private Texture texture;	

	public static Model getModel(String s) throws IOException {
		return new Model(s);
	}
	
	private Model(String path) throws IOException {
		verts = new ArrayList<Vector3f>();
		norms = new ArrayList<Vector3f>();
		faces = new ArrayList<Face>();
		new ModelLoader(this, path);
	}
	
	public void render() {
		texture = TextureLoad.loadTexture("earth", "jpg");
		glBindTexture(GL_TEXTURE_2D, texture.getTextureID());
		texture.bind();
		//glEnable(GL_POLYGON_OFFSET_FILL);
		glPolygonMode(GL_FRONT_AND_BACK, GL_FILL);
		glBegin(GL_TRIANGLES);
		//glMaterialf(GL_FRONT, GL_SHININESS, GL_SHADE_MODEL);
		for(Face f: faces) {
			Vector3f v1 = verts.get((int) f.verts.x - 1);
			Vector3f n1 = verts.get((int) f.norms.x - 1);
			Vector3f v2 = verts.get((int) f.verts.y - 1);
			Vector3f n2 = verts.get((int) f.norms.y - 1);
			Vector3f v3 = verts.get((int) f.verts.z - 1);
			Vector3f n3 = verts.get((int) f.norms.z - 1);
		
			glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_S, GL_REPEAT);
            glTexCoord2f(v1.x, v1.y);
			glNormal3f(n1.x, n1.y, n1.z);
			glVertex3f(v1.x, v1.y, v1.z);
			glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_S, GL_REPEAT);
			glTexCoord2f(v2.x, v2.y);
			glNormal3f(n2.x, n2.y, n2.z);
			glVertex3f(v2.x, v2.y, v2.z);
			glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_S, GL_REPEAT);
			glTexCoord2f(v3.x, v3.y);
			glNormal3f(n3.x, n3.y, n3.z);
			glVertex3f(v3.x, v3.y, v3.z);
			
			  
			
		}
		glEnd();
	}
	
}
