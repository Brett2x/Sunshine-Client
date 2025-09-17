package cc.sunshine.feature.module.impl.visual;

import cc.sunshine.eventbus.impl.PlayerUpdateEvent;
import cc.sunshine.eventbus.interfaces.Listener;
import cc.sunshine.eventbus.interfaces.SubscribeEvent;
import cc.sunshine.feature.module.AbstractModule;
import cc.sunshine.feature.module.enums.ModuleCategory;
import cc.sunshine.feature.module.interfaces.ModuleData;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

@ModuleData(name = "Alt View", description = "Allows you to view the perspective of other players", category = ModuleCategory.VISUAL)
public class AltViewModule extends AbstractModule {


    @Override
    protected void onDisable() {

        mc.setCameraEntity(mc.player);

        super.onDisable();
    }

    @SubscribeEvent
    private final Listener<PlayerUpdateEvent> playerUpdateEventListener = event -> {
        if(mc.world == null || mc.player == null) return;

        Entity targetEntity;

        List<Entity> entityList = new ArrayList<>();
        mc.world.getEntities().forEach(entityList::add);
        entityList.removeIf(entity -> entity == mc.player || !(entity instanceof PlayerEntity));
        entityList.sort(Comparator.comparingDouble(e -> e.distanceTo(mc.player)));
        Collections.reverse(entityList);

        int index = 0;

        if(mc.player.input.pressingRight && index < entityList.size() - 1)
            index++;
        if(mc.player.input.pressingLeft && index != 0)
            index--;

        if(index < entityList.size() - 1)
            index = entityList.size() - 1;

        if(entityList.size() > 0)
            targetEntity = entityList.get(index);
        else
            targetEntity = mc.player;



        mc.setCameraEntity(targetEntity);

    };

}
