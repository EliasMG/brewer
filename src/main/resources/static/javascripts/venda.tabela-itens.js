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
	}
	
	return TabelaItens;
	
}());

$(function() {
	var autocompleteItens = new Brewer.AutocompleteItens();
	autocompleteItens.iniciar();
	
	var tabelaItens = new Brewer.TabelaItens(autocompleteItens);
	tabelaItens.iniciar();
});