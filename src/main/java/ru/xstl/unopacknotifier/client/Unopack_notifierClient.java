package ru.xstl.unopacknotifier.client;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.loader.api.FabricLoader;

import java.io.*;

public class Unopack_notifierClient implements ClientModInitializer {
    public static int lastMessageId = 0;
    public final static String filename = FabricLoader.getInstance().getConfigDir().toAbsolutePath() + "/unopack-notfier";
    @Override
    public void onInitializeClient() {
        File lastMessage = new File(filename);

        System.out.println(filename);

        if (!lastMessage.exists()) {
            try {
                lastMessage.createNewFile();

            } catch (IOException e) {
                throw new RuntimeException(e);
            }

            try {
                FileWriter myWriter = new FileWriter(filename);
                myWriter.write(String.valueOf(lastMessageId));
                myWriter.close();

            } catch (IOException e) {
                e.printStackTrace();
            }

        } else {

            BufferedReader reader = null;
            try {
                reader = new BufferedReader(new FileReader(filename));
                String currentLine = reader.readLine();
                reader.close();

                lastMessageId = Integer.parseInt(currentLine);

            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        System.out.println(lastMessageId);
    }
}
