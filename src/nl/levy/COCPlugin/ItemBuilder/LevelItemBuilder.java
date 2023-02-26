package nl.levy.COCPlugin.ItemBuilder;

import com.google.gson.Gson;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class LevelItemBuilder {
    public GoldMineData goldMineData;
    public ElixirCollectorData elixirCollectorData;
    public GoldStorageData goldStorageData;
    public ElixirTankData elixirTankData;
    public TownHallData townHallData;

    public static LevelItemBuilder create() {
        try {
            String json = Files.readString(Path.of("C:\\Users\\Gebruiker\\Documents\\Server\\plugins\\data\\objectdata.json"));

            return new Gson().fromJson(json, LevelItemBuilder.class);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
