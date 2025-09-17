package cc.sunshine.feature.module.impl.move;

import cc.sunshine.eventbus.impl.PlayerUpdateEvent;
import cc.sunshine.eventbus.impl.player.PlayerStrafeEvent;
import cc.sunshine.eventbus.interfaces.Listener;
import cc.sunshine.eventbus.interfaces.SubscribeEvent;
import cc.sunshine.feature.module.AbstractModule;
import cc.sunshine.feature.module.enums.ModuleCategory;
import cc.sunshine.feature.module.interfaces.ModuleData;
import cc.sunshine.feature.property.impl.EnumProperty;
import cc.sunshine.feature.property.impl.interfaces.IEnumProperty;
import cc.sunshine.utils.player.MoveUtil;

@ModuleData(name = "Sprint", category = ModuleCategory.MOVE)
public class SprintModule extends AbstractModule {

    private final EnumProperty<MODE> mode = new EnumProperty<>("Mode", MODE.LEGIT);

    @SubscribeEvent
    private final Listener<PlayerStrafeEvent> onPlayerStrafe = event -> {
        if(mode.getValue().equals(MODE.OMNISPRINT) && MoveUtil.isMoving()) {
            mc.player.setSprinting(true);
            mc.options.sprintKey.setPressed(true);
        }
    };

    @SubscribeEvent
    private final Listener<PlayerUpdateEvent> onPlayerUpdate = event -> {
        if(mode.getValue().equals(MODE.LEGIT) && MoveUtil.isMoving()) {
            //mc.player.setSprinting(true);
            mc.options.sprintKey.setPressed(true);
        }
    };

    private enum MODE implements IEnumProperty {

        LEGIT("Legit"),
        OMNISPRINT("Omni Sprint");

        private final String name;

        MODE(String name) {
            this.name = name;
        }

        @Override
        public String getName() {
            return name;
        }
    }
}
