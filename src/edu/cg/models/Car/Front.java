package edu.cg.models.Car;

import com.jogamp.opengl.GL2;

import edu.cg.models.IRenderable;
import edu.cg.models.SkewedBox;

public class Front implements IRenderable {
	// TODO: The front of the car is build from the following elements:
	private SkewedBox hoodBox1 = new SkewedBox(Specification.F_HOOD_LENGTH_1,Specification.F_HOOD_HEIGHT_1,Specification.F_HOOD_HEIGHT_2,Specification.F_HOOD_DEPTH_1,Specification.F_HOOD_DEPTH_2);
	private SkewedBox hoodBox2 = new SkewedBox(Specification.F_HOOD_LENGTH_2,Specification.F_HOOD_HEIGHT_2,Specification.F_BUMPER_HEIGHT_1,Specification.F_HOOD_DEPTH_2,Specification.F_HOOD_DEPTH_3);
	private SkewedBox bumperBox = new SkewedBox(Specification.F_BUMPER_LENGTH,Specification.F_BUMPER_HEIGHT_1,Specification.F_BUMPER_HEIGHT_2,Specification.F_BUMPER_DEPTH,Specification.F_BUMPER_DEPTH);
	private SkewedBox bumperWingsBox = new SkewedBox(Specification.F_BUMPER_LENGTH,Specification.F_BUMPER_WINGS_HEIGHT,Specification.F_BUMPER_HEIGHT_2,Specification.F_BUMPER_WINGS_DEPTH,Specification.F_BUMPER_WINGS_DEPTH);
	private PairOfWheels wheels = new PairOfWheels();


	@Override
	public void render(GL2 gl) {
		// TODO: Render the front of the car.
//		start with rendering two hood skewedbox
		Materials.SetRedMetalMaterial(gl);
		gl.glPushMatrix();

		this.hoodBox1.render(gl);
//		allign second hoodbox according to end of first box
		double distance = Specification.F_HOOD_LENGTH_1/2 +Specification.F_HOOD_LENGTH_2/2;
		gl.glTranslated( distance,0,0);
		this.hoodBox2.render(gl);
//		allign pair of weels in the middle of the second hood box.
		this.wheels.render(gl);
//		add first bumperBox (add half of x value)
		distance = Specification.F_HOOD_LENGTH_2/2 + Specification.F_BUMPER_LENGTH/2;
		gl.glTranslated(distance,0,0);
//		reset colro to red
		Materials.SetRedMetalMaterial(gl);
		this.bumperBox.render(gl);
//		add two wings box
		gl.glPushMatrix();
//		first wing
		distance = Specification.F_BUMPER_DEPTH/2 +Specification.S_WINGS_DEPTH/2;
		gl.glTranslated(0,0,distance);
		this.bumperWingsBox.render(gl);
		gl.glPopMatrix();
//		second wing
		gl.glTranslated(0,0,-distance);
		this.bumperWingsBox.render(gl);

		gl.glPopMatrix();

	}


	@Override
	public void destroy(GL2 gl) {//TODO:write this shit}
	}

	@Override
	public void init(GL2 gl) {
		// TODO Auto-generated method stub

	}

}
