package fr.kirosnn.putschjobs;

import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Job {

    private String name;
    private String friendlyName;
    private Map<String, Integer> objectives = new HashMap<>();

    public Job(String name, YamlConfiguration config) {
        this.name = name;
        this.friendlyName = config.getString("settings.friendly-name", "Nom du métier");

        List<String> objectiveList = config.getStringList("objectives");
        for (String objective : objectiveList) {
            String[] parts = objective.split(";");
            if (parts.length == 2) {
                objectives.put(parts[0], Integer.parseInt(parts[1]));
            }
        }
    }

    public String getName() {
        return name;
    }

    public String getFriendlyName() {
        return friendlyName;
    }

    public boolean isObjective(String objective) {
        return objectives.containsKey(objective);
    }

    public void addExperience(Player player, String objective) {
        int xp = objectives.getOrDefault(objective, 0);
        player.sendMessage("Vous avez gagné " + xp + " XP pour " + objective);
    }
}
