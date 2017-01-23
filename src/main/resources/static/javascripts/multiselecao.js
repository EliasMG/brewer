Brewer = Brewer || {};

Brewer.MultiSelecao = (function() {
	
	function MultiSelecao() {
		this.statusBtn = $('.js-status-btn');
	} 
	
	MultiSelecao.prototype.iniciar = function() {
		this.statusBtn.on('click', onStatusBtnClicado.bind(this));
	}
	
	function onStatusBtnClicado(event) {
		var botaoClicado = $(event.currentTarget);
		console.log(botaoClicado.data('status'))
						
	}
	
	return MultiSelecao;
	
}());

$(function() {
	
	var multiSelecao = new Brewer.MultiSelecao();
	multiSelecao.iniciar();
	
});