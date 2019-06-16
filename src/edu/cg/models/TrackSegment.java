//package edu.cg.models;
//
//import java.io.File;
//import java.io.IOException;
//import java.rmi.ServerError;
//import java.util.LinkedList;
//
//import com.jogamp.opengl.GL2;
//import com.jogamp.opengl.glu.GLU;
//import com.jogamp.opengl.glu.GLUquadric;
//import com.jogamp.opengl.util.texture.Texture;
//import com.jogamp.opengl.util.texture.TextureIO;
//
//import edu.cg.algebra.Point;
//import edu.cg.models.Car.Materials;
//
//public class TrackSegment implements IRenderable {
//	// TODO: Some constants you can use
//	public final static double ASPHALT_TEXTURE_WIDTH = 20.0;
//	public final static double ASPHALT_TEXTURE_DEPTH = 10.0;
//	public final static double GRASS_TEXTURE_WIDTH = 10.0;
//	public final static double GRASS_TEXTURE_DEPTH = 10.0;
//	public final static double TRACK_LENGTH = 500.0;
//	public final static double BOX_LENGTH = 1.5;
//	private LinkedList<Point> boxesLocations; // Store the boxes centroids (center points) here.
//	// TODO Add additional fields, for example:
//	//		- Add wooden box model (you will only need one object which can be rendered many times)
//	private SkewedBox box=null;
//	//      - Add grass and asphalt textures.
//	private Texture AsphaltTexture;
//	private Texture GrassTexture;
//
//	public void setDifficulty(double difficulty) {
//		// TODO: Set the difficulty of the track segment. Here you decide what are the boxes locations.
//		//		 We provide a simple implementation. You can change it if you want. But if you do decide to use it, then it is your responsibility to understand the logic behind it.
//		//       Note: In our implementation, the difficulty is the probability of a box to appear in the scene.
//		//             We divide the scene into rows of boxes and we sample boxes according the difficulty probability.
//		difficulty = Math.min(difficulty, 0.95);
//		difficulty = Math.max(difficulty, 0.05);
//		double numberOfLanes = 4.0;
//		double deltaZ = 0.0;
//		if (difficulty < 0.25) {
//			deltaZ = 100.0;
//		} else if (difficulty < 0.5) {
//			deltaZ = 75.0;
//		} else {
//			deltaZ = 50.0;
//		}
//		boxesLocations = new LinkedList<Point>();
//		for (double dz = deltaZ; dz < TRACK_LENGTH - BOX_LENGTH / 2.0; dz += deltaZ) {
//			int cnt = 0; // Number of boxes sampled at each row.
//			boolean flag = false;
//			for (int i = 0; i < 12; i++) {
//				double dx = -((double) numberOfLanes / 2.0) * ((ASPHALT_TEXTURE_WIDTH - 2.0) / numberOfLanes) + BOX_LENGTH / 2.0
//						+ i * BOX_LENGTH;
//				if (Math.random() < difficulty) {
//					boxesLocations.add(new Point(dx, BOX_LENGTH / 2.0, -dz));
//					cnt += 1;
//				} else if (!flag) {// The first time we don't sample a box then we also don't sample the box next to. We want enough space for the car to pass through.
//					i += 1;
//					flag = true;
//				}
//				if (cnt > difficulty * 10) {
//					break;
//				}
//			}
//		}
//	}
//
//	public TrackSegment(double difficulty) {
//		// TODO initialize your fields here.
//		// Here by setting up the difficulty, we decide on the boxes locations.
//		setDifficulty(difficulty);
//		this.box=new SkewedBox(BOX_LENGTH,true);
//	}
//
//	@Override
//	public void render(GL2 gl) {
//		// TODO: Render the track segment
//	}
//
//	public void renderGeomtery(){
//
//
//	}
//	/**
//	 * This method renders a texture .
//	 * @param args Unused.
//	 * @return Nothing.
//	 * @exception IOException On input error.
//	 * @see IOException
//	 */
//	public void renderSplitedQuadsTextures(){
//
//
//	}
//
//
//
//
////	private void render BOxes
//	@Override
//	public void init(GL2 gl) {
//		// TODO: Initialize textures.
//		try{
//		this.AsphaltTexture=TextureIO.newTexture(new File("Textures/RoadTexture.jpg"),true);
//		this.GrassTexture=TextureIO.newTexture(new File("Textures/RoadTexture.jpg"),true);
//		}
//		catch (IOException e)
//		{
//			System.err.println("Failed loading textures"+e.getMessage());
//		}
//	}
//	public void destroy(GL2 gl) {
//		// TODO: destroy textures.
//		this.AsphaltTexture.destroy(gl);
//		this.GrassTexture.destroy(gl);
//		this.box.destroy(gl);
//	}
//
//
//}





//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package edu.cg.models;

import com.jogamp.opengl.GL2;
import com.jogamp.opengl.glu.GLU;
import com.jogamp.opengl.glu.GLUquadric;
import com.jogamp.opengl.util.texture.Texture;
import com.jogamp.opengl.util.texture.TextureIO;
import edu.cg.algebra.Point;
import edu.cg.algebra.Vec;
import edu.cg.models.Car.Materials;
import java.io.File;
import java.util.Iterator;
import java.util.LinkedList;

import static com.jogamp.opengl.GL.*;
import static com.jogamp.opengl.GL.GL_LINEAR;

public class TrackSegment implements IRenderable {
	public static final double ASPHALT_TEXTURE_WIDTH = 20.0D;
	public static final double ASPHALT_TEXTURE_DEPTH = 10.0D;
	public static final double GRASS_TEXTURE_WIDTH = 10.0D;
	public static final double GRASS_TEXTURE_DEPTH = 10.0D;
	public static final double TRACK_LENGTH = 500.0D;
	public static final double BOX_LENGTH = 1.5D;
	private LinkedList<Point> boxesLocations;
	private SkewedBox box = null;
	private Texture texRoad;
	private Texture texGrass;

	public void setDifficulty(double difficulty) {
		difficulty = Math.min(difficulty, 0.95D);
		difficulty = Math.max(difficulty, 0.05D);
		double numberOfLanes = 4.0D;
		double deltaZ = 0.0D;
		if (difficulty < 0.25D) {
			deltaZ = 100.0D;
		} else if (difficulty < 0.5D) {
			deltaZ = 75.0D;
		} else {
			deltaZ = 50.0D;
		}

		this.boxesLocations = new LinkedList();

		for (double dz = deltaZ; dz < 499.25D; dz += deltaZ) {
			int cnt = 0;
			boolean flag = false;

			for (int i = 0; i < 12; ++i) {
				double dx = -(numberOfLanes / 2.0D) * (18.0D / numberOfLanes) + 0.75D + (double) i * 1.5D;
				if (Math.random() < difficulty) {
					this.boxesLocations.add(new Point(dx, 0.75D, -dz));
					++cnt;
				} else if (!flag) {
					++i;
					flag = true;
				}

				if ((double) cnt > difficulty * 10.0D) {
					break;
				}
			}
		}

	}

	public TrackSegment(double difficulty) {
		this.box = new SkewedBox(1.5D, true);
		this.setDifficulty(difficulty);
	}

	public void render(GL2 gl) {
		this.renderBoxes(gl);
		this.renderAsphalt(gl);
		this.renderGrass(gl);
	}

	private void renderBoxes(GL2 gl) {
//		Materials.setWoodenBoxMaterial(gl);
		Iterator var3 = this.boxesLocations.iterator();

		while (var3.hasNext()) {
			Point p = (Point) var3.next();
			gl.glPushMatrix();
			gl.glTranslated((double) p.x, 0.0D, (double) p.z);
			this.box.render(gl);
			gl.glPopMatrix();
		}

	}

	private void renderAsphalt(GL2 gl) {
//		Materials.setAsphaltMaterial(gl);
		gl.glPushMatrix();
//		glPushMatrixthis.renderQuadraticTexture(gl, this.texRoad, 20.0D, 10.0D, 6, 500.0D);
		gl.glPopMatrix();
	}

	private void renderGrass(GL2 gl) {
		Materials.setGreenMaterial(gl);
		double dx = 15.0D;
		gl.glTranslated(dx, 0.0D, 0.0D);
//		this.renderQuadraticTexture(gl, this.texGrass, 10.0D, 10.0D, 2, 500.0D);
		gl.glTranslated(-2.0D * dx, 0.0D, 0.0D);
//		this.renderQuadraticTexture(gl, this.texGrass, 10.0D, 10.0D, 2, 500.0D);
		gl.glPopMatrix();
	}


