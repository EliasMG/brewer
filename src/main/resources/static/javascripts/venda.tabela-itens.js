Brewer.TabelaItens = (function() {
	
	function TabelaItens(autocompleteItens) {
		this.autocompleteItens = autocompleteItens;
		this.tabelaCervejasContainer = $('.js-tabela-cervejas-container');
		this.uuid = $('#uuid').val();
		this.emitter = $({});
		this.on = this.emitter.on.bind(this.emitter);
	}
	
	TabelaItens.prototype.iniciar = function() {
		this.autocompleteItens.on('item-selecionado', onItemSelecionado.bind(this));
	}
	
	function onItemSelecionado(evento, item) {
		var resposta = $.ajax({
			url: 'item',
			method: 'POST',
			data: {
				codigoCerveja: item.codigo,
				uuid: this.uuid
			}
		});
		
		resposta.done(onItemAdicionadoNoServidor.bind(this));
	}
	
	function onItemAdicionadoNoServidor(html) {
		this.tabelaCervejasContainer.html(html);
		var quantidadeItemInput = $('.js-tabela-cerveja-quantidade-item');
		var tabelaItem = $('.js-tabela-item');
		
		quantidadeItemInput.on('change', onQuantidadeItemAlterado.bind(this));
		quantidadeItemInput.maskMoney({ precision: 0, thousands: '' });
		
		tabelaItem.on('dblclick', onDoubleClick);
		$('.js-exclusao-item-btn').on('click', onExclusaoItemClick.bind(this));
		
		this.emitter.trigger('tabela-itens-atualizada', tabelaItem.data('valor-total'));
	}
	
	function onQuantidadeItemAlterado(evento) {
		var input = $(evento.target);
		var quantidade = input.val();
		
		if (quantidade <= 0) {
			input.val(1);
			quantidade = 1;
		}
		
		var codigoCerveja = input.data('codigo');
		
		var resposta = $.ajax({
			url: 'item/' + codigoCerveja,
			method: 'PUT',
			data: {
				novaQuantidade: quantidade,
				uuid: this.uuid
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
			url: 'item/' + this.uuid + '/' + codigoCerveja,
			method: 'DELETE',
		});
		
		resposta.done(onItemAdicionadoNoServidor.bind(this));
	}
	
	return TabelaItens;
	
}());