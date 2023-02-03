package frc.robot.subsystems

import edu.wpi.first.wpilibj2.command.CommandBase
import edu.wpi.first.wpilibj2.command.SubsystemBase
import edu.wpi.first.wpilibj.motorcontrol.MotorControllerGroup
import edu.wpi.first.wpilibj.motorcontrol.PWMSparkMax
import edu.wpi.first.wpilibj.drive.DifferentialDrive
import com.revrobotics.CANSparkMaxLowLevel.MotorType
import frc.robot.Constants


class DriveSubsystem : SubsystemBase() {
    //Controllers
    public val driveLeftFront:PWMSparkMax = PWMSparkMax(Constants.driveConstants.driveLeftFrontID)
    public val driveLeftBack:PWMSparkMax = PWMSparkMax(Constants.driveConstants.driveLeftBackID)
    public val driveRightFront:PWMSparkMax = PWMSparkMax(Constants.driveConstants.driveRightFrontID)
    public val driveRightBack:PWMSparkMax = PWMSparkMax(Constants.driveConstants.driveRightBackID)
    //Groups
    public val driveLeft:MotorControllerGroup = MotorControllerGroup(driveLeftFront, driveLeftBack)
    public val driveRight:MotorControllerGroup = MotorControllerGroup(driveRightBack, driveRightBack)
    //Drive
    public val drive:DifferentialDrive = DifferentialDrive(driveLeft, driveRight)
    //invert left(?)
    driveLeft.setInverted(true)

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
