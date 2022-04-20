package br.com.cdp.balanca.application;

import br.com.cdp.balanca.model.entities.Pesagem;
import br.com.cdp.balanca.model.services.PesagemServices;
import br.com.cdp.balanca.utils.ShowReports;

import java.util.ArrayList;
import java.util.List;

public class Teste {
    public static void main(String[] args){
        PesagemServices services = new PesagemServices();

        Pesagem pesagem = services.findById(1126418);

        List<Pesagem> lista = new ArrayList<>();
        lista.add(pesagem);

        ShowReports.printComprovanteSegundaPesagem(pesagem);
    }
}
