package com.example.projeto.layoutsprontos


import android.content.Context
import android.graphics.Bitmap
import android.graphics.ImageDecoder
import android.net.Uri
import android.os.Build
import android.provider.MediaStore
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.example.projeto.ui.theme.Dongle
import com.example.projeto.ui.theme.LARANJA
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.Center
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Alignment.Companion.CenterStart
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.compose.ui.zIndex
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.projeto.R
import com.example.projeto.bottomNavigation.BottomNavItem
import com.example.projeto.ui.theme.AZULCLARO
import kotlinx.coroutines.launch


//Carregar uma imagem do github:
@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun loadImage(path:String, contentDescription:String, contentScale: ContentScale, modifier: Modifier){
    GlideImage(
    model = path,
    contentDescription = contentDescription,
    contentScale = contentScale,
    modifier = modifier
    )

 }


@Composable
fun OutlinedLogin(value:String, onValueChange: (String) -> Unit, label:String, keyboardOptions: KeyboardOptions, visualTransformation: VisualTransformation, leadingIcon: @Composable (() -> Unit)? = null){

    OutlinedTextField(
        value = value,
        onValueChange,
        label = {
            Text(text = label,
                fontFamily = Dongle,
                fontSize = 27.sp,
            )
        },
        colors = TextFieldDefaults.outlinedTextFieldColors(
            unfocusedBorderColor = Color.White,
            focusedBorderColor = LARANJA,
            focusedLabelColor = LARANJA,
            backgroundColor = Color(0xFFF2f2f2),
            cursorColor = LARANJA,
        ),
        keyboardOptions = keyboardOptions,
        maxLines = 1,
        singleLine = true,
        shape = RoundedCornerShape(50.dp),
        visualTransformation = visualTransformation,
        leadingIcon = leadingIcon,
        modifier = Modifier
            .padding(horizontal = 40.dp)
            .padding(bottom = 7.dp)
    )

}

@Composable
fun OutlinedRegistro(value: String, onValueChange: (String) -> Unit, label:String, keyboardOptions: KeyboardOptions,visualTransformation: VisualTransformation,leadingIcon: @Composable (() -> Unit)? = null){
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        label = {
            Text(text = label,
                fontFamily = Dongle,
                fontSize = 35.sp,
            )
        },
        keyboardOptions = keyboardOptions,
        maxLines = 1,
        singleLine = true,
        colors = TextFieldDefaults.outlinedTextFieldColors(
            unfocusedBorderColor = Color(0xA1000000),
            focusedBorderColor = LARANJA,
            focusedLabelColor = LARANJA,
            backgroundColor = Color(0xFFFFFFFF),
            cursorColor = LARANJA,
        ),
        visualTransformation = visualTransformation,
        shape = RoundedCornerShape(50.dp),
        leadingIcon = leadingIcon,
        modifier = Modifier
            .padding(bottom = 0.dp)
            .padding(bottom = 5.dp)
            .padding(horizontal = 0.dp)
            .fillMaxWidth()
            .height(60.dp)

    )
}

