package frc.robot.subsystems

import edu.wpi.first.wpilibj2.command.CommandBase
import edu.wpi.first.wpilibj2.command.SubsystemBase
import edu.wpi.first.wpilibj2.command.Command
import edu.wpi.first.wpilibj.motorcontrol.MotorControllerGroup
import edu.wpi.first.wpilibj.motorcontrol.PWMSparkMax
import edu.wpi.first.wpilibj.drive.DifferentialDrive
import com.revrobotics.CANSparkMaxLowLevel.MotorType
import com.revrobotics.CANSparkMax
import frc.robot.Constants


class DriveSubsystem : SubsystemBase() {
    //Controllers
    private val driveLeftFront:CANSparkMax = CANSparkMax(Constants.motorConstants.driveLeftFrontID, MotorType.kBrushless)
    private val driveLeftBack:CANSparkMax = CANSparkMax(Constants.motorConstants.driveLeftBackID, MotorType.kBrushless)
    private val driveRightFront:CANSparkMax = CANSparkMax(Constants.motorConstants.driveRightFrontID, MotorType.kBrushless)
    private val driveRightBack:CANSparkMax = CANSparkMax(Constants.motorConstants.driveRightBackID, MotorType.kBrushless)
    //Groups
    private val driveLeft:MotorControllerGroup = MotorControllerGroup(driveLeftFront, driveLeftBack)
    private val driveRight:MotorControllerGroup = MotorControllerGroup(driveRightBack, driveRightBack)
    //Drive
    private val drive:DifferentialDrive = DifferentialDrive(driveLeft, driveRight)
    
    init{
        //inversion
        driveLeft.setInverted(true)
    }

    /**
     * Method to get gyro angle for balancing
     * @param axis the axis to get from gyro, pitch is 1, roll is 2, and yaw is 3
     * @return gyro reading on the requested axis
     */
    fun getGyroAngle(axis:Int): Double {
        when(axis){
            1 -> return 0.0
            2 -> return 0.0
            3 -> return 0.0
            else -> return 0.0
        }
    }

    //balance by setting speed proportional to angle.
    fun autoBalanceCommand(): Command{
        return run{
            drive.arcadeDrive(Math.sin(getGyroAngle(1)*(Math.PI/180)) * -1, 0.0)
        }
    }

    fun stopMotor() {
        drive.stopMotor()
    }

    fun arcadeDriveCommand(fwd:Double, rot:Double) : Command{
        return run {
            drive.arcadeDrive(fwd, rot)
        }
    }

    fun TankDriveCommand(left:Double, right:Double): Command{
        return run{
            drive.tankDrive(left, right)
        }
    }

    /** This method will be called once per scheduler run  */
    override fun periodic() {
    }

    /** This method will be called once per scheduler run during simulation  */
    override fun simulationPeriodic() {
    }
}
