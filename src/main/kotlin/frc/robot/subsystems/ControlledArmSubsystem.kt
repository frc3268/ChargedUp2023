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
import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardTab
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard
import edu.wpi.first.wpilibj.shuffleboard.BuiltInWidgets
import com.revrobotics.SparkMaxPIDController.ArbFFUnits;

class ControlledArmSubsystem(ArmConsts: Arm) : SubsystemBase() {
    val motor: CANSparkMax = CANSparkMax(ArmConsts.motorPort, MotorType.kBrushless)
    val encoder: RelativeEncoder = motor.getEncoder()
    val pidcontroller: SparkMaxPIDController = motor.getPIDController()
    
    var gravityFeedForward  = ArmConsts.kgrav

    var setpoint:Double = 0.0

    val operatortab: ShuffleboardTab = Shuffleboard.getTab("PID Config")
    val pwidget = operatortab.add("P Gain", ArmConsts.kp).withWidget(BuiltInWidgets.kNumberSlider).withProperties(mapOf("min" to 0, "max" to 0.1)).getEntry()
    val iwidget = operatortab.add("I Gain", ArmConsts.ki).withWidget(BuiltInWidgets.kNumberSlider).withProperties(mapOf("min" to 0, "max" to 0.1)).getEntry()
    val dwidget = operatortab.add("D Gain", ArmConsts.kd).withWidget(BuiltInWidgets.kNumberSlider).withProperties(mapOf("min" to 0, "max" to 0.1)).getEntry()
    val ffwidget = operatortab.add("FeedForward(gravity) Gain", ArmConsts.kgrav).withWidget(BuiltInWidgets.kNumberSlider).withProperties(mapOf("min" to 0, "max" to 0.5)).getEntry()

    val encoderlabel = operatortab.add("encoder dist", 0.0).getEntry()
    
    init {
        pidcontroller.setP(ArmConsts.kp)
        pidcontroller.setI(ArmConsts.ki)
        pidcontroller.setD(ArmConsts.kd)
        pidcontroller.setOutputRange(ArmConsts.kminoutput, ArmConsts.kmaxoutput)
        encoder.setPositionConversionFactor(360/(147/1.0))
    }

    override fun periodic() {
        //configure pid gains
        pidcontroller.setP(pwidget.getDouble(1.0))
        pidcontroller.setI(iwidget.getDouble(1.0))
        pidcontroller.setD(dwidget.getDouble(1.0))
        gravityFeedForward = ffwidget.getDouble(1.0)
        encoderlabel.setDouble(Units.degreesToRadians(encoder.position))
        // This method will be called once per scheduler run
        val cosinescalar = Math.cos(encoder.getPosition())
        val feedforward = cosinescalar * gravityFeedForward
        pidcontroller.setReference((Units.radiansToDegrees(setpoint)), CANSparkMax.ControlType.kPosition, 0, feedforward, ArbFFUnits.kPercentOut)

    }

    override fun simulationPeriodic() {
        // This method will be called once per scheduler run during simulation
    }

    /**
     * Rotates the motor by the given number of radians.
     */
    fun rotateRadians(radiansR: Double) {
        //147:1 as the gear ratio
        //1 revolution is 2pi radians
        //multiply by 147 to convert from motor revolutions to sprocket revolutions
        setpoint = radiansR
    }

    /**
     * Sets the motor to a given number of radians.
     */
    fun moveToGoal(targetPosR: Double) {
        //will not move if you try totell it to tunr past 270 deg.
        if (targetPosR > Units.degreesToRadians(270.0)){
            return
        }
        val currPosR: Double = Units.degreesToRadians(encoder.getPosition())
        rotateRadians(currPosR-targetPosR)
    }

    fun resetPos() : Command {
        return runOnce {
            moveToGoal(Constants.armPositions.retractedD)
        }
    }
}
