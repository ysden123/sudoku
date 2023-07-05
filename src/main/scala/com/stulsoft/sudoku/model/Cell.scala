/*
 * Copyright (c) 2023. StulSoft
 */

package com.stulsoft.sudoku.model

import java.awt.Color
import javax.swing.border.EtchedBorder
import javax.swing.text.StyleConstants.FontConstants
import scala.swing.BorderPanel.Position
import scala.swing.{Graphics2D, Label}

class Cell() extends Label(""):
  private var theValue: Option[Int] = None
  private var isActive: Boolean = false

  opaque = true
  border = new EtchedBorder

  def value: Option[Int] = theValue

  def value_=(aValue: Option[Int]): Unit =
    require((aValue.isDefined && (aValue.get > 0 && aValue.get < 10)) || aValue.isEmpty)
    theValue = aValue
    text = if value.isDefined then value.get.toString else ""

  def active:Boolean = isActive

  def active_=(isActive:Boolean):Unit=
    this.isActive=isActive

  override def paint(g: Graphics2D): Unit =
    val stringWidth = g.getFontMetrics.stringWidth(text)
    val componentWidth = bounds.width
    // Find out how much the font can grow in width.
    val widthRatio = componentWidth.toFloat / stringWidth.toFloat

    val newFontSize = (font.getSize * widthRatio).toInt
    val componentHeight = bounds.height

    // Pick a new font size so it will not be larger than the height of label.
    val fontSizeToUse = Math.min(newFontSize, componentHeight)

    // Set the label's font size to the newly determined size.
    font = font.deriveFont(fontSizeToUse.toFloat)

    background = if active then Color.RED else Color.GRAY

    super.paint(g)
  end paint

  override def toString: String =
    s"Cell: value: $value, active: $active"
