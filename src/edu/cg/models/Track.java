package edu.cg.models;

import com.jogamp.opengl.GL2;
import edu.cg.models.Car.Specification;

public class Track implements IRenderable {
	private TrackSegment currentTrackSegment = null;
	private TrackSegment nextTrackSegment = null;
	private double currentDifficulty = 0.5; // Current track segment difficulty
	private final double DIFFICULTY_DELTA = 0.05; // Difficulty difference between adjacent track segments
	private final double MAXIMUM_DIFFICULTY = 0.95;

	public Track() {
		// TODO: Initialize the track. We did it for you.
		currentTrackSegment = new TrackSegment(currentDifficulty);
		nextTrackSegment = new TrackSegment(currentDifficulty + DIFFICULTY_DELTA);
	}

	@Override
	public void render(GL2 gl) {
		// TODO: Render the track by rendering the current and next segment.
		gl.glPushMatrix();
		this.currentTrackSegment.render(gl);
		gl.glTranslated(0,0,-Specification.TRACK_SEGMENT_LENGTH);
		this.nextTrackSegment.render(gl);
		gl.glPopMatrix();
	}

	@Override
	public void init(GL2 gl) {
		// TODO: Initialize the track segments. We already did it for you.
		// The init method for both segments will load the textures of the models.
		currentTrackSegment.init(gl);
		nextTrackSegment.init(gl);
	}

	@Override
	public void destroy(GL2 gl) {
		// TODO: Destroy the track segments. We already did it for you.
		// This will destroy the textures of the track segments.
		// Note if this method is invoked, then you cannot longer render the track -
		// because the textures are not available.
		currentTrackSegment.destroy(gl);
		nextTrackSegment.destroy(gl);
		currentTrackSegment = nextTrackSegment = null;
	}

	public void changeTrack(GL2 gl) {
		// TODO: Change the current track by switch the current and next track.
		// - We provided an implementation, you can change it if you want.
		TrackSegment tmp = currentTrackSegment;
		currentTrackSegment = nextTrackSegment;
		currentDifficulty += DIFFICULTY_DELTA;
		currentDifficulty = Math.min(currentDifficulty, MAXIMUM_DIFFICULTY);
		tmp.setDifficulty(currentDifficulty + DIFFICULTY_DELTA);
		nextTrackSegment = tmp;
	}

}

//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

//package edu.cg.models;
//
//import com.jogamp.opengl.GL2;
//
//public class Track implements IRenderable {
//    private TrackSegment currentTrackSegment = null;
//    private TrackSegment nextTrackSegment = null;
//    private double currentDifficulty = 0.5D;
//    private final double DIFFICULTY_DELTA = 0.05D;
//    private final double MAXIMUM_DIFFICULTY = 0.95D;
//
//    public Track() {
//        this.currentTrackSegment = new TrackSegment(this.currentDifficulty);
//        this.nextTrackSegment = new TrackSegment(this.currentDifficulty + 0.05D);
//    }
//
//    public void render(GL2 gl) {
//        gl.glPushMatrix();
//        this.currentTrackSegment.render(gl);
//        gl.glTranslated(0.0D, 0.0D, -500.0D);
//        this.nextTrackSegment.render(gl);
//        gl.glPopMatrix();
//    }
//
//    public void init(GL2 gl) {
//        this.currentTrackSegment.init(gl);
//        this.nextTrackSegment.init(gl);
//    }
//
//    public void destroy(GL2 gl) {
//        this.currentTrackSegment.destroy(gl);
//        this.nextTrackSegment.destroy(gl);
//        this.currentTrackSegment = this.nextTrackSegment = null;
//    }
//
//    public void changeTrack(GL2 gl) {
//        TrackSegment tmp = this.currentTrackSegment;
//        this.currentTrackSegment = this.nextTrackSegment;
//        this.currentDifficulty += 0.05D;
//        this.currentDifficulty = Math.min(this.currentDifficulty, 0.95D);
//        tmp.setDifficulty(this.currentDifficulty + 0.05D);
//        this.nextTrackSegment = tmp;
//    }
//}
//
