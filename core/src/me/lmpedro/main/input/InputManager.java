package me.lmpedro.main.input;

import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.utils.Array;

public class InputManager implements InputProcessor {
    private final GameKeys[] keyMapping;
    private final boolean[] keyState;
    private final Array<InputListener> listeners;
    public boolean keyPressed;

    public InputManager() {
        this.keyMapping = new GameKeys[256];
        for (final GameKeys gameKey : GameKeys.values()) {
            for (final int code : gameKey.keyCode) {
                keyMapping[code] = gameKey;
            }
        }
        keyState = new boolean[GameKeys.values().length];
        listeners = new Array<InputListener>();
    }

    public void addInputListener(final InputListener listener) {
        listeners.add(listener);
    }

    public void removeInputListener(final InputListener listener) {
        listeners.removeValue(listener, true);
    }

    @Override
    public boolean keyDown(final int keycode) {
        final GameKeys gameKey = keyMapping[keycode];
        if (gameKey == null) {
            return false;
        }
        notifyKeyDown(gameKey);
        return false;
    }

    public void notifyKeyDown(final GameKeys gameKey) {
        keyState[gameKey.ordinal()] = true;
        for (final InputListener listener : listeners) {
            listener.keyPressed(this, gameKey);
        }
    }

    @Override
    public boolean keyUp(final int keycode) {
        final GameKeys gameKey = keyMapping[keycode];
        keyPressed = false;
        if (gameKey == null) {
            return false;
        }
        notifyKeyUp(gameKey);
        return false;
    }

    public void notifyKeyUp(final GameKeys gameKey) {
        keyState[gameKey.ordinal()] = false;
        for (final InputListener listener : listeners) {
            listener.keyUp(this, gameKey);
        }
    }

    public boolean isKeyDown(final GameKeys gameKey) {
        return keyState[gameKey.ordinal()];
    }

    @Override
    public boolean keyTyped(char character) {
        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        return false;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        return false;
    }

    @Override
    public boolean scrolled(float amountX, float amountY) {
        return false;
    }
}
