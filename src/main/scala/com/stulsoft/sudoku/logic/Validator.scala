/*
 * Copyright (c) 2023. StulSoft
 */

package com.stulsoft.sudoku.logic

import com.stulsoft.sudoku.model.{Square, Table}

object Validator:
  def isValidSquare(square: Square): Boolean =
    var isValid = true
    val usedValues = Array.fill[Int](9)(0)
    for {
      row <- 1 to 3 if isValid
      column <- 1 to 3 if isValid
    } {
      val cell = square.cell(row, column)
      val value = cell.value
      if value.isDefined then
        val index = value.get
        usedValues(index - 1) += 1
        if usedValues(index - 1) > 1 then isValid = false
    }

    isValid

  def isValidInSquare(square: Square, value: Option[Int]): Boolean =
    var isValid = true
    if value.isDefined then
      for {
        row <- 1 to 3 if isValid
        column <- 1 to 3 if isValid
      } {
        val theValue = square.cell(row, column).value
        if theValue.isDefined && theValue.get == value.get then isValid = false
      }

    isValid

  def isValidRow(table: Table, row: Int): Boolean =
    var isValid = true
    val usedValues = Array.fill[Int](9)(0)
    val squareRowIndex = Util.squareIndex(row)
    val rowInSquare = Util.indexInSquare(row)
    for (squareIndex <- 1 to 3 if isValid)
      val square = table.square(squareRowIndex, squareIndex)
      for (column <- 1 to 3 if isValid)
        val cell = square.cell(rowInSquare, column)
        val value = cell.value
        if value.isDefined then
          val index = value.get
          usedValues(index - 1) += 1
          if usedValues(index - 1) > 1 then isValid = false

    isValid

  def isValidInRow(table: Table, row: Int, value: Option[Int]): Boolean =
    var isValid = true
    if value.isDefined then
      val squareRowIndex = Util.squareIndex(row)
      val rowInSquare = Util.indexInSquare(row)

      for {squareIndex <- 1 to 3 if isValid
           cellIndex <- 1 to 3 if isValid
           } {
        val square = table.square(squareRowIndex, cellIndex)
        for (columnIndex <- 1 to 3 if isValid)
          val theValue = square.cell(rowInSquare, columnIndex).value
          if theValue.isDefined && theValue.get == value.get then isValid = false
      }
    isValid

  def isValidColumn(table: Table, column: Int): Boolean =
    var isValid = true
    val usedValues = Array.fill[Int](9)(0)
    val squareColumnIndex = Util.squareIndex(column)
    val columnInSquare = Util.indexInSquare(column)
    for (squareIndex <- 1 to 3 if isValid)
      val square = table.square(squareIndex, squareColumnIndex)
      for (row <- 1 to 3 if isValid)
        val cell = square.cell(row, columnInSquare)
        val value = cell.value
        if value.isDefined then
          val index = value.get
          usedValues(index - 1) += 1
          if usedValues(index - 1) > 1 then isValid = false

    isValid

  def isValidInColumn(table: Table, column: Int, value: Option[Int]): Boolean =
    var isValid = true
    if value.isDefined then
      val squareColumnIndex = Util.squareIndex(column)
      val columnInSquare = Util.indexInSquare(column)

      for {
        squareIndex <- 1 to 3 if isValid
        cellIndex <- 1 to 3 if isValid
      } {
        val square = table.square(cellIndex, squareColumnIndex)
        for (rowIndex <- 1 to 3 if isValid)
          val theValue = square.cell(rowIndex, columnInSquare).value
          if theValue.isDefined && theValue.get == value.get then isValid = false
      }

    isValid

  def isValidTable(table: Table): Boolean =
    var isValid = true

    for (row <- 1 to 9 if isValid)
      isValid = isValidRow(table, row)

    for (column <- 1 to 9 if isValid)
      isValid = isValidColumn(table, column)

    for {
      row <- 1 to 3 if isValid
      column <- 1 to 3 if isValid
    }
      isValid = isValidSquare(table.square(row, column))

    isValid