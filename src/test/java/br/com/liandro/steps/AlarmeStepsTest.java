package br.com.liandro.steps;

import br.com.liandro.page.AlarmePageObject;
import br.com.liandro.utils.Utils;
import org.junit.After;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import java.io.IOException;
import java.net.MalformedURLException;


@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class AlarmeStepsTest {
    private Utils utils = new Utils();

    @Before
    public void iniciar() throws MalformedURLException {
        utils.iniciar();
    }

    @After
    public void finalizar() {
        utils.driver.quit();
    }

    @Test
    public void adicionarUmAlarme() throws IOException {

        AlarmePageObject alarmePageObject = new AlarmePageObject(utils.driver);

        alarmePageObject.clicarNoBotaoAlarmeEValidarTelaAlarme("adicionar Um Alarme");

        alarmePageObject.clicarEmAdicionarAlarme();

        alarmePageObject.clicarEmAlternarModoDeEntrada();

        alarmePageObject.verificarQueDialogFoiExibido();

        alarmePageObject.preencherCampoHoras("10");

        alarmePageObject.preencherCampoMinutos("00");

        alarmePageObject.clicarNoBotaoOkEEvideciar("ao completar envio de valores ao alarme");

        alarmePageObject.verificarAlarmeEHorario("Ao completar alarme");

        alarmePageObject.clicarNoBotaoRepetirLigagoOuDesligado();

        alarmePageObject.verificarSeMarcadorFoiExibido();

        alarmePageObject.clicarNoBotaoAdicionarMarcador();

        alarmePageObject.preencherCampoMarcador("Alarme Workshop");

        alarmePageObject.clicarNoBotaoOkEEvideciar("ao finalizar cadastro de alarme");

        alarmePageObject.clicarNoBotaoRecolheralarme();

        alarmePageObject.verificarAlarmeEHorario("ao finalizar inclusão do alarme");

        alarmePageObject.verificarSeMarcadorRecebeuNomeDefinido();

    }
//    @Test
//    public void excluirAlarme() throws IOException {
//        utils.iniciar();
//
//        clicarNoBotaoAlarmeEValidarTelaAlarme("Excluir alarme");
//
//        utils.driver.findElementByAccessibilityId("Monday, Tuesday, Wednesday, Thursday, Friday").click();
//        tirarPrintEImprimirNoLog("Clicar no Alarme que será excluido");
//
//        utils.driver.findElementById("com.google.android.deskclock:id/delete").click();
//        tirarPrintEImprimirNoLog("Clicar no botão para excluir Alarme Worshop");
//
//        Assert.assertTrue(utils.driver.findElementById("com.google.android.deskclock:id/snackbar_text").isDisplayed());
//        Assert.assertEquals("Alarm deleted", utils.driver.findElementById("com.google.android.deskclock:id/snackbar_text").getText());
//        tirarPrintEImprimirNoLog("Validamos que o Alarme foi excluido com sucesso");
//
//    }
}
