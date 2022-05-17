package me.lmpedro.main.input;

public interface InputListener {
    void keyPressed(final InputManager manager, final GameKeys key);

    void keyUp(final InputManager manager, final GameKeys key);
}
