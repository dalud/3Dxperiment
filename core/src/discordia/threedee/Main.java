package discordia.threedee;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.loaders.ModelLoader;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.PerspectiveCamera;
import com.badlogic.gdx.graphics.g3d.Environment;
import com.badlogic.gdx.graphics.g3d.Model;
import com.badlogic.gdx.graphics.g3d.ModelBatch;
import com.badlogic.gdx.graphics.g3d.ModelInstance;
import com.badlogic.gdx.graphics.g3d.attributes.ColorAttribute;
import com.badlogic.gdx.graphics.g3d.environment.DirectionalLight;
import com.badlogic.gdx.graphics.g3d.loader.G3dModelLoader;
import com.badlogic.gdx.graphics.g3d.loader.ObjLoader;
import com.badlogic.gdx.utils.UBJsonReader;


public class Main extends ApplicationAdapter {
	PerspectiveCamera cam;
	Model ground, cubism, sky;
	ModelInstance groundIns, cubismIns, skyIns;
	ModelBatch modelBatch;
	Environment environment;
	MyInput input;
	Music sappy;

	@Override
	public void create () {
		cam = new PerspectiveCamera(67, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		cam.position.set(1, 2.1f, 5);
		cam.lookAt(0, 3, 0);
		cam.near = .1f;
		cam.far = 500f;
		cam.update();

		input = new MyInput(cam);
		Gdx.input.setInputProcessor(input);

		//ModelLoader loader = new ObjLoader();
		ModelLoader g3dLoader = new G3dModelLoader(new UBJsonReader());

		ground = g3dLoader.loadModel(Gdx.files.internal("ground/ground.g3db"));
		groundIns = new ModelInstance(ground);
		groundIns.transform.setToTranslation(0, -.3f, 0);

		cubism = g3dLoader.loadModel(Gdx.files.internal("cubism/cubism.g3db"));
		cubismIns = new ModelInstance(cubism);
		cubismIns.transform.setToTranslation(0, 3.5f, 0);

		sky = g3dLoader.loadModel(Gdx.files.internal("sky/sky.g3db"));
		skyIns = new ModelInstance(sky);
		int scale = 4;
		skyIns.nodes.get(0).scale.set(scale, scale, scale);
		skyIns.calculateTransforms();

		modelBatch = new ModelBatch();

		environment = new Environment();
		environment.set(new ColorAttribute(ColorAttribute.AmbientLight, 0.4f, 0.4f, 0.4f, 1f));
		environment.add(new DirectionalLight().set(0.7f, 0.7f, 0.7f, -1f, -0.8f, -0.2f));
		environment.add(new DirectionalLight().set(0, .5f, 0, -1, .3f, 0));

		sappy = Gdx.audio.newMusic(Gdx.files.internal("sounds/sappy.mp3"));
		sappy.setLooping(true);
		sappy.play();
	}

	@Override
	public void render () {
		Gdx.gl.glViewport(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT);

		input.poll();
		cam.update();
		cubismIns.transform.rotate(0, 1, 0, .2f);

		modelBatch.begin(cam);
		modelBatch.render(groundIns, environment);
		modelBatch.render(skyIns);
		modelBatch.render(cubismIns, environment);
		modelBatch.end();
	}
	
	@Override
	public void dispose () {
		sky.dispose();
		ground.dispose();
		cubism.dispose();
		modelBatch.dispose();
	}
}