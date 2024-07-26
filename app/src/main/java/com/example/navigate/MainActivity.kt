package com.example.navigate

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SearchBar
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.dialog
import androidx.navigation.compose.rememberNavController
import com.example.navigate.ui.theme.NavigateTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val nav = rememberNavController()
            NavigateTheme {
            // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    AppNavHost(nav)
                }
            }
        }
    }
}

@Composable
fun Home(nav: NavHostController){
    Text(text = "Food Plaza", color = Color.Red, fontSize = 50.sp,
        modifier = Modifier.padding(5.dp), textAlign = TextAlign.Center,
        fontFamily = FontFamily.Serif, textDecoration = TextDecoration.Underline
    )
    Text(text = "Choose your best food have a great day",
        color = Color.Gray, fontSize = 23.sp,
        fontFamily = FontFamily.Serif, modifier = Modifier.padding(0.dp, 90.dp, 0.dp, 0.dp))
    Image(painter = painterResource(R.drawable.image),
        modifier = Modifier
            .size(30.dp)
            .padding(0.dp, 160.dp, 0.dp, 0.dp),
        contentDescription = "Food image",
        alignment = Alignment.TopCenter,
    )
    Image(painter = painterResource(R.drawable.image2),
        contentDescription = "Image 2",
        modifier = Modifier.padding(0.dp, 350.dp, 0.dp, 0.dp))
    Row(verticalAlignment = Alignment.Bottom, horizontalArrangement = Arrangement.Center, modifier = Modifier.padding(0.dp, 0.dp, 0.dp, 15.dp))
    {
        Button(
            onClick = { nav.navigate("menu") }, modifier = Modifier.wrapContentSize(),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color.Red,
                contentColor = Color.White
            )
        ) {
            Text(text = "Place Order")
        }
        Button(
            onClick = { nav.navigate("search") }, modifier = Modifier.wrapContentSize(),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color.Red,
                contentColor = Color.White
            )
        ) {
            Text(text = "Search Items")
        }
    }
}


@Composable
fun Menu(nav: NavHostController){
    Box(contentAlignment = Alignment.TopStart) {
        Row {
            Button(onClick = { nav.navigate("home") },
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.Red,
                    contentColor = Color.White
                ), modifier = Modifier
                    .padding(5.dp)
                    .wrapContentSize()
            ) {
                Text(text = "<-")
            }
            Text(text = "Choose Items", color = Color.Red,
                fontSize = 20.sp, modifier = Modifier.padding(80.dp, 10.dp, 0.dp, 0.dp))
        }
    }
    val items= listOf("Veg Burger","Chicken Burger", "Veg Loaded Pizza", "Chicken Shwarma", "Tandoori Naan", "Butter Roti", "Chicken Tikka", "Butter Chicken", "Chicken Biryani", "Veg Biryani")
    val checkedstate= remember {
        items.map { mutableStateOf(false) }
    }
    Column(verticalArrangement = Arrangement.Center) {
        items.forEachIndexed(){ index, item->
            Row(modifier = Modifier
                .fillMaxWidth()
                .padding(5.dp)) {
                Checkbox(checked = checkedstate[index].value, onCheckedChange =
                {
                    isChecked->checkedstate[index].value=isChecked
                    if(isChecked)
                        check.add(item)
                    else check.remove(item)
                }, Modifier.size(60.dp))
                Text(text = item, modifier = Modifier.padding(5.dp), fontSize = 30.sp)
            }
        }
    }
    Box(contentAlignment = Alignment.BottomCenter) {
        Button(onClick = { nav.navigate("checkout")},
            colors = ButtonDefaults.buttonColors(
                containerColor = Color.Red,
                contentColor = Color.White
            ), modifier = Modifier
                .padding(5.dp)
                .wrapContentSize()
        ) {
            Text(text = "Go to Checkout")
        }
    }
}

val check= mutableListOf<String>()

@Composable
fun AppNavHost(nav:NavHostController){
    NavHost(navController = nav, startDestination = "home"){
        composable(route = Screen.Home.route){
            Home(nav)
        }
        composable(route = Screen.Menu.route){
            Menu(nav)
        }
        composable(route = Screen.Checkout.route){
            Checkout(nav)
        }
        dialog(route=Screen.Alert.route){
            Alert(nav)
        }
        composable(route=Screen.Search.route){
            Search(nav)
        }
    }
}

@Composable
fun Checkout(nav: NavHostController){
    Box(contentAlignment = Alignment.TopCenter) {
        Text(text = "Confirm Order", color = Color.Red, fontSize = 25.sp)
    }
    Box(contentAlignment = Alignment.Center){
        Column {
            for(i in check){
                Text(text = i, fontSize = 35.sp)
            }
        }
    }
    Box(contentAlignment = Alignment.BottomCenter, modifier = Modifier.padding(0.dp, 0.dp, 0.dp, 10.dp)) {
        Row {
            Button(onClick = {nav.navigate("menu") }, colors = ButtonDefaults.buttonColors(
                contentColor = Color.White,
                containerColor = Color.Red
            )) {
                Text(text = "Edit Items", Modifier.wrapContentSize())
            }
            Button(onClick = {nav.navigate("alert") }, colors = ButtonDefaults.buttonColors(
                contentColor = Color.White,
                containerColor = Color.Red
            )){
                Text(text = "Cancel Order", Modifier.wrapContentSize())
            }
        }
    }
}

@Composable
fun Alert(nav: NavHostController){
    Box(
        Modifier
            .background(color = Color.White)
            .height(150.dp)
            .wrapContentSize()) {
        Text(text = "Are you sure to cancel your order?", fontSize = 20.sp)
        Row(verticalAlignment = Alignment.Bottom, modifier = Modifier.padding(60.dp, 90.dp, 0.dp, 0.dp)) {
            Button(onClick = { nav.popBackStack() }) {
                Text(text = "Discard")
            }
            Button(onClick = { nav.navigate("home") }) {
                Text(text = "OK")
            }
        }
    }
}

@Composable
fun Search(nav: NavHostController){
    val search= remember {
        mutableStateOf("")
    }
    TextField(value = "", onValueChange = {search.value=it},
        placeholder = {Text(text = "Search Items")})
    Box(contentAlignment = Alignment.Center) {
        Text(text = search.value)
    }
}

open class Screen(val route:String){
    object Home: Screen("home")
    object Menu: Screen("menu")
    object Checkout:Screen("checkout")
    object Alert:Screen("alert")
    object Search:Screen("search")
}

//@Preview(showBackground = true)
//@Composable
//fun GreetingPreview() {
//    NavigateTheme {
//        Greeting("Android")
//    }
//}