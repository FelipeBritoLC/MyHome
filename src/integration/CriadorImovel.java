package integration;

import model.Imovel;

@FunctionalInterface
public interface CriadorImovel {
    Imovel criar(String[] colunas);
}