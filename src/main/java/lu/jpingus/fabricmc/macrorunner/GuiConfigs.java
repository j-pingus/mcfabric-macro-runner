package lu.jpingus.fabricmc.macrorunner;

import fi.dy.masa.malilib.gui.GuiConfigsBase;
import fi.dy.masa.malilib.gui.button.ButtonBase;
import fi.dy.masa.malilib.gui.button.ButtonGeneric;
import fi.dy.masa.malilib.gui.button.IButtonActionListener;
import lu.jpingus.fabricmc.macrorunner.config.ConfigGroupInterface;
import lu.jpingus.fabricmc.macrorunner.config.Configs;

import java.util.List;

public class GuiConfigs extends GuiConfigsBase {
    public static ConfigGroupInterface currentTab = Configs.GENERIC;

    public GuiConfigs() {
        super(10, 50, Reference.MOD_ID, null, "macro-runner.gui.config.title");
    }

    @Override
    public void initGui() {
        super.initGui();
        this.clearOptions();

        int x = 10;
        int y = 26;
        int rows = 1;

        for (ConfigGroupInterface tab : Configs.GROUPS) {
            int width = this.getStringWidth(tab.getName()) + 10;

            if (x >= this.width - width - 10) {
                x = 10;
                y += 22;
                rows++;
            }

            x += this.createButton(x, y, width, tab);

        }
    }

    @Override
    public List<ConfigOptionWrapper> getConfigs() {
        return ConfigOptionWrapper.createFor(currentTab.getOptions());
    }

    private int createButton(int x, int y, int width, ConfigGroupInterface tab) {
        ButtonGeneric button = new ButtonGeneric(x, y, width, 20, tab.getName());
        button.setEnabled(GuiConfigs.currentTab != tab);
        this.addButton(button, new ButtonListenerConfigTabs(tab, this));

        return button.getWidth() + 2;
    }


    private static class ButtonListenerConfigTabs implements IButtonActionListener {
        private final GuiConfigs parent;
        private final ConfigGroupInterface tab;

        public ButtonListenerConfigTabs(ConfigGroupInterface tab, GuiConfigs parent) {
            this.tab = tab;
            this.parent = parent;
        }

        @Override
        public void actionPerformedWithButton(ButtonBase button, int mouseButton) {
            GuiConfigs.currentTab = tab;
            this.parent.reCreateListWidget(); // apply the new config width
            this.parent.getListWidget().resetScrollbarPosition();
            this.parent.initGui();

        }
    }

}
