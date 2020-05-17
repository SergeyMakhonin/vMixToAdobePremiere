package GUI

import javafx.collections.FXCollections
import javafx.scene.control.ChoiceBox
import javafx.scene.control.TextField
import javafx.scene.layout.Pane
import main
import tornadofx.*

class MyApp: App(MainView::class)

class MainView : View() {

    override val root: Pane by fxml("MainView.fxml")
    private val channel: ChoiceBox<String> by fxid("channel")
    private val frames: TextField by fxid("frames")

    fun runMain(){
        main(frames.text, channel.selectionModel.selectedItem)
    }
}