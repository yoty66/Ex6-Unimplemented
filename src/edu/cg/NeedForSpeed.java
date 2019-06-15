package edu.cg;

import java.awt.Component;

import com.jogamp.opengl.GL2;
import com.jogamp.opengl.GLAutoDrawable;
import com.jogamp.opengl.GLEventListener;
import com.jogamp.opengl.glu.GLU;
import com.jogamp.opengl.util.FPSAnimator;

import edu.cg.algebra.Vec;
import edu.cg.models.Car.Wheel;
import edu.cg.models.SkewedBox;
import edu.cg.models.Track;
import edu.cg.models.TrackSegment;
import edu.cg.models.Car.F1Car;
import edu.cg.models.Car.Specification;

/**
 * An OpenGL 3D Game.
 *
 */
public class NeedForSpeed implements GLEventListener {
	private GameState gameState = null; // Tracks the car movement and orientation
	private F1Car car = null; // The F1 car we want to render
	private Vec carCameraTranslation = null; // The accumulated translation that should be applied on the car, camera
												// and light sources
	private Track gameTrack = null; // The game track we want to render
	private FPSAnimator ani; // This object is responsible to redraw the model with a constant FPS
	private Component glPanel; // The canvas we draw on.
	private boolean isModelInitialized = false; // Whether model.init() was called.
	private boolean isDayMode = true; // Indicates whether the lighting mode is day/night.
	// TODO: add fields as you want. For example:
	// - Car initial position (should be fixed).
	// - Camera initial position (should be fixed)

	public NeedForSpeed(Component glPanel) {
		this.glPanel = glPanel;
		gameState = new GameState();
		gameTrack = new Track();
		carCameraTranslation = new Vec(0.0);
		car = new F1Car();
	}

	@Override
	public void display(GLAutoDrawable drawable) {
		GL2 gl = drawable.getGL().getGL2();
		if (!isModelInitialized) {
			initModel(gl);
		}
		if (isDayMode) {
			// TODO: Setup background color when day mode is on (You can choose differnt color)
			gl.glClearColor(0.52f, 0.824f, 1.0f, 1.0f);
		} else {
			// TODO: Setup background color when night mode is on (You can choose differnt color)
			gl.glClearColor(0.0f, 0.0f, 0.32f, 1.0f);
		}
		gl.glClear(GL2.GL_COLOR_BUFFER_BIT | GL2.GL_DEPTH_BUFFER_BIT);
		gl.glMatrixMode(GL2.GL_MODELVIEW);
		gl.glLoadIdentity();
		// TODO: This is the flow in which we render the scene. You can use different flow.
		// Step (1) You should update the accumulated translation that needs to be
		// applied on the car, camera and light sources.
//		updateCarCameraTranslation(gl);
		// Step (2) Position the camera and setup its orientation
		setupCamera(gl);
		// Step (3) setup the lighting.
		setupLights(gl);
		// Step (4) render the car.
		renderCar(gl);
		// Step (5) render the track.
		renderTrack(gl);
	}

	private void updateCarCameraTranslation(GL2 gl) {

		double roadBound = TrackSegment.ASPHALT_TEXTURE_WIDTH/2.0;
		Vec distance = this.gameState.getNextTranslation();
//		avoid car getting out of the segment
		if(distance.x + this.carCameraTranslation.x > 10f)
			distance.x = 10.0f - this.carCameraTranslation.x;
		if(distance.x + this.carCameraTranslation.x < -10f)
			distance.x = -10.0f + this.carCameraTranslation.x;
		this.carCameraTranslation.add(distance);
//		check if we moved to next trackSegment
		if(carCameraTranslation.z < -500){
			carCameraTranslation.z+=500;
			this.gameTrack.changeTrack(gl);
		}


//JARRRRRRR
//		Vec ret = this.gameState.getNextTranslation();
//		this.carCameraTranslation = this.carCameraTranslation.add(ret);
//		double dx = Math.max((double)this.carCameraTranslation.x, -7.0D);
//		this.carCameraTranslation.x = (float)Math.min(dx, 7.0D);
//		if ((double)Math.abs(this.carCameraTranslation.z) >= 510.0D) {
//			this.carCameraTranslation.z = -((float)((double)Math.abs(this.carCameraTranslation.z) % 500.0D));
//			this.gameTrack.changeTrack(gl);
//		}
	}

	private void setupCamera(GL2 gl) {
		double[] eyeCenter = {this.carCameraTranslation.x, 3 + this.carCameraTranslation.y, this.carCameraTranslation.z +3};
		double[] referencePoint = {this.carCameraTranslation.x, 2 + (double)this.carCameraTranslation.y, -10 + (double)this.carCameraTranslation.z};
		GLU glu = new GLU();
		glu.gluLookAt(eyeCenter[0], eyeCenter[1], eyeCenter[2],referencePoint[0], referencePoint[1], referencePoint[2], 0, 0.5, -2);

	}

	private void addSpotlight(GL2 gl, int light, float[] position, float[] lightColor, float[] lightDirection) {

		gl.glLightfv(light, GL2.GL_POSITION, position, 0);
		gl.glLightf(light,GL2.GL_SPOT_CUTOFF,60);
		gl.glLightfv(light, GL2.GL_SPOT_DIRECTION, lightDirection, 0);
		gl.glLightfv(light, GL2.GL_SPECULAR, lightColor, 0);
		gl.glLightfv(light, GL2.GL_DIFFUSE, lightColor, 0);
		gl.glLightf(light, GL2.GL_SPOT_EXPONENT, 0.3f);
		gl.glEnable(light);

	}


