package br.com.liandro.page;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import org.apache.commons.io.FileUtils;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;

import java.io.File;
import java.io.IOException;
import java.sql.Timestamp;

public class AlarmePageObject {

    AppiumDriver<MobileElement> driver;
    public AlarmePageObject(AppiumDriver<MobileElement> driver) {
        this.driver = driver;
    }

    public void tirarPrintTela(String nomeEtapa) throws IOException {

        Timestamp timeNow = new Timestamp(System.currentTimeMillis());
        String timeNowFormatado;
        File evidencia = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
        String nomeEtapaFormatado = nomeEtapa
                .toLowerCase()
                .replace("ã", "a")
                .replace("ç", "c")
                .replace(" ", "_");
        timeNowFormatado = timeNow.toString()
                .replace(":", ".")
                .replace(" ", "_");
        FileUtils.moveFile(evidencia, new File("target/screenshots/evidencia_" + nomeEtapaFormatado + "_" + timeNowFormatado + ".jpg"));
    }

    public void tirarPrintEImprimirNoLog(String passoAtual) throws IOException {
        System.out.println(passoAtual);
        tirarPrintTela(passoAtual);
    }

    public void verificarAlarmeEHorario(String passoNome) throws IOException {
        Assert.assertTrue(driver.findElementByXPath("//android.widget.TextView[@content-desc=\"10:00 PM\"]").isDisplayed());
        Assert.assertEquals("10:00 PM", driver.findElementByXPath("//android.widget.TextView[@content-desc=\"10:00 PM\"]").getText());
        tirarPrintEImprimirNoLog("Confirmei que a hora cadastrada esta de acordo com os valores enviados " + passoNome);
    }

    public void clicarNoBotaoOkEEvideciar(String passoAtual) throws IOException {
        driver.findElementByXPath("//android.widget.Button[@text='OK']").click();
        tirarPrintEImprimirNoLog("Cliquei no botao OK " + passoAtual);
    }

    public void clicarNoBotaoAlarmeEValidarTelaAlarme(String passoAtual) throws IOException {
        driver.findElement(By.xpath("//android.widget.TextView[@text='Alarm']")).click();
        tirarPrintEImprimirNoLog("Clicou no botão Alarme " + passoAtual);

        Assert.assertTrue(driver.findElement(By.xpath("//android.widget.TextView[@text='Alarm']")).isDisplayed());
        Assert.assertEquals("Alarm", driver.findElement(By.xpath("//android.widget.TextView[@text='Alarm']")).getText());
        tirarPrintEImprimirNoLog("Validamos que a tela de alarme foi exibida" + passoAtual);
    }

    public void clicarEmAdicionarAlarme() throws IOException {
        driver.findElementByAccessibilityId("Add alarm").click();
        tirarPrintEImprimirNoLog("Clicou no botão Adicionar alarme");

    }

    public void clicarEmAlternarModoDeEntrada() throws IOException {
        driver.findElementByAccessibilityId("Switch to text input mode for the time input.").click();
        tirarPrintEImprimirNoLog("Clicou no botão Adicionar mudança de entrada de texto");
    }

    public void verificarQueDialogFoiExibido() throws IOException {
        Assert.assertTrue(driver.findElementById("android:id/input_header").isDisplayed());
        Assert.assertEquals("Set time", driver.findElementById("android:id/input_header").getText());
        tirarPrintEImprimirNoLog("Confirmei que a dialog foi exibida");
    }

    public void preencherCampoHoras(String hora) throws IOException {
        driver.findElementById("android:id/input_hour").clear();
        driver.findElementById("android:id/input_hour").sendKeys(hora);
        tirarPrintEImprimirNoLog("Lancei o valor 10 no campo hora");
    }

    public void preencherCampoMinutos(String minutos) throws IOException {
        driver.findElementById("android:id/input_minute").clear();
        driver.findElementById("android:id/input_minute").sendKeys(minutos);
        tirarPrintEImprimirNoLog("Lancei o valor 00 no campo minuto");
        driver.findElementById("android:id/am_pm_spinner").click();
        driver.findElementByXPath("//android.widget.CheckedTextView[@text='PM']").click();
    }

    public void clicarNoBotaoRepetirLigagoOuDesligado() throws IOException {
        driver.findElementById("com.google.android.deskclock:id/repeat_onoff").click();
        tirarPrintEImprimirNoLog("Cliquei no botao para repetir o alarme");
    }

    public void verificarSeMarcadorFoiExibido() throws IOException {
        driver.findElementById("com.google.android.deskclock:id/edit_label").isDisplayed();
        Assert.assertEquals("Label", driver.findElementById("com.google.android.deskclock:id/edit_label").getText());
        tirarPrintEImprimirNoLog("Confirmei que a dialog do marcador foi exibida com sucesso");
    }

    public void clicarNoBotaoAdicionarMarcador() throws IOException {
        driver.findElementById("com.google.android.deskclock:id/edit_label").click();
        tirarPrintEImprimirNoLog("Cliquei no botão label");
    }

    public void preencherCampoMarcador(String nomeMarcador) throws IOException {
        driver.findElementById("com.google.android.deskclock:id/label_input_field").clear();
        driver.findElementById("com.google.android.deskclock:id/label_input_field").sendKeys(nomeMarcador);
        tirarPrintEImprimirNoLog("Lancei o valor Alarme Workshop no campo label");
    }

    public void clicarNoBotaoRecolheralarme() throws IOException {
        driver.findElementByAccessibilityId("Collapse alarm").click();
        tirarPrintEImprimirNoLog("Cliquei no bortão recolher alarme");
    }

    public void verificarSeMarcadorRecebeuNomeDefinido() throws IOException {
        driver.findElementByAccessibilityId("Label Alarme Workshop").isDisplayed();
        Assert.assertEquals("Alarme Workshop", driver.findElementByAccessibilityId("Label Alarme Workshop").getText());
        tirarPrintEImprimirNoLog("Confirmei que o marcador recebeu o nome de Alarme Workshop");

    }
}
