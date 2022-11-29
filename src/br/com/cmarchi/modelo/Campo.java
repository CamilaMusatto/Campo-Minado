package br.com.cmarchi.modelo;

import br.com.cmarchi.excecao.ExplosaoException;

import java.util.ArrayList;
import java.util.List;
import java.io.*;

public class Campo {

    private final int linha;
    private final int coluna;
    private boolean aberto = false;
    private boolean minado = false;
    private boolean marcado = false;

    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_RED = "\u001B[31m";
    public static final String ANSI_CYAN = "\u001B[36m";
    public static final String ANSI_GREEN = "\u001B[32m";
    public static final String ANSI_YELLOW = "\u001B[33m";


    private List<Campo> vizinhos = new ArrayList<>();

    Campo(int linha, int coluna){
        this.linha = linha;
        this.coluna = coluna;

    }

    boolean adicionarVizinho(Campo vizinho) {
        boolean linhaDiferente = linha != vizinho.linha;
        boolean colunaDiferente = coluna != vizinho.coluna;
        boolean diagonal = linhaDiferente && colunaDiferente;

        int deltaLinha = Math.abs(linha - vizinho.linha);
        int deltaColuna = Math.abs(coluna - vizinho.coluna);
        int deltaGeral = deltaColuna + deltaLinha;

        if(deltaGeral == 1 && !diagonal) {
            vizinhos.add(vizinho);
            return true;
        } else if(deltaGeral == 2 && diagonal) {
            vizinhos.add(vizinho);
            return true;
        } else {
            return false;
        }
    }

    void alternarMarcacao() {
        if(!aberto) {
            marcado = !marcado;
        }
    }

    boolean abrir() {
        if(!aberto && !marcado) {
            aberto = true;

            if(minado) {
                throw new ExplosaoException();
            }

            if(vizinhancaSegura()) {
                vizinhos.forEach(v -> v.abrir());
            }
            return true;
        } else {
            return false;
        }
    }

    boolean vizinhancaSegura(){
        return  vizinhos.stream().noneMatch(v -> v.minado);
    }

    void minar() {
        minado = true;
    }

    boolean isAberto() {
        return aberto;
    }

     void setAberto(boolean aberto) {
        this.aberto = aberto;
    }

    boolean isFechado()
    {
        return !isAberto();
    }

    boolean isMinado() {
        return minado;
    }

    boolean isMarcado() {
        return marcado;
    }

    public int getLinha() {
        return linha;
    }

    public int getColuna() {
        return coluna;
    }

    boolean objetivoAlcancado() {
        boolean desvendado = !minado && aberto;
        boolean protegido = minado && marcado;
        return desvendado || protegido;
    }

    long minasNaVizinhanca() {
        return vizinhos.stream().filter(v -> v.minado).count();
    }

    void reiniciarCampo() {
        aberto = false;
        minado = false;
        marcado = false;
    }
    @Override
    public String toString() {
        if(marcado) {
            return "x";
        } else if (aberto && minado) {
            return ANSI_YELLOW + "*" + ANSI_RESET;
        } else if (aberto && minasNaVizinhanca() > 0) {
            if(minasNaVizinhanca() == 1){
                return ANSI_CYAN + Long.toString(minasNaVizinhanca()) + ANSI_RESET;
            } else if(minasNaVizinhanca() == 2){
                return ANSI_GREEN + Long.toString(minasNaVizinhanca()) + ANSI_RESET;
            } else {
                return ANSI_RED + Long.toString(minasNaVizinhanca()) + ANSI_RESET;
            }

        } else if (aberto) {
            return " ";
        } else  {
            return "?";
        }
    }
}
