package fr.unice.scale.latencyaware.controller.entity;

public class Partition {

    private Double lagCapacity;
    private Double arrivalCapacity;
    private int id;

    public Partition(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }


    @Override
    public String toString() {
        return "Partition{" +
                "id= " + id +
                "}";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Partition partition = (Partition) o;
        return id == partition.id;
    }

    @Override
    public int hashCode() {
        return Integer.hashCode(id);
    }

}
