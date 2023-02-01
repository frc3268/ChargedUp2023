package frc.robot

import edu.wpi.first.wpilibj.Joystick
import edu.wpi.first.wpilibj2.command.button.JoystickButton

/**
 * Add your docs here.
 */
class IO {

    val joystick:Joystick = Joystick(Constants.OperatorConstants.JoystickPort)
    val firstButton:JoystickButton = JoystickButton(joystick, Constants.OperatorConstants.FirstButton)

}
