package fr.kirosnn.putschjobs;

import fr.kirosnn.putschjobs.Managers.JobManager;
import fr.kirosnn.putschjobs.commands.JobsCommand;
import fr.kirosnn.putschjobs.listeners.MenuListener;
import org.bukkit.plugin.java.JavaPlugin;

public class PutschJobs extends JavaPlugin {

    private JobManager jobManager;

    @Override
    public void onEnable() {
        saveDefaultConfig();
        jobManager = new JobManager(this);

        getLogger().info("Le plugin vient de s'allumer !");

        //Enregistrement des commandes
        getCommand("jobs").setExecutor(new JobsCommand(this));
    }

    @Override
    public void onDisable() {
        getLogger().info("Le plugin vient de s'eteindre !");
    }

    public JobManager getJobManager() {
        return jobManager;
    }
}
