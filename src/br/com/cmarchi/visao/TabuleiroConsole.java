package br.com.cmarchi.visao;

import br.com.cmarchi.excecao.ExplosaoException;
import br.com.cmarchi.excecao.SairException;
import br.com.cmarchi.modelo.Tabuleiro;

import java.util.Arrays;
import java.util.Iterator;
import java.util.Scanner;

public class TabuleiroConsole {

    private Tabuleiro tabuleiro;

    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_GREEN = "\u001B[32m";

    Scanner entrada = new Scanner(System.in);

    public TabuleiroConsole(Tabuleiro tabuleiro){
        this.tabuleiro = tabuleiro;

        executarJogo();
    }

    private void executarJogo(){
        try{
            boolean continuar = true;

            while(continuar) {
                cicloDoJogo();

                System.out.println("Outra partida? (S/n) ");
                String resposta = entrada.nextLine();

                if("n".equalsIgnoreCase(resposta)) {
                    continuar = false;
                } else {
                    tabuleiro.reiniciar();
                }
            }
        }catch (SairException e){
            System.out.println("Bye Bye!!!");
        }finally {
            entrada.close();
        }
    }

    private void cicloDoJogo() {
        try{
            while(!tabuleiro.objetivoAlcancado()){
                System.out.println(tabuleiro);

                String digitado = capturarValorDigitado("Digite (x,y): ");

                Iterator<Integer> xy = Arrays.stream(digitado.split(","))
                        .map(e -> Integer.parseInt(e.trim())).iterator();

                digitado = capturarValorDigitado("1 - Abrir ou 2 - (Des)Marcar: ");

                if("1".equals(digitado)) {
                    tabuleiro.abrir(xy.next(), xy.next());
                } else if("2".equals(digitado)) {
                    tabuleiro.alternarMarcacao(xy.next(), xy.next());
                }
            }
            System.out.println(tabuleiro);
            System.out.println("***************************");
            System.out.println(ANSI_GREEN + "VOCÊ GANHOU !!!" + ANSI_RESET);
            System.out.println("***************************");
        }catch (ExplosaoException e) {
            System.out.println(tabuleiro);
            System.err.println("**************************");
            System.err.println("VOCÊ PERDEU !!!");
            System.err.println("**************************");
        }
    }
    private String capturarValorDigitado(String texto) {
        System.out.print(texto);
        String digitado = entrada.nextLine();

        if("sair".equalsIgnoreCase(digitado)){
            throw new SairException();
        }
        return digitado;

    }
}
