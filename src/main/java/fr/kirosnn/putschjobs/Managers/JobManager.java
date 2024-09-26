package fr.kirosnn.putschjobs.Managers;

import fr.kirosnn.putschjobs.Jobs.Job;
import fr.kirosnn.putschjobs.PutschJobs;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

public class JobManager {

    private final PutschJobs plugin;
    private final Map<String, Job> jobs = new HashMap<>();

    public JobManager(PutschJobs plugin) {
        this.plugin = plugin;
        loadJobs();
    }

    private void loadJobs() {
        File jobsFolder = new File(plugin.getDataFolder(), "jobs");
        if (!jobsFolder.exists()) jobsFolder.mkdir();

        File[] files = jobsFolder.listFiles((dir, name) -> name.endsWith(".yml"));
        if (files != null) {
            for (File file : files) {
                FileConfiguration config = YamlConfiguration.loadConfiguration(file);
                String name = file.getName().replace(".yml", "");

                String friendlyName = config.getString("settings.friendly-name");
                int maxlevel = config.getInt("settings.max-level");

                Map<String, JobObjective> objectives = new HashMap<>();
                for (String key : config.getConfigurationSection("objectives").getKeys(false)) {
                    String[] parts = key.split(";");
                    String mobType =parts[0];
                    int xp = Integer.parseInt(parts[1]);
                    String description = config.getString("objectives." + key);

                    objectives.put(mobType, new JobObjective(mobType, xp, description));
                }

                Job job = new Job(friendlyName, maxlevel, objectives);

                jobs.put(name.toLowerCase(), job);
            }
        }
    }

    public Job getJob(String name) {
        return jobs.get(name.toLowerCase());
    }

    public Map<String, Job> getJobs() {
        return jobs;
    }
}
