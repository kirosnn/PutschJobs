package fr.kirosnn.putschjobs.Jobs;

public class JobObjective {

    private final String mobType;
    private final int xp;
    private final String description;

    public JobObjective(String mobType, int xp, String description) {
        this.mobType = mobType;
        this.xp = xp;
        this.description = description;
    }

    public String getMobType() {
        return mobType;
    }

    public int getXp() {
        return xp;
    }

    public String getDescription() {
        return description;
    }
}
