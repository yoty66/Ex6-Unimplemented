package edu.cg.models.Car;

import com.jogamp.opengl.GL2;

public class Materials {
	// TODO: Use this class to update the color of the vertexes when drawing.
	private static final float DARK_GRAY[] = { 0.2f, 0.2f, 0.2f,1 };
	private static final float DARK_RED[] = { 0.25f, 0.01f, 0.01f,1 };
	private static final float RED[] = { 0.7f, 0f, 0f,1 };
	private static final float BLACK[] = { 0.05f, 0.05f, 0.05f,1 };

	public static void SetMetalMaterial(GL2 gl, float[] color) {
		gl.glColor3fv(color, 0);
	}

	public static void SetBlackMetalMaterial(GL2 gl){
		gl.glMaterialfv(GL2.GL_FRONT_AND_BACK, GL2.GL_DIFFUSE, BLACK, 0);
		gl.glMaterialf(GL2.GL_FRONT_AND_BACK, GL2.GL_SHININESS, 10f);

	}

	public static void SetRedMetalMaterial(GL2 gl) {
		gl.glMaterialfv(GL2.GL_FRONT_AND_BACK, GL2.GL_DIFFUSE, RED, 0);
		gl.glMaterialf(GL2.GL_FRONT_AND_BACK, GL2.GL_SHININESS, 10f);
	}

	public static void SetDarkRedMetalMaterial(GL2 gl) {
		gl.glMaterialfv(GL2.GL_FRONT_AND_BACK, GL2.GL_DIFFUSE, DARK_RED, 0);
		gl.glMaterialf(GL2.GL_FRONT_AND_BACK, GL2.GL_SHININESS, 10f);
	}
	
	public static void SetDarkGreyMetalMaterial(GL2 gl) {
		gl.glMaterialfv(GL2.GL_FRONT_AND_BACK, GL2.GL_DIFFUSE, DARK_GRAY, 0);
		gl.glMaterialf(GL2.GL_FRONT_AND_BACK, GL2.GL_SHININESS, 10f);
	}

	public static void setMaterialTire(GL2 gl) {
		float col[] = { .05f, .05f, .05f,1 };
		gl.glMaterialfv(GL2.GL_FRONT_AND_BACK, GL2.GL_DIFFUSE, col, 0);
		gl.glMaterialf(GL2.GL_FRONT_AND_BACK, GL2.GL_SHININESS, 10f);
	}


	public static void setMaterialRims(GL2 gl) {
		gl.glMaterialfv(GL2.GL_FRONT_AND_BACK, GL2.GL_DIFFUSE, DARK_GRAY, 0);
		gl.glMaterialf(GL2.GL_FRONT_AND_BACK, GL2.GL_SHININESS, 10f);
	}




}
