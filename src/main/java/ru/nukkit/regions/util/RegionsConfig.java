package ru.nukkit.regions.util;

import cn.nukkit.plugin.Plugin;
import cn.nukkit.utils.SimpleConfig;
import ru.nukkit.regions.builder.Builder;
import ru.nukkit.regions.builder.BuilderQueed;
import ru.nukkit.regions.builder.SimpleBuilder;

public class RegionsConfig extends SimpleConfig {

    @Path("general.language")
    public String language = "default";

    @Path("general.language-save")
    public boolean saveLanguage = false;

    @Path("general.debug-mode")
    public boolean debugMode = false;

    // Selection particles
    @Path("selection.particles.enable")
    public boolean selectionShow;

    @Path("selection.particles.draw-wall")
    public boolean selectionDrawWall = true;

    @Path("selection.particles.solid")
    public boolean selectionSolidWall = false;

    @Path("selection.particles.replay-interval-ticks")
    public int selectionTick = 10;

    @Path("selection.particles.limit-amount")
    public int selectionLimitAmount = 500;

    @Path("selection.particles.limit-distance")
    public int particleDistance = 64;

    @Path("selection.particles.show-intersections")
    public boolean selectionShowIntersections = true;

    // Player Move detections
    @Path("player-move.enable-detection")
    public boolean enablePlayerMove = true;

    @Path("player-move.use-player-move-event")
    public boolean usePlayerMoveEvent = true;

    @Path("player-move.recheck-interval-ticks")
    public int playerMoveRechek = 10;

    @Path("player-move.provide-custom-events")
    public boolean enableCustomEvents = false;


    // Claim command configuration
    @Path("claim.max-regions-per-player")
    public int maxRegionPerPlayer = 5;

    @Path("claim.max-claim-volume")
    public int maxClaimVolume = 10000;

    @Path("claim.claim-only-existing-regions")
    public boolean claimOnlyExisting = false;

    @Path("claim.claim-only-when-player-inside-region")
    public boolean claimOnlyInside = false;

    @Path("claim.allow-to-instersect-with-other-regions")
    public boolean intersectionsAllowed = false;


    /*
     * Builder configuration
     *
     *
     **/

    @Path("builder.type")
    private String builderType = "SIMPLE"; // SIMPLE / QUEUE

    @Path("builder.log.volume-to-inform")
    public int builderLogAmount = 5000;

    @Path("builder.queue.max-time-per-tick-ms")
    public long builderTicks = 30;

    @Path("enable-undo-operations")
    public boolean builderUseUndo = true;


    public RegionsConfig(Plugin plugin) {
        super(plugin);
    }

    public Builder getConfiguredBuilder() {
        if (this.builderType.equalsIgnoreCase("QUEUE")) return new BuilderQueed();
        return new SimpleBuilder();
    }
}