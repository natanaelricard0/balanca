package br.com.cdp.balanca.model.services;

import br.com.cdp.balanca.model.dao.DaoFactory;
import br.com.cdp.balanca.model.dao.ItemPesagemDAO;
import br.com.cdp.balanca.model.entities.ItemPesagem;

public class ItemPesagemServices {

    ItemPesagemDAO service = DaoFactory.createItemPesagemDao();

    public void insert(ItemPesagem itemPesagem){
        service.insert(itemPesagem);
    }
}
