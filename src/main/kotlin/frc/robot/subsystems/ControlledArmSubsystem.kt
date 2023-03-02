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
import edu.wpi.first.math.controller.ArmFeedforward
import com.revrobotics.SparkMaxPIDController.ArbFFUnits;

class ControlledArmSubsystem(ArmConsts: Arm) : SubsystemBase() {
    val motor: CANSparkMax = CANSparkMax(ArmConsts.motorPort, MotorType.kBrushless)
    val encoder: RelativeEncoder = motor.getEncoder()
    val pidcontroller: SparkMaxPIDController = motor.getPIDController()
    
    val gravityFeedForward  = ArmConsts.kgrav

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
        val cosinescalar = Math.cos(encoder.getPosition())
        val feedforward = cosinescalar * gravityFeedForward
        //147:1 as the gear ratio
        pidcontroller.setReference((-radiansR * (2*Math.PI) / 147), CANSparkMax.ControlType.kPosition, 0, feedforward, ArbFFUnits.kPercentOut)
    }

    /**
     * Sets the motor to a given number of radians.
     */
    fun moveToGoal(targetPosR: Double) {
        //will not move if you try totell it to tunr past 270 deg.
        if (targetPosR > Units.degreesToRadians(270.0)){
            return
        }
        val currPosR: Double = Units.degreesToRadians(encoder.getPosition()) + offsetR
        rotateRadians(currPosR-targetPosR)
    }

    fun resetPos() : Command {
        return runOnce {
            moveToGoal(offsetR)
        }
    }
}}
