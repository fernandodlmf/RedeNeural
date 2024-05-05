package com.pedeapps.redeneural;

class IdentityActivationFunction implements ActivationFunction {
    @Override
    public double apply(double x) {
        return x; // A função de identidade retorna a entrada sem alterações
    }
}
