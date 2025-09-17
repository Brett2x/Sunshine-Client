package cc.sunshine.feature.module.impl.world;

import cc.sunshine.eventbus.impl.PlayerUpdateEvent;
import cc.sunshine.eventbus.interfaces.Listener;
import cc.sunshine.eventbus.interfaces.SubscribeEvent;
import cc.sunshine.feature.module.AbstractModule;
import cc.sunshine.feature.module.enums.ModuleCategory;
import cc.sunshine.feature.module.interfaces.ModuleData;
import cc.sunshine.feature.property.impl.FloatProperty;

@ModuleData(name = "Collect Garbage", description = "Runs the java garbage collector on enable.", category = ModuleCategory.WORLD)
public class GarbageCollectModule extends AbstractModule {

    private final FloatProperty memory = new FloatProperty("Memory", 50, 15, 100);

    @Override
    protected void onEnable() {
        super.onEnable();
        //System.gc();
        //System.out.println("Ran the java garbage collector!");
    }

    @SubscribeEvent
    private final Listener<PlayerUpdateEvent> playerUpdateEventListener = event -> {
        long l = Runtime.getRuntime().maxMemory();
        long m = Runtime.getRuntime().totalMemory();
        long n = Runtime.getRuntime().freeMemory();
        long o = m - n;

        long percent = o * 100L / l;

        if(percent > memory.getValue()) {
            System.gc();
            System.out.println("Ran the java garbage collector!");
        }

    };
}
