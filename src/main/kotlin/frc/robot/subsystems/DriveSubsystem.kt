package frc.robot.subsystems

import edu.wpi.first.wpilibj2.command.CommandBase
import edu.wpi.first.wpilibj2.command.SubsystemBase
import edu.wpi.first.wpilibj.motorcontrol.MotorControllerGroup
import edu.wpi.first.wpilibj.drive.DifferentialDrive
import com.revrobotics.CANSparkMax
import com.revrobotics.CANSparkMaxLowLevel.MotorType
import frc.robot.Constants


class DriveSubsystem : SubsystemBase() {
    //Controllers
    public val driveLeftFront:CANSparkMax = CANSparkMax(Constants.driveConstants.driveLeftFrontID, MotorType.kBrushless)
    public val driveLeftBack:CANSparkMax = CANSparkMax(Constants.driveConstants.driveLeftBackID, MotorType.kBrushless)
    public val driveRightFront:CANSparkMax = CANSparkMax(Constants.driveConstants.driveRightFrontID, MotorType.kBrushless)
    public val driveRightBack:CANSparkMax = CANSparkMax(Constants.driveConstants.driveRightBackID, MotorType.kBrushless)
    //Groups
    public val driveLeft:MotorControllerGroup = MotorControllerGroup(driveLeftFront, driveLeftBack)
    public val driveRight:MotorControllerGroup = MotorControllerGroup(driveRightBack, driveRightBack)
    //Drive
    public val drive:DifferentialDrive = DifferentialDrive(driveLeft, driveRight)

    /**
     * Example command factory method.
     *
     * @return a command
     */
    fun exampleMethodCommand(): CommandBase {
        // Inline construction of command goes here.
        // runOnce implicitly requires this subsystem.
        return runOnce {}
    }

    /**
     * An example method querying a boolean state of the subsystem (for example, a digital sensor).
     *
     * @return value of some boolean subsystem state, such as a digital sensor.
     */
    fun exampleCondition(): Boolean {
        // Query some boolean state, such as a digital sensor.
        return false
    }

    /** This method will be called once per scheduler run  */
    override fun periodic() {
    }

    /** This method will be called once per scheduler run during simulation  */
    override fun simulationPeriodic() {
    }
}