	private void setupLights(GL2 gl) {
		gl.glLightModeli(GL2.GL_LIGHT_MODEL_TWO_SIDE,GL2.GL_TRUE);
		if (isDayMode) {

//			disable night lights and moon light
			gl.glDisable(GL2.GL_LIGHT1);
			gl.glDisable(GL2.GL_LIGHT2);
			gl.glDisable(GL2.GL_LIGHT3);
			int dayLight = GL2.GL_LIGHT0;
//			position
			gl.glLightfv(dayLight, 4611, new float[]{0,1,1}, 0);

//			default properties of LIGHT0 fits a sun light
			gl.glEnable(dayLight);
		}
		else {

//			disable sun light
			gl.glDisable(GL2.GL_LIGHT0);
			// * Remember: spotlight sources also move with the camera.
			int nightLight1 = GL2.GL_LIGHT1;
			int nightLight2 = GL2.GL_LIGHT2;
			int moonLight = GL2.GL_LIGHT3;
//add low ambient light from moon
			float[] ambientLight = { 0.3f, 0.3f, 0.3f,0f };  // weak ambient light
			gl.glLightfv(moonLight, GL2.GL_AMBIENT, ambientLight, 0);
			gl.glEnable(moonLight);
//			add two spotlight from both sides of the car
			float[] light1Position = {5 + this.carCameraTranslation.x, 10 + this.carCameraTranslation.y,10 + this.carCameraTranslation.z,1};
			float[] spotLightColor = {0.85F, 0.85F, 0.85F, 1.0F};
			float[] spotLightDirection = {0.0F, -1.0F, 0.0F};
			this.addSpotlight(gl, nightLight1, light1Position,spotLightColor,spotLightDirection );
			float[] light2Position = {this.carCameraTranslation.x - 5, 10 + this.carCameraTranslation.y,this.carCameraTranslation.z -5, 1};
			this.addSpotlight(gl, nightLight2, light2Position, spotLightColor,spotLightDirection);



		}

	}

	private void renderTrack(GL2 gl) {
//		simply render the track
		gl.glPushMatrix();
		this.gameTrack.render(gl);
		gl.glPopMatrix();


	}

	private void renderCar(GL2 gl) {
		gl.glPushMatrix();
		gl.glTranslated(carCameraTranslation.x,carCameraTranslation.y, carCameraTranslation.z);
		gl.glTranslated(0,0,-5.7);

		//turns
		double carRotation = gameState.getCarRotation();
		gl.glRotated(-carRotation, 0, 1, 0);

		//The original car EX5 was on the side
		gl.glRotated(90, 0, 1, 0);

		gl.glScaled(3.5, 3.5, 3.5);
		gl.glPushMatrix();
		gl.glTranslated(Specification.C_BASE_DEPTH,0,0);
		this.car.render(gl);
		gl.glPopMatrix();
		gl.glPopMatrix();
	}

	public GameState getGameState() {
		return gameState;
	}

	@Override
	public void dispose(GLAutoDrawable drawable) {
		// TODO: Destroy all models.
	}

	@Override
	public void init(GLAutoDrawable drawable) {
		GL2 gl = drawable.getGL().getGL2();

		// Initialize display callback timer
		ani = new FPSAnimator(30, true);
		ani.add(drawable);
		glPanel.repaint();

		initModel(gl);
		ani.start();
	}

	public void initModel(GL2 gl) {
		// TODO: You can change OpenGL modes during implementation for debug purposes.
		//		 Remember to change OpenGL mode as it was before.
		gl.glCullFace(GL2.GL_BACK);
		gl.glEnable(GL2.GL_CULL_FACE);
		gl.glEnable(GL2.GL_NORMALIZE);
		gl.glEnable(GL2.GL_DEPTH_TEST);
		gl.glEnable(GL2.GL_LIGHTING);
		gl.glEnable(GL2.GL_SMOOTH);

		car.init(gl);
		gameTrack.init(gl);
		isModelInitialized = true;
	}

	@Override
	public void reshape(GLAutoDrawable drawable, int x, int y, int width, int height) {
		// TODO Setup the projection matrix here.
		//		- It is recommended to use gluPerspective - with fovy 57.0


		//TODO:This is a Direct Copy
		GL2 gl = drawable.getGL().getGL2();
		GLU glu = new GLU();
		double ratio = (double)width/ height;
		gl.glMatrixMode(GL2.GL_PROJECTION);

		gl.glLoadIdentity();
		glu.gluPerspective(57, ratio, 2.0D, 500.0D);
	}

	/**
	 * Start redrawing the scene with 30 FPS
	 */
	public void startAnimation() {
		if (!ani.isAnimating())
			ani.start();
	}

	/**
	 * Stop redrawing the scene with 30 FPS
	 */
	public void stopAnimation() {
		if (ani.isAnimating())
			ani.stop();
	}

	public void toggleNightMode() {
		isDayMode = !isDayMode;
	}

}
