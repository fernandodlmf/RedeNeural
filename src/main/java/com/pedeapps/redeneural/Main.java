package com.pedeapps.redeneural;

public class Main {
    public static void main(String[] args) {
        // Definir os parâmetros da rede neural
        int inputSize = 2;
        int hiddenSize = 3;
        int outputSize = 1;
        double learningRate = 0.1;
        ActivationFunction activationFunction = new SigmoidActivationFunction();
        LossFunction lossFunction = new MeanSquaredErrorLoss();

        // Criar a rede neural
        NeuralNetwork nn = new NeuralNetwork(inputSize, hiddenSize, outputSize, activationFunction, lossFunction,
                learningRate);

        // Exemplo de dados de treinamento (problema XOR)
        double[][] trainingData = {
                {0, 0, 0},
                {0, 1, 1},
                {1, 0, 1},
                {1, 1, 0}
        };

        // Treinar a rede neural
        int epochs = 1000;
        int batchSize = 2;
        nn.train(trainingData, epochs, batchSize);

        // Testar a rede neural com novos dados
        double[] newInput1 = {0, 0};
        double[] newInput2 = {1, 1};

        double[] output1 = nn.feedForward(newInput1);
        double[] output2 = nn.feedForward(newInput2);

        // Exibir resultados
        if (output1.length > 0)
            System.out.println("Resultado para [0, 0]: " +  output1[0]);

        if (output2.length > 0)
            System.out.println("Resultado para [1, 1]: " + output2[0]);
    }
}

