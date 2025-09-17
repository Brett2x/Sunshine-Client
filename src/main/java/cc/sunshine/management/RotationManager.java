package cc.sunshine.management;

import cc.sunshine.ExampleMod;
import cc.sunshine.eventbus.impl.player.*;
import cc.sunshine.eventbus.interfaces.IHandler;
import cc.sunshine.eventbus.interfaces.Listener;
import cc.sunshine.eventbus.interfaces.SubscribeEvent;
import cc.sunshine.feature.property.impl.interfaces.IEnumProperty;
import cc.sunshine.utils.player.MoveUtil;
import lombok.experimental.UtilityClass;
import net.minecraft.util.math.Vec2f;

public class RotationManager implements IHandler {
    public RotationManager() {
        ExampleMod.INSTANCE.getEventBus().registerHandler(this);
    }

    private Vec2f nextRotation;
    private MovementFixMode movementFix = MovementFixMode.OFF;
    private int ticks;

    public void setNextRotation(Vec2f rotation) {
        if(nextRotation == null) {
            nextRotation = rotation;
        }
    }

    public void setNextRotation(Vec2f rotation, MovementFixMode movementFix) {
        if(nextRotation == null) {
            nextRotation = rotation;
            this.movementFix = movementFix;
        }
    }

    public void forceNextRotation(Vec2f rotation) {
        nextRotation = rotation;
    }

    public void forceNextRotation(Vec2f rotation, MovementFixMode movementFix) {
        nextRotation = rotation;
        this.movementFix = movementFix;
    }

    @SubscribeEvent
    private final Listener<PlayerNetworkUpdateEvent> playerNetworkUpdateEventListener = event -> {
        if(nextRotation != null) {
            event.setRotation(nextRotation);
            nextRotation = null;
            movementFix = MovementFixMode.OFF;
        }
    };

    @SubscribeEvent
    private final Listener<PlayerMovementInputEvent> playerMoveEventListener = event -> {
        if(movementFix == MovementFixMode.ADVANCED) {
            MoveUtil.fixMovement(nextRotation.x);
        }
    };

    @SubscribeEvent
    private final Listener<PlayerStrafeEvent> playerStrafeEventListener = event -> {
        if(movementFix != MovementFixMode.OFF) {
            event.setYaw(nextRotation.x);
        }
    };

    @SubscribeEvent
    private final Listener<PlayerJumpEvent> playerJumpEventListener = event -> {
        if(movementFix != MovementFixMode.OFF) {
            event.setYaw(nextRotation.x);
        }
    };

    public enum MovementFixMode implements IEnumProperty {
        OFF("Off"),
        BASIC("Basic"),
        ADVANCED("Advanced");

        private final String name;

        MovementFixMode(String name) {
            this.name = name;
        }

        @Override
        public String getName() {
            return name;
        }
    }
}
