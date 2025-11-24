package fr.unice.scale.latencyaware.controller.entity.meta_data;

import fr.unice.scale.latencyaware.controller.entity.Partition;

public class PartitionMetaData {
    private long lag;
    private double arrivalRate;
    private Partition partition;

    public PartitionMetaData(Partition partition) {
        this.partition = partition;
        this.lag = 0;
        this.arrivalRate = 0.0;
    }

    public PartitionMetaData(Partition partition, long lag, double arrivalRate) {
        this.partition = partition;
        this.lag = lag;
        this.arrivalRate = arrivalRate;
    }

    public Partition getPartition() {
        return partition;
    }

    public long getLag() {
        return lag;
    }

    public void setLag(long lag) {
        this.lag = lag;
    }

    public double getArrivalRate() {
        return arrivalRate;
    }

    public void setArrivalRate(double arrivalRate) {
        this.arrivalRate = arrivalRate;
    }

    @Override
    public int hashCode() {
        int result = 0;
        long temp;
        result = 31 * result + (int) (lag ^ (lag >>> 32));
        temp = Double.doubleToLongBits(arrivalRate);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        return result;
    }

    @Override
    public String toString() {
        return "PartitionMetaData{" +
                "partition=" + partition +
                ", lag=" + lag +
                ", arrivalRate=" + arrivalRate +
                '}';
    }
}
