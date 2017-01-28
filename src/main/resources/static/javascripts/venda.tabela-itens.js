Brewer.TabelaItens = (function() {
	
	function TabelaItens(autocompleteItens) {
		this.autocompleteItens = autocompleteItens;
		this.tabelaCervejasContainer = $('.js-tabela-cervejas-container');
	}
	
	TabelaItens.prototype.iniciar = function() {
		this.autocompleteItens.on('item-selecionado', onItemSelecionado.bind(this));
	}
	
	function onItemSelecionado(evento, item) {
		var resposta = $.ajax({
			url: 'item',
			method: 'POST',
			data: {
				codigoCerveja: item.codigo
			}
		});
		
		resposta.done(onItemAdicionadoNoServidor.bind(this));
	}
	
	function onItemAdicionadoNoServidor(html) {
		this.tabelaCervejasContainer.html(html);
		$('.js-tabela-cerveja-quantidade-item').on('change', onQuantidadeItemAlterado.bind(this));
		$('.js-tabela-item').on('dblclick', onDoubleClick);
		$('.js-exclusao-item-btn').on('click', onExclusaoItemClick.bind(this));
	}
	
	function onQuantidadeItemAlterado(evento) {
		var input = $(evento.target);
		var quantidade = input.val();
		var codigoCerveja = input.data('codigo');
		
		var resposta = $.ajax({
			url: 'item/' + codigoCerveja,
			method: 'PUT',
			data: {
				novaQuantidade: quantidade
			}
		});
		
		resposta.done(onItemAdicionadoNoServidor.bind(this));
	}
	
	function onDoubleClick(evento) {
		$(this).toggleClass('solicitando-exclusao');
	}
	
	function onExclusaoItemClick(evento) {
		var codigoCerveja = $(evento.target).data('codigo');
		var resposta = $.ajax({
			url: 'item/' + codigoCerveja,
			method: 'DELETE',
		});
		
		resposta.done(onItemAdicionadoNoServidor.bind(this));
	}
	
	return TabelaItens;
	
}());

$(function() {
	var autocompleteItens = new Brewer.AutocompleteItens();
	autocompleteItens.iniciar();
	
	var tabelaItens = new Brewer.TabelaItens(autocompleteItens);
	tabelaItens.iniciar();
});