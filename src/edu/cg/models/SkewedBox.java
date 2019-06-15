package edu.cg.models;

import com.jogamp.opengl.GL2;
import com.jogamp.opengl.GL2ES3;
import com.jogamp.opengl.util.texture.Texture;
import com.jogamp.opengl.util.texture.TextureIO;
import edu.cg.algebra.Point;
import edu.cg.algebra.Vec;
import edu.cg.models.Car.Materials;
import edu.cg.models.IRenderable;

import java.io.File;

import static com.jogamp.opengl.GL.*;
import static edu.cg.models.Car.Materials.setWoodenBoxMaterial;

public class SkewedBox implements IRenderable {
	private double length, height1, height2, depth1, depth2;
	private boolean paintWithTexture=false;//for the obstacles
	private Texture boxTexture = null;


	public SkewedBox() {
		length = .1;
		height1 = .2;
		height2 = .1;
		depth1 = .2;
		depth2 = .1;
	};

	public SkewedBox(double length, double h1, double h2, double d1, double d2) {
		this.length = length;
		this.height1 = h1;
		this.height2 = h2;
		this.depth1 = d1;
		this.depth2 = d2;
	}

	public SkewedBox(double length, double h1, double h2, double d1, double d2, boolean paintWithTexture) {
		this( length,  h1, h2, d1, d2);
		this.paintWithTexture=paintWithTexture;
	}



