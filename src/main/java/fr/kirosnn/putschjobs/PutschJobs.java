package fr.kirosnn.putschjobs;

import fr.kirosnn.putschjobs.commands.*;
import org.bukkit.ChatColor;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.configuration.file.YamlConfiguration;
import me.clip.placeholderapi.PlaceholderAPI;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class PutschJobs extends JavaPlugin {

    private File dataFile;
    private YamlConfiguration dataConfig;
    private YamlConfiguration inventoryConfig;
    private Map<String, Job> jobs;

    @Override
    public void onEnable() {
        getServer().getConsoleSender().sendMessage(ChatColor.GREEN + "Activation du plugin PutschJobs...");

        jobs = new HashMap<>();

        try {
            jobs = new HashMap<>();
            createDataFile();
            loadInventoryConfig();
            loadJobs();

            new JobPlaceholder(this).register();
            getCommand("jobs").setExecutor(new JobsCommand(this));

            getCommand("jobs reload").setExecutor(new ReloadCommand(this));

            getServer().getConsoleSender().sendMessage(ChatColor.GREEN + "PutschJobs plugin activé avec succès!");
        } catch (Exception e) {
            getServer().getConsoleSender().sendMessage(ChatColor.RED + "Erreur lors de l'activation de PutschJobs : " + e.getMessage());
            e.printStackTrace();
            getServer().getPluginManager().disablePlugin(this);
        }
    }

    @Override
    public void onDisable() {
        getServer().getConsoleSender().sendMessage(ChatColor.YELLOW + "Désactivation du plugin PutschJobs...");
        getServer().getConsoleSender().sendMessage(ChatColor.YELLOW + "PutschJobs plugin désactivé!");
    }

    private void createDataFile() {
        dataFile = new File(getDataFolder(), "data.yml");

        if (!dataFile.exists()) {
            if (!getDataFolder().exists()) {
                getDataFolder().mkdir();
            }

            try {
                dataFile.createNewFile();
                dataConfig = YamlConfiguration.loadConfiguration(dataFile);
                getServer().getConsoleSender().sendMessage(ChatColor.GREEN + "Fichier data.yml créé avec succès!");
            } catch (IOException e) {
                getServer().getConsoleSender().sendMessage(ChatColor.RED + "Impossible de créer data.yml!");
                e.printStackTrace();
            }
        } else {
            dataConfig = YamlConfiguration.loadConfiguration(dataFile);
            getServer().getConsoleSender().sendMessage(ChatColor.GREEN + "Fichier data.yml chargé avec succès!");
        }

        if (dataConfig == null) {
            getServer().getConsoleSender().sendMessage(ChatColor.RED + "Erreur : data.yml n'a pas pu être chargé correctement.");
        }
    }

    public void loadInventoryConfig() {
        File inventoryFile = new File(getDataFolder(), "inventories.yml");

        if (!inventoryFile.exists()) {
            saveResource("inventories.yml", false);
        }

        inventoryConfig = YamlConfiguration.loadConfiguration(inventoryFile);

        if (inventoryConfig == null) {
            getServer().getConsoleSender().sendMessage(ChatColor.RED + "Erreur : inventories.yml n'a pas pu être chargé correctement.");
        } else {
            getServer().getConsoleSender().sendMessage(ChatColor.GREEN + "Fichier inventories.yml chargé avec succès!");
        }
    }

    public void loadJobs() {
        File jobsFolder = new File(getDataFolder(), "jobs");

        if (!jobsFolder.exists()) {
            jobsFolder.mkdir();
            getServer().getConsoleSender().sendMessage(ChatColor.GREEN + "Dossier jobs créé avec succès.");
        }

        File[] jobFiles = jobsFolder.listFiles();
        if (jobFiles != null) {
            for (File jobFile : jobFiles) {
                if (jobFile.getName().endsWith(".yml")) {
                    YamlConfiguration jobConfig = YamlConfiguration.loadConfiguration(jobFile);
                    String jobName = jobFile.getName().replace(".yml", "");
                    Job job = new Job(jobName, jobConfig);
                    jobs.put(jobName, job);
                    getServer().getConsoleSender().sendMessage(ChatColor.GREEN + "Métier " + jobName + " chargé avec succès!");
                }
            }
        } else {
            getServer().getConsoleSender().sendMessage(ChatColor.RED + "Aucun fichier de métier trouvé dans le dossier jobs.");
        }
    }

    public Map<String, Job> getJobs() {
        return jobs;
    }

    public YamlConfiguration getInventoryConfig() {
        return inventoryConfig;
    }

    public YamlConfiguration getJobConfig(String jobName) {
        File jobFile = new File(getDataFolder(), "jobs/" + jobName + ".yml");

        if (!jobFile.exists()) {
            saveResource("jobs/" + jobName + ".yml", false);
        }

        return YamlConfiguration.loadConfiguration(jobFile);
    }

    public String getFriendlyName(String jobName) {
        YamlConfiguration jobConfig = getJobConfig(jobName);
        return jobConfig.getString("settings.friendly-name", "Nom du métier");
    }

    public String replacePlaceholders(String jobName) {
        String friendlyName = getFriendlyName(jobName);
        return PlaceholderAPI.setPlaceholders(null, "%putschjobs_friendlyname_" + jobName + "%").replace("%putschjobs_friendlyname_" + jobName + "%", friendlyName);
    }

}
