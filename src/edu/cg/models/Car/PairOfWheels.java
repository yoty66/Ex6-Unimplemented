package edu.cg.models.Car;

import com.jogamp.opengl.GL2;
import com.jogamp.opengl.glu.GLU;
import com.jogamp.opengl.glu.GLUquadric;

import edu.cg.models.IRenderable;

public class PairOfWheels implements IRenderable {
	@Override
	public void destroy(GL2 gl) {//TODO:write this shit}
	}
	// TODO: Use the wheel field to render the two wheels.
	private final Wheel wheel = new Wheel();
	
	@Override
	public void render(GL2 gl) {
		// TODO: Render the pair of wheels.
		GLU glu = new GLU();
		gl.glPushMatrix();
//		gl.glPushMatrix();


//first create rod between two wheels

		Materials.SetDarkGreyMetalMaterial(gl);
		GLUquadric quad = glu.gluNewQuadric();
//		create rod and allign middle of rod on the z axis
		gl.glTranslated(0.0D, 0.0D, -Specification.PAIR_OF_WHEELS_ROD_DEPTH/2);
		glu.gluCylinder(quad, Specification.PAIR_OF_WHEELS_ROD_RADIUS, Specification.PAIR_OF_WHEELS_ROD_RADIUS, Specification.PAIR_OF_WHEELS_ROD_DEPTH, 50, 1);

//		draw pair of weels
		gl.glPopMatrix();
		gl.glPushMatrix();

//		allign weel to side of the rod
		gl.glTranslated(0.0D, 0.0D, -(Specification.PAIR_OF_WHEELS_ROD_DEPTH/2 + Specification.TIRE_DEPTH/2));
		this.wheel.render(gl);

//go twice the opposite direction from before
		gl.glTranslated(0.0D, 0.0D, + Specification.PAIR_OF_WHEELS_ROD_DEPTH + Specification.TIRE_DEPTH);
		this.wheel.render(gl);
		glu.gluDeleteQuadric(quad);
		gl.glPopMatrix();
	}

	@Override
	public void init(GL2 gl) {
		// TODO Auto-generated method stub

	}
	
	@Override
	public String toString() {
		return "PairOfWheels";
	}

}
