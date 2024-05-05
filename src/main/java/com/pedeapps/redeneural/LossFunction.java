package com.pedeapps.redeneural;

interface LossFunction {
    double calculate(double[] output, double[] target);
}