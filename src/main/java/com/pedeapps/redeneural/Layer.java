package com.pedeapps.redeneural;

import java.util.Random;

class Layer {
    private double[][] weights;
    private double[] biases;
    private ActivationFunction activationFunction;

    public Layer(int inputSize, int outputSize, ActivationFunction activationFunction) {
        this.activationFunction = activationFunction;
        this.weights = new double[inputSize][outputSize];
        this.biases = new double[outputSize];
        initializeWeightsAndBiases();
    }

    private void initializeWeightsAndBiases() {
        Random rand = new Random();
        for (int i = 0; i < weights.length; i++) {
            for (int j = 0; j < weights[i].length; j++) {
                weights[i][j] = rand.nextDouble() - 0.5;
            }
        }
        for (int i = 0; i < biases.length; i++) {
            biases[i] = rand.nextDouble() - 0.5;
        }
    }

    public double[] forward(double[] inputs) {
        double[] output = new double[biases.length];
        for (int i = 0; i < output.length; i++) {
            double sum = biases[i];
            for (int j = 0; j < inputs.length; j++) {
                sum += inputs[j] * weights[j][i];
            }
            output[i] = activationFunction.apply(sum);
        }
        return output;
    }

    public double[][] getWeights() {
        return weights;
    }
}
