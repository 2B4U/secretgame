package me.lmpedro.main.ui;

import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.ProgressBar;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import me.lmpedro.main.Main;

public class LoadingUI extends Table {
    private final ProgressBar progressBar;
    private final TextButton textButton;
    private final TextButton pressAnyKey;

    public LoadingUI(final Main context) {
        super(context.getSkin());
        setFillParent(true);


        progressBar = new ProgressBar(0, 1, 0.1f, false, getSkin(), "default");
        progressBar.setAnimateDuration(5);


        textButton = new TextButton("[Red]Loading...", getSkin(), "huge");
        textButton.getLabel().setWrap(true);
        textButton.getLabel().setFontScale(2);

        pressAnyKey = new TextButton("[Red]Press Any Key", getSkin(), "normal");
        pressAnyKey.getLabel().setWrap(true);
        pressAnyKey.setVisible(false);
        pressAnyKey.getLabel().setFontScale(3);

        add(pressAnyKey).expand().fillX().bottom().row();
        add(textButton).expandX().fillX().bottom().row();
        add(progressBar).expandX().fillX().bottom().pad(50, 300, 50, 300);
        setDebug(true, true);


    }

    public void setProgress(final float progress) {
        progressBar.setValue(progress);
        if (progress < 1) {
            textButton.addAction(Actions.forever(Actions.sequence(Actions.alpha(1, 0.5f), Actions.alpha(0, 0.5f))));
        } else if (progress >= 1 && !pressAnyKey.isVisible()) {
            pressAnyKey.setVisible(true);
            pressAnyKey.addAction(Actions.forever(Actions.sequence(Actions.alpha(1, 0.7f), Actions.alpha(0, 0.7f))));
            pressAnyKey.addAction(Actions.moveBy(0, -55, 2));
            textButton.setVisible(false);
            progressBar.setVisible(false);
        }
    }
}
