# Serratec-ReactNative-APICadastro
API para cadastro de usuários do sistema UniGames,  criado para o Projeto Final do módulo React Native da residência do Serratec.


<h3>POST de Usuário</h3>
http://localhost:8080/usuario

{
	"email": "luciana@gmail.com",
	"senha": "12345678",
	"nome": "Luluzinha",
	"cpf": "36422166095",
	"dataNascimento":"1999-09-20",
	"url": "https://img.ibxk.com.br/2012/5/materias/89232585816113157_crop.jpg?w=1120&h=420&mode=crop&scale=both"
}


<h3>GET Todos os Usuários</h3>
http://localhost:8080/usuario/todos


<h3>GET de Detalhes de um Usuário</h3>
http://localhost:8080/usuario/detalhe/{id}


<h3>PUT Altera dados do Usuário</h3>
http://localhost:8080/usuario/{1}

{
	"email": "luciana@gmail.com",
	"senha": "12345678",
	"nome": "Luluzinha Souza",
	"cpf": "36422166095",
	"dataNascimento":"1999-09-20",
	"url": "https://img.ibxk.com.br/2012/5/materias/89232585816113157_crop.jpg?w=1120&h=420&mode=crop&scale=both"
}

<h3>GET Desativar a Conta de um Usuário</h3>
http://localhost:8080/usuario/desativar/{email}


<h3>GET Reativar a Conta de um Usuário</h3>
http://localhost:8080/usuario/reativar/{email}


<h3>POST Solicitar email para recuperação de senha</h3>
http://localhost:8080/usuario/email-senha

{
	"email": "luciana_boher@gmail.com"
}


<h3>POST Alteração de Senha</h3>
http://localhost:8080/usuario/alterar-senha

{
	"email": "luciana_boher@gmail.com",
	"senha": "00000000"
}



<h3>POST de Authorization</h3>
http://localhost:8080/auth

{
	"user": "luciana@gmail.com",
	"pass": "12345678"
}
