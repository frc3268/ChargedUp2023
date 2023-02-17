package frc.robot

/**
 * The Constants class provides a convenient place for teams to hold robot-wide numerical or boolean
 * constants. This class should not be used for any other purpose. All constants should be declared
 * globally (i.e. inside the companion object). Do not put anything functional in this class.
 *
 *
 * It is advised to statically import this class (or one of its inner classes) wherever the
 * constants are needed, to reduce verbosity.
 */
class Constants {
    object OperatorConstants {
        const val JoystickPort = 0
        const val FirstButton = 1
    }
    object motorConstants {
        const val driveLeftFrontID = 1
        const val driveLeftBackID = 2
        const val driveRightFrontID = 3
        const val driveRightBackID = 4
        const val gripperPort = 5
    }

    object errorCodes {
        const val frameNotFoundError = -1.0
        const val targetsNotFoundError = -2.0
    }

    data class Arm (
        val motorPort:Int,
        val kp:Double,
        val ki:Double,
        val kd:Double,
        val kiz:Double,
        val kff:Double,
        val kmaxoutput:Double,
        val kminoutput:Double,
    )

    data class targetResults(
        val pitch:Int
        val yaw:Int
    )

    val firstArm:Arm = Arm(1, 5.0,3.0,1.0,0.0,0.0,1.0,-1.0)
    val secondArm:Arm = Arm(2, 5.0,3.0,1.0,0.0,0.0,1.0,-1.0)
}
