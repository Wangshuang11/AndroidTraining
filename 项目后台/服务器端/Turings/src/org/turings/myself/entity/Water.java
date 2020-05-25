package org.turings.myself.entity;

public class Water {
    private int id;
    private int waterdrop;
    private int process;
    private int dryStatus;

    public int getDryStatus() {
        return dryStatus;
    }

    public void setDryStatus(int dryStatus) {
        this.dryStatus = dryStatus;
    }
    public Water() {
        super();
    }
    public Water(int id, int waterdrop, int process,int dryStatus) {
        this.id = id;
        this.waterdrop = waterdrop;
        this.process = process;
        this.dryStatus=dryStatus;
    }

    @Override
    public String toString() {
        return "Water{" +
                "id=" + id +
                ", waterdrop=" + waterdrop +
                ", process=" + process +
                '}';
    }
    public int getId() {
        return id;
    }
    public int getWaterdrop() {
        return waterdrop;
    }
    public int getProcess() {
        return process;
    }
    public void setId(int id) {
        this.id = id;
    }
    public void setWaterdrop(int waterdrop) {
        this.waterdrop = waterdrop;
    }
    public void setProcess(int process) {
        this.process = process;
    }
}
