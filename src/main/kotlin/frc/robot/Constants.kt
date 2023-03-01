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

    public object OperatorConstants {
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

    // Measured in meters
    object setHeights {
        const val poleTapeLowI: Double = 23.0 // 23 inches up
        const val cameraI: Double = 13.0 // 13 inches up
        const val poleTapeHighI:Double = 46.0
    }

    object setDistances {
        const val goalDistLowI: Double = 14.0
        const val goaldistFloorI:Double = 24.0
        const val goaldistHighI:Double = 12.0
        const val pickupDistI:Double = 24.0
    }

    object driveConsts {
        //!system identification to find these constants
        const val ksVolts: Double = 0.0
        const val kvVoltSecondsPerMeter: Double = 0.0
        const val kaVoltSecondsSquaredPerMeter: Double = 0.0
        const val kPDriveVel: Double = 0.0
        const val kMaxSpeedMetersPerSecond: Double = 3.0
        const val kMaxAccelerationMetersPerSecondSquared: Double = 1.0
        //distance between wheels
        const val kTrackwidthMeters: Double = 0.0
        //ramsete config, can be kept as is
        const val kRamseteB: Int = 2 
        const val ramseteZeta: Double = 0.7
        const val jerkDelta: Double = 0.5
    }

    object armPositions {
        const val retractedD: Double = 0.0
        const val higherD: Double = 180.0
        const val pickupD: Double = 225.0
        const val loweredD: Double = 270.0
        const val extendedD: Double = 180.0
        const val partlyextendedD:Double = 225.0
    }

    data class Arm(
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

    data class MovementTarget(val distanceM: Double, val yawD: Double)

    data class ArcadeDriveSpeeds(val fwd: Double, val rot: Double)

    public val first_Arm: Arm = Arm(5, 0.5, 0.0, 0.0, 0.0, 0.0, 1.0, -1.0, 1.0)
}
