package lu.jpingus.fabricmc.macrorunner;

import fi.dy.masa.malilib.hotkeys.IHotkey;
import fi.dy.masa.malilib.hotkeys.IKeybindManager;
import fi.dy.masa.malilib.hotkeys.IKeybindProvider;
import lu.jpingus.fabricmc.macrorunner.config.Configs;
import lu.jpingus.fabricmc.macrorunner.config.MacroConfigGroup;

public class InputHandler implements IKeybindProvider {
    private static final InputHandler INSTANCE = new InputHandler();

    private InputHandler() {
        super();
    }

    public static InputHandler getInstance() {
        return INSTANCE;
    }

    @Override
    public void addKeysToMap(IKeybindManager manager) {
        for (IHotkey hotkey : Configs.GENERIC.getHotkeys()) {
            manager.addKeybindToMap(hotkey.getKeybind());
        }
        for (MacroConfigGroup group : Configs.MACROS) {
            for (IHotkey hotkey : group.getHotkeys()) {
                manager.addKeybindToMap(hotkey.getKeybind());
            }
        }
    }

    @Override
    public void addHotkeys(IKeybindManager manager) {
        manager.addHotkeysForCategory(Reference.MOD_NAME, "minihud.hotkeys.category.generic_hotkeys", Configs.GENERIC.getHotkeys());
        for (MacroConfigGroup group : Configs.MACROS) {
            manager.addHotkeysForCategory(Reference.MOD_NAME, group.getName(), group.getHotkeys());
        }
    }
}
