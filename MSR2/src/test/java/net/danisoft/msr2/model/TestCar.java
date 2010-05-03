package net.danisoft.msr2.model;

import java.io.IOException;
import java.net.URL;

import org.junit.Test;

import com.jme.app.SimpleGame;
import com.jme.app.AbstractGame.ConfigShowMode;
import com.jme.input.KeyBindingManager;
import com.jme.input.KeyInput;
import com.jme.math.FastMath;
import com.jme.math.Quaternion;
import com.jme.math.Vector3f;
import com.jme.scene.Node;
import com.jme.scene.TriMesh;
import com.jme.scene.shape.Box;
import com.jme.scene.shape.Sphere;
import com.jme.util.export.binary.BinaryImporter;
import com.jme.util.export.xml.XMLImporter;
import com.jmex.jbullet.PhysicsSpace;
import com.jmex.jbullet.collision.CollisionEvent;
import com.jmex.jbullet.collision.CollisionListener;
import com.jmex.jbullet.collision.shapes.CollisionShape;
import com.jmex.jbullet.nodes.PhysicsNode;

public class TestCar extends SimpleGame implements CollisionListener{
	
	private static PhysicsSpace pSpace=PhysicsSpace.getPhysicsSpace();
	Car car;
	
	@Test
	public void simpleCarTestCase(){
		TestCar app = new TestCar();
		app.setConfigShowMode(ConfigShowMode.AlwaysShow);
		app.start();
	}

	@Override
	protected void simpleInitGame() {
		//Spatials for the car
		Box box1=new Box("physicscar",Vector3f.ZERO,0.5f,0.5f,2f);

		//URL's for the car body and wheels
		URL bodyModel = TestCar.class.getClassLoader().getResource("car-jme.xml");
		URL wheelModel = TestCar.class.getClassLoader().getResource("wheel-jme.xml");
		URL trackModel = TestCar.class.getClassLoader().getResource("track-jme.xml");
		
		//Model loading
		Node wheel1 = null;
		Node wheel2 = null;
		Node wheel3 = null;
		Node wheel4 = null;
		Node body = null;
		Node trackNode = null;
		try {
			wheel1 = (Node)XMLImporter.getInstance().load(wheelModel);
			wheel2 = (Node)XMLImporter.getInstance().load(wheelModel);
			wheel3 = (Node)XMLImporter.getInstance().load(wheelModel);
			wheel4 = (Node)XMLImporter.getInstance().load(wheelModel);
			body = (Node)XMLImporter.getInstance().load(bodyModel);
			trackNode = (Node)XMLImporter.getInstance().load(trackModel);
		} catch (IOException e) {
			e.printStackTrace();
			System.exit(0);
		}
		
		//Car Data
		CarData carData = new CarData(500, 4.4f, 2.3f, 20f, new Vector3f(-0.75f,-0.75f,3.0f), new Vector3f(0.75f,-0.75f,3.0f), new Vector3f(-0.75f,-0.75f,-1.0f), new Vector3f(0.75f,-0.75f,-1.0f));

		//Create the car
		car = new Car(body,wheel1, wheel2, wheel3, wheel4, carData, new Vector3f(0,-5,0));
		
		//Put the car in the world
		this.rootNode.attachChild(car.getPhysicNode());
		car.getPhysicNode().updateRenderState();
		pSpace.add(car.getPhysicNode());
		
		//floor
		Track track = new Track(trackNode.getChild(0), new TrackData(), new Vector3f(0f,-16,0f));
        
        this.rootNode.attachChild(track.getPhysicsNode());
        track.getPhysicsNode().updateRenderState();
        pSpace.add(track.getPhysicsNode());
        
        //Key Mappings
        KeyBindingManager.getKeyBindingManager().add("accelerate", KeyInput.KEY_NUMPAD8);
        KeyBindingManager.getKeyBindingManager().add("steer_right", KeyInput.KEY_NUMPAD6);
        KeyBindingManager.getKeyBindingManager().add("steer_left", KeyInput.KEY_NUMPAD4);
        KeyBindingManager.getKeyBindingManager().add("brake", KeyInput.KEY_NUMPAD2);
	}
	
	@Override
	protected void simpleUpdate() {
		super.simpleUpdate();
		
		if(KeyBindingManager.getKeyBindingManager().isValidCommand("accelerate", true)){
			car.getPhysicNode().accelerate(0.8f);
		}else{
			car.getPhysicNode().accelerate(0f);
		}
		if(KeyBindingManager.getKeyBindingManager().isValidCommand("steer_right", true)){
			car.getPhysicNode().steer(-1f);
		}else if(KeyBindingManager.getKeyBindingManager().isValidCommand("steer_left", true)){
			car.getPhysicNode().steer(1f);
		}else{
			car.getPhysicNode().steer(0f);
		}
		if(KeyBindingManager.getKeyBindingManager().isValidCommand("brake", true)){
			car.getPhysicNode().brake(1f);
		}else{
			car.getPhysicNode().brake(0);
		}
		
		pSpace.update(this.tpf);
	}

	public void collision(CollisionEvent arg0) {
		// TODO Auto-generated method stub
		
	}
}
