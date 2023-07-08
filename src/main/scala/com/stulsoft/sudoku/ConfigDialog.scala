/*
 * Copyright (c) 2023. StulSoft
 */

package com.stulsoft.sudoku

import scala.swing.BorderPanel.Position
import scala.swing.event.ButtonClicked
import scala.swing.{BorderPanel, Button, Dialog, Dimension, GridPanel, Label, MainFrame, TextField}

object ConfigDialog:
  def showDialog(mainFrame: MainFrame): Unit =
    new Dialog(mainFrame){
      val dialogFrame: Dialog = this
      title = "Configuration"
      size = new Dimension(300, 300)
      resizable = false
      centerOnScreen()
      val levelField = new TextField(Config.level().toString)

      val okButton: Button = new Button("OK"){
        reactions += {
          case ButtonClicked(_) =>
            Config.level = levelField.text.toInt
            Config.save()
            dialogFrame.close()
        }
      }

      val cancelButton:Button=new Button("Cancel"){
        reactions += {
          case ButtonClicked(_) =>
            dialogFrame.close()
        }
      }

      val dataPanel: GridPanel = new GridPanel(1, 2) {
        contents ++= Seq(new Label("level"), levelField)
      }

      val buttonPanel: GridPanel = new GridPanel(2, 2) {
        contents ++= Seq(new Label(""), new Label(""), okButton, cancelButton)
      }
      contents = new BorderPanel {
        layout(dataPanel) = Position.Center
        layout(buttonPanel) = Position.South
      }
    }.open()