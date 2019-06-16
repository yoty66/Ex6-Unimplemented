package edu.cg.models;

import java.io.File;
import java.io.IOException;
import java.rmi.ServerError;
import java.util.Iterator;
import java.util.LinkedList;

import com.jogamp.opengl.GL2;
import com.jogamp.opengl.GLAutoDrawable;
import com.jogamp.opengl.glu.GLU;
import com.jogamp.opengl.glu.GLUquadric;
import com.jogamp.opengl.util.texture.Texture;
import com.jogamp.opengl.util.texture.TextureIO;

import edu.cg.algebra.Point;
import edu.cg.models.Car.Materials;

import static com.jogamp.opengl.GL.*;

public class TrackSegment implements IRenderable {
	// TODO: Some constants you can use
	public final static double ASPHALT_TEXTURE_WIDTH = 20.0;
	public final static double ASPHALT_TEXTURE_DEPTH = 10.0;
	public final static double GRASS_TEXTURE_WIDTH = 10.0;
	public final static double GRASS_TEXTURE_DEPTH = 10.0;
	public final static double TRACK_LENGTH = 500.0;
	public final static double BOX_LENGTH = 1.5;
 //	GL2 prev_gl = null;
//	boolean first = true;

	private LinkedList<Point> boxesLocations; // Store the boxes centroids (center points) here.
	// TODO Add additional fields, for example:
	//		- Add wooden box model (you will only need one object which can be rendered many times)
	private SkewedBox box = null;
	//      - Add grass and asphalt textures.
	private Texture AsphaltTexture;
	private Texture GrassTexture;

	private boolean isSetDifficulty=false;

	private Texture texRoad;
	private Texture texGrass;


	public void setDifficulty(double difficulty) {
		// TODO: Set the difficulty of the track segment. Here you decide what are the boxes locations.
		//		 We provide a simple implementation. You can change it if you want. But if you do decide to use it, then it is your responsibility to understand the logic behind it.
		//       Note: In our implementation, the difficulty is the probability of a box to appear in the scene.
		//             We divide the scene into rows of boxes and we sample boxes according the difficulty probability.
//		if(!isSetDifficulty) {
//			this.isSetDifficulty=true;
			difficulty = Math.min(difficulty, 0.95);
			difficulty = Math.max(difficulty, 0.05);
			double numberOfLanes = 4.0;
			double deltaZ = 0.0;
			if (difficulty < 0.25) {
				deltaZ = 100.0;
			} else if (difficulty < 0.5) {
				deltaZ = 75.0;
			} else {
				deltaZ = 50.0;
			}
			boxesLocations = new LinkedList<Point>();
			for (double dz = deltaZ; dz < TRACK_LENGTH - BOX_LENGTH / 2.0; dz += deltaZ) {
				int cnt = 0; // Number of boxes sampled at each row.
				boolean flag = false;
				for (int i = 0; i < 12; i++) {
					double dx = -((double) numberOfLanes / 2.0) * ((ASPHALT_TEXTURE_WIDTH - 2.0) / numberOfLanes) + BOX_LENGTH / 2.0
							+ i * BOX_LENGTH;
					if (Math.random() < difficulty) {
						boxesLocations.add(new Point(dx, BOX_LENGTH / 2.0, -dz));
						cnt += 1;
					} else if (!flag) {// The first time we don't sample a box then we also don't sample the box next to. We want enough space for the car to pass through.
						i += 1;
						flag = true;
					}
					if (cnt > difficulty * 10) {
						break;
					}
				}
			}

		}
//	}

//	public TrackSegment(double difficulty) {
//		// TODO initialize your fields here.
//		// Here by setting up the difficulty, we decide on the boxes locations.
//		setDifficulty(difficulty);
//		this.box=new SkewedBox(BOX_LENGTH,true);
//	}

//	@Override
//	public void render(GL2 gl) {
//		// TODO: Render the track segment
//	}
//
//
////	private void render BOxes
	@Override
	public void init(GL2 gl) {
		// TODO: Initialize textures.
		try{
		this.AsphaltTexture=TextureIO.newTexture(new File("Textures/RoadTexture.jpg"),true);
		this.GrassTexture=TextureIO.newTexture(new File("Textures/GrassTexture.jpg"),true);
		}
		catch (IOException e)
		{
			System.err.println("Failed loading textures"+e.getMessage());
		}
	}



	public void destroy(GL2 gl) {
		// TODO: destroy textures.
		this.AsphaltTexture.destroy(gl);
		this.GrassTexture.destroy(gl);
		this.box.destroy(gl);
	}


//JARRRRR