	@Override
	public void render(GL2 gl) {
		// TODO : Render the skewed-box using.
		//        Use the fields: length, height1, height2, depth1, depth2
		if (paintWithTexture)
		{
			gl.glEnable(GL_TEXTURE_2D);

			this.init(gl);
			this.boxTexture.bind(gl);
			gl.glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER,GL_NEAREST);
			gl.glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER,GL_LINEAR);

		}

		renderGeometry(gl);

		if (paintWithTexture)
		{
			gl.glDisable(GL_TEXTURE_2D);
		}
	}

	private void renderGeometry(GL2 gl)
	{
		// From front view see SkwedBox.PPt  slide 1
		//The D2xH2 from bottom-left point and clockwise order is:  p1,p2,p3,p4
		double [] p1= { length/2.0,0.0,-depth2/2.0};
		double [] p2= {length/2,height2,-depth2/2.0};
		double [] p3= { length/2,height2,depth2/2.0};
		double [] p4= {length/2.0,0.0,depth2/2.0};

		//The D1xH1 from bottom-left point and clockwise order is:  p5,p6,p7,p8

		double [] p5= { -length/2.0,0.0,-depth1/2.0};
		double [] p6= { -length/2.0,height1,-depth1/2.0};
		double [] p7= { -length/2.0,height1,depth1/2.0};
		double [] p8= {-length/2.0 ,0.0,depth1/2.0};



		gl.glBegin(GL2.GL_QUADS);

		gl.glEnable(GL2.GL_NORMALIZE);


		//Front view x-axis:
//		normal to box
		Vec v1 = new Vec(p4[0],p4[1],p4[2]);
		Vec v2 = new Vec(p1[0],p1[1],p1[2]);
		Vec v3 = new Vec(p2[0],p2[1],p2[2]);
		Vec v1Neg = v1.mult(-1);
		Vec normal = v2.add(v1Neg).cross(v3.add(v1Neg));

		gl.glNormal3d(normal.x, normal.y, normal.z);

		gl.glVertex3d(p4[0],p4[1],p4[2]);//p4
		gl.glTexCoord2d(0.0D, 0.0D);

		gl.glVertex3d(p1[0],p1[1],p1[2]);//p1
		gl.glTexCoord2d(0.0D, 1.0D);

		gl.glVertex3d(p2[0],p2[1],p2[2]);//p2
		gl.glTexCoord2d(1.0D, 1.0D);

		gl.glVertex3d(p3[0],p3[1],p3[2]);//p3
		gl.glTexCoord2d(1.0D, 0.0D);

		//		normal to box
		v1 = new Vec(p5[0],p5[1],p5[2]);
		v2 = new Vec(p8[0],p8[1],p8[2]);
		v3 = new Vec(p7[0],p7[1],p7[2]);
		v1Neg = v1.mult(-1);
		normal = v2.add(v1Neg).cross(v3.add(v1Neg));
		gl.glNormal3d(normal.x, normal.y, normal.z);

		gl.glVertex3d(p5[0],p5[1],p5[2]);
		gl.glTexCoord2d(0.0D, 0.0D);

		gl.glVertex3d(p8[0],p8[1],p8[2]);
		gl.glTexCoord2d(0.0D, 1.0D);

		gl.glVertex3d(p7[0],p7[1],p7[2]);
		gl.glTexCoord2d(1.0D, 1.0D);

		gl.glVertex3d(p6[0],p6[1],p6[2]);
		gl.glTexCoord2d(1.0D, 0.0D);

		v1 = new Vec(p8[0],p8[1],p8[2]);
		v2 = new Vec(p5[0],p5[1],p5[2]);
		v3 = new Vec(p1[0],p1[1],p1[2]);
		v1Neg = v1.mult(-1);
		normal = v2.add(v1Neg).cross(v3.add(v1Neg));
		gl.glNormal3d(normal.x, normal.y, normal.z);
//		Top view y-axis:
		gl.glVertex3d(p8[0],p8[1],p8[2]);
		gl.glTexCoord2d(0.0D, 0.0D);

		gl.glVertex3d(p5[0],p5[1],p5[2]);
		gl.glTexCoord2d(0.0D, 1.0D);

		gl.glVertex3d(p1[0],p1[1], p1[2]);
		gl.glTexCoord2d(1.0D, 1.0D);

		gl.glVertex3d(p4[0],p4[1],p4[2]);
		gl.glTexCoord2d(1.0D, 0.0D);


		v1 = new Vec(p7[0],p7[1],p7[2]);
		v2 = new Vec(p3[0],p3[1],p3[2]);
		v3 = new Vec(p2[0],p2[1],p2[2]);
		v1Neg = v1.mult(-1);
		normal = v2.add(v1Neg).cross(v3.add(v1Neg));
		gl.glNormal3d(normal.x, normal.y, normal.z);

		gl.glVertex3d(p7[0],p7[1],p7[2]);
		gl.glTexCoord2d(0.0D, 0.0D);

		gl.glVertex3d(p3[0],p3[1],p3[2]);
		gl.glTexCoord2d(0.0D, 1.0D);

		gl.glVertex3d(p2[0],p2[1],p2[2]);
		gl.glTexCoord2d(1.0D, 1.0D);

		gl.glVertex3d(p6[0],p6[1],p6[2]);
		gl.glTexCoord2d(1.0D, 0.0D);


		v1 = new Vec(p5[0],p5[1],p5[2]);
		v2 = new Vec(p6[0],p6[1],p6[2]);
		v3 = new Vec(p2[0],p2[1],p2[2]);
		v1Neg = v1.mult(-1);
		normal = v2.add(v1Neg).cross(v3.add(v1Neg));

		gl.glNormal3d(normal.x, normal.y, normal.z);
//		Side view z-axis:
		gl.glVertex3d(p5[0],p5[1],p5[2]);
		gl.glTexCoord2d(0.0D, 0.0D);

		gl.glVertex3d(p6[0],p6[1],p6[2]);
		gl.glTexCoord2d(0.0D, 1.0D);

		gl.glVertex3d(p2[0],p2[1],p2[2]);
		gl.glTexCoord2d(1.0D, 1.0D);

		gl.glVertex3d(p1[0],p1[1],p1[2]);
		gl.glTexCoord2d(1.0D, 0.0D);


		v1 = new Vec(p7[0],p7[1],p7[2]);
		v2 = new Vec(p8[0],p8[1],p8[2]);
		v3 = new Vec(p4[0],p4[1],p4[2]);
		v1Neg = v1.mult(-1);
		normal = v2.add(v1Neg).cross(v3.add(v1Neg));

		gl.glNormal3d(normal.x, normal.y, normal.z);
		gl.glVertex3d(p7[0],p7[1],p7[2]);
		gl.glTexCoord2d(0.0D, 0.0D);

		gl.glVertex3d(p8[0],p8[1],p8[2]);
		gl.glTexCoord2d(0.0D, 1.0D);

		gl.glVertex3d(p4[0],p4[1],p4[2]);
		gl.glTexCoord2d(1.0D, 1.0D);

		gl.glVertex3d(p3[0],p3[1],p3[2]);
		gl.glTexCoord2d(1.0D, 0.0D);

		//
		gl.glEnd();


	}

	public SkewedBox(double allDimensions, boolean useTexture ) {
		this(allDimensions,allDimensions,allDimensions,allDimensions,allDimensions,useTexture);
	}

	@Override
	public void destroy(GL2 gl) {
		this.boxTexture.destroy(gl);
	}
	@Override
	public void init(GL2 gl) {
		try {
			File f = new File("Textures/WoodBoxTexture.jpg");
			this.boxTexture = TextureIO.newTexture (f, true); //Mipmap is enabled
		}
		catch(Exception e) {
			System.err.println("Unable  to load texture" );
			e.printStackTrace();
		}

	}

	@Override
	public String toString() {
		return "SkewedBox";
	}

}

