package com.example.projeto.datasource

import com.example.projeto.listener.ListenerAuth
import com.google.firebase.FirebaseNetworkException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.FirebaseAuthUserCollisionException
import com.google.firebase.auth.FirebaseAuthWeakPasswordException
import com.google.firebase.firestore.FirebaseFirestore
import javax.inject.Inject


//essa classe aqui está servindo apenas como autenticação de um cadastro. É praticamente pronto do firebase.
class Authentication @Inject constructor() {

    //iniciando o serviço de autenticação do firebase, é preciso fazer isso aqui:
    val auth = FirebaseAuth.getInstance()

    //Usado para interagir com o Firestore, aqui conseguimos usar isso para puxar os dados dele e manipular tudo
    val firestore = FirebaseFirestore.getInstance()


    //Primeiro os parâmetros para o cadastro do aluno:
    fun cadastroAluno(
        nome: String,
        email: String,
        senha: String,
        rm: String,
        codigoturma: String,
        listenerAuth: ListenerAuth
    ) {


        //Aqui criei uma variavel para acessar o conteúdo da coleção "Data" lá do firebase, ela vai servir como "porta" pra todos os dados.
        val colecaoData = firestore.collection("Data")
        //E aqui em baixo usamos a variavel de cima para acessar o RM e utilizar na variavel abaixo (rmDocumento). Ou seja, a de cima é a porta e a de baixo uma pessoa usando ela.
        val rmDocumento =  colecaoData.document("RM")


        //Eu não consegui fazer o processo de validação dentro dos if la em baixo mas, de toda forma, não existe problema aparente em validar antes, o compose está sempre sendo atualizado.
        //Puxando os dados
        rmDocumento.get()
            .addOnSuccessListener { documentSnapshot ->
                //Agora uma variavel para auxiliar na validação do aluno e garantir que o rm dele exista. Começa como false, e se o RM existir irá mudar para true. Vai me ajudar com a validação:
                var condicaoRM = false
                //E uma variavel para guardar o valor do rm do usuário atual. Vou usar ela para validar também:
                val rmBox = rm


                val rmData = documentSnapshot.data
                //aqui usamos a variavel acima para puxar todos os dados do array que eu criei no firebase e inserir na variavel "arrayRM"
                //Ou seja, eu fiz uma cópia do array original do firebase e armazenei em uma variavel aqui localmente.
                val arrayRM = rmData?.get("rm_lista") as? List<String>


                //Começo da validação para garantir que o aluno está matriculado na escola:
                if (arrayRM != null) {
                    for (item in arrayRM) {
                        if (item == rmBox) {//Se o indice for igual ao rmBox (que é o rm que o usuário vai inserir, a condição vai para true).
                            condicaoRM = true
                            // Uma vez que encontramos um valor verdadeiro, não precisamos continuar o loop.
                            break
                        }
                    }
                }


                //Começo da validação para garantir que o rm já não está cadastrado por algum aluno e acabe sobreescrevendo os dados:
                val alunoColecao = firestore.collection("Alunos")
                //Uma condição para garantir nos If abaixo:
                var cadastroRM = false //Ela começa como false, e se o rm nao for achado na coleção dos alunos irá mudar para true.

                alunoColecao.get()
                    .addOnSuccessListener { querySnapshot ->
                        for (document in querySnapshot.documents) {
                            val alunoData = document.data
                            val rmAluno = alunoData?.get("rm") as? String

                            if (rmAluno == rm) {
                                cadastroRM = true
                                break // Não é necessário continuar o loop se já encontramos um rm matriculado.
                            }

                        }
                    }
                    .addOnFailureListener { exception ->
                        listenerAuth.onFailure("RM já cadastrado por outro usuário!")
                    }

                //Agora a validação de todos os campos preenchidos:
                if (nome.isEmpty()) {
                    listenerAuth.onFailure("Insira o seu nome para a identificação!")
                } else if (email.isEmpty()) {
                    listenerAuth.onFailure("O email não pode estar vazio.")
                } else if (senha.isEmpty()) {
                    listenerAuth.onFailure("Insira uma senha válida!")
                } else if (rm.isEmpty()) {
                    listenerAuth.onFailure("O RM é necessário para a validação!")
                }
                else if (codigoturma.isEmpty()) {
                    listenerAuth.onFailure("Forneça o seu código de turma!")
                }
                else if(condicaoRM == true && cadastroRM == false){
                    //testando a validação:
                    auth.createUserWithEmailAndPassword(email, senha)
                        .addOnCompleteListener { cadastro ->
                            if (cadastro.isSuccessful) { //Depois que o cadastro for um "sucesso", a gente vai salvar os seguintes dados no firebase:

                                //Pegando o ID do usuário que acabou de se cadastrar usando o "auth", ou seja, o usuário atual.
                                var usuarioID = FirebaseAuth.getInstance().currentUser?.uid.toString()

                                //mapeando o que eu quero salvar com as variaveis nos parâmetros:
                                val dadosUsuariosMap = hashMapOf(
                                    "nome" to nome,
                                    "email" to email,
                                    "rm" to rm,
                                    "codigoturma" to codigoturma,
                                    "usuarioID" to usuarioID
                                )

                                //Iniciando o comando para setar um caminho para uma coleção e gravando os dados nela
                                //O document(rm) significa que o identificador de cada aluno vai se dar pelo RM. Caso deixe o nome vai acabar sobreescrevendo os dados
                                firestore.collection("Alunos").document(rm).set(dadosUsuariosMap)
                                    .addOnCompleteListener {
                                        listenerAuth.onSucess("Usuário cadastrado com sucesso!")
                                    }.addOnFailureListener {
                                        listenerAuth.onFailure("Ocorreu um erro ao salvar os dados.")
                                    }

                            }
                        }//Fechamento do cadastrado com sucesso. Agora, vamos tratar alguns possíveis erros que evite o cadastro:


                        //Comando pra iniciar o tratamento de "erro" no cadastramento:
                        .addOnFailureListener { exception ->

                            //"quando" o erro for igual a determinada coisa, faça tal coisa:
                            val erro = when (exception) {
                                is FirebaseAuthUserCollisionException -> "Outra conta já cadastrada com estes dados."
                                is FirebaseAuthWeakPasswordException -> "A senha deve ter no mínimo 6 caracteres."
                                is FirebaseNetworkException -> "Sem conexão."

                                else -> "Email inválido."
                            }
                            listenerAuth.onFailure(erro)
                        }
                }

                else {
                    listenerAuth.onFailure("RM não encontrado no banco de dados.")
                }

            }
      }


    //Após cadastrar vamos chamar a função de login.
    fun loginAluno(email:String, senha: String, listenerAuth: ListenerAuth){

        if (email.isEmpty()){
            listenerAuth.onFailure("Insira o seu email!")
        }
        else if(senha.isEmpty()){
            listenerAuth.onFailure("Insira a sua senha!")
        }
        else{
            auth.signInWithEmailAndPassword(email,senha).addOnCompleteListener{login ->
                if (login.isSuccessful){
                    listenerAuth.onSucess("Login efetuado com sucesso. Bem vindo(a)!")
                }
            }
                .addOnFailureListener{exception ->
                    val erro = when(exception){
                        is FirebaseAuthInvalidCredentialsException -> "Senha incorreta."
                        is FirebaseNetworkException -> "Sem conexão."

                        else -> "Dados inválidos."
                    }
                    listenerAuth.onFailure(erro)
                }
        }
    }

                     ////////////////////////////////////////////////////



}
