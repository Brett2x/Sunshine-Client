package cc.sunshine.feature.module.impl.combat;

import cc.sunshine.ExampleMod;
import cc.sunshine.eventbus.impl.PlayerUpdateEvent;
import cc.sunshine.eventbus.interfaces.Listener;
import cc.sunshine.eventbus.interfaces.SubscribeEvent;
import cc.sunshine.feature.module.AbstractModule;
import cc.sunshine.feature.module.enums.ModuleCategory;
import cc.sunshine.feature.module.interfaces.ModuleData;
import cc.sunshine.management.RotationManager;
import cc.sunshine.mixin.interfaces.IMouseMixin;
import cc.sunshine.utils.rotation.RotationUtil;
import com.google.common.base.Stopwatch;
import net.minecraft.client.Mouse;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.network.packet.c2s.play.PlayerInputC2SPacket;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

@ModuleData(name = "Auto Botter", description = "Rapes bwhub players", category = ModuleCategory.COMBAT)
public class AutoBotterModule extends AbstractModule {

    private final Stopwatch timer = Stopwatch.createStarted();

    @SubscribeEvent
    private final Listener<PlayerUpdateEvent> playerUpdateEventListener = event -> {
        if(event.getState() == PlayerUpdateEvent.State.POST) return;
        Entity target;
        List<Entity> entityList = new ArrayList<>();
        mc.world.getEntities().forEach(entityList::add);
        entityList.removeIf(e -> e.distanceTo(mc.player) > 6 || e == mc.player || !(e instanceof PlayerEntity));

        if(!entityList.isEmpty())
            target = entityList.get(0);
        else
            target = null;

        long ms = timer.elapsed(TimeUnit.MILLISECONDS);

        if(target.distanceTo(mc.player) <= 4) {
            mc.player.setYaw(RotationUtil.aimTarget(target).x);
            mc.player.setPitch(RotationUtil.aimTarget(target).y);

            final Mouse mouse = new Mouse(mc);

            if(ms < 100) {
                ((IMouseMixin)mouse).setLeftClick(true);
            } else {
                ((IMouseMixin)mouse).setLeftClick(false);
            }

            if(mc.player.distanceTo(target) <= 2.5 && ms <= 70) {
                mc.options.forwardKey.setPressed(false);
            }

            timer.reset();
        }
    };

}
