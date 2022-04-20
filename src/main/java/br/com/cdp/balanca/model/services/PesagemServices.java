package br.com.cdp.balanca.model.services;

import br.com.cdp.balanca.model.dao.DaoFactory;
import br.com.cdp.balanca.model.dao.ItemAutorizacaoDAO;
import br.com.cdp.balanca.model.dao.ItemPesagemDAO;
import br.com.cdp.balanca.model.dao.PesagemDAO;
import br.com.cdp.balanca.model.entities.ItemAutorizacao;
import br.com.cdp.balanca.model.entities.ItemPesagem;
import br.com.cdp.balanca.model.entities.Pesagem;

import java.util.List;

public class PesagemServices {

    PesagemDAO service = DaoFactory.createPesagemDao();

    ItemPesagemDAO itemPesagemService = DaoFactory.createItemPesagemDao();

    ItemAutorizacaoDAO itemAutorizacaoService = DaoFactory.createItemAutorizacaoDao();

    public List<Pesagem> listarPesagensPendentes(){
        return service.pesagensPendentes();
    }

    public List<Pesagem> listarPesagensPesquisa(String valor){
        return service.buscarPesagensPorPlaca(valor);
    }

    public void insertPrimeiraPesagem(Pesagem pesagem){ service.insertPesagemPendente(pesagem);}

    public void insertSegundaPesagem(Pesagem pesagem){ service.updatePesagemPendente(pesagem);}

    public Pesagem findById(int id){ return service.findById(id); }

    public void insertSegundaPesagem(Pesagem pesagem, Float pesoLiquido){
        service.updatePesagemPendente(pesagem);
        service.insert(pesagem);
        ItemAutorizacao itemAutorizacao = itemAutorizacaoService.findById(pesagem.getIdAutorizacao());
        ItemPesagem itemPesagem = new ItemPesagem();
        itemPesagem.setIdAutorizacaoEntradaSaida(pesagem.getIdAutorizacao());
        itemPesagem.setIdItemIO(itemAutorizacao.getIdItemEmbarqueDesembarque());
        itemPesagem.setIdSubitemIO(itemAutorizacao.getIdSubitemEmbarqueDesembarque());
        itemPesagem.setPesoLiquido(pesoLiquido);
        itemPesagemService.insert(itemPesagem);
    }

    public void insertPesagemImportacao(Pesagem pesagem){
        service.insert(pesagem);
        ItemAutorizacao itemAutorizacao = itemAutorizacaoService.findById(pesagem.getIdAutorizacao());
        ItemPesagem itemPesagem = new ItemPesagem();
        itemPesagem.setIdAutorizacaoEntradaSaida(pesagem.getIdAutorizacao());
        itemPesagem.setIdItemIO(itemAutorizacao.getIdItemEmbarqueDesembarque());
        itemPesagem.setIdSubitemIO(itemAutorizacao.getIdSubitemEmbarqueDesembarque());
        itemPesagem.setPesoLiquido(pesagem.getPesoLiquido());
        itemPesagemService.insert(itemPesagem);
    }
}
