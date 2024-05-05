package com.pedeapps.redeneural;

import java.util.Arrays;
import java.util.Random;
class NeuralNetwork {
    private Layer inputLayer;
    private Layer hiddenLayer;
    private Layer outputLayer;
    private LossFunction lossFunction;
    private double learningRate;

    public NeuralNetwork(int inputSize, int hiddenSize, int outputSize, ActivationFunction activationFunction,
                         LossFunction lossFunction, double learningRate) {
        this.inputLayer = new Layer(inputSize, hiddenSize, activationFunction);
        this.hiddenLayer = new Layer(hiddenSize, outputSize, activationFunction);
        //this.outputLayer = new Layer(outputSize, 0, activationFunction); // No activation function for output layer
        //this.outputLayer = new Layer(outputSize, 0, new IdentityActivationFunction());
        this.outputLayer = new Layer(outputSize, outputSize, new IdentityActivationFunction()); // Remover a função de ativação para a camada de saída


        this.lossFunction = lossFunction;
        this.learningRate = learningRate;
    }

    public double[] feedForward(double[] inputs) {
        double[] hiddenOutput = inputLayer.forward(inputs);
        double[] output = hiddenLayer.forward(hiddenOutput);
        return outputLayer.forward(output);
    }

    public void train(double[][] trainingData, int epochs, int batchSize) {
        for (int epoch = 0; epoch < epochs; epoch++) {
            System.out.println("Epoch " + epoch);
            shuffle(trainingData);
            for (int i = 0; i < trainingData.length; i += batchSize) {
                int endIndex = Math.min(i + batchSize, trainingData.length);
                double[][] batch = Arrays.copyOfRange(trainingData, i, endIndex);
                updateWeights(batch);
            }
        }
    }
    private void updateWeights(double[][] batch) {
        for (double[] data : batch) {
            double[] inputs = Arrays.copyOf(data, inputLayer.getWeights().length);
            double[] targets = Arrays.copyOfRange(data, inputLayer.getWeights().length, data.length);

            // Forward pass
            double[] hiddenOutput = inputLayer.forward(inputs);
            double[] output = hiddenLayer.forward(hiddenOutput);
            double[] outputWithoutActivation = outputLayer.forward(output);

            // Backpropagation
            double[] outputErrors = calculateOutputErrors(outputWithoutActivation, targets);
            double[] hiddenErrors = calculateHiddenErrors(outputErrors);

            // Update weights
            updateLayerWeights(outputErrors, hiddenOutput, hiddenLayer);
            updateLayerWeights(hiddenErrors, inputs, inputLayer);

            System.out.println("Updated hidden layer weights:");
            printWeights(hiddenLayer);
            System.out.println("Updated output layer weights:");
            printWeights(outputLayer);
        }
    }

    private void printWeights(Layer layer) {
        double[][] weights = layer.getWeights();
        for (int i = 0; i < weights.length; i++) {
            System.out.println(Arrays.toString(weights[i]));
        }
    }

    private double[] calculateOutputErrors(double[] output, double[] target) {
        double[] errors = new double[output.length];
        for (int i = 0; i < output.length; i++) {
            errors[i] = output[i] - target[i];
        }
        return errors;
    }

    private double[] calculateHiddenErrors(double[] outputErrors) {
        double[] hiddenErrors = new double[hiddenLayer.getWeights()[0].length];
        for (int i = 0; i < hiddenErrors.length; i++) {
            double error = 0;
            for (int j = 0; j < outputErrors.length; j++) {
                error += outputErrors[j] * hiddenLayer.getWeights()[i][j];
            }
            hiddenErrors[i] = error;
        }
        return hiddenErrors;
    }


//    private void updateLayerWeights(double[] errors, double[] input, Layer layer) {
//        if (errors.length == 0 || input.length == 0) {
//            return; // Se não houver erros ou não houver dados de entrada, não há nada para corrigir
//        }
//
//        for (int i = 0; i < layer.getWeights().length; i++) {
//            for (int j = 0; j < layer.getWeights()[i].length; j++) {
//                int errorIndex = i % errors.length; // Usamos o operador módulo para garantir que o índice do erro esteja dentro dos limites
//                int inputIndex = j % input.length; // Usamos o operador módulo para garantir que o índice dos dados de entrada esteja dentro dos limites
//                layer.getWeights()[i][j] -= learningRate * errors[errorIndex] * input[inputIndex];
//            }
//        }
//    }

    private void updateLayerWeights(double[] errors, double[] input, Layer layer) {
        if (errors.length == 0 || input.length == 0) {
            return; // Se não houver erros ou não houver dados de entrada, não há nada para corrigir
        }

        for (int i = 0; i < layer.getWeights().length; i++) {
            for (int j = 0; j < layer.getWeights()[i].length; j++) {
                int errorIndex = i % errors.length; // Usamos o operador módulo para garantir que o índice do erro esteja dentro dos limites
                int inputIndex = j % input.length; // Usamos o operador módulo para garantir que o índice dos dados de entrada esteja dentro dos limites
                layer.getWeights()[i][j] -= learningRate * errors[errorIndex] * input[inputIndex];
            }
        }
    }



    private void shuffle(double[][] array) {
        Random rand = new Random();
        for (int i = array.length - 1; i > 0; i--) {
            int index = rand.nextInt(i + 1);
            double[] temp = array[index];
            array[index] = array[i];
            array[i] = temp;
        }
    }
}