	public TrackSegment(double difficulty) {

		// TODO initialize your fields here.
		// Here by setting up the difficulty, we decide on the boxes locations.
		setDifficulty(difficulty);
		this.box = new SkewedBox(BOX_LENGTH, true);



	}

	public void render(GL2 gl) {

	    this.box.init(gl);
		// TODO: Render the track segment
//		gl.glPushMatrix();
        renderObstacles(gl);

        renderGrass(gl);
        renderRoad(gl);
//		gl.glPopMatrix();
	}

	private void renderObstacles(GL2 gl)
    {
        Materials.setWoodenBoxMaterial(gl);
        Iterator <Point>iter=boxesLocations.listIterator();
        while(iter.hasNext())
        {
        gl.glPushMatrix();
        Point location= iter.next();
        gl.glTranslated(location.x,0,location.z);
        box.render(gl);
        gl.glPopMatrix();
        }
//
    }
	private void renderGrass(GL2 gl)
	{
		gl.glPushMatrix();
		Materials.setGreenMaterial(gl);
		gl.glTranslated(ASPHALT_TEXTURE_WIDTH/2,0,0);
		setTextureParams(gl,this.GrassTexture);
		renderQuadraticTexture(gl, GRASS_TEXTURE_WIDTH,2*GRASS_TEXTURE_DEPTH,GRASS_TEXTURE_WIDTH,TRACK_LENGTH);
//		renderQuadraticTexture(gl, GRASS_TEXTURE_WIDTH,TRACK_LENGTH,GRASS_TEXTURE_WIDTH,TRACK_LENGTH);
		destroyTextureParams(gl);
		gl.glPopMatrix();

		gl.glPushMatrix();
		gl.glTranslated(-GRASS_TEXTURE_WIDTH-ASPHALT_TEXTURE_WIDTH/2,0,0);
		setTextureParams(gl,this.GrassTexture);
		renderQuadraticTexture(gl, GRASS_TEXTURE_WIDTH,GRASS_TEXTURE_DEPTH,GRASS_TEXTURE_WIDTH,TRACK_LENGTH);
//		renderQuadraticTexture(gl, GRASS_TEXTURE_WIDTH,TRACK_LENGTH,GRASS_TEXTURE_WIDTH,TRACK_LENGTH);
		destroyTextureParams(gl);
		gl.glPopMatrix();
	}
//
	private void renderRoad(GL2 gl)
	{
		gl.glPushMatrix();
		Materials.setAsphaltMaterial(gl);
		gl.glTranslated(-ASPHALT_TEXTURE_WIDTH/2,0,0);
		setTextureParams(gl,this.AsphaltTexture);
		renderQuadraticTexture(gl, ASPHALT_TEXTURE_WIDTH,ASPHALT_TEXTURE_DEPTH,ASPHALT_TEXTURE_WIDTH,TRACK_LENGTH);
//		renderQuadraticTexture(gl, ASPHALT_TEXTURE_WIDTH,TRACK_LENGTH,ASPHALT_TEXTURE_WIDTH,TRACK_LENGTH);
		destroyTextureParams(gl);
		gl.glPopMatrix();

	}


