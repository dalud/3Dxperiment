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
    int width, height, action, finger, XAmplitude, YAmplitude, minYAmp, keysXAmp, keysYAmp;
    boolean turn;
    int[] pointerXCoords, pointerYCoords;

    public MyInput(PerspectiveCamera cam){
        this.cam = cam;
        width = Gdx.graphics.getWidth();
        height = Gdx.graphics.getHeight();
        pointerXCoords = new int[2];
        pointerYCoords = new int[2];
        minYAmp = 100;
        keysXAmp = 210;
        keysYAmp = 300;
    }

    public void poll(){
        if(finger < 1) {
            turn = false;
            YAmplitude = XAmplitude = 0;
        }

        //NAPPI POHJASSA (ei toimi atm, koska Amp)
        if(Gdx.input.isKeyPressed(Input.Keys.W)) {
            action = 1;
            YAmplitude = keysYAmp;
        }
        if(Gdx.input.isKeyPressed(Input.Keys.A)) {
            XAmplitude = -keysYAmp;
            turn = true;
        }
        if(Gdx.input.isKeyPressed(Input.Keys.S)) {
            action = 1;
            YAmplitude = -keysYAmp;
        }
        if(Gdx.input.isKeyPressed(Input.Keys.D)) {
            XAmplitude = keysXAmp;
            turn = true;
        }

        //TOUCH
        if(turn) move(2);
        move(action);
    }

    @Override
    public boolean keyDown(int keycode) {
        return false;
    }

    private void move(int direction) {
        //1 = FORWARD or BACK
        //2 = TURN LEFT or RIGHT

        switch (direction) {
            case 1:
                if(cam.position.len2() < 130) cam.translate(cam.direction.x*YAmplitude/4000, 0, cam.direction.z*YAmplitude/4000);
                else StepBack();
                break;
            case 2:
                cam.rotate((float)XAmplitude/200, 0, -1, 0);
                break;
            default:
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
        XAmplitude = YAmplitude = action = 0;
        turn = false;

        return false;
    }

    @Override
    public boolean keyTyped(char character) {
        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        finger++;
        YAmplitude = minYAmp;
        action = 1;

        pointerXCoords[pointer] = screenX;
        pointerYCoords[pointer] = screenY;

        return false;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        finger--;

        if(finger < 1) {
            action = 0;
            turn = false;
        }

        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        XAmplitude = screenX - pointerXCoords[pointer];

        int deltaY = pointerYCoords[pointer] - screenY;
        if(deltaY > 0) YAmplitude = minYAmp + deltaY;
        else if(deltaY < -10) YAmplitude = deltaY;

        if(screenY != 0) action = 1;
        if(screenX != 0) turn = true;

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