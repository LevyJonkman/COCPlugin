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
    public ArcherTowerData archerTowerData;

    private static LevelItemBuilder levelItemBuilder;

    public static LevelItemBuilder getInstance() {
        if (levelItemBuilder == null) {
            try {
                String json = Files.readString(Path.of("C:\\Users\\l.jonkman\\prive\\repos\\COCServer\\objectdata.json"));

                levelItemBuilder = new Gson().fromJson(json, LevelItemBuilder.class);
                System.out.println(new Gson().toJson(levelItemBuilder));

            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        return levelItemBuilder;
    }

}
