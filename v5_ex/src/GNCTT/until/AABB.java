package GNCTT.until;

import GNCTT.graphics.Sprite;
import GNCTT.entity.Entity;
import GNCTT.titles.TileMap;
import GNCTT.titles.TileMapObj;

public class AABB {
    private Vector2f pos;
    private float xoffset = 0;
    private float yoffset = 0;
    private float w;
    private float h;
    private float r;
    private int size;
    private Entity e;

    public AABB(Vector2f pos, int w, int h) {
        this.pos = pos;
        this.w = w;;
        this.h = h;

        size = Math.max(w, h);
    }

    public AABB(Vector2f pos, int r, Entity e) {
        this.pos = pos;
        this.r = r;
        size = r;
        this.e = e;

    }

    public float getXoffset() {
        return xoffset;
    }

    public float getYoffset() {
        return yoffset;
    }

    public Vector2f getPos() {
        return pos;
    }

    public float getRadius () {
        return r;
    }

    public float getWidth() {
        return w;
    }
    public float getHeight() {
        return h;
    }
    public void setBox(Vector2f pos, int w, int h) {
        this.pos = pos;
        this.w = w;
        this.h = h;

        size = Math.max(w, h);
    }

    public void setCircle(Vector2f pos, int r) {
        this.pos = pos;
        this.r = r;
    }

    public void setWidth(float w) {
        this.w = w;
    }

    public void setHeight(float h) {
        this.h = h;
    }

    public void setXoffset(float xoffset) {
        this.xoffset = xoffset;
    }

    public void setYoffset(float yoffset) {
        this.yoffset = yoffset;
    }

    public boolean collides(AABB bBox) {
        float ax = ((pos.getWorldVar().x + (xoffset)) + (w / 2));
        float ay = ((pos.getWorldVar().y + (yoffset)) + (h / 2));
        float bx = ((bBox.pos.getWorldVar().x + (bBox.xoffset)) + (w / 2));
        float by = ((bBox.pos.getWorldVar().y + (bBox.yoffset)) + (h / 2));

        if (Math.abs(ax - bx) < (this.w / 2) + (bBox.w / 2)) {
            if (Math.abs(ay - by) < (this.h / 2) + (bBox.h / 2 )) {
                return true;
            }
        }
        return false;
    }

    public boolean colCircleBox(AABB aBox) {
        float cx = (float)(pos.getWorldVar().x + r / Math.sqrt(2) - e.getSize() / Math.sqrt(2) );
        float cy = (float)(pos.getWorldVar().y + r / Math.sqrt(2) - e.getSize() / Math.sqrt(2) );

        float xDelta = cx - Math.max(aBox.pos.getWorldVar().x + (aBox.getWidth() / 2), Math.min(cx, aBox.pos.getWorldVar().x));
        float yDelta = cy - Math.max(aBox.pos.getWorldVar().y + (aBox.getWidth() / 2), Math.min(cy, aBox.pos.getWorldVar().y));

        if ((xDelta * xDelta + yDelta * yDelta) < ((this.r / Math.sqrt(2) * (this.r / Math.sqrt(2)) ))) {
            return true;
        }

        return false;
    }

    public Boolean collisiontile(float ax, float ay) {
        for ( int c = 0; c < 4; c++) {
            int xt = (int) ( (pos.x + ax) + (c % 2) * w * xoffset) / 64;
            int yt = (int) ( (pos.y + ay) + (c / 2) * h * yoffset) / 64;

            if (TileMapObj.tmo_block.containsKey(String.valueOf(xt) + "," + String.valueOf(yt))) {
                return TileMapObj.tmo_block.get(String.valueOf(xt) + "," + String.valueOf(yt)).update(this);
            }
        }
        return false;
    }
}
