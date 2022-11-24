package br.com.cmarchi.modelo;
import static org.junit.jupiter.api.Assertions.*;

import br.com.cmarchi.excecao.ExplosaoException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class CampoTeste {

    private Campo campo;
    private static final String NUMERO_UM = "1";


    @BeforeEach
    void iniciarCampo() {
            campo = new Campo(3, 3);
    }

    @Test
    void testeVizinhoDistancia1Esquerda() {
        Campo vizinho = new Campo(3, 2);
        boolean resultado = campo.adicionarVizinho(vizinho);
        assertTrue(resultado);

    }

    @Test
    void testeVizinhoDistancia1Direita(){
        Campo vizinho = new Campo(3, 4);
        boolean resultado = campo.adicionarVizinho(vizinho);
        assertTrue(resultado);
    }

    @Test
    void testeVizinhoDistancia1Encima() {
        Campo vizinho = new Campo(2, 3);
        boolean resultado = campo.adicionarVizinho(vizinho);
        assertTrue(resultado);
    }

    @Test
    void testeVizinhoDistancia1Embaixo() {
        Campo vizinho = new Campo(4, 3);
        boolean resultado = campo.adicionarVizinho(vizinho);
        assertTrue(resultado);
    }

    @Test
    void testeVizinhoDistancia2() {
        Campo vizinho = new Campo(2, 2);
        boolean resultado = campo.adicionarVizinho(vizinho);
        assertTrue(resultado);
    }

    @Test
    void testeNaoVizinho() {
        Campo vizinho = new Campo(1,3);
        boolean resultado = campo.adicionarVizinho(vizinho);
        assertFalse(resultado);
    }

    @Test
    void testeValorPadraoAtributoMarcado() {
        assertFalse(campo.isMarcado());
    }

    @Test
    void testeAlternarMarcacao() {
        campo.alternarMarcacao();
        assertTrue(campo.isMarcado());
    }

    @Test
    void testeAlternarMarcacaoDuasChamadas() {
        campo.alternarMarcacao();
        campo.alternarMarcacao();
        assertFalse(campo.isMarcado());
    }

    @Test
    void testeAbrirNaoMinadoNaoMarcado() {
        assertTrue(campo.abrir());
    }

    @Test
    void testeAbrirNaoMinadoMarcado() {
        campo.alternarMarcacao();
        assertFalse(campo.abrir());
    }

    @Test
    void testeAbrirMinadoMarcado() {
        campo.alternarMarcacao();
        campo.minar();
        assertFalse(campo.abrir());
    }

    @Test
    void testeAbrirMinadoNaoMarcado() {
        campo.minar();

        assertThrows(ExplosaoException.class, () -> {
            campo.abrir();
        });
    }

    @Test
    void testeAbrirComVizinho () {

        Campo campo11 = new Campo(1, 1);
        Campo campo22 = new Campo(2,2);
        campo22.adicionarVizinho(campo11);

        campo.adicionarVizinho(campo22);
        campo.abrir();

        assertTrue(campo22.isAberto() && campo11.isAberto());


    }

    @Test
    void testeAbrirComVizinho2 () {

        Campo campo11 = new Campo(1, 1);
        Campo campo12 = new Campo(1,2);
        campo12.minar();

        Campo campo22 = new Campo(2,2);
        campo22.adicionarVizinho(campo11);
        campo22.adicionarVizinho(campo12);

        campo.adicionarVizinho(campo22);
        campo.abrir();

        assertTrue(campo22.isAberto() && campo11.isFechado());


    }

    @Test
    void testeObjetivoAlcancadoCampoNaoMinadoEAberto() {
        campo.abrir();
        assertTrue(campo.objetivoAlcancado());
    }

    @Test
    void testeObjetivoAlcancadoCampoMinadoEAberto() {
        campo.abrir();
        campo.minar();
        assertFalse(campo.objetivoAlcancado());
    }

    @Test
    void testeObjetivoAlcancadoCampoNaoMinadoENaoAberto() {

        assertFalse(campo.objetivoAlcancado());
    }

    @Test
    void testeObjetivoAlcancadoCampoMinadoEMarcado() {
        campo.minar();
        campo.alternarMarcacao();
        assertTrue(campo.objetivoAlcancado());
    }

    @Test
    void testeObjetivoAlcancadoCampoMinadoENaoMarcado() {
        campo.minar();
        assertFalse(campo.objetivoAlcancado());
    }

    @Test
    void testeMinasNaVizinhanca() {
        Campo campo23 = new Campo(2, 3);
        Campo campo43 = new Campo(4, 3);

        campo.adicionarVizinho(campo23);
        campo.adicionarVizinho(campo43);

        campo43.minar();
        campo23.minar();

        Long resultado = campo.minasNaVizinhanca();

        assertEquals(2L, resultado);
    }

    @Test
    void testeMinasNaVizinhanca0minas() {
        Campo campo23 = new Campo(2, 3);
        Campo campo43 = new Campo(4, 3);

        campo.adicionarVizinho(campo23);
        campo.adicionarVizinho(campo43);

        Long resultado = campo.minasNaVizinhanca();

        assertEquals(0, resultado);

    }

    @Test
    void testeToStringMarcado() {
        campo.alternarMarcacao();

        String resultado = campo.toString();
        //System.out.println(campo.toString());

        assertEquals("x", resultado);
    }

    @Test
    void testeToStringAbertoEMinado() {
        campo.abrir();
        campo.minar();

        //System.out.println(campo.toString());

        String resultado = campo.toString();

        assertEquals("*", resultado);
    }

    @Test
    void testeToStringAbertoEMinasNaVizinhanca() {

        Campo campo22 = new Campo(2, 2);
        Campo campo23 = new Campo(2, 3);

        campo23.adicionarVizinho(campo22);
        campo.adicionarVizinho(campo22);
        campo22.minar();
        campo.abrir();

        Long resultado = campo.minasNaVizinhanca();

        String resultadoFinal =campo.toString();

        assertEquals(NUMERO_UM, resultadoFinal);
    }

    @Test
    void testeToStringCampo() {
        campo.abrir();

        String resultado = campo.toString();

        assertEquals(" ", resultado);
    }

    @Test
    void testeToStringNenhumaOpcao() {

        String resultado = campo.toString();


        assertEquals("?", resultado);
    }

    @Test
    void testeToStringNenhumaOpcao2() {
        campo.minar();

        String resultado = campo.toString();
        //System.out.println(campo22.toString());

        assertEquals("*", resultado);

    }









}
