package net.danisoft.msr2.model;

import org.junit.Test;

import com.jme.app.SimpleGame;
import com.jme.app.AbstractGame.ConfigShowMode;
import com.jme.math.Vector3f;
import com.jme.scene.shape.Box;
import com.jme.scene.shape.Sphere;
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
		Sphere wheel1=new Sphere("wheel",8,8,0.5f);
		Sphere wheel2=new Sphere("wheel",8,8,0.5f);
		Sphere wheel3=new Sphere("wheel",8,8,0.5f);
		Sphere wheel4=new Sphere("wheel",8,8,0.5f);
		
		//Car Data
		CarData carData = new CarData(500, 4.4f, 2.3f, 20f, new Vector3f(-1f,-0.5f,2f), new Vector3f(1f,-0.5f,2f), new Vector3f(-1f,-0.5f,-2f), new Vector3f(1f,-0.5f,-2f));

		//Create the car
		car = new Car(box1,wheel1, wheel2, wheel3, wheel4, carData, new Vector3f(10,-2,0));
		
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
		car.getPhysicNode().accelerate(0.8f);
		car.getPhysicNode().steer(1f);
		pSpace.update(this.tpf);
	}

	public void collision(CollisionEvent arg0) {
		// TODO Auto-generated method stub
		
	}
}
