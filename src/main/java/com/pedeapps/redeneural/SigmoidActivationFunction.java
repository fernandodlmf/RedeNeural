package com.pedeapps.redeneural;

class SigmoidActivationFunction implements ActivationFunction {
    @Override
    public double apply(double x) {
        return 1 / (1 + Math.exp(-x));
    }
}