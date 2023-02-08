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
    object driveConstants {
        const val driveLeftFrontID = 0
        const val driveLeftBackID = 1
        const val driveRightFrontID = 2
        const val driveRightBackID = 3
    }

    data class Arm (
        var motorPort:Int,
        var kp:Double,
        var ki:Double,
        var kd:Double,
        var kiz:Double,
        var kff:Double,
        var kmaxoutput:Double,
        var kminoutput:Double,
    )

    val firstArm:Arm = Arm(1, 5.0,3.0,1.0,0.0,0.0,1.0,-1.0)
    val secondArm:Arm = Arm(2, 5.0,3.0,1.0,0.0,0.0,1.0,-1.0)
}
