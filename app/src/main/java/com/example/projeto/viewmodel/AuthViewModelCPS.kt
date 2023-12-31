package com.example.projeto.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.projeto.listener.ListenerAuth
import com.example.projeto.repositorio.AuthRepositorioCPS
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

//classe criada ainda no processo de autenticação e criação dos usuarios:

@HiltViewModel
class AuthViewModelCPS @Inject constructor(private val authRepositorioCPS: AuthRepositorioCPS): ViewModel() {


    //Viewmodel dos professores/administração
    fun cpsCadastro(nome:String, email: String, senha: String, id:String, codigoEtec:String, listenerAuth: ListenerAuth){

        viewModelScope.launch {
            authRepositorioCPS.cpsCadastro(nome, email, senha, id, codigoEtec, listenerAuth)
        }

    }
}