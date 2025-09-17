package cc.sunshine.eventbus.impl.player;

import cc.sunshine.eventbus.Event;
import net.minecraft.entity.Entity;

public final class PlayerAttackEvent extends Event {
    private final Entity entity;

    public PlayerAttackEvent(Entity entity) {
        this.entity = entity;
    }

    public Entity getEntity() {
        return entity;
    }
}