@Composable
fun BotaoEscolha(onClick: () -> Unit, text:String, fontSize: TextUnit = 36.sp, imageVector: ImageVector, descricao:String, imageVector2: ImageVector, modifier: Modifier, spacerWidth: Dp){

    Button(onClick = onClick,
        colors = ButtonDefaults.buttonColors(
            backgroundColor = Color(0xFFF2f2f2),
        ),
        shape = RoundedCornerShape(25.dp),
        contentPadding = PaddingValues(0.dp),
        modifier = Modifier

    ) {
        IconButton(
            onClick = onClick,

        ) {

            Row(
                horizontalArrangement = Arrangement.Start,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {
                IconButton(
                    onClick = onClick,
                        // Ação ao clicar no ícone

                ) {
                    Image(
                        imageVector = imageVector,
                        contentDescription = descricao,
                        modifier = Modifier.size(24.dp)
                    )
                }

                Text(
                    text = text,
                    fontFamily = Dongle,
                    color = Color(0xFFF5E5E5E),
                    fontSize = fontSize,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(top = 5.dp, start = (0).dp)
                )

                //Spacer para separar o nome do play:
                Spacer(modifier = Modifier
                    .width(spacerWidth)
                    .border(1.dp, Color.Red))


                IconButton(
                    onClick = onClick,
                        // Ação ao clicar no ícone
                ) {
                    Image(
                        imageVector = imageVector2,
                        contentDescription = "",
                        modifier = Modifier.size(20.dp)
                    )
                }

            }
        }
    }
}

@Composable
fun CheckBoxPersonalizada(onCheckedChange : (Boolean) -> Unit){
    var isChecked by remember { mutableStateOf(false) }

    Checkbox(
        checked = isChecked,
        onCheckedChange = {
            isChecked = it
            onCheckedChange(it)
        },
        colors = CheckboxDefaults.colors(
            checkedColor = LARANJA,
            uncheckedColor = Color.Gray,
        ),

    )
}

@Composable
fun BotaoRegistrar(onClick: () -> Unit, corBotao: Color, fontSize: TextUnit = 22.sp) {
    Button(
        onClick = onClick,
        colors = ButtonDefaults.buttonColors(
            backgroundColor = corBotao
        ),
        shape = RoundedCornerShape(30.dp),
        modifier = Modifier.width(200.dp)
    ) {
        Text(text = "Registrar-se",
        color = Color.White,
        fontSize = fontSize)
    }
}

@Composable
fun TextDuasCores(color1: Color, color2:Color, texto1: String, texto2: String, fontSize: TextUnit, onclick: () -> Unit) {
    val text = buildAnnotatedString {
        withStyle(style = SpanStyle(color = color1)) {
            append(texto1)
        }
        withStyle(style = SpanStyle(color = color2)) {
            append(texto2)
        }
    }



    Text(
        text = text,
        fontSize = fontSize,
        overflow = TextOverflow.Ellipsis,
        maxLines = 1,
        modifier = Modifier
            .padding(top = 13.dp, end = 17.dp)
            .clickable(onClick = {
                onclick()

            })
    )
}


@Composable
fun AlertDialogPersonalizado(
    dialogo: MutableState<Boolean>,
    onDismissRequest: () -> Unit,
    cor: Color
) {
    val scrollState = rememberScrollState()

    if (dialogo.value) {

        Dialog(
            onDismissRequest = { onDismissRequest() },
            properties = DialogProperties(
                dismissOnClickOutside = true,
            ),
        ) {
            // Conteúdo
            //Esse card serve para nao bugar e ficar sem fundo
            Card(
                elevation = 5.dp,
                shape = RoundedCornerShape(16.dp),
                backgroundColor = Color.White,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(460.dp)
            ) {
                Column(
                    modifier = Modifier
                        .padding(16.dp)
                        .verticalScroll(scrollState)
                ) {
                    Text(text = "Termos e Condições",
                        fontSize = 37.sp,
                        fontFamily = Dongle,
                        color = cor,
                        fontWeight = FontWeight.Bold
                    )
                    //Linha apenas para estética
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .size(2.dp)
                            .background(color = Color(209, 209, 209, 255))
                    ) {}
                    Spacer(modifier = Modifier.height(16.dp))

                        Text(
                            text = "Bem-vindo à nossa rede social móvel para alunos e professores da [ETEC Zona Leste]. Estes termos e condições regem o uso do nosso aplicativo. Ao utilizá-lo, você concorda expressamente com os seguintes termos e condições:",
                            fontSize = 16.sp,
                            modifier = Modifier.padding(bottom = 18.dp)
                        )
                    //Privacidade
                        Text(text = "1. Privacidade:", fontSize = 16.sp, color = cor, modifier = Modifier.padding(bottom = 5.dp))
                        Text(text = "Respeitamos sua privacidade e comprometemo-nos a proteger seus dados pessoais. Para obter informações detalhadas sobre como os coletamos, usamos e protegemos suas informações pessoais, consulte algum dos desenvolvedores."
                            ,fontSize = 16.sp, modifier = Modifier.padding(bottom = 10.dp))
                    //Cadastro de usuário
                        Text(text = "2. Cadastro de usuário:", fontSize = 16.sp, color = cor, modifier = Modifier.padding(bottom = 5.dp))
                        Text(text = "Para utilizar nosso aplicativo, você deve criar uma conta. Você é responsável por manter a confidencialidade de suas credenciais de login e por todas as atividades que ocorrerem em sua conta durante o uso."
                            ,fontSize = 16.sp, modifier = Modifier.padding(bottom = 10.dp))
                    //Uso Aceitável
                        Text(text = "3. Uso Aceitável:", fontSize = 16.sp, color = cor, modifier = Modifier.padding(bottom = 5.dp))
                        Text(text = "Você concorda em utilizar nosso aplicativo de maneira respeitosa e ética. Comportamentos inadequados como assédio, machismo, racismo ou qualquer outro tipo de discurso de ódio que viole os direitos de terceiros, não será tolerado."
                            ,fontSize = 16.sp, modifier = Modifier.padding(bottom = 10.dp))
                    //Propriedade Intelectual:
                        Text(text = "4. Propriedade Intelectual:", fontSize = 16.sp, color = cor, modifier = Modifier.padding(bottom = 5.dp))
                        Text(text = "Todo o conteúdo gerado pelos usuários como postagens, fotos, vídeos ou comentários, pertence aos respectivos criadores. Você não tem permissão para usar esse conteúdo sem a devida autorização."
                            ,fontSize = 16.sp, modifier = Modifier.padding(bottom = 10.dp))
                    //Responsabilidade:
                        Text(text = "5. Responsabilidade:", fontSize = 16.sp, color = cor, modifier = Modifier.padding(bottom = 5.dp))
                        Text(text = "Você reconhece que os desenvolvedores não são responsáveis por qualquer dano, perda, inconveniência ou prejuízo causado pelo uso de nosso aplicativo. Utilize-o por sua conta e risco."
                            ,fontSize = 16.sp, modifier = Modifier.padding(bottom = 10.dp))
                    //Encerramento de Conta:
                        Text(text = "6. Encerramento de conta:", fontSize = 16.sp, color = cor, modifier = Modifier.padding(bottom = 5.dp))
                        Text(text = "Você pode encerrar sua conta a qualquer momento contactando qualquer um dos desenvolvedores. Isso resultará na exclusão permanente de seus dados, ou seja, não podemos recuperar informações de contas excluídas."
                            ,fontSize = 16.sp, modifier = Modifier.padding(bottom = 10.dp))
                    //Diretrizes de Conteúdo:
                        Text(text = "7. Diretrizes de Conteúdo:", fontSize = 16.sp, color = cor, modifier = Modifier.padding(bottom = 5.dp))
                        Text(text = "É estritamente proibido qualquer tipo de postagem que inclua conteúdo ilegal, como discurso de ódio, nudez, violência, etc. O usuário irá arcar com as consequências caso o mesmo ocorra."
                            ,fontSize = 16.sp, modifier = Modifier.padding(bottom = 10.dp))
                    //Rescisão de Serviço:
                        Text(text = "8. Rescisão de Serviço:", fontSize = 16.sp, color = cor, modifier = Modifier.padding(bottom = 5.dp))
                        Text(text = "Reservamos o direito para que os desenvolvedores possam encerrar ou modificar o serviço a qualquer momento, com ou sem aviso prévio."
                            ,fontSize = 16.sp, modifier = Modifier.padding(bottom = 10.dp))


                    Spacer(modifier = Modifier.height(16.dp))

                    Text(text = "Entendido",
                        color = cor,
                        fontSize = 18.sp,
                        modifier = Modifier
                            .clickable(
                                onClick = { onDismissRequest() }
                            )
                            .padding(start = 200.dp)
                    )
                }
            }
        }



    }
}

