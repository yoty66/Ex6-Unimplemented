package edu.cg.models.Car;

import com.jogamp.opengl.*;

import edu.cg.models.IRenderable;

/**
 * A F1 Racing Car.
 *
 */

public class F1Car implements IRenderable {
	@Override
	public void destroy(GL2 gl) {//TODO:write this shit}
	}
	IRenderable back = new Back();
	IRenderable front = new Front();
	IRenderable center = new Center();
	@Override
	public void render(GL2 gl) {
		// TODO: Render the whole car. 
//		//       Here You should only render the three parts: back, center and front.


		gl.glPushMatrix();
//		render center
		this.center.render(gl);
//		allign front to end of center

		double distance = Specification.C_BASE_LENGTH/2 + Specification.F_HOOD_LENGTH_1/2;
		gl.glTranslated(distance,0,0);
		this.front.render(gl);
		gl.glPopMatrix();

//		allign back to end of center

		gl.glPushMatrix();
		distance =Specification.C_BASE_LENGTH/2 + Specification.B_LENGTH;
		this.back.render(gl);
		gl.glPopMatrix();


		gl.glPopMatrix();





	}

	@Override
	public String toString() {
		return "F1Car";
	}

	@Override
	public void init(GL2 gl) {

	}
}
