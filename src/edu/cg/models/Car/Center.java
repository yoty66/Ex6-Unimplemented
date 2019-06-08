package edu.cg.models.Car;

import com.jogamp.opengl.GL2;

import edu.cg.models.IRenderable;
import edu.cg.models.SkewedBox;

public class Center implements IRenderable {
	@Override
	public void destroy(GL2 gl) {//TODO:write this shit}
	}
	// TODO: The following elements are used to build the center of the body.
	// bodyBase is the black base of the center.
	private SkewedBox bodyBase = new SkewedBox(Specification.C_BASE_LENGTH, Specification.C_BASE_HEIGHT, Specification.C_BASE_HEIGHT, Specification.C_BASE_DEPTH, Specification.C_BASE_DEPTH);
	// backSeatBox is the back seat of the center.
	private SkewedBox backSeatBox = new SkewedBox(Specification.C_BACK_LENGTH,Specification.C_BACK_HEIGHT_1,Specification.C_BACK_HEIGHT_2,Specification.C_BACK_DEPTH,Specification.C_BACK_DEPTH);
	// frontBox is used to render both the 'front' and 'back' parts of the centers 
	// Look at the provided example with wireframe mode to better visualize where these elements should be placed.
	private SkewedBox frontBox = new SkewedBox(Specification.C_FRONT_LENGTH,Specification.C_FRONT_HEIGHT_1,Specification.C_FRONT_HEIGHT_2,Specification.C_FRONT_DEPTH_1,Specification.C_FRONT_DEPTH_2);
	// sideBox is used to render both the right-side and left-side of the center of the body.
	private SkewedBox sideBox = new SkewedBox(Specification.C_SIDE_LENGTH,Specification.C_SIDE_HEIGHT_1,Specification.C_SIDE_HEIGHT_2,Specification.C_SIDE_DEPTH_1,Specification.C_SIDE_DEPTH_2);
	
	@Override
	public void render(GL2 gl) {
		// TODO: Render the center of the car.
		gl.glPushMatrix();
		//allign body base at the middle
		Materials.SetBlackMetalMaterial(gl);
		this.bodyBase.render(gl);
//		add two side boxes
		Materials.SetRedMetalMaterial(gl);
// 		allign box to end of base box
		double distance = Specification.C_BASE_DEPTH/2 - Specification.C_SIDE_LENGTH/2;
		double height_distance = Specification.C_BASE_HEIGHT;
//		height_distance = 0;
		gl.glTranslated(0,height_distance,distance);
//		first box rotatate 90 deg ccw on y axis
		gl.glRotated(90,0,1,0);
		this.sideBox.render(gl);
//		second box
		gl.glPopMatrix();
		gl.glPushMatrix();
		gl.glTranslated(0,height_distance,-distance);
		gl.glRotated(-90,0,1,0);
		this.sideBox.render(gl);


////		front and back box
		gl.glPopMatrix();
		gl.glPushMatrix();
//
////		front
		distance = Specification.C_BASE_LENGTH/2 - Specification.C_FRONT_LENGTH/2;
		gl.glTranslated(distance,0,0);
		this.frontBox.render(gl);
		gl.glPopMatrix();
//		back
		gl.glPopMatrix();
		gl.glPushMatrix();
		gl.glTranslated(-distance,0,0);
//		rotate box 180 deg on y axis
		gl.glRotated(180,0,1,0);
		this.frontBox.render(gl);
		gl.glPopMatrix();

//		back seat
		Materials.SetBlackMetalMaterial(gl);
		gl.glPushMatrix();
		gl.glTranslated(-Specification.C_BACK_LENGTH/2,0,0);
		this.backSeatBox.render(gl);
		gl.glPopMatrix();


	}

	@Override
	public void init(GL2 gl) {
	}
}
