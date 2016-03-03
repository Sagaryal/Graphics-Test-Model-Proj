package gra29;

import org.lwjgl.util.vector.Vector3f;

public class Face {

	public Vector3f norms, verts;
	
	public Face(Vector3f n, Vector3f v) {
		norms = n;
		verts = v;
	}
}
