package fr.kirosnn.putschjobs.Jobs;

import java.util.Map;

public class Job {

    private final String friendlyName;
    private final int maxlevel;
    private final Map<String, JobObjective> objectives;

    public Job(String friendlyName, int maxlevel, Map<String, JobObjective> objectives) {
        this.friendlyName = friendlyName;
        this.maxlevel = maxlevel;
        this.objectives = objectives;
    }

    public String getFriendlyName() {
        return friendlyName;
    }

    public int getMaxlevel() {
        return maxlevel;
    }

    public Map<String, JobObjective> getObjectives() {
        return objectives;
    }
}
