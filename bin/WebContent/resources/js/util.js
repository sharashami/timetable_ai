
$(document).ready(function() {
	$('#frmGeral\\:enviar').click(function() {
		var senha = $('#frmGeral\\:senha').val(); //pegando o valor da senha
		var hash = CryptoJS.SHA256(senha); //gerando o hash
		$('#frmGeral\\:senha').val(hash); //atualizando o valor da senha
	});
});


function gerarHash() {
	
}


