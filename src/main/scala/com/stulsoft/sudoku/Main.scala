/*
 * Copyright (c) 2023. StulSoft
 */

package com.stulsoft.sudoku

import com.stulsoft.sudoku.model.{Cell, Square, Table}
import com.typesafe.scalalogging.LazyLogging

import java.awt.Color
import scala.swing.{Action, Dimension, Frame, Label, MainFrame, Menu, MenuBar, MenuItem, SimpleSwingApplication}

object Main extends SimpleSwingApplication:
  override def top: Frame = new MainFrame:
    private val theMainFrame: MainFrame = this
    private val manifestInfo = ManifestInfo("com.stulsoft", "sudoku")
    private val version = manifestInfo.version() match
      case Some(version) =>
        version
      case None =>
        ""
    private val buildDate = manifestInfo.buildDate() match
      case Some(theBuildDate) => theBuildDate
      case None => ""
    title = "Sudoku " + version + " " + buildDate

    /*
        private val testLabel = Cell()
        testLabel.value = Some(1)
        testLabel.active = true
        contents = testLabel
    */
    /*
            private val square=new Square
            square.cell(1,1).value = Some(1)
            square.cell(1,2).value = Some(2)
            square.cell(1,3).value = Some(3)
            square.cell(2,2).value = Some(4)
            square.cell(3,1).value = Some(5)
            square.cell(3,3).value = Some(7)
            contents = square
    */
    private val table = new Table
    /*
        table.square(2,2).cell(2,2).value = Some(5)
        table.square(2,2).cell(2,2).active = true
    */
    table.initialize()
    /*
        table.initialize()
        table.initialize()
        table.initialize()
    */
    contents = table

    menuBar = new MenuBar {
      contents += new Menu("Configuration") {
        contents += new MenuItem(Action("Change configuration") {
          ConfigDialog.showDialog(theMainFrame)
        })
      }

      contents += new Menu("Game"){
        contents += new MenuItem(Action("Regenerate"){
          table.initialize()
        })
        contents += new MenuItem(Action("Restore initial table state"){
          table.restoreInitialState()
        })
      }
    }

    size = new Dimension(600, 600)
    centerOnScreen()
