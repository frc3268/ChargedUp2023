package frc.robot.subsystems

import edu.wpi.first.wpilibj2.command.SubsystemBase
import edu.wpi.first.wpilibj2.command.Command
import edu.wpi.first.wpilibj.motorcontrol.Talon
import frc.robot.Constants

class GripperSubsystem : SubsystemBase() {
    public val motorPort:Int = Constants.motorConstants.gripperPort
    public val motor:Talon = Talon(motorPort)
    //start closed
    public var state:Double = -1.0

    fun changeState() : Command{
        state = state * -1
        //may want to change settime
        return runOnce{motor.set(state)}.withTimeout(2.0)
    }

    override fun periodic() {
        // This method will be called once per scheduler run
    }

    override fun simulationPeriodic() {
        // This method will be called once per scheduler run during simulation
    }
}