	private void setTextureParams(GL2 gl, Texture tex){

		gl.glEnable(GL_TEXTURE_2D);
		this.init(gl);
		tex.bind(gl);
		gl.glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_NEAREST);
		gl.glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_LINEAR);
		gl.glBegin(GL2.GL_QUADS);
		gl.glEnable(GL2.GL_NORMALIZE);
	}
	private void destroyTextureParams(GL2 gl)
	{
		gl.glEnd();
		gl.glDisable(GL_TEXTURE_2D);
	}


	// creates totalWidth/width x totalDepth/depth quadratics with the given texture .
	// @pre - 1. totalWidth/width, totalDepth/depth are integers 2. Texture is already defind and bounded  .
	//
	private void renderQuadraticTexture(GL2 gl, double width, double depth, double totalWidth, double totalDepth) {



		TrackSegment.QuadPointsHolder points = new TrackSegment.QuadPointsHolder();
		for (double currentDepth = 0; currentDepth < totalDepth; currentDepth += depth) {
			points.setBottomLeft(new double[]{0, 0, -currentDepth});
			points.setTopLeft(new double[]{0, 0, -1 * (currentDepth + depth)});
			for (double currentWidth = width; currentWidth <= totalWidth; currentWidth += width) {
				points.setBottomRight(new double[]{currentWidth, 0, -currentDepth});
				points.setTopRight(new double[]{currentWidth, 0, -1 * (currentDepth + depth)});
				renderTextureQuad(gl, points);
				points.setBottomLeft(points.bottomRight);
				points.setTopLeft(points.topRight);
			}
		}
	}


	//	Renders a texture quad on a -z-x-plane . The points are given relative to the x (right left) and the negative -z (top bottom)
	//  @pre a Texture is already defind and bounded  .
	public void renderTextureQuad(GL2 gl, TrackSegment.QuadPointsHolder points) {


		gl.glNormal3d(0, 1, 0);
		gl.glVertex3d(points.bottomRight[0], points.bottomRight[1], points.bottomRight[2]);
		gl.glTexCoord2d(0.0D, 1.0D);

        gl.glVertex3d(points.topRight[0], points.topRight[1], points.topRight[2]);
        gl.glTexCoord2d(1.0, 1.0);

        gl.glVertex3d(points.topLeft[0], points.topLeft[1], points.topLeft[2]);
        gl.glTexCoord2d(1.0D, 0.0D);
		gl.glVertex3d(points.bottomLeft[0], points.bottomLeft[1], points.bottomLeft[2]);
        gl.glTexCoord2d(0.0D, 0.0D);
    }



	public void testRenderQuadraticTexture(GL2 gl) {
//		this.renderQuadraticTexture(gl, this.GrassTexture, 5, 5, 10, 500.0D);
	}
	private class QuadPointsHolder {
		public double[] topLeft;
		public double[] topRight;
		public double[] bottomRight;
		public double[] bottomLeft = null;

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
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
////	Renders a texture quad on a -z-x-plane . The points are given relative to the x (right left) and the negative -z (top bottom)
////  Assume the Texture is already defind and binded .
////	public void renderTextureQuad2(GL2 gl, double[] topLeft, double[] topRight, double[] bottomRight, double[] bottomLeft) {
////
//////		Vec v1 = new Vec(bottomRight[0],bottomRight[1],bottomRight[2]);
//////		Vec v2 = new Vec(topRight[0],topRight[1],topRight[2]);
//////		Vec v3 = new Vec(topLeft[0],topLeft[1],topLeft[2]);
//////		Vec v1Neg = v1.mult(-1);
//////		Vec normal = v2.add(v1Neg).cross(v3.add(v1Neg));
////
////		gl.glNormal3d(0, 1, 0);
////		gl.glVertex3d(bottomRight[0], bottomRight[1], bottomRight[2]);
////		gl.glTexCoord2d(0, 0);
////
////		gl.glVertex3d(topRight[0], topRight[1], topRight[2]);
////		gl.glTexCoord2d(0.0D, 1.0D);
////
////		gl.glVertex3d(topLeft[0], topLeft[1], topLeft[2]);
////		gl.glTexCoord2d(1.0D, 1.0D);
////		gl.glVertex3d(bottomLeft[0], bottomLeft[1], bottomLeft[2]);
////		gl.glTexCoord2d(1.0D, 0.0D);
////	}






























//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//
//
//package edu.cg.models;
//
//import com.jogamp.opengl.GL2;
//import com.jogamp.opengl.glu.GLU;
//import com.jogamp.opengl.glu.GLUquadric;
//import com.jogamp.opengl.util.texture.Texture;
//import com.jogamp.opengl.util.texture.TextureIO;
//import edu.cg.algebra.Point;
//import edu.cg.models.Car.Materials;
//import java.io.File;
//import java.util.Iterator;
//import java.util.LinkedList;
//
//public class TrackSegment implements IRenderable {
//	public static final double ASPHALT_TEXTURE_WIDTH = 20.0D;
//	public static final double ASPHALT_TEXTURE_DEPTH = 10.0D;
//	public static final double GRASS_TEXTURE_WIDTH = 10.0D;
//	public static final double GRASS_TEXTURE_DEPTH = 10.0D;
//	public static final double TRACK_LENGTH = 500.0D;
//	public static final double BOX_LENGTH = 1.5D;
//	private LinkedList<Point> boxesLocations;
//	private SkewedBox box = null;
//	private Texture texRoad;
//	private Texture texGrass;
//
//	public void setDifficulty(double difficulty) {
//		difficulty = Math.min(difficulty, 0.95D);
//		difficulty = Math.max(difficulty, 0.05D);
//		double numberOfLanes = 4.0D;
//		double deltaZ = 0.0D;
//		if (difficulty < 0.25D) {
//			deltaZ = 100.0D;
//		} else if (difficulty < 0.5D) {
//			deltaZ = 75.0D;
//		} else {
//			deltaZ = 50.0D;
//		}
//
//		this.boxesLocations = new LinkedList();
//
//		for(double dz = deltaZ; dz < 499.25D; dz += deltaZ) {
//			int cnt = 0;
//			boolean flag = false;
//
//			for(int i = 0; i < 12; ++i) {
//				double dx = -(numberOfLanes / 2.0D) * (18.0D / numberOfLanes) + 0.75D + (double)i * 1.5D;
//				if (Math.random() < difficulty) {
//					this.boxesLocations.add(new Point(dx, 0.75D, -dz));
//					++cnt;
//				} else if (!flag) {
//					++i;
//					flag = true;
//				}
//
//				if ((double)cnt > difficulty * 10.0D) {
//					break;
//				}
//			}
//		}
//
//	}
//
//	public TrackSegment(double difficulty) {
//		this.box = new SkewedBox(1.5D, true);
//		this.setDifficulty(difficulty);
//	}
//
//	public void render(GL2 gl) {
//		this.renderBoxes(gl);
//		this.renderAsphalt(gl);
//		this.renderGrass(gl);
//	}
//
//	private void renderBoxes(GL2 gl) {
//		Materials.setWoodenBoxMaterial(gl);
//		Iterator var3 = this.boxesLocations.iterator();
//
//		while(var3.hasNext()) {
//			Point p = (Point)var3.next();
//			gl.glPushMatrix();
//			gl.glTranslated((double)p.x, 0.0D, (double)p.z);
//			this.box.render(gl);
//			gl.glPopMatrix();
//		}
//
//	}
//
//	private void renderAsphalt(GL2 gl) {
//		Materials.setAsphaltMaterial(gl);
//		gl.glPushMatrix();
//		this.renderQuadraticTexture(gl, this.texRoad, 20.0D, 10.0D, 6, 500.0D);
//		gl.glPopMatrix();
//	}
//
//	private void renderGrass(GL2 gl) {
//		Materials.setGreenMaterial(gl);
//		double dx = 15.0D;
//		gl.glTranslated(dx, 0.0D, 0.0D);
//		this.renderQuadraticTexture(gl, this.texGrass, 10.0D, 10.0D, 2, 500.0D);
//		gl.glTranslated(-2.0D * dx, 0.0D, 0.0D);
//		this.renderQuadraticTexture(gl, this.texGrass, 10.0D, 10.0D, 2, 500.0D);
//		gl.glPopMatrix();
//	}
//
//	private void renderQuadraticTexture(GL2 gl, Texture tex, double quadWidth, double quadDepth, int split, double totalDepth) {
//		gl.glEnable(3553);
//		tex.bind(gl);
//		gl.glTexEnvi(8960, 8704, 8448);
//		gl.glTexParameteri(3553, 10241, 9987);
//		gl.glTexParameteri(3553, 10240, 9729);
//		gl.glTexParameteri(3553, 33083, 1);
//		gl.glColor3d(1.0D, 0.0D, 0.0D);
//		GLU glu = new GLU();
//		GLUquadric quad = glu.gluNewQuadric();
//		gl.glColor3d(1.0D, 0.0D, 0.0D);
//		gl.glNormal3d(0.0D, 1.0D, 0.0D);
//		double d = 1.0D / (double)split;
//		double dz = quadDepth / (double)split;
//		double dx = quadWidth / (double)split;
//
//		for(double tz = 0.0D; tz < totalDepth; tz += quadDepth) {
//			for(double i = 0.0D; i < (double)split; ++i) {
//				gl.glBegin(5);
//
//				for(double j = 0.0D; j <= (double)split; ++j) {
//					gl.glTexCoord2d(j * d, (i + 1.0D) * d);
//					gl.glVertex3d(-quadWidth / 2.0D + j * dx, 0.0D, -tz - (i + 1.0D) * dz);
//					gl.glTexCoord2d(j * d, i * d);
//					gl.glVertex3d(-quadWidth / 2.0D + j * dx, 0.0D, -tz - i * dz);
//				}
//
//				gl.glEnd();
//			}
//		}
//
//		glu.gluDeleteQuadric(quad);
//		gl.glDisable(3553);
//	}
//
//	public void init(GL2 gl) {
//		this.box.init(gl);
//
//		try {
//			this.texRoad = TextureIO.newTexture(new File("Textures/RoadTexture.jpg"), true);
//			this.texGrass = TextureIO.newTexture(new File("Textures/GrassTexture.jpg"), true);
//		} catch (Exception var3) {
//			System.err.print("Unable to read texture : " + var3.getMessage());
//		}
//
//	}
//
//	public void destroy(GL2 gl) {
//		this.texRoad.destroy(gl);
//		this.texGrass.destroy(gl);
//		this.box.destroy(gl);
//	}
//}
