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
import edu.wpi.first.wpilibj.BuiltInAccelerometer
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard
import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardTab
import edu.wpi.first.wpilibj.shuffleboard.BuiltInWidgets
import edu.wpi.first.math.util.Units
import com.kauailabs.navx.frc.AHRS;

import edu.wpi.first.wpilibj.SPI;

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
            MotorControllerGroup(driveRightFront, driveRightBack)
    // Drive
    private val drive: DifferentialDrive = DifferentialDrive(driveLeft, driveRight)

    // once periodic starts, the joystick mode will be activated.
    // ? is this needed ?
    public var joystickOn: Boolean = true

    // PID
    // PID constants should be tuned per robot
    val linearP: Double = 0.6
    val linearD: Double = 0.0
    val forwardController: PIDController = PIDController(linearP, 0.0, linearD)

    val angularP: Double = 0.3
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

    val accelerometer: BuiltInAccelerometer = BuiltInAccelerometer()
    var lastaccel: Double = 0.0
    var currentaccel: Double = 0.0

    var trajectoryConfig: TrajectoryConfig =
        TrajectoryConfig(
            Constants.driveConsts.kMaxSpeedMetersPerSecond,
            Constants.driveConsts.kMaxAccelerationMetersPerSecondSquared
        )
            // Add kinematics to ensure max speed is actually obeyed
            .setKinematics(driveKinematics)
            // Apply the voltage constraint
            .addConstraint(voltageConstraint)

    //gyro
    val gyro:AHRS = AHRS(SPI.Port.kMXP)

    init {
        // Matthew says this is needed but doesn't know why. Do not remove -- Weiju
        driveLeft.setInverted(true)
    }

    /**
     * Method to get gyro angle for balancing
     * @param axis the axis to get from gyro, pitch is 1, roll is 2, and yaw is 3
     * @return gyro reading on the requested axis (what unit? -- Weiju)
     */
    fun getGyroAngle(axis: Constants.Axis): Double {
        when(axis){
            Constants.Axis.PITCH -> return gyro.getPitch().toDouble()
            Constants.Axis.ROLL -> return gyro.getRoll().toDouble()
            Constants.Axis.YAW -> return gyro.getYaw().toDouble()
            else -> return 0.0
        }
    }

    fun stopMotor() {
        drive.stopMotor()
    }

    public fun arcadeDrive(speeds: Constants.ArcadeDriveSpeeds) {
        drive.arcadeDrive(speeds.fwd, speeds.rot)
    }

    public fun pidSpeedsCalculate(
        target: Constants.MovementTarget,
        goalDistM: Double
    ): Constants.ArcadeDriveSpeeds =
        Constants.ArcadeDriveSpeeds(
            forwardController.calculate(target.distanceM, goalDistM),
            turnController.calculate(target.yawD, 0.0)
        )

    fun tankDrive(left: Double, right: Double) {
        drive.tankDrive(left, right)
    }


    // balance by setting speed proportional to angle.
    fun autoBalanceCommand(): Command =
        run {
            drive.arcadeDrive(Math.sin(Units.degreesToRadians(getGyroAngle(Constants.Axis.PITCH)) * 1.5), 0.0)
        }.until({Math.abs(getGyroAngle(Constants.Axis.PITCH)) < 5.0})
    fun driveBackUntilIncline(): Command = 
        run{
            drive.arcadeDrive(0.5, 0.0)
        }.until({Math.abs(getGyroAngle(Constants.Axis.PITCH)) > 10.0})
    fun arcadeDriveCommand(fwd: DoubleSupplier, rot: DoubleSupplier): Command =
        run {
            arcadeDrive(Constants.ArcadeDriveSpeeds(fwd.getAsDouble(), rot.getAsDouble()))
        }

    /** This method will be called once per scheduler run */
    override fun periodic() {
        lastaccel = currentaccel
        currentaccel = accelerometer.getX()
    }

    /** This method will be called once per scheduler run during simulation */
    override fun simulationPeriodic() {}
}
