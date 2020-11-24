package GNCTT.titles;

import GNCTT.graphics.Sprite;
import GNCTT.titles.blocks.Block;
import GNCTT.titles.blocks.HoleBlock;
import GNCTT.titles.blocks.ObjBlock;
import GNCTT.until.Vector2f;

import java.awt.*;
import java.util.HashMap;

public class TileMapObj extends TileMap {

    public static HashMap<String, Block> tmo_block;
    public static Block[] event_blocks;

    public TileMapObj(String data, Sprite sprite, int width, int height, int tileWidth, int tileHeight, int tileColumns) {
        Block tempBlock;
        event_blocks = new Block[width * height];
        tmo_block = new HashMap<String, Block>();

        String [] block = data.split(",");
        for ( int i = 0; i < (width * height); i++) {
            int temp = Integer.parseInt(block[i].replaceAll("\\s+", ""));
            if (temp != 0 ) {
                if (temp == 172) {
                    tempBlock = new HoleBlock(sprite.getSprite((int) ((temp - 1) % tileColumns), (int) ((temp - 1) / tileColumns) ), new Vector2f((int) (i % width) * tileWidth, (int) (i / height) * tileHeight), tileWidth, tileHeight );
                }
                else {
                    tempBlock = new ObjBlock(sprite.getSprite((int) ((temp - 1) % tileColumns), (int) ((temp - 1) / tileColumns) ), new Vector2f((int) (i % width) * tileWidth, (int) (i / height) * tileHeight), tileWidth, tileHeight );
                }
                tmo_block.put(String.valueOf((int) (i % width)) + "," + String.valueOf((int) ( i % height)), tempBlock);
                event_blocks[i] = tempBlock;
            }
        }
    }

    public void render(Graphics2D g) {
        for (Block block: tmo_block.values()) {
            block.render(g);
        }
    }
}
