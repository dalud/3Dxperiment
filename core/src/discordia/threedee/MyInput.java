package discordia.threedee;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.PerspectiveCamera;

/**
 * Created by Dalud on 25.2.2017.
 */

public class MyInput implements InputProcessor {
    PerspectiveCamera cam;
    int Xdown, Ydown;

    public MyInput(PerspectiveCamera cam){
        this.cam = cam;
    }

    public void poll(){
        if(Gdx.input.isKeyPressed(Input.Keys.W)) move(1);
        if(Gdx.input.isKeyPressed(Input.Keys.S)) move(3);
        if(Gdx.input.isKeyPressed(Input.Keys.A)) cam.rotate(1, 0, 1, 0);
        if(Gdx.input.isKeyPressed(Input.Keys.D)) cam.rotate(1, 0, -1, 0);
    }

    @Override
    public boolean keyDown(int keycode) {
        return false;
    }

    private void move(int direction) {
        //1 = FORWARD
        //3 = BACK

        switch (direction) {
            case 1:
                if(cam.position.len2() < 130) cam.translate(cam.direction.x / 15, 0, cam.direction.z / 15);
                else StepBack();
                break;
            case 3:
                if(cam.position.len2() < 130) cam.translate(-cam.direction.x / 15, 0, -cam.direction.z / 15);
                else StepBack();
                break;
        }
    }

    private void StepBack() {
        float step = .001f, x = 0, z = 0;

        if(cam.position.x > 0) x = -step;
        else if(cam.position.x < 0) x = step;

        if(cam.position.z > 0) z = -step;
        else if(cam.position.z < 0) z = step;

        cam.translate(x, 0, z);
    }


    @Override
    public boolean keyUp(int keycode) {

        return false;
    }

    @Override
    public boolean keyTyped(char character) {
        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        Xdown = screenX;
        Ydown = screenY;
        return false;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {


        float deltaX = (float)(screenX - Xdown);
        float deltaY = (float)(screenY - Ydown);

        cam.rotate(1, 0, deltaX/10, 0);
        if(deltaY > 0) move(1);
        else if(deltaY < 0) move(3);

        Xdown = screenX;
        Ydown = screenY;

        return false;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        return false;
    }

    @Override
    public boolean scrolled(int amount) {
        return false;
    }
}
