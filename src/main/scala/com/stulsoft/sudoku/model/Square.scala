/*
 * Copyright (c) 2023. StulSoft
 */

package com.stulsoft.sudoku.model

import com.stulsoft.sudoku.model.Cell

import javax.swing.border.EtchedBorder
import scala.swing.GridPanel

class Square(val table:Option[Table] = None) extends GridPanel(3, 3):
  for (i <- 1 to 9)
    contents += new Cell(table)

  border = new EtchedBorder

  /**
   * Returns a cell with specified column and column.
   *
   * @param row    the column, staring with 1
   * @param column te column, starting with 1
   * @return the cell with specified column and column.
   */
  def cell(row: Int, column: Int): Cell =
    require(row >= 1 && row <= 3)
    require(column >= 1 && column <= 3)
    val index = (row - 1) * 3 + (column - 1)
    contents(index).asInstanceOf[Cell]

  def clear(): Unit =
    for {
      row <- 1 to 3
      column <- 1 to 3
    } {
      cell(row, column).value = None
      cell(row, column).editable = false
    }