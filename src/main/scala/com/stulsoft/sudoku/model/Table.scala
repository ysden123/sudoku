/*
 * Copyright (c) 2023. StulSoft
 */

package com.stulsoft.sudoku.model

import com.stulsoft.sudoku.logic.{Util, Validator}

import javax.swing.border.EtchedBorder
import scala.swing.GridPanel
import scala.util.Random

class Table extends GridPanel(3, 3):
  for (i <- 1 to 9)
    contents += new Square

  border = new EtchedBorder

  def clear(): Unit =
    for {
      row <- 1 to 3
      column <- 1 to 3
    } square(row, column).clear()

  def initialize(): Unit =
    val start = System.currentTimeMillis()
    val random = Random(System.currentTimeMillis())
    var maxIteration = 0
    var maxAttempt = 0
    var tableFilled = false
    for (attempt <- 1 to 10 if !tableFilled) {
      clear()
      for (row <- 1 to 9)
        var rowFilled = false
        for (iteration <- 1 to 10 if !rowFilled)
          for (column <- 1 to 9)
            val numbers = random.shuffle(List(1, 2, 3, 4, 5, 6, 7, 8, 9))
            var done = false
            for {
              n <- numbers if !done
            } {
              if Validator.isValidInRow(this, row, Some(n))
                && Validator.isValidInColumn(this, column, Some(n))
                && Validator.isValidInSquare(square(Util.squareIndex(row), Util.squareIndex(column)), Some(n)) then
                updateCell(row, column, Some(n))
                done = true
            }
          end for
          rowFilled = countFilledCellsInRow(row) == 9
          if iteration > maxIteration then maxIteration = iteration
        end for
      end for
      tableFilled = countFilledCellsInTable() == 9 * 9
      maxAttempt=attempt
    }
    println(s"Duration: ${Util.durationToString(start, System.currentTimeMillis())}, tableFilled=$tableFilled, maxAttempt=$maxAttempt, maxIteration=$maxIteration")
    if tableFilled then
      for(row <- 1 to 9)
        val numbers = random.shuffle(List(1, 2, 3, 4, 5, 6, 7, 8, 9)).toArray
        for(columnItem <- 0 to 1)
          val column = numbers(columnItem)
          updateCell(row, column, None)
    else
      clear()

  /**
   * Returns a square with specified column and column.
   *
   * @param row    the column, starting with 1
   * @param column the column, starting with 1
   * @return the square with specified column and column.
   */
  def square(row: Int, column: Int): Square =
    require(row >= 1 && row <= 3)
    require(column >= 1 && column <= 3)
    val index = (row - 1) * 3 + (column - 1)
    contents(index).asInstanceOf[Square]

  /**
   * Assigns a new value to specified cell.
   *
   * @param row    the table row, starting with 1
   * @param column the table column, starting with 1
   * @param value  the new value (None or Some(1), ..., Some(9))
   */
  def updateCell(row: Int, column: Int, value: Option[Int]): Unit =
    require((value.isDefined && (value.get > 0 && value.get < 10)) || value.isEmpty)
    val squareRowIndex = Util.squareIndex(row)
    val rowInSquare = Util.indexInSquare(row)

    val squareColumnIndex = Util.squareIndex(column)
    val columnInSquare = Util.indexInSquare(column)

    square(squareRowIndex, squareColumnIndex)
      .cell(rowInSquare, columnInSquare)
      .value = value

  private def countFilledCellsInRow(row: Int): Int =
    var count = 0
    val squareRowIndex = Util.squareIndex(row)
    val rowInSquare = Util.indexInSquare(row)

    for (column <- 1 to 9)
      val squareColumnIndex = Util.squareIndex(column)
      val columnInSquare = Util.indexInSquare(column)
      square(squareRowIndex, squareColumnIndex)
        .cell(rowInSquare, columnInSquare)
        .value.foreach(_ => count += 1)

    count

  private def countFilledCellsInTable(): Int =
    var count = 0
    for (row <- 1 to 9)
      count += countFilledCellsInRow(row)
    count