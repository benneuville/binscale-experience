package fr.unice.scale.latencyaware.controller.processing;

import fr.unice.scale.latencyaware.controller.entity.meta_data.CGMetaData;

/**
 * This scaler processor implements the binpack classic scaling strategy (+ externalArrivalRate in Parental Propagation).
 * <br>
 * Include here the logic to propagate arrival rates through the graph for t+1.
 * <br><br>
 * <i>This implementation is an extension based on M. Ezzeddine work, by including externalArrivalRate from each node</i>
 */
public class SeparateArrivalRateClassicScalerProcessor extends ClassicScalerProcessor {

    @Override
    protected double getPropagatedParentalArrivalRate(CGMetaData data) {
        return data.getParentArrivalRate() + data.getTotalExternalArrivalRate();
    }
}
