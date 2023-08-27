package com.example.projeto.views

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.projeto.R
import com.example.projeto.layoutsprontos.loadImage
import com.example.projeto.ui.theme.Dongle
import com.example.projeto.ui.theme.Jomhuria

@Composable
fun RegistroAluno(navController: NavController) {


    //Background
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Red)
    ) {
        loadImage(path = "",
            contentDescription = "Background Registrar",
            contentScale = ContentScale.Crop,
            modifier = Modifier.fillMaxSize()
        )
    }


    //Começo constraint
    ConstraintLayout(
        modifier = Modifier.fillMaxSize()
    ) {
        val (capeloBox,titulo,identificacao) = createRefs()


        //Capelo
        Box(
            modifier = Modifier
                .constrainAs(capeloBox) {
                    top.linkTo(parent.top, margin = 80.dp)
                    start.linkTo(parent.start, margin = 100.dp)
                    end.linkTo(parent.end, margin = 100.dp)
                }
                .size(150.dp)
        ) {
            loadImage(path = "https://raw.githubusercontent.com/jonatas1096/Projeto/master/app/src/main/res/drawable/capelo.png",
                contentDescription = "Capelo de aluno",
                contentScale = ContentScale.Fit,
                modifier = Modifier
            )
        }

        //Titulo
        Text(text = "ConnectSTUDENT",
            modifier = Modifier.constrainAs(titulo){
                top.linkTo(parent.top, margin = 140.dp)
                start.linkTo(parent.start)
                end.linkTo(parent.end)
            }
            ,
            fontSize = 78.sp,
            color = Color.White,
            fontFamily = Jomhuria,
        )

        //Identificação
        Row(
            modifier = Modifier
                .constrainAs(identificacao){
                    top.linkTo(parent.top, margin = 220.dp)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                }
        ) {
            Icon(imageVector = ImageVector.vectorResource(id = R.drawable.ic_aluno),
                contentDescription = "Você está na área do Aluno.",
                modifier = Modifier.size(40.dp))
            
            Text(text = "Aluno",
                fontSize = 44.sp,
                color = Color.White,
                fontWeight = FontWeight.Bold,
                fontFamily = Dongle)
        }
    }


}



@Preview(showBackground = true)
@Composable
fun PreviewAluno(){
    RegistroAluno(navController = rememberNavController())
}