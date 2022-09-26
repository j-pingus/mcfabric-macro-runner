package lu.jpingus.fabricmc.macrorunner;

import com.mojang.brigadier.CommandDispatcher;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayConnectionEvents;
import net.fabricmc.fabric.api.networking.v1.PacketSender;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayNetworkHandler;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.command.CommandSource;

public class ConnectionHandler implements ClientPlayConnectionEvents.Join, ClientPlayConnectionEvents.Disconnect{
    @Override
    public void onPlayReady(ClientPlayNetworkHandler handler, PacketSender sender, MinecraftClient client) {
        CommandDispatcher<CommandSource> commandDispatcher = handler.getCommandDispatcher();
        MacroRunner.getInstance().setCommandDispatcher(commandDispatcher);
        MacroRunnerMod.LOGGER.info("Client:{}",client.getClass().getName());
    }

    @Override
    public void onPlayDisconnect(ClientPlayNetworkHandler handler, MinecraftClient client) {
        MacroRunner.getInstance().setCommandDispatcher(null);
    }
}
