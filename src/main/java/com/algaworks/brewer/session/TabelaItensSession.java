package com.algaworks.brewer.session;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.SessionScope;

import com.algaworks.brewer.model.Cerveja;
import com.algaworks.brewer.model.ItemVenda;

@SessionScope
@Component
public class TabelaItensSession {
	
	private Set<TabelaItensVenda> tabelas = new HashSet<>();

	public void adicionarIten(String uuid, Cerveja cerveja, int quantidade) {
		TabelaItensVenda tabela = buscarTabelaPorUuid(uuid);
		tabela.adicionarIten(cerveja, quantidade);
		tabelas.add(tabela);
	}

	public void alterarQuantidade(String uuid, Cerveja cerveja, Integer novaQuantidade) {
		TabelaItensVenda tabela = buscarTabelaPorUuid(uuid);
		tabela.alterarQuantidade(cerveja, novaQuantidade);
	}

	public void excluirItemVenda(String uuid, Cerveja cerveja) {
		TabelaItensVenda tabela = buscarTabelaPorUuid(uuid);
		tabela.excluirItemVenda(cerveja);
	}

	public List<ItemVenda> getItens(String uuid) {
		return buscarTabelaPorUuid(uuid).getItens();
	}
	private TabelaItensVenda buscarTabelaPorUuid(String uuid) {
		TabelaItensVenda tabela = tabelas.stream()
				.filter(t -> t.getUuid().equals(uuid))
				.findAny().orElse(new TabelaItensVenda(uuid));
		return tabela;
	}
}