@Composable
fun BottomNavigationBar(items: List<BottomNavItem>, navController: NavController, modifier: Modifier = Modifier, onClickItem: (BottomNavItem) -> Unit, ModifierIcon : Modifier){

    val backStackEntry = navController.currentBackStackEntryAsState()

    BottomNavigation(
        modifier = modifier,
        backgroundColor = Color(0xFFFAFAFA),
        elevation = 20.dp
    ) {
        items.forEach{ item ->

            val selected = item.route == backStackEntry.value?.destination?.route
            BottomNavigationItem(
                selected = selected,
                onClick = {onClickItem(item)},
                selectedContentColor = AZULCLARO,
                unselectedContentColor = Color(0xFF383838),
                icon = {
                    Column(
                        horizontalAlignment = CenterHorizontally
                    ) {
                        if (item.badgeCount > 0){
                            BadgedBox(
                                badge = {
                                    Surface(
                                        color = AZULCLARO,
                                        shape = CircleShape,
                                        modifier = Modifier
                                           // .border(2.dp, Color.Black)
                                            .size(20.dp)
                                            //.clip(CircleShape)
                                    ) {
                                        Column(
                                            verticalArrangement = Arrangement.Center,
                                            horizontalAlignment = CenterHorizontally,
                                            modifier = Modifier.fillMaxSize()
                                        ) {
                                            if (item.badgeCount > 99){
                                                Text(text = "99+",
                                                    color = Color.White,
                                                    fontWeight = FontWeight.Bold,
                                                    textAlign = TextAlign.Center,
                                                    fontSize = 11.sp

                                                )
                                            }
                                            else{
                                                Text(text = item.badgeCount.toString(),
                                                    color = Color.White,
                                                    fontWeight = FontWeight.Bold,
                                                    textAlign = TextAlign.Center,
                                                    fontSize = 11.sp

                                                )
                                            }

                                        }

                                    }

                                }
                            ) {
                                Icon(imageVector = item.icon,
                                    contentDescription = item.nome,
                                    modifier = item.iconModifier ?: Modifier
                                )
                            }
                        }
                        else{
                            Icon(imageVector = item.icon,
                                contentDescription = item.nome,
                                modifier = item.iconModifier ?: Modifier
                            )
                        }
                        if (selected){
                            Text(text = item.nome,
                                textAlign = TextAlign.Center,
                                fontSize = 10.sp
                            )
                        }
                    }

                }
            )
        }
    }

}

