/*
 * Copyright (c) 2023. StulSoft
 */

package com.stulsoft.sudoku

import com.stulsoft.common.ManifestInfo
import com.stulsoft.sudoku.model.Table

import scala.swing.*

object Main extends SimpleSwingApplication:
  override def top: Frame = new MainFrame:
    private val theMainFrame: MainFrame = this

    title = ManifestInfo("com.stulsoft", "sudoku").buildTitle("Sudoku")
    private val table = new Table
    table.initialize()
    contents = table

    menuBar = new MenuBar {
      contents += new Menu("Configuration") {
        contents += new MenuItem(Action("Change configuration") {
          ConfigDialog.showDialog(theMainFrame)
        })
      }

      contents += new Menu("Game") {
        contents += new MenuItem(Action("Regenerate") {
          table.initialize()
        })
        contents += new MenuItem(Action("Restore initial table state") {
          table.restoreInitialState()
        })
      }
    }

    size = new Dimension(600, 600)
    centerOnScreen()
