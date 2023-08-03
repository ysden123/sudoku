/*
 * Copyright (c) 2023. StulSoft
 */

package com.stulsoft.sudoku

import com.typesafe.scalalogging.StrictLogging

import javax.sound.sampled.{AudioFormat, AudioSystem}
import java.io.File
import scala.concurrent.{ExecutionContext, Future}

object Sound extends StrictLogging:
  given ExecutionContext = ExecutionContext.global
  def playFanfare():Unit=
    Future {
      try
        val clip = AudioSystem.getClip()
        val protocol = Sound.getClass.getResource("Sound.class").getProtocol
        val fileName = if protocol == "jar" then
          "sounds/fanfare.wav"
        else
          "src/universal/bin/sounds/fanfare.wav"
        val file = new File(fileName)
        clip.open(AudioSystem.getAudioInputStream(file))
        clip.start()
      catch
        case exception: Exception =>
          logger.error(exception.getMessage, exception)
    }
