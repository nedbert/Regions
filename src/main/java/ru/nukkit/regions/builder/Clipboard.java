package ru.nukkit.regions.builder;

import cn.nukkit.Player;
import cn.nukkit.Server;
import cn.nukkit.block.Block;
import cn.nukkit.level.Location;
import ru.nukkit.regions.Regions;
import ru.nukkit.regions.RegionsPlugin;
import ru.nukkit.regions.areas.Area;

import java.util.LinkedList;
import java.util.List;

public class Clipboard {

    String playerName;
    Location playerLocation;
    Location minLocation;
    List<Block> blocks;

    public Clipboard() {
        this.playerName = "";
        this.playerLocation = null;
        this.minLocation = null;
        this.blocks = new LinkedList<Block>();
    }

    public Clipboard(Player player) {
        this.playerName = player.getName();
        this.playerLocation = player.getLocation();
    }

    public Clipboard(Player player, List<Block> blocks, Location minLocation) {
        this(blocks, minLocation);
        this.playerName = player.getName();
        this.playerLocation = player.getLocation();
    }

    public Clipboard(List<Block> blocks, Location minLocation) {
        this();
        this.minLocation = minLocation;
        this.blocks = new LinkedList<Block>();
        if (this.minLocation == null) {
            blocks.addAll(blocks);
        } else {
            for (Block b : blocks)
                this.blocks.add(Block.get(b.getId(), b.getDamage(), b.getLocation().subtract(this.minLocation)));
        }
    }

    public Clipboard(Area area) {
        this();
        this.minLocation = area.getMin();
        for (int chX = area.getChunkX1(); chX <= area.getChunkX2(); chX++)
            for (int chZ = area.getChunkZ1(); chZ <= area.getChunkZ2(); chZ++)
                for (int y = area.getY1(); y <= area.getY2(); y++) {
                    for (int x = 0; x < 16; x++)
                        for (int z = 0; z < 16; z++) {
                            int blockX = x + (chX << 4);
                            int blockZ = z + (chZ << 4);
                            if (blockX < area.getX1() || blockX > area.getX2()) continue;
                            if (blockZ < area.getZ1() || blockZ > area.getZ2()) continue;
                        }
                }
    }

    public Clipboard(Location loc1, Location loc2) {
        this(new Area(loc1, loc2));
    }


    public void add(Block block) {
        this.blocks.add(minLocation == null ? block : Block.get(block.getId(), block.getDamage(), block.getLocation().subtract(minLocation)));
    }

    public void add(Block... block) {
        for (Block b : block) add(b);
    }

    public void paste() {
        Regions.getBuilder().setBlock(playerName, blocks);
    }

    public static Clipboard createUndoClipBoard(String playerName) {
        if (!RegionsPlugin.getCfg().builderUseUndo) return null;
        if (playerName == null || playerName.isEmpty()) return null;
        Player player = Server.getInstance().getPlayerExact(playerName);
        if (player == null || !player.isOnline()) return null;
        if (!player.hasPermission("regions.undo")) return null;
        Clipboard clip = new Clipboard();
        clip.playerName = player.getName();
        return clip;
    }

    public void paste(Location loc, boolean asPlayer) {
        Location start = asPlayer && this.playerLocation != null ? loc.add(this.minLocation.subtract(this.playerLocation)) : loc;
        List<Block> blocks = new LinkedList<Block>();
        for (Block block : this.blocks) {
            blocks.add(Block.get(block.getId(), block.getDamage(), start.add(block)));
        }
        Regions.getBuilder().setBlock(playerName, blocks);
    }

    public void remove(List<Block> blocks) {
        blocks.removeAll(blocks);
    }

    public void remove(Clipboard removeClip) {
        blocks.removeAll(removeClip.blocks);
    }

}
