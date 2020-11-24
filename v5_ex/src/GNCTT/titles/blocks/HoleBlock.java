package GNCTT.titles.blocks;

import GNCTT.until.AABB;
import GNCTT.until.Vector2f;

import java.awt.*;
import java.awt.image.BufferedImage;

public class HoleBlock extends Block{
    public HoleBlock(BufferedImage img, Vector2f pos, int w, int h) {
        super(img, pos, w, h);
    }
    public boolean update(AABB p) {
        return false;
    }
    public void render(Graphics2D g) {
        super.render(g);
        g.setColor(Color.green);
        g.drawRect((int) pos.getWorldVar().x, (int)pos.getWorldVar().y, w, h);
    }
}
