package lu.jpingus.fabricmc.macrorunner;

import fi.dy.masa.malilib.config.IConfigBoolean;
import fi.dy.masa.malilib.config.options.ConfigBooleanHotkeyed;
import fi.dy.masa.malilib.hotkeys.IKeybind;
import fi.dy.masa.malilib.hotkeys.KeyAction;
import fi.dy.masa.malilib.hotkeys.KeyCallbackToggleBooleanConfigWithMessage;
import net.minecraft.client.MinecraftClient;

import javax.annotation.Nullable;

public class KeyCallbackToggleBoolean extends KeyCallbackToggleBooleanConfigWithMessage {
    final Runnable actionOn;
    final Runnable actionOff;
    public static void register(ConfigBooleanHotkeyed config, @Nullable Runnable actionOn, @Nullable Runnable actionOff){
        KeyCallbackToggleBoolean callback = new KeyCallbackToggleBoolean(config,actionOn,actionOff);
        config.getKeybind().setCallback(callback);
    }
    private KeyCallbackToggleBoolean(IConfigBoolean config, @Nullable Runnable actionOn, @Nullable Runnable actionOff) {
        super(config);
        this.actionOff = actionOff;
        this.actionOn = actionOn;
    }

    @Override
    public boolean onKeyAction(KeyAction action, IKeybind key) {
        MinecraftClient mc = MinecraftClient.getInstance();
        if (mc != null && mc.player != null && super.onKeyAction(action, key)) {
            if (this.config.getBooleanValue() == false) {
                if (actionOff != null)
                    actionOff.run();
            } else {
                if (actionOn != null)
                    actionOn.run();
            }
            return true;
        }
        return false;
    }
}
