package nl.levy.COCPlugin.COC;

import nl.levy.COCPlugin.COCItems.COCMainManager;
import nl.levy.COCPlugin.COCItems.COCObjective;
import nl.levy.COCPlugin.COCItems.ResourceType;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.entity.Zombie;
import org.bukkit.scoreboard.*;

import java.util.ArrayList;
import java.util.List;

public class COCScoreboardManager {

    private final ScoreboardManager scoreboardManager;
    private static final List<Player> items = new ArrayList<>();
    private COCMainManager cocMainManager;

    public COCScoreboardManager(COCMainManager cocMainManager) {
        this.cocMainManager = cocMainManager;
        scoreboardManager = Bukkit.getScoreboardManager();
    }

    public void addPlayerToDisplay(Player player){
        displayToPlayer(player);
        items.add(player);
    }

    private void displayToPlayer(Player player) {
        var manager = cocMainManager.getManager(player);

        var board = scoreboardManager.getNewScoreboard();
        var objective = board.registerNewObjective("Boeie", "Boeie", "Boeie");

        objective.setDisplayName("COCManager");//Naam van scoreboard ding

        objective.setDisplaySlot(DisplaySlot.SIDEBAR);

        var i = 16;
        objective.getScore("").setScore(i--);
        objective.getScore(" ").setScore(i--);
        objective.getScore("Gold: " + manager.resourceManger.getCurrent(ResourceType.Gold)).setScore(i--);
        objective.getScore("  ").setScore(i--);
        objective.getScore("Elixir: " + manager.resourceManger.getCurrent(ResourceType.Elixir)).setScore(i--);
        objective.getScore("   ").setScore(i--);
        objective.getScore("    ").setScore(i);

        player.setScoreboard(board);
    }
//
//    public void belowName(Zombie zombie) {
//        var board = scoreboardManager.getNewScoreboard();
//
//        var obj = board.registerNewObjective("bla", "bla", "bla");
//        obj.setDisplaySlot(DisplaySlot.BELOW_NAME);
//        var a = new Object();
//
//        obj.getScore(zombie.hashCode() + "").setScore("");
//
//    }

    public void update() {
        for (var item : items) {
            displayToPlayer(item);
        }
    }
//
//
//    public COCScoreboardManager(COCManager manager) {
//        this.cocManager = manager;
//        this.manager = Bukkit.getScoreboardManager();
//        this.objective = board.registerNewObjective("test", "dummy", "test")
//    }

//    private void displayToPlayer(Player player) {
//        Scoreboard board = manager.getNewScoreboard();
//
//        Objective objective = ;
//        objective.setDisplaySlot(DisplaySlot.SIDEBAR);
//        objective.setDisplayName(ChatColor.RED + "Manager board");
//
//
//    }
//
//    private void addScore(String text) {
//
//    }
//
//    private void test() {
//
//
//        Score score = objective.getScore("Score10");
//        score.setScore(10);
//        Score score1 = objective.getScore("Score9");
//        score1.setScore(9);
//        Score score2 = objective.getScore("Score8");
//        score2.setScore(8);
//        Score score3 = objective.getScore("§6Colors");
//        score.set
//        score3.setScore(7);
//        player.setScoreboard(board);
//    }
}
