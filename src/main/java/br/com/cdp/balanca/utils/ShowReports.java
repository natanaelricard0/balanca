package br.com.cdp.balanca.utils;

import br.com.cdp.balanca.model.entities.Pesagem;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import net.sf.jasperreports.view.JasperViewer;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class ShowReports {

    public static void printComprovantePrimeiraPesagem(Pesagem pesagem){
        List<Pesagem> lista = new ArrayList<>();
        lista.add(pesagem);

        try{
            JasperDesign desenho = JRXmlLoader.load(ShowReports.class.getResourceAsStream("/br/com/cdp/balanca/reports/comprovante_primeira_pesagem.jrxml"));
            JasperReport relatorio = JasperCompileManager.compileReport(desenho);
            JasperPrint impressao = JasperFillManager.fillReport(relatorio,null, new JRBeanCollectionDataSource(lista));
            JasperPrintManager.printPage(impressao,0,true);
        }catch (JRException e){
            e.printStackTrace();
        }
    }

    public static void printComprovanteSegundaPesagem(Pesagem pesagem){
        List<Pesagem> lista = new ArrayList<>();
        lista.add(pesagem);

        try{
            JasperDesign desenho = JRXmlLoader.load(ShowReports.class.getResourceAsStream("/br/com/cdp/balanca/reports/comprovante_segunda_pesagem.jrxml"));
            JasperReport relatorio = JasperCompileManager.compileReport(desenho);
            JasperPrint impressao = JasperFillManager.fillReport(relatorio,null, new JRBeanCollectionDataSource(lista));
            JasperPrintManager.printPage(impressao,0,true);
        }catch (JRException e){
            e.printStackTrace();
        }
    }

    public static void printRelatorioPesagem(List<Pesagem> lista){
        try{
            InputStream fonte = ShowReports.class.getResourceAsStream("/br/com/cdp/balanca/reports/relatorio_personalizado.jrxml");
            JasperReport relatorio = JasperCompileManager.compileReport(fonte);
            JasperPrint impressao = JasperFillManager.fillReport(relatorio,null, new JRBeanCollectionDataSource(lista));
//            JasperPrintManager.printPage(impressao,0,true);

            JasperViewer.viewReport(impressao,false);

        }catch (JRException e){
            e.printStackTrace();
        }
    }
}