@Composable
fun botaoDrawer(onClick: () -> Unit){

    IconButton(
        onClick = onClick,
        modifier = Modifier
            .size(40.dp)
            .padding(start = 5.dp)
            .padding(top = 15.dp)
    ){
        Image(ImageVector.vectorResource(id = R.drawable.ic_drawermenu),
            contentDescription = "Publicar",)
    }
}


@Composable
fun drawerPersonalizado(){

    Column(modifier = Modifier
        .fillMaxSize()
        .border(2.dp, Color.Black)
        .zIndex(1F)) {
        Text(text = "Texto1")
        Text(text = "Texto2")
        Text(text = "Texto3")
        Text(text = "Texto4")
    }
}


@OptIn(ExperimentalMaterialApi::class)
@Composable
fun CardPostagem(cardState: Boolean, onCardClose: () -> Unit){

    //toda a palhaçada do jetpack só pra abrir o bottomshet
    val sheetState = rememberBottomSheetState(initialValue = BottomSheetValue.Collapsed)
    val scaffoldState = rememberBottomSheetScaffoldState(bottomSheetState = sheetState)
    val scope = rememberCoroutineScope()
    //

    if (cardState){
        Card(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            elevation = 18.dp
        ) {
            //Essa aqui é a parte de baixo (vai entender kkkk)
            BottomSheetScaffold(
                scaffoldState = scaffoldState,
                sheetContent = {
                    Box(modifier = Modifier
                        .fillMaxWidth()
                        .height(240.dp))
                    {
                        Column(
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(start = 10.dp)
                                .padding(end = 10.dp),
                            horizontalAlignment = CenterHorizontally,
                        ) {
                            //botão para subir o bottomsheet
                            IconButton(onClick = {
                                scope.launch {
                                    if (sheetState.isCollapsed){
                                        sheetState.expand()
                                    }
                                    else{
                                        sheetState.collapse()
                                    }

                                }
                            }) {
                                Image(ImageVector.vectorResource(id = R.drawable.ic_minus),
                                    contentDescription = "Subir o BottomSheet",
                                    modifier = Modifier
                                        .size(80.dp),
                                    colorFilter = ColorFilter.tint(Color(0xFFC5C4C4))
                                )
                            }

                            //Midias
                            Row(
                                //contentAlignment = CenterStart,
                               modifier = Modifier
                                   .fillMaxWidth()
                                   .border(1.dp, Color.Red)
                                   .clickable(
                                       onClick = {


                                       })
                            ) {
                                //Imagem da imagem
                                Image(ImageVector.vectorResource(id = R.drawable.ic_imagem),
                                    contentDescription = "Adicionar foto ou video",
                                    modifier = Modifier
                                        .size(38.dp)
                                    /*colorFilter = ColorFilter.tint(Color(0xFFC5C4C4)
                                    )*/
                                )
                                
                                //Só pra espaçar um pouco a imagem e o texto
                                Spacer(modifier = Modifier
                                    .width(20.dp)
                                    .border(2.dp, Color.Green))

                                //Texto da Imagem
                                Text(text = "Foto/vídeo",
                                    fontSize = 25.sp,
                                    color = Color(0xFF303030),
                                )
                            }

                        }

                    }
            },
                sheetBackgroundColor = Color(0xFFFFFFFF),
                sheetShape = RoundedCornerShape(25.dp, 25.dp,0.dp, 0.dp),
                sheetElevation = 8.dp,
                )
            { //e a partir destas chaves é a parte de cima
                Card(modifier = Modifier.fillMaxSize(),
                    elevation = 10.dp,
                    backgroundColor = Color(0xFFFAFAFA)
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .size(10.dp)
                            .border(2.dp, Color.Black)
                    ) {
                        Text("Container top")

                    }
                }
            }

            //ConstraintLayout para posicionar o que precisa
            ConstraintLayout(modifier = Modifier.fillMaxSize()) {
                val (fecharcard) = createRefs()

                IconButton(
                    onClick = { onCardClose() },
                    modifier = Modifier
                        .constrainAs(fecharcard) {
                            top.linkTo(parent.top)
                            start.linkTo(parent.start)
                        }
                        .size(60.dp)
                ){
                    Image(ImageVector.vectorResource(id = R.drawable.ic_fechar),
                        contentDescription = "Fechar o Card",
                    )
                }
            }

        }
    }


}




//Preview:
@Composable
@Preview(showBackground = true)
fun previewLayouts(){

}