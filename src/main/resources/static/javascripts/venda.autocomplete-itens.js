Brewer = Brewer || {};

Brewer.AutocompleteItens = (function() {
	
	function AutocompleteItens() {
		this.skuOuNomeInput = $('.js-sku-nome-cerveja-input');
		var htmlTemplateAutocomplete = $('#template-autocomplete-cerveja').html();
		this.template = Handlebars.compile(htmlTemplateAutocomplete);
	}
	
	AutocompleteItens.prototype.iniciar = function() {
		var options = {
			url: function(skuOuNome) {
				return "/brewer/cervejas?skuOuNome=" + skuOuNome;
			} ,
			getValue: 'nome',
			minCharNumber: 3,
			requestDelay: 300,
			ajaxSettings: {
				contentType: 'application/json'
			},
			template: {
				type: 'custom',
				method: function(nome, cerveja) {
					cerveja.valorFormatado = Brewer.FormatarMoeda(cerveja.valor);
					return this.template(cerveja);
				}.bind(this)
			}
		};
		
		this.skuOuNomeInput.easyAutocomplete(options);
	}
	
	return AutocompleteItens;
	
}());

$(function() {
	var autocompleteItens = new Brewer.AutocompleteItens();
	autocompleteItens.iniciar();
})