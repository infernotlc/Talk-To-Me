package com.example.talk_to_me

sealed class Screens(val route:String){
    object LoginScreen:Screens("login_screen")
    object MainScreen:Screens("main_screen")
    object DetailScreen:Screens("detail_screen")
}
