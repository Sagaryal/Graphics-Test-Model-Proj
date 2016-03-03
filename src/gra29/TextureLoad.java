package gra29;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import org.newdawn.slick.opengl.Texture;
import org.newdawn.slick.opengl.TextureLoader;

public class TextureLoad {

	public static Texture loadTexture(String filename, String extension) {
		try {
			return TextureLoader.getTexture(extension.toUpperCase(), new FileInputStream(new File("res/" + filename + "." + extension)));
		} 
		catch(IOException e) {
			e.printStackTrace();
		}	
	return null;
	}
}