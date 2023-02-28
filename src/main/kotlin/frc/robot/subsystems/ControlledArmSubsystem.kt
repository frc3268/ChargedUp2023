package frc.robot.subsystems

import com.revrobotics.CANSparkMax
import com.revrobotics.CANSparkMaxLowLevel.MotorType
import com.revrobotics.RelativeEncoder
import com.revrobotics.SparkMaxPIDController
import edu.wpi.first.wpilibj2.command.SubsystemBase
import edu.wpi.first.wpilibj2.command.Command
import frc.robot.Constants.Arm
import frc.robot.Constants

class ControlledArmSubsystem(ArmConsts: Arm) : SubsystemBase() {
    val motor: CANSparkMax = CANSparkMax(ArmConsts.motorPort, MotorType.kBrushless)
    val encoder: RelativeEncoder = motor.getEncoder()
    val pidcontroller: SparkMaxPIDController = motor.getPIDController()
    val offset: Double = ArmConsts.armsStartRads

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
     * Rotates the motor by `radians` radians.
     */
    fun rotateRadians(radians: Double) {
        pidcontroller.setReference(Units.radiansToDegrees(radians), CANSparkMax.ControlType.kPosition)
    }

    fun moveToGoal(goalPos: Double) {
        // sets position to a given amount of radians, hopefully it works
        val current: Double = Units.degreesToRadians(encoder.getPosition()) + offset
        val toMove: Double =
            if (current > goalPos)
                (Math.abs(current - goalPos) / (2 * Math.PI))
            else
                (current - goalPos / (2 * Math.PI))
        pidcontroller.setReference(toMove, CANSparkMax.ControlType.kPosition)
    }

    fun resetPos() : Command {
        return runOnce {
            moveToGoal(offset)
        }
    }
}
