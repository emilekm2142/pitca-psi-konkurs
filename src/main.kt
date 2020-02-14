
fun main(args: Array<String>) {
    val mainView = MainView(i)
    i.currentView=mainView;
    while (true){
        i.loop()
    }
}