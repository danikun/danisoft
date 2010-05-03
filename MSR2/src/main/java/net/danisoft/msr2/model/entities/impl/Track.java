package net.danisoft.msr2.model.entities.impl;

import net.danisoft.msr2.model.data.TrackData;
import net.danisoft.msr2.model.entities.Entity;

import com.jme.bounding.BoundingBox;
import com.jme.math.Vector3f;
import com.jme.scene.Spatial;
import com.jmex.jbullet.collision.shapes.CollisionShape;
import com.jmex.jbullet.nodes.PhysicsNode;

public class Track implements Entity {
	
	/** Track Model. */
	private Spatial model;
	/** Physic node. */
	private PhysicsNode physicsNode;
	/** Track data. */
	private TrackData trackData;
	
	/**
	 * Constructor for real game structure. 
	 * 
	 * @param country Country where the track is.
	 * @param name Name of the track.
	 */
	public Track(String country, String name){
		
	}
	
	/**
	 * Constructor for testing purposes.
	 * 
	 * @param model 3D Model of the track.
	 * @param trackData Track data.
	 * @param pos Position in the 3D space. 
	 */
	public Track(Spatial model, TrackData trackData, Vector3f pos){
		this.model = model;
		this.trackData = trackData;
		
		this.physicsNode = new PhysicsNode(this.model, CollisionShape.ShapeTypes.MESH, 0);
		
		this.physicsNode.setModelBound(new BoundingBox());
		this.physicsNode.updateModelBound();
		this.physicsNode.setLocalTranslation(pos);
	}

	/**
	 * @return the physicsNode
	 */
	public PhysicsNode getPhysicsNode() {
		return physicsNode;
	}

	public void update() {
		// TODO Auto-generated method stub
		
	}
	
}
