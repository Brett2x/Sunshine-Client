package cc.sunshine.management;

import cc.sunshine.ExampleMod;
import cc.sunshine.eventbus.impl.chat.ChatSendEvent;
import cc.sunshine.eventbus.interfaces.IHandler;
import cc.sunshine.eventbus.interfaces.Listener;
import cc.sunshine.eventbus.interfaces.SubscribeEvent;
import cc.sunshine.feature.command.AbstractCommand;
import cc.sunshine.feature.command.impl.BindCommand;
import cc.sunshine.feature.module.AbstractModule;
import net.minecraft.client.MinecraftClient;

import java.util.*;

public final class CommandManager implements IHandler {

    private final Class<? extends AbstractCommand>[] COMMAND_CLASSES = new Class[] {
            BindCommand.class
    };

    private final Map<Class<? extends AbstractCommand>, AbstractCommand> commandMap = new HashMap<>();



    public CommandManager() {
        Arrays.stream(COMMAND_CLASSES).forEach(command -> {
            try {
                commandMap.put(command, command.newInstance());
                ExampleMod.INSTANCE.getEventBus().registerHandler(command.newInstance());
            } catch (Exception ex) {
                ex.printStackTrace();
            }

        });
        System.out.printf("Registered %s commands", commandMap.size());

        ExampleMod.INSTANCE.getEventBus().registerHandler(this);
    }

    @SubscribeEvent
    private final Listener<ChatSendEvent> chatSendEventListener = event ->  {
        if(!event.getText().startsWith(".")) return;
        event.setCancelled(true);
        String[] argSplitter = event.getText().split(" ");
        AbstractCommand command = commandMap.get(argSplitter[0].replace(".", "").toLowerCase());
        if(command != null)
            command.run(argSplitter);
        else {
            for (AbstractCommand commands : commandMap.values()) {
                for (String alias : commands.getAlias()) {
                    if (alias.equalsIgnoreCase(argSplitter[0].replace(".", "").toLowerCase())) {
                        commands.run(argSplitter);
                        return;
                    }
                }
            }
        }
    };


    public Collection<AbstractCommand> getCommandMap() {
        return commandMap.values();
    }

}
