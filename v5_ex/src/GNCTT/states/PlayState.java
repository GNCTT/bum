package GNCTT.states;

import GNCTT.GamePanel;
import GNCTT.entity.Player;
import GNCTT.graphics.Font;
import GNCTT.graphics.Sprite;
import GNCTT.titles.TileManager;
import GNCTT.until.KeyHandler;
import GNCTT.until.MouseHandler;
import GNCTT.until.Vector2f;

import java.awt.*;

public class PlayState extends GameState {
    private Font font;
    private Player player;
    private TileManager tm;

    public static Vector2f map;

    public PlayState (GameStateManager gsm) {
        super(gsm);
        map = new Vector2f();
        Vector2f.setWorldVar(map.x, map.y);

        tm = new TileManager("tile/tilemap.xml");
        font = new Font("font/Font.png", 10, 10);
        player = new Player(new Sprite("entity/linkFormatted.png"), new Vector2f(0 + GamePanel.width / 2 - 32, 0 + GamePanel.height / 2 - 32), 64);
    }
    public void update() {
        Vector2f.setWorldVar(map.x, map.y);
        player.update();
    }

    public void input(MouseHandler mouse, KeyHandler key) {
        player.input(mouse, key);
    }

    public void render(Graphics2D g) {
        tm.render(g);
        Sprite.drawArray(g, font, GamePanel.oldFrameCount + " FPS", new Vector2f(GamePanel.width - 192, 32), 32 , 32, 32, 0);
        player.render(g);
    }
}