	// creates totalWidth/width x totalDepth/depth quadratics with the given texture .
	// @pre - totalWidth/width, totalDepth/depth are integers
	//
	private void renderQuadraticTexture(GL2 gl, Texture tex, double width, double depth, double totalWidth, double totalDepth) {


		gl.glEnable(GL_TEXTURE_2D);
		this.init(gl);
		tex.bind(gl);
		gl.glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_NEAREST);
		gl.glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_LINEAR);
		gl.glBegin(GL2.GL_QUADS);
		gl.glEnable(GL2.GL_NORMALIZE);


		QuadPointsHolder points = new QuadPointsHolder();
		for (double currentDepth = 0; currentDepth < totalDepth; currentDepth += depth)
		{
			points.setBottomLeft(new double[]{0, 0, -currentDepth});
			points.setTopLeft(new double[]{0, 0, -1 * (currentDepth + depth)  });
			for (double currentWidth = width; currentWidth <=totalWidth; currentWidth += width)
				{
					points.setBottomRight(new double[]{currentWidth, 0, -currentDepth});
					points.setTopRight(new double[]{currentWidth, 0, -1 * (currentDepth + depth)  });
					renderTextureQuad(gl,points);
					points.setBottomLeft(points.bottomRight);
					points.setTopLeft(points.topRight);
				}
		}
		gl.glEnd();
		gl.glDisable(GL_TEXTURE_2D);
	}


	//	Renders a texture quad on a -z-x-plane . The points are given relative to the x (right left) and the negative -z (top bottom)
	//  Assume a Texture is already defind and bounded  .
	public void renderTextureQuad(GL2 gl,QuadPointsHolder points )
	{


		gl.glNormal3d(0, 1, 0);
		gl.glVertex3d(points.bottomRight[0],points.bottomRight[1],points.bottomRight[2]);
		gl.glTexCoord2d(0, 0);

		gl.glVertex3d(points.topRight[0],points.topRight[1],points.topRight[2]);
		gl.glTexCoord2d(0.0D, 1.0D);

		gl.glVertex3d(points.topLeft[0],points.topLeft[1],points.topLeft[2]);
		gl.glTexCoord2d(1.0D, 1.0D);
		gl.glVertex3d(points.bottomLeft[0],points.bottomLeft[1],points.bottomLeft[2]);
		gl.glTexCoord2d(1.0D, 0.0D);
	}

//	Renders a texture quad on a -z-x-plane . The points are given relative to the x (right left) and the negative -z (top bottom)
//  Assume the Texture is already defind and binded .
	public void renderTextureQuad2(GL2 gl, double [] topLeft,double []  topRight,double [] bottomRight, double [] bottomLeft ){

//		Vec v1 = new Vec(bottomRight[0],bottomRight[1],bottomRight[2]);
//		Vec v2 = new Vec(topRight[0],topRight[1],topRight[2]);
//		Vec v3 = new Vec(topLeft[0],topLeft[1],topLeft[2]);
//		Vec v1Neg = v1.mult(-1);
//		Vec normal = v2.add(v1Neg).cross(v3.add(v1Neg));

		gl.glNormal3d(0, 1, 0);
		gl.glVertex3d(bottomRight[0],bottomRight[1],bottomRight[2]);
		gl.glTexCoord2d(0, 0);

		gl.glVertex3d(topRight[0],topRight[1],topRight[2]);
		gl.glTexCoord2d(0.0D, 1.0D);

		gl.glVertex3d(topLeft[0],topLeft[1],topLeft[2]);
		gl.glTexCoord2d(1.0D, 1.0D);
		gl.glVertex3d(bottomLeft[0],bottomLeft[1],bottomLeft[2]);
		gl.glTexCoord2d(1.0D, 0.0D);
	}

	public void testRenderQuadraticTexture(GL2 gl){
		this.renderQuadraticTexture(gl, this.texGrass, 5,5, 10, 500.0D);
	}

	public void init(GL2 gl) {
		this.box.init(gl);

		try {
			this.texRoad = TextureIO.newTexture(new File("Textures/RoadTexture.jpg"), true);
			this.texGrass = TextureIO.newTexture(new File("Textures/GrassTexture.jpg"), true);
		} catch (Exception var3) {
			System.err.print("Unable to read texture : " + var3.getMessage());
		}

	}

	public void destroy(GL2 gl) {
		this.texRoad.destroy(gl);
		this.texGrass.destroy(gl);
		this.box.destroy(gl);
	}

	private class QuadPointsHolder{
		public double [] topLeft;
		public double []  topRight;
		public double [] bottomRight;
		public double [] bottomLeft=null;

		public void setTopLeft(double[] topLeft) {
			this.topLeft = topLeft;
		}

		public void setTopRight(double[] topRight) {
			this.topRight = topRight;
		}

		public void setBottomRight(double[] bottomRight) {
			this.bottomRight = bottomRight;
		}

		public void setBottomLeft(double[] bottomLeft) {
			this.bottomLeft = bottomLeft;
		}
	}
}
