package frc.robot.subsystems

import com.revrobotics.CANSparkMax
import com.revrobotics.CANSparkMaxLowLevel.MotorType
import edu.wpi.first.math.controller.PIDController
import edu.wpi.first.math.controller.SimpleMotorFeedforward
import edu.wpi.first.math.kinematics.DifferentialDriveKinematics
import edu.wpi.first.math.trajectory.TrajectoryConfig
import edu.wpi.first.math.trajectory.constraint.DifferentialDriveVoltageConstraint
import edu.wpi.first.wpilibj.drive.DifferentialDrive
import edu.wpi.first.wpilibj.motorcontrol.MotorControllerGroup
import edu.wpi.first.wpilibj2.command.Command
import edu.wpi.first.wpilibj2.command.SubsystemBase
import frc.robot.Constants
import java.util.function.DoubleSupplier

class DriveSubsystem : SubsystemBase() {
    // Controllers
    private val driveLeftFront: CANSparkMax =
            CANSparkMax(Constants.motorConstants.driveLeftFrontID, MotorType.kBrushless)
    private val driveLeftBack: CANSparkMax =
            CANSparkMax(Constants.motorConstants.driveLeftBackID, MotorType.kBrushless)
    private val driveRightFront: CANSparkMax =
            CANSparkMax(Constants.motorConstants.driveRightFrontID, MotorType.kBrushless)
    private val driveRightBack: CANSparkMax =
            CANSparkMax(Constants.motorConstants.driveRightBackID, MotorType.kBrushless)
    // Groups
    private val driveLeft: MotorControllerGroup =
            MotorControllerGroup(driveLeftFront, driveLeftBack)
    private val driveRight: MotorControllerGroup =
            MotorControllerGroup(driveRightBack, driveRightBack)
    // Drive
    private val drive: DifferentialDrive = DifferentialDrive(driveLeft, driveRight)

    // once periodic starts, the joystick mode will be activated.
    // ? is this needed ?
    public var joystickOn: Boolean = true

    // PID
    // PID constants should be tuned per robot
    val linearP: Double = 0.1
    val linearD: Double = 0.0
    val forwardController: PIDController = PIDController(linearP, 0.0, linearD)

    val angularP: Double = 0.1
    val angularD: Double = 0.0
    val turnController = PIDController(angularP, 0.0, angularD)

    // kinematics and odometry
    val driveKinematics: DifferentialDriveKinematics =
            DifferentialDriveKinematics(Constants.driveConsts.kTrackwidthMeters)
    var voltageConstraint =
            DifferentialDriveVoltageConstraint(
                    SimpleMotorFeedforward(
                            Constants.driveConsts.ksVolts,
                            Constants.driveConsts.kvVoltSecondsPerMeter,
                            Constants.driveConsts.kaVoltSecondsSquaredPerMeter
                    ),
                    driveKinematics,
                    10.0
            )

    // Create config for trajectory

    var trajectoryConfig: TrajectoryConfig =
            TrajectoryConfig(
                            Constants.driveConsts.kMaxSpeedMetersPerSecond,
                            Constants.driveConsts.kMaxAccelerationMetersPerSecondSquared
                    )

                    // Add kinematics to ensure max speed is actually obeyed

                    .setKinematics(driveKinematics)

                    // Apply the voltage constraint

                    .addConstraint(voltageConstraint)

    init {
        // inversion
        driveLeft.setInverted(true)
    }

    /**
     * Method to get gyro angle for balancing
     * @param axis the axis to get from gyro, pitch is 1, roll is 2, and yaw is 3
     * @return gyro reading on the requested axis
     */
    fun getGyroAngle(axis: Int): Double {
        when (axis) {
            1 -> return 0.0
            2 -> return 0.0
            3 -> return 0.0
            else -> return 0.0
        }
    }

    fun stopMotor() {
        drive.stopMotor()
    }

    public fun arcadeDrive(speeds: Constants.arcadeDriveSpeeds) {
        drive.arcadeDrive(speeds.fwd, speeds.rot)
    }

    public fun pidSpeedsCalculate(
            target: Constants.movementTarget,
            goalDist: Double
    ): Constants.arcadeDriveSpeeds {
        return Constants.arcadeDriveSpeeds(
                forwardController.calculate(target.distance, goalDist),
                turnController.calculate(target.yaw, 0.0)
        )
    }

    fun tankDrive(left: Double, right: Double) {
        drive.tankDrive(left, right)
    }

    // balance by setting speed proportional to angle.
    fun autoBalanceCommand(): Command {
        return run { drive.arcadeDrive(Math.sin(getGyroAngle(1) * (Math.PI / 180)) * -1, 0.0) }
    }

    fun arcadeDriveCommand(fwd: DoubleSupplier, rot: DoubleSupplier): Command {
        return run {
            arcadeDrive(Constants.arcadeDriveSpeeds(fwd.getAsDouble(), rot.getAsDouble()))
        }
                .finallyDo { stopMotor() }
    }
    /** This method will be called once per scheduler run */
    override fun periodic() {}

    /** This method will be called once per scheduler run during simulation */
    override fun simulationPeriodic() {}
}
