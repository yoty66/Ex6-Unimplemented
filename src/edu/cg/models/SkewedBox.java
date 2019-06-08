package edu.cg.models;

import com.jogamp.opengl.GL2;
import com.jogamp.opengl.GL2ES3;
import com.jogamp.opengl.util.texture.Texture;
import edu.cg.algebra.Point;

public class SkewedBox implements IRenderable {
	private double length, height1, height2, depth1, depth2;
	private Texture boxTexture=null;

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

	@Override
	public void render(GL2 gl) {
		// TODO : Render the skewed-box using. 
		//        Use the fields: length, height1, height2, depth1, depth2

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

		//Front view x-axis:
        gl.glVertex3d(p4[0],p4[1],p4[2]);//p4
        gl.glVertex3d(p1[0],p1[1],p1[2]);//p1
        gl.glVertex3d(p2[0],p2[1],p2[2]);//p2
        gl.glVertex3d(p3[0],p3[1],p3[2]);//p3

		gl.glVertex3d(p5[0],p5[1],p5[2]);
		gl.glVertex3d(p8[0],p8[1],p8[2]);
		gl.glVertex3d(p7[0],p7[1],p7[2]);
		gl.glVertex3d(p6[0],p6[1],p6[2]);


		//Top view y-axis:
		gl.glVertex3d(p8[0],p8[1],p8[2]);
		gl.glVertex3d(p5[0],p5[1],p5[2]);
		gl.glVertex3d(p1[0],p1[1], p1[2]);
		gl.glVertex3d(p4[0],p4[1],p4[2]);

		gl.glVertex3d(p7[0],p7[1],p7[2]);
		gl.glVertex3d(p3[0],p3[1],p3[2]);
		gl.glVertex3d(p2[0],p2[1],p2[2]);
		gl.glVertex3d(p6[0],p6[1],p6[2]);

		//Side view z-axis:
		gl.glVertex3d(p5[0],p5[1],p5[2]);
		gl.glVertex3d(p6[0],p6[1],p6[2]);
		gl.glVertex3d(p2[0],p2[1],p2[2]);
		gl.glVertex3d(p1[0],p1[1],p1[2]);

		gl.glVertex3d(p7[0],p7[1],p7[2]);
		gl.glVertex3d(p8[0],p8[1],p8[2]);
		gl.glVertex3d(p4[0],p4[1],p4[2]);
		gl.glVertex3d(p3[0],p3[1],p3[2]);
		//
		gl.glEnd();

	}
	public SkewedBox(double allDimensions, boolean useTexture ) {//TODO: Write this method YOtam }
	}
	@Override
	public void destroy(GL2 gl) {
		this.boxTexture.destroy(gl);
	}
	@Override
	public void init(GL2 gl) {
	}
	
	@Override
	public String toString() {
		return "SkewedBox";
	}

}
