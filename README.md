# Serratec-ReactNative-APICadastro
API para cadastro de usuários do sistema UniGames,  criado para o Projeto Final do módulo React Native da residência do Serratec.


<h3>POST de Usuário</h3>
http://localhost:8080/usuario

{
	"email": "luciana@gmail.com",
	"senha": "12345678",
	"nome": "Luluzinha",
	"cpf": "36422166095",
	"dataNascimento":"1999-09-20"
}


<h3>GET Todos os Usuários</h3>
http://localhost:8080/usuario/todos


<h3>POST de Authorization</h3>
http://localhost:8080/auth

{
	"user": "luciana@gmail.com",
	"pass": "12345678"
}


<h3>GET de Detalhes de um Usuário</h3>
http://localhost:8080/usuario/detalhe/{id}
