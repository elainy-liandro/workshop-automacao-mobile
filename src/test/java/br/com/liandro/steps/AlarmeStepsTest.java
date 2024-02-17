package br.com.liandro.steps;

import br.com.liandro.page.AlarmePageObject;
import br.com.liandro.utils.Utils;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.pt.Dado;
import io.cucumber.java.pt.E;
import io.cucumber.java.pt.Então;
import io.cucumber.java.pt.Quando;

import org.junit.FixMethodOrder;
import org.junit.runners.MethodSorters;

import java.io.IOException;
import java.net.MalformedURLException;


@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class AlarmeStepsTest {
    private Utils utils = new Utils();
    AlarmePageObject alarmePageObject;


    @Before
    public void iniciar() throws MalformedURLException {
        utils.iniciar();
        alarmePageObject = new AlarmePageObject(utils.driver);
    }

    @After
    public void finalizar() {
        utils.driver.quit();
    }

    @Dado("que esteja na tela inicial do App relógio")
    public void que_esteja_na_tela_inicial_do_app_relógio() throws IOException {
          alarmePageObject.clicarNoBotaoAlarmeEValidarTelaAlarme("adicionar Um Alarme");
    }

    @Quando("eu clicar em adicionar um alarme")
    public void eu_clicar_em_adicionar_um_alarme() throws IOException {
        alarmePageObject.clicarEmAdicionarAlarme();
    }

    @E("preencher todos os requisitos")
    public void preencher_todos_os_requisitos() throws IOException {

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
    }

    @Então("devo validar que foi adicionado um alarme com sucesso")
    public void devo_validar_que_foi_adicionado_um_alarme_com_sucesso() throws IOException {
        alarmePageObject.verificarAlarmeEHorario("ao finalizar inclusão do alarme");
        alarmePageObject.verificarSeMarcadorRecebeuNomeDefinido();
    }

}
