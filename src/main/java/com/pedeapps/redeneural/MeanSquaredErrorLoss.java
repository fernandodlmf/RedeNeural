package com.pedeapps.redeneural;

class MeanSquaredErrorLoss implements LossFunction {
    @Override
    public double calculate(double[] output, double[] target) {
        double sum = 0;
        for (int i = 0; i < output.length; i++) {
            sum += Math.pow(output[i] - target[i], 2);
        }
        return sum / output.length;
    }
}
