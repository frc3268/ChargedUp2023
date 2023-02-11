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
    private val driveLeftFront:PWMSparkMax = PWMSparkMax(Constants.motorConstants.driveLeftFrontID)
    private val driveLeftBack:PWMSparkMax = PWMSparkMax(Constants.motorConstants.driveLeftBackID)
    private val driveRightFront:PWMSparkMax = PWMSparkMax(Constants.motorConstants.driveRightFrontID)
    private val driveRightBack:PWMSparkMax = PWMSparkMax(Constants.motorConstants.driveRightBackID)
    //Groups
    private val driveLeft:MotorControllerGroup = MotorControllerGroup(driveLeftFront, driveLeftBack)
    private val driveRight:MotorControllerGroup = MotorControllerGroup(driveRightBack, driveRightBack)
    //Drive
    public val drive:DifferentialDrive = DifferentialDrive(driveLeft, driveRight)
    
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
            else{
                return 0.0
            }
        }
    }

    /** This method will be called once per scheduler run  */
    override fun periodic() {
    }

    /** This method will be called once per scheduler run during simulation  */
    override fun simulationPeriodic() {
    }
}
