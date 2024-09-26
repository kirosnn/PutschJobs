package fr.kirosnn.putschjobs;

import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import org.bukkit.entity.Player;

public class JobPlaceholder extends PlaceholderExpansion {

    private final PutschJobs plugin;

    public JobPlaceholder(PutschJobs plugin) {
        this.plugin = plugin;
    }

    @Override
    public String getIdentifier() {
        return "putschjobs";
    }

    @Override
    public String getAuthor() {
        return "Kirosnn";
    }

    @Override
    public String getVersion() {
        return "1.0";
    }

    @Override
    public boolean persist() {
        return true;
    }

    @Override
    public String onPlaceholderRequest(Player player, String identifier) {

        if (identifier.startsWith("friendlyname_")) {
            String jobName = identifier.replace("friendlyname_", "");
            Job job = plugin.getJobs().get(jobName);
            if (job != null) {
                return job.getFriendlyName();
            }
        }

        return null;
    }
}
