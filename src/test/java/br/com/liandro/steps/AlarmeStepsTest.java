package br.com.liandro.steps;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.remote.MobileCapabilityType;
import org.apache.commons.io.FileUtils;
import org.junit.Assert;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.remote.DesiredCapabilities;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.sql.Timestamp;
import java.util.concurrent.TimeUnit;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class AlarmeStepsTest {

    private AppiumDriver driver;
    DesiredCapabilities capabilities = new DesiredCapabilities();

    public void iniciar() throws MalformedURLException {
        capabilities.setCapability(MobileCapabilityType.PLATFORM_NAME, "Android");
        capabilities.setCapability(MobileCapabilityType.DEVICE_NAME, "emulator");
        capabilities.setCapability(MobileCapabilityType.UDID, "emulator-5554");
        capabilities.setCapability(MobileCapabilityType.AUTOMATION_NAME, "UiAutomator2");
        capabilities.setCapability("appPackage", "com.google.android.deskclock");
        capabilities.setCapability("appActivity", "com.android.deskclock.DeskClock");

        driver = new AppiumDriver(new URL("http://127.0.0.1:4723/"), capabilities);
        driver.manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS);
    }

    public void tirarPrintTela(String nomeEtapa) throws IOException {

        Timestamp timeNow = new Timestamp(System.currentTimeMillis());
        String timeNowFormatado;
        File evidencia = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
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
        tirarPrintEImprimirNoLog("Confirmei que a hora cadastrada esta de acordo com os valores enviados" + passoNome);
    }
    public void clicarNoBotaoOkEEvideciar(String passoAtual) throws IOException {
        driver.findElementByXPath("//android.widget.Button[@text='OK']").click();
        tirarPrintEImprimirNoLog("Cliquei no botao OK" + passoAtual);
    }
    public void clicarNoBotaoAlarmeEValidarTelaAlarme(String passoAtual) throws IOException {
        driver.findElement(By.xpath("//android.widget.TextView[@text='Alarm']")).click();
        tirarPrintEImprimirNoLog("Clicou no botão Alarme" + passoAtual);

        Assert.assertTrue(driver.findElement(By.xpath("//android.widget.TextView[@text='Alarm']")).isDisplayed());
        Assert.assertEquals("Alarm", driver.findElement(By.xpath("//android.widget.TextView[@text='Alarm']")).getText());
        tirarPrintEImprimirNoLog("Validamos que a tela  de alarme foi exibida" + passoAtual);
    }

    @Test
    public void adicionarUmAlarme() throws IOException {
        iniciar();

        clicarNoBotaoAlarmeEValidarTelaAlarme("adicionar Um Alarme");

        driver.findElementByAccessibilityId("Add alarm").click();
        tirarPrintEImprimirNoLog("Clicou no botão Adicionar alarme");

        driver.findElementByAccessibilityId("Switch to text input mode for the time input.").click();
        tirarPrintEImprimirNoLog("Clicou no botão Adicionar mudança de entrada de texto");

        Assert.assertTrue(driver.findElementById("android:id/input_header").isDisplayed());
        Assert.assertEquals("Set time", driver.findElementById("android:id/input_header").getText());
        tirarPrintEImprimirNoLog("Confirmei que a dialog foi exibida");

        driver.findElementById("android:id/input_hour").clear();
        driver.findElementById("android:id/input_hour").sendKeys("10");
        tirarPrintEImprimirNoLog("Lancei o valor 10 no campo hora");

        driver.findElementById("android:id/input_minute").clear();
        driver.findElementById("android:id/input_minute").sendKeys("00");
        tirarPrintEImprimirNoLog("Lancei o valor 00 no campo minuto");

        clicarNoBotaoOkEEvideciar("ao completar envio de valores ao alarme");

        verificarAlarmeEHorario("Ao completar alarme");

        driver.findElementById("com.google.android.deskclock:id/repeat_onoff").click();
        tirarPrintEImprimirNoLog("Cliquei no botao para repetir o alarme");

        Assert.assertTrue(driver.findElementById("com.google.android.deskclock:id/edit_label").isDisplayed());
        Assert.assertEquals("Label", driver.findElementById("com.google.android.deskclock:id/edit_label").getText());
        tirarPrintEImprimirNoLog("Confirmei que a dialog do marcador foi exibida com sucesso");

        driver.findElementById("com.google.android.deskclock:id/edit_label").click();
        tirarPrintEImprimirNoLog("Cliquei no botão label");

        driver.findElementById("com.google.android.deskclock:id/label_input_field").clear();
        driver.findElementById("com.google.android.deskclock:id/label_input_field").sendKeys("Alarme Workshop");
        tirarPrintEImprimirNoLog("Lancei o valor Alarme Workshop no campo label");

        clicarNoBotaoOkEEvideciar("ao finalizar cadastro de alarme");

        driver.findElementByAccessibilityId("Collapse alarm").click();
        tirarPrintEImprimirNoLog("Cliquei no bortão recolher alarme");

        verificarAlarmeEHorario("ao finalizar inclusão do alarme");

        driver.findElementByAccessibilityId("Label Alarme Workshop").isDisplayed();
        Assert.assertEquals("Alarme Workshop", driver.findElementByAccessibilityId("Label Alarme Workshop").getText());
        tirarPrintEImprimirNoLog("Confirmei que o marcador recebeu o nome de Alarme Workshop");

    }
    @Test
    public void excluirAlarme() throws IOException {
        iniciar();

        clicarNoBotaoAlarmeEValidarTelaAlarme("Excluir alarme");


//        Assert.assertTrue(driver.findElement(By.xpath("//android.widget.TextView[@text='Alarm']")).isDisplayed());
//        Assert.assertEquals("Alarm", driver.findElement(By.xpath("//android.widget.TextView[@text='Alarm']")).getText());
//        tirarPrintEImprimirNoLog("Validamos que a tela do Alarme foi xibida");

        driver.findElementByAccessibilityId("Monday, Tuesday, Wednesday, Thursday, Friday").click();
        tirarPrintEImprimirNoLog("Clicar no Alarme que será excluido");

        driver.findElementById("com.google.android.deskclock:id/delete").click();
        tirarPrintEImprimirNoLog("Clicar no botão para excluir Alarme Worshop");

        Assert.assertTrue(driver.findElementById("com.google.android.deskclock:id/snackbar_text").isDisplayed());
        Assert.assertEquals("Alarm deleted", driver.findElementById("com.google.android.deskclock:id/snackbar_text").getText());
        tirarPrintEImprimirNoLog("Validamos que o Alarme foi excluido com sucesso");

    }
}
