package frc.robot

/**
 * The Constants class provides a convenient place for teams to hold robot-wide numerical or boolean
 * constants. This class should not be used for any other purpose. All constants should be declared
 * globally (i.e. inside the companion object). Do not put anything functional in this class.
 *
 * It is advised to statically import this class (or one of its inner classes) wherever the
 * constants are needed, to reduce verbosity.
 */
class Constants {

    enum class Axis {
        PITCH, ROLL, YAW
    }

    object OperatorConstants {
        const val JoystickPort = 0
        const val FirstButton = 1
    }
    public object motorConstants {
        const val driveLeftFrontID = 1
        const val driveLeftBackID = 2
        const val driveRightFrontID = 3
        const val driveRightBackID = 4
        const val gripperPort = 5
    }

    public object errorCodes {
        const val frameNotFoundError = -1.0
        const val targetsNotFoundError = -2.0
    }

    object setHeights {
        const val poleTapeLow = 0.5588  //22 inches up
        const val camera = 0.381   //15 inches up
    }

    object setDistances {
        const val goalDistLow = 0.0 //!fix
    }

    object driveConsts {
        //!system identification to find these constants
        const val ksVolts = 0.0
        const val kvVoltSecondsPerMeter = 0.0
        const val kaVoltSecondsSquaredPerMeter = 0.0
        const val kPDriveVel = 0.0
        const val kMaxSpeedMetersPerSecond = 3.0
        const val kMaxAccelerationMetersPerSecondSquared = 1.0
        //distance between wheels
        const val kTrackwidthMeters = 0.0
        //ramsete config, can be kept as is
        const val kRamseteB = 2 
        const val ramseteZeta = 0.7
    }

    public data class Arm(
            val motorPort: Int,
            val kp: Double,
            val ki: Double,
            val kd: Double,
            val kiz: Double,
            val kff: Double,
            val kmaxoutput: Double,
            val kminoutput: Double,
            val armsStartRads: Double
    )

    public data class movementTarget(
            val distance: Double,
            val yaw: Double,
    )

    data class arcadeDriveSpeeds(val fwd: Double, val rot: Double)

    public val first_Arm: Arm = Arm(5, 0.3, 0.0, 0.0, 0.0, 0.0, 1.0, -1.0, 1.0)
}
