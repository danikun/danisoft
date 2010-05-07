package net.danisoft.msr2.model;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import net.danisoft.msr2.model.data.CarData;
import net.danisoft.msr2.model.data.TrackData;
import net.danisoft.msr2.model.entities.Entity;
import net.danisoft.msr2.model.entities.impl.Car;
import net.danisoft.msr2.model.entities.impl.Track;

import org.junit.Test;

import com.jme.app.SimpleGame;
import com.jme.app.AbstractGame.ConfigShowMode;
import com.jme.image.Texture;
import com.jme.input.KeyBindingManager;
import com.jme.input.KeyInput;
import com.jme.light.Light;
import com.jme.light.PointLight;
import com.jme.math.FastMath;
import com.jme.math.Quaternion;
import com.jme.math.Vector3f;
import com.jme.scene.Node;
import com.jme.scene.TriMesh;
import com.jme.scene.shape.Box;
import com.jme.scene.shape.Sphere;
import com.jme.scene.state.TextureState;
import com.jme.util.TextureManager;
import com.jme.util.export.binary.BinaryImporter;
import com.jme.util.export.xml.XMLImporter;
import com.jmex.jbullet.PhysicsSpace;
import com.jmex.jbullet.collision.CollisionEvent;
import com.jmex.jbullet.collision.CollisionListener;
import com.jmex.jbullet.collision.shapes.CollisionShape;
import com.jmex.jbullet.nodes.PhysicsNode;

public class TestCar extends SimpleGame implements CollisionListener{
	
	private static PhysicsSpace pSpace=PhysicsSpace.getPhysicsSpace();
	private List<Entity> entities;
	
	@Test
	public void simpleCarTestCase(){
		TestCar app = new TestCar();
		app.setConfigShowMode(ConfigShowMode.AlwaysShow);
		app.start();
	}

	@Override
	protected void simpleInitGame() {
		//Init entities List
		this.entities = new ArrayList<Entity>();

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
		Car car = new Car(body,wheel1, wheel2, wheel3, wheel4, carData, new Vector3f(0,-5,0));
		
		//Create file structure car
		Car car2 = new Car("test", "test", new Vector3f(14,-5,0));
		
		//Put the cars in the world
		this.rootNode.attachChild(car.getPhysicNode());
		this.rootNode.attachChild(car2.getPhysicNode());
		car.getPhysicNode().updateRenderState();
		car2.getPhysicNode().updateRenderState();
		pSpace.add(car.getPhysicNode());
		pSpace.add(car2.getPhysicNode());
		
		entities.add(car);
		
		//floor texture
		URL floorTex = TestCar.class.getClassLoader().getResource("textura-test.png");
		TextureState ts = display.getRenderer().createTextureState();
		Texture t = TextureManager.loadTexture(floorTex,
				Texture.MinificationFilter.BilinearNearestMipMap,
				Texture.MagnificationFilter.Bilinear);
		t.setWrap(Texture.WrapMode.BorderClamp);
		ts.setTexture(t);
		ts.setEnabled(true);
		
		//floor
		Track track = new Track(trackNode.getChild(0), new TrackData(), new Vector3f(0f,-16,0f), ts);
        
        this.rootNode.attachChild(track.getPhysicsNode());
        track.getPhysicsNode().updateRenderState();
        pSpace.add(track.getPhysicsNode());
        
        entities.add(track);
        
        //Key Mappings
        KeyBindingManager.getKeyBindingManager().add("accelerate", KeyInput.KEY_NUMPAD8);
        KeyBindingManager.getKeyBindingManager().add("steer_right", KeyInput.KEY_NUMPAD6);
        KeyBindingManager.getKeyBindingManager().add("steer_left", KeyInput.KEY_NUMPAD4);
        KeyBindingManager.getKeyBindingManager().add("brake", KeyInput.KEY_NUMPAD2);
        
	}
	
	@Override
	protected void simpleUpdate() {
		super.simpleUpdate();
		
		for(Entity entity : entities){
			entity.update();
		}

		pSpace.update(this.tpf);
	}

	public void collision(CollisionEvent arg0) {
		// TODO Auto-generated method stub
		
	}
}
