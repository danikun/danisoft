package net.danisoft.msr2.model.entities.impl;

import java.io.IOException;
import java.net.URL;

import net.danisoft.msr2.model.TestCar;
import net.danisoft.msr2.model.data.CarData;
import net.danisoft.msr2.model.entities.Entity;

import com.jme.bounding.BoundingBox;
import com.jme.input.KeyBindingManager;
import com.jme.math.Vector3f;
import com.jme.scene.Node;
import com.jme.scene.Spatial;
import com.jme.util.export.xml.XMLImporter;
import com.jmex.jbullet.collision.shapes.BoxCollisionShape;
import com.jmex.jbullet.collision.shapes.CollisionShape;
import com.jmex.jbullet.collision.shapes.CompoundCollisionShape;
import com.jmex.jbullet.nodes.PhysicsVehicleNode;



public class Car implements Entity{
	
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
	 * Constructor for real game structure.
	 * 
	 * @param manufacturer Car manufacturer 
	 * @param model Car model
	 * @param initPos initial position
	 */
	public Car(String manufacturer, String model, Vector3f initPos){
		
		//URL's for the car body and wheels
		URL bodyModel = TestCar.class.getClassLoader().getResource("cars/" + manufacturer + "/" + model + "/" + "car-jme.xml");
		URL wheelModel = TestCar.class.getClassLoader().getResource("cars/" + manufacturer + "/" + model + "/" + "wheel-jme.xml");
		URL carDataFile = TestCar.class.getClassLoader().getResource("cars/" + manufacturer + "/" + model + "/" + "car-data.xml");
		
		//Model loading
		try {
			wheel1 = (Node)XMLImporter.getInstance().load(wheelModel);
			wheel2 = (Node)XMLImporter.getInstance().load(wheelModel);
			wheel3 = (Node)XMLImporter.getInstance().load(wheelModel);
			wheel4 = (Node)XMLImporter.getInstance().load(wheelModel);
			body = (Node)XMLImporter.getInstance().load(bodyModel);
		} catch (IOException e) {
			e.printStackTrace();
			System.exit(0);
		}
		
		carData = new CarData(carDataFile);
		
		//Setup the car
		this.setup(initPos);
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

		//Setup the car		
		this.setup(initPos);
	}
	
	/**
	 * Method that creates and initializes the physic object.
	 * 
	 * @param initPos starting position of the car.
	 */
	private void setup(Vector3f initPos){
		
		CompoundCollisionShape compoundCollisionShape=new CompoundCollisionShape();
		BoxCollisionShape boxCollisionShape=new BoxCollisionShape(new Vector3f(1f,1f,5f));
		compoundCollisionShape.addChildShape(boxCollisionShape, new Vector3f(0,1f,0));
		this.body.setLocalTranslation(new Vector3f(0,1f,0));
		
		//Create the physics vehicle
		this.physicNode = new PhysicsVehicleNode(this.body, compoundCollisionShape, 20);
		this.physicNode.setMaxSuspensionTravelCm(carData.getMaxSuspensionTravelCm());
		this.physicNode.setSuspensionCompression(carData.getSuspensionCompression());
		this.physicNode.setSuspensionDamping(carData.getSuspensionDamping());
		this.physicNode.setSuspensionStiffness(carData.getSuspensionStiffness());
		
		//Add the 4 wheels
		Vector3f wheelDirection = new Vector3f(0,-1,0);
		Vector3f wheelAxle = new Vector3f(-1,0,0);
		
		this.physicNode.addWheel(this.wheel1, this.carData.getWheelPos1(), wheelDirection, wheelAxle, 0.1f, 0.5f, true);
		this.physicNode.setRollInfluence(0, 1);
        
		this.physicNode.addWheel(this.wheel2, this.carData.getWheelPos2(), wheelDirection, wheelAxle, 0.1f, 0.5f, true);
		this.physicNode.setRollInfluence(1, 1);

		this.physicNode.addWheel(this.wheel3, this.carData.getWheelPos3(), wheelDirection, wheelAxle, 0.2f, 0.5f, false);
		this.physicNode.setRollInfluence(2, 1);

		this.physicNode.addWheel(this.wheel4, this.carData.getWheelPos4(), wheelDirection, wheelAxle, 0.2f, 0.5f, false);
		this.physicNode.setRollInfluence(3, 1);

		//Set the physic vehicle position
		this.physicNode.setModelBound(new BoundingBox());
		this.physicNode.updateModelBound();
		this.physicNode.setLocalTranslation(initPos);
	}

	/**
	 * @return the physicNode
	 */
	public PhysicsVehicleNode getPhysicNode() {
		return physicNode;
	}

	public void update() {
		if(KeyBindingManager.getKeyBindingManager().isValidCommand("accelerate", true)){
			this.getPhysicNode().accelerate(20f);
		}else{
			this.getPhysicNode().accelerate(0f);
		}
		if(KeyBindingManager.getKeyBindingManager().isValidCommand("steer_right", true)){
			this.getPhysicNode().steer(-0.5f);
		}else if(KeyBindingManager.getKeyBindingManager().isValidCommand("steer_left", true)){
			this.getPhysicNode().steer(0.5f);
		}else{
			this.getPhysicNode().steer(0f);
		}
		if(KeyBindingManager.getKeyBindingManager().isValidCommand("brake", true)){
			this.getPhysicNode().brake(1f);
		}else{
			this.getPhysicNode().brake(0);
		}
	}
	
}
