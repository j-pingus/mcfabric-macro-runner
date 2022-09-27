package lu.jpingus.fabricmc.macrorunner;

import net.minecraft.client.MinecraftClient;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MacroRunner {
    private static MacroRunner INSTANCE;
    private final ExecutorService executor;

    private MacroRunner() {
        this.executor = Executors.newSingleThreadExecutor();
    }

    public static MacroRunner getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new MacroRunner();
        }
        return INSTANCE;
    }

    public void run(Macro macro) {
        this.executor.submit(() -> {
            boolean canDeactivate = true;
            MacroRunnerMod.LOGGER.info("Running Macro {}", macro);
            try {
                MinecraftClient mc = MinecraftClient.getInstance();

                if (mc == null) {
                    MacroRunnerMod.LOGGER.warn("No good conditions to run macro mc:{} ", mc);
                    return;
                }
                if (mc.player == null) {
                    MacroRunnerMod.LOGGER.warn("No player");
                    return;
                }

                MacroRunnerMod.LOGGER.debug("before command {}", macro);
                mc.player.sendCommand(macro.getCommand());
                MacroRunnerMod.LOGGER.debug("after command {}", macro);
                if (macro.isRepeat() && macro.isActive()) {
                    Thread.sleep(macro.getDelay() * 1000L);
                    if (macro.isActive()) {
                        MacroRunnerMod.LOGGER.info("repeating {}", macro);
                        canDeactivate = false;
                        MacroRunner.getInstance().run(macro);
                    }
                }
            } catch (InterruptedException e) {
                MacroRunnerMod.LOGGER.error("Macro interrupted");
            } catch (Throwable e) {
                MacroRunnerMod.LOGGER.error("Macro interrupted by an error :{}", e.getLocalizedMessage(), e);
            } finally {
                //Always deactivate after a run if not in loop.
                if (canDeactivate) {
                    MacroRunnerMod.LOGGER.info("End of macro run {}", macro);
                    MacroRunnerMod.printPlayerMessage("Macro run ended");
                    macro.deactivate();
                }
            }
        });
    }
}
