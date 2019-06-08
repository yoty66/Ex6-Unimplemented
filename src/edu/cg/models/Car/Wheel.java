package edu.cg.models.Car;

import com.jogamp.opengl.GL2;
import com.jogamp.opengl.glu.GLU;
import com.jogamp.opengl.glu.GLUquadric;

import edu.cg.models.IRenderable;

public class Wheel implements IRenderable {
	@Override
	public void destroy(GL2 gl) {//TODO:write this shit}
	}

	@Override
	public void render(GL2 gl) {
		// The wheel should be in the center relative to its local coordinate system.
//
		Materials.setMaterialTire(gl);
		GLU glu=new GLU();
		GLUquadric q=glu.gluNewQuadric();
		gl.glPushMatrix();
		gl.glPushMatrix();
		//translate s.t the center of the wheel is on the origin
		gl.glTranslated(0,0,-Specification.TIRE_DEPTH/2.0);
		glu.gluCylinder(q,Specification.TIRE_RADIUS,Specification.TIRE_RADIUS,Specification.TIRE_DEPTH,Specification.TIRE_SLICES_NUMBER,1);
		gl.glPopMatrix();
		gl.glPushMatrix();

		gl.glTranslated(0,0,Specification.TIRE_DEPTH/2);
		//generate first disk of the wheel
		glu.gluDisk(q,0.04,Specification.TIRE_RADIUS,Specification.TIRE_SLICES_NUMBER,1 );
		Materials.setMaterialRims(gl);
		glu.gluDisk(q,0,0.04,Specification.TIRE_SLICES_NUMBER,1);


		gl.glPopMatrix();
		gl.glTranslated(0,0,-Specification.TIRE_DEPTH/2+0.01);
		//rotate 180 deg in order that the face of the wheel will be rendered.
		gl.glRotated(180.0,0.0,1.0,0);
		glu.gluDisk(q,0,0.04,Specification.TIRE_SLICES_NUMBER,1);
		Materials.setMaterialTire(gl);
		glu.gluDisk(q,0.04,Specification.TIRE_RADIUS,Specification.TIRE_SLICES_NUMBER,1 );


		gl.glPopMatrix();
		glu.gluDeleteQuadric(q);





	}

	@Override
	public void init(GL2 gl) {}
		// TODO Auto-generated method stub

	@Override
	public String toString() {
		return "Wheel";
	}

}
