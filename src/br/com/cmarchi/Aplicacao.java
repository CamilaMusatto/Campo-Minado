package br.com.cmarchi;

import br.com.cmarchi.modelo.Tabuleiro;
import br.com.cmarchi.visao.TabuleiroConsole;

public class Aplicacao {

    public static void main(String[] args) {

        Tabuleiro tabuleiro = new Tabuleiro(6, 6, 6);
        new TabuleiroConsole(tabuleiro);



    }
}
