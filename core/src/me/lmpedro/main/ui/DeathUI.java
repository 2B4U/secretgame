package me.lmpedro.main.ui;

import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import me.lmpedro.main.Main;
import me.lmpedro.main.ecs.components.PlayerComponent;

public class DeathUI extends Table {
    private final Label score;
    private final Label text;

    /*private final TextButton mainmenu;*/

    public DeathUI(Main context) {
        super(context.getSkin());
        setFillParent(true);


        text = new Label("Oh Dear", getSkin(),"huge");
        score = new Label("Score", getSkin(), "huge");
        text.setFontScale(3);
        score.setFontScale(3);


        add(text);
        row();
        add(score);
        row();
        /*setDebug(true, true);*/
    }

    public void updateUi(float delta, Main context){
        text.setText("Oh dear, your dead haha");
        score.setText("Your Score: " + context.lastScore);
    }
}
