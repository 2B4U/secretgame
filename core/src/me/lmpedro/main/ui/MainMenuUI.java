package me.lmpedro.main.ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import me.lmpedro.main.Main;
import me.lmpedro.main.audio.AudioType;
import me.lmpedro.main.screens.ScreenType;

public class MainMenuUI extends Table {
    private final TextButton settings;
    private final TextButton singlePlayer;
    private final TextButton exit;

    public MainMenuUI(final Main context) {
        super(context.getSkin());
        setFillParent(true);

        singlePlayer = new TextButton("[White]Single Player", getSkin(), "huge");
        singlePlayer.getLabel().setWrap(true);
        singlePlayer.getLabel().setFontScale(1.5f);
        singlePlayer.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                context.setScreen(ScreenType.GAME);
                context.getAudioManager().playAudio(AudioType.SELECT);
            }
        });

        settings = new TextButton("[White]Settings", getSkin(), "huge");
        settings.getLabel().setWrap(true);
        settings.getLabel().setFontScale(1.5f);
        /*        settings.pad(20, 40, 33, 40);*/


        exit = new TextButton("[Red]Exit", getSkin(), "huge");
        exit.getLabel().setWrap(true);
        exit.getLabel().setFontScale(1.5f);
        /*        exit.pad(20, 40, 33, 40);*/
        exit.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                context.getAudioManager().playAudio(AudioType.SELECT);
                Gdx.app.exit();
            }
        });

        add(singlePlayer).expandX().fillX().bottom().pad(10, 475, 10, 475).row();
        add(settings).expandX().fillX().bottom().pad(10, 475, 10, 475).row();
        add(exit).expandX().fillX().bottom().pad(10, 475, 10, 475);
        bottom();

        setDebug(true, true);
    }

}
