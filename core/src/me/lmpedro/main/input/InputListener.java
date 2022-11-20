package me.lmpedro.main.input;

public interface InputListener {
    void keyPressed(final InputManager manager, final GameKeys key);

    void keyUp(final InputManager manager, final GameKeys key);

    boolean touchDown(int screenX, int screenY, int pointer, int button);

    boolean touchUp(int screenX, int screenY, int pointer, int button);

    boolean mouseMoved(int screenX, int screenY);
}
