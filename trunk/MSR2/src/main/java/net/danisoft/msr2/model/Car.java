package net.danisoft.msr2.model;

import com.jme.math.Vector3f;
import com.jme.scene.Spatial;
import com.jmex.jbullet.collision.shapes.CollisionShape;
import com.jmex.jbullet.nodes.PhysicsVehicleNode;



public class Car {
	
	/**	Chassis model */
	private Spatial body;
	/** 1st Wheel model */
	private Spatial wheel1;
	/** 2nd Wheel model */
	private Spatial wheel2;
	/** 3rd Wheel model */
	private Spatial wheel3;
	/** 4th Wheel model */
	private Spatial wheel4;
	/** Physic Node */
	private PhysicsVehicleNode physicNode;
	/** Car data & specifications */
	private CarData carData;
	
	/**
	 * Class constructor for real game structure.
	 * 
	 * @param manufacturer Car manufacturer 
	 * @param model Car model
	 * @param initPos initial position
	 */
	public Car(String manufacturer, String model, Vector3f initPos){
		
	}
	
	/**
	 * Class constructor for testing.
	 * 
	 * @param body body mesh
	 * @param wheel wheel mesh
	 * @param carData URL pointing to car meta-data file
	 * @param initPos initial position
	 */
	public Car(Spatial body, Spatial wheel1, Spatial wheel2, Spatial wheel3, Spatial wheel4, CarData carData, Vector3f initPos){
		this.body = body;
		this.wheel1 = wheel1;
		this.wheel2 = wheel2;
		this.wheel3 = wheel3;
		this.wheel4 = wheel4;
		this.carData = carData;
		
		//Create the physics vehicle
		this.physicNode = new PhysicsVehicleNode(this.body, CollisionShape.ShapeTypes.BOX);
		this.physicNode.setMaxSuspensionTravelCm(carData.getMaxSuspensionTravelCm());
		this.physicNode.setSuspensionCompression(carData.getSuspensionCompression());
		this.physicNode.setSuspensionDamping(carData.getSuspensionDamping());
		this.physicNode.setSuspensionStiffness(carData.getSuspensionStiffness());
		
		//Add the 4 wheels
		Vector3f wheelDirection = new Vector3f(0,-1,0);
		Vector3f wheelAxle = new Vector3f(-1,0,0);
		
		this.physicNode.addWheel(this.wheel1, this.carData.getWheelPos1(), wheelDirection, wheelAxle, 0.2f, 0.5f, true);
		this.physicNode.setRollInfluence(0, 1);
        
		this.physicNode.addWheel(this.wheel2, this.carData.getWheelPos2(), wheelDirection, wheelAxle, 0.2f, 0.5f, true);
		this.physicNode.setRollInfluence(1, 1);

		this.physicNode.addWheel(this.wheel3, this.carData.getWheelPos3(), wheelDirection, wheelAxle, 0.2f, 0.5f, false);
		this.physicNode.setRollInfluence(2, 1);

		this.physicNode.addWheel(this.wheel4, this.carData.getWheelPos4(), wheelDirection, wheelAxle, 0.2f, 0.5f, false);
		this.physicNode.setRollInfluence(3, 1);

		//Set the physic vehicle position
		this.physicNode.setLocalTranslation(initPos);
	}

	/**
	 * @return the physicNode
	 */
	public PhysicsVehicleNode getPhysicNode() {
		return physicNode;
	}
	
}
