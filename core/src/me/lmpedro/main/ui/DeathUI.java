package me.lmpedro.main.ui;

import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import me.lmpedro.main.Main;
import me.lmpedro.main.ecs.ECSEngine;
import me.lmpedro.main.ecs.components.PlayerComponent;

public class DeathUI extends Table {
    private final Label score;
    private final Label text;
    private int zcore;
    /*private final TextButton mainmenu;*/

    public DeathUI(Main context) {
        super(context.getSkin());
        zcore = context.getWorldFactory().player.getComponent(PlayerComponent.class).score;
        setFillParent(true);

        text = new Label("Oh Dear, Your Dead Haha", getSkin(),"huge");
        score = new Label("Your Score", getSkin(), "huge");

        add(text);
        row();
        add(score);
        row();
        setDebug(true, true);
    }

    public void updateUi(float delta, Main context){
        score.setText("[Red]Your Score: " + zcore);
    }
}
