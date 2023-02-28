package frc.robot.subsystems

import com.revrobotics.CANSparkMax
import com.revrobotics.CANSparkMaxLowLevel.MotorType
import com.revrobotics.RelativeEncoder
import com.revrobotics.SparkMaxPIDController
import edu.wpi.first.wpilibj2.command.SubsystemBase
import edu.wpi.first.wpilibj2.command.Command
import frc.robot.Constants.Arm
import frc.robot.Constants
import edu.wpi.first.math.util.Units

class ControlledArmSubsystem(ArmConsts: Arm) : SubsystemBase() {
    val motor: CANSparkMax = CANSparkMax(ArmConsts.motorPort, MotorType.kBrushless)
    val encoder: RelativeEncoder = motor.getEncoder()
    val pidcontroller: SparkMaxPIDController = motor.getPIDController()
    val offsetR: Double = ArmConsts.armsStartRads

    init {
        pidcontroller.setP(ArmConsts.kp)
        pidcontroller.setI(ArmConsts.ki)
        pidcontroller.setD(ArmConsts.kd)
        pidcontroller.setIZone(ArmConsts.kiz)
        pidcontroller.setFF(ArmConsts.kff)
        pidcontroller.setOutputRange(ArmConsts.kminoutput, ArmConsts.kmaxoutput)
    }

    override fun periodic() {
        // This method will be called once per scheduler run
    }

    override fun simulationPeriodic() {
        // This method will be called once per scheduler run during simulation
    }

    /**
     * Rotates the motor by the given number of radians.
     */
    fun rotateRadians(radiansR: Double) {
        pidcontroller.setReference(Units.radiansToDegrees(radiansR), CANSparkMax.ControlType.kPosition)
    }

    /**
     * Sets the motor to a given number of radians.
     */
    fun moveToGoal(targetPosR: Double) {
        val currPosR: Double = Units.degreesToRadians(encoder.getPosition()) + offsetR
        pidcontroller.setReference(
            Units.radiansToDegrees(currPosR - targetPosR),
            CANSparkMax.ControlType.kPosition
        )
    }

    fun resetPos() : Command {
        return runOnce {
            moveToGoal(offsetR)
        }
    }
}
