package frc.robot.subsystems

import edu.wpi.first.wpilibj2.command.SubsystemBase
import edu.wpi.first.wpilibj2.command.Command
import edu.wpi.first.wpilibj.motorcontrol.Talon
import frc.robot.Constants

class GripperSubsystem : SubsystemBase() {
    public val motorPort: Int = Constants.motorConstants.gripperPort
    public val motor: Talon = Talon(motorPort)
    public var closed: Boolean = true

    fun toggle() : Command {
        closed = !closed
        //may want to change settime
        return runOnce {
            motor.set(if(closed) 1.0 else -1.0)
        }.withTimeout(2.0)
    }

    override fun periodic() {
        // This method will be called once per scheduler run
    }

    override fun simulationPeriodic() {
        // This method will be called once per scheduler run during simulation
    }
}
