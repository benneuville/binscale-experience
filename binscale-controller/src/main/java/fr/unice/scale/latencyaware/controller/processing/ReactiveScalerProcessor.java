package fr.unice.scale.latencyaware.controller.processing;

import fr.unice.scale.latencyaware.controller.entity.meta_data.CGMetaData;
import fr.unice.scale.latencyaware.controller.entity.meta_data.PartitionMetaData;

public class ReactiveScalerProcessor extends ScalerProcessor {

    @Override
    protected double getArrivalRateForPartitionCalculation(CGMetaData cgdata, PartitionMetaData partitionMetaData) {
        return cgdata.getAvgTotalInputArrivalRate();
    }

    @Override
    protected double getRootArrivalRate(CGMetaData data) {
        return data.getTotalInputArrivalRate();
    }

    @Override
    protected double getPropagatedParentalArrivalRate(CGMetaData data) {
        return 0.0;
    }
}
