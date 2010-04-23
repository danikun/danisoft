package net.danisoft.msr2.model;

import java.io.IOException;
import java.net.URL;

import org.junit.Test;

import com.jme.app.SimpleGame;
import com.jme.app.AbstractGame.ConfigShowMode;
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
//		Sphere wheel1=new Sphere("wheel",8,8,0.5f);
//		Sphere wheel2=new Sphere("wheel",8,8,0.5f);
//		Sphere wheel3=new Sphere("wheel",8,8,0.5f);
//		Sphere wheel4=new Sphere("wheel",8,8,0.5f);
		
		URL bodyModel = TestCar.class.getClassLoader().getResource("car-jme.xml");
		URL wheelModel = TestCar.class.getClassLoader().getResource("wheel-jme.xml");
		Node wheel1 = null;
		Node wheel2 = null;
		Node wheel3 = null;
		Node wheel4 = null;
		Node body = null;
		try {
			wheel1 = (Node)XMLImporter.getInstance().load(wheelModel);
			Quaternion quaternion = new Quaternion();
			quaternion.fromAngleAxis(FastMath.DEG_TO_RAD * 90, new Vector3f(0,1,0));

			wheel2 = (Node)XMLImporter.getInstance().load(wheelModel);
			wheel3 = (Node)XMLImporter.getInstance().load(wheelModel);
			wheel4 = (Node)XMLImporter.getInstance().load(wheelModel);
			body = (Node)XMLImporter.getInstance().load(bodyModel);
		} catch (IOException e) {
			e.printStackTrace();
			System.exit(0);
		}
		
		//Car Data
		CarData carData = new CarData(500, 4.4f, 2.3f, 20f, new Vector3f(-0.75f,-0.75f,3.0f), new Vector3f(0.75f,-0.75f,3.0f), new Vector3f(-0.75f,-0.75f,-1.0f), new Vector3f(0.75f,-0.75f,-1.0f));

		//Create the car
		car = new Car(body,wheel1, wheel2, wheel3, wheel4, carData, new Vector3f(10,-2,0));
		
		//Put the car in the world
		this.rootNode.attachChild(car.getPhysicNode());
		car.getPhysicNode().updateRenderState();
		pSpace.add(car.getPhysicNode());
		
		//floor
        PhysicsNode floor =new PhysicsNode(new Box("physicsfloor",Vector3f.ZERO,100f,0.2f,100f),CollisionShape.ShapeTypes.MESH,0);
        floor.setLocalTranslation(new Vector3f(0f,-6,0f));
        this.rootNode.attachChild(floor);
        floor.updateRenderState();
        pSpace.add(floor);

	}
	
	@Override
	protected void simpleUpdate() {
		super.simpleUpdate();
		//car.getPhysicNode().accelerate(0.7f);
		//car.getPhysicNode().steer(0.2f);
		pSpace.update(this.tpf);
	}

	public void collision(CollisionEvent arg0) {
		// TODO Auto-generated method stub
		
	}
}
