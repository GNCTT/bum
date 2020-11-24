package GNCTT.entity;

import GNCTT.graphics.Animation;
import GNCTT.graphics.Sprite;
import GNCTT.until.AABB;
import GNCTT.until.Vector2f;

import java.awt.*;
import java.awt.image.BufferedImage;

public abstract class Entity {

    private final int UP = 3;
    private final int DOWN = 2;
    private final int RIGHT = 0;
    private final int LEFT = 1;
    protected int currentAnimation;

    protected Animation ani;
    protected Sprite sprite;
    protected Vector2f pos;
    protected int size;

    protected boolean up;
    protected boolean down;
    protected boolean right;
    protected boolean left;
    protected boolean attack;
    protected int attckSpeed;
    protected int attackDuration;

    protected  float dx;
    protected float dy;

    protected float maxSpeed = 4f;
    protected float acc = 3f;
    protected float deacc = 0.3f;

    protected AABB hitBounds;
    protected AABB bounds;

    public Entity (Sprite sprite, Vector2f orgin, int size) {
        this.sprite = sprite;
        pos = orgin;
        this.size = size;

        bounds = new AABB(orgin, size, size);
        hitBounds = new AABB(new Vector2f(orgin.x + (size / 2), orgin.y), size, size);

        ani = new Animation();
        setAnimation(RIGHT, sprite.getSpriteArray(RIGHT), 10);
    }

    public void setSprite(Sprite sprite) {
        this.sprite = sprite;
    }

    public void setSize(int i) {
        this.size = i;
    }

    public void setMaxSpeed(float maxSpeed) {
        this.maxSpeed = maxSpeed;
    }

    public void setAcc(float acc) {
        this.acc = acc;
    }

    public void setDeacc(float deacc) {
        this.deacc = deacc;
    }

    public AABB getBounds () {
        return bounds;
    }



    public int getSize() {
        return size;
    }

    public Animation getAnimation() {
        return ani;
    }

    public void setAnimation(int i, BufferedImage[] frames, int delay) {
        currentAnimation = i;
        ani.setFrames(frames);
        ani.setDelay(delay);
    }

    public void animate() {
        if (up) {
            if (currentAnimation != UP || ani.getDelay() == -1)  {
                setAnimation(UP, sprite.getSpriteArray(UP), 5);
            }
        }
        else if (down) {
            if (currentAnimation != DOWN || ani.getDelay() == -1)  {
                setAnimation(DOWN, sprite.getSpriteArray(DOWN), 5);
            }
        }
        else if (left) {
            if (currentAnimation != LEFT || ani.getDelay() == -1)  {
                setAnimation(LEFT, sprite.getSpriteArray(LEFT), 5);
            }
        }
        else if (right) {
            if (currentAnimation != RIGHT || ani.getDelay() == -1)  {
                setAnimation(RIGHT, sprite.getSpriteArray(RIGHT), 5);
            }
        }
        else {
            setAnimation(currentAnimation, sprite.getSpriteArray(currentAnimation), -1);
        }
    }

    private void setHitBoxDirerction() {
        if (up) {
            hitBounds.setYoffset(-size / 2);
            hitBounds.setXoffset(-size / 2);
        }
        else if (down) {
            hitBounds.setYoffset(size / 2);
            hitBounds.setXoffset(-size / 2);
        }
        else if (left) {
            hitBounds.setYoffset(0);
            hitBounds.setXoffset(-size);
        }
        else if (right) {
            hitBounds.setXoffset(0);
            hitBounds.setYoffset(0);
        }
    }

    public void update() {
        animate();
        setHitBoxDirerction();
        ani.update();
    }

    public abstract void render(Graphics2D g);
}
