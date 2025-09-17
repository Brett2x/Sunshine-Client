package cc.sunshine.feature.command.impl;

import cc.sunshine.ExampleMod;
import cc.sunshine.feature.command.AbstractCommand;
import cc.sunshine.feature.command.interfaces.CommandData;
import cc.sunshine.feature.module.AbstractModule;
import cc.sunshine.utils.chat.ChatUtil;
import net.minecraft.client.MinecraftClient;
import org.lwjgl.glfw.GLFW;

@CommandData(name = "bind", description = "Allows the user to bind modules to keys", alias = "b")
public class BindCommand extends AbstractCommand {
    @Override
    public void run(String[] args) {
        try {
            mc.player.jump();
            AbstractModule module = ExampleMod.INSTANCE.getModuleManager().getModuleByName(args[1]);
            assert module != null;
            module.setKey(getKeyCodeFromKey(args[2]));
            ChatUtil.prefixMessage("Bound " + module.getName() + " to " + args[2]);
            MinecraftClient.getInstance().player.jump();
        } catch (Exception e) {
            ChatUtil.prefixMessage("Incorrect syntax, the correct usage is '.bind <module> <key>'");
            ExampleMod.LOGGER.warn("The user used incorrect syntax for the bind command. Here is a stacktrace: ", e);
            e.printStackTrace();
        }
    }

    public int getKeyCodeFromKey(String key)
    {
        for(int i = 39; i < 97; i++)
        {
            if(key.equalsIgnoreCase(GLFW.glfwGetKeyName(GLFW.GLFW_KEY_UNKNOWN, i)))
                return i;
        }

        return Short.MIN_VALUE;
    }

}



