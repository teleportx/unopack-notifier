package ru.xstl.unopacknotifier.client.mixin;

import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayNetworkHandler;
import net.minecraft.network.message.LastSeenMessagesCollector;
import net.minecraft.network.message.MessageChain;
import net.minecraft.network.message.MessageSignatureData;
import net.minecraft.network.packet.s2c.play.GameJoinS2CPacket;
import net.minecraft.text.Text;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import ru.xstl.unopacknotifier.Message;

import java.io.FileWriter;
import java.io.IOException;
import java.time.Instant;

import static ru.xstl.unopacknotifier.client.Unopack_notifierClient.filename;
import static ru.xstl.unopacknotifier.client.Unopack_notifierClient.lastMessageId;

@Mixin(ClientPlayNetworkHandler.class)
public abstract class ClientPlayNetworkHandlerMixin {
    @Shadow protected abstract MessageSignatureData method_45722(Instant par1, long par2, LastSeenMessagesCollector.LastSeenMessages par3, String par4);

    @Inject(at = @At("TAIL"), method = "onGameJoin")
    private void onJoin(GameJoinS2CPacket packet, CallbackInfo ci) throws IOException {
        Message message = Message.getMessage();

        if (lastMessageId != message.id) {
            lastMessageId = message.id;

            try {
                FileWriter myWriter = new FileWriter(filename);
                myWriter.write(String.valueOf(lastMessageId));
                myWriter.close();

            } catch (IOException e) {
                e.printStackTrace();
            }

            String language = MinecraftClient.getInstance().getLanguageManager().getLanguage();
            System.out.println(language);
            String sendText;
            if (message.text.containsKey(language)) {
                sendText = message.text.get(language);

            } else {
                sendText = message.text.get("en_us");
            }

            MinecraftClient.getInstance().player.sendMessage(Text.of(sendText));
        }
    }

}