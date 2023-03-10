package frc.robot.commands

import edu.wpi.first.wpilibj2.command.CommandBase
import frc.robot.subsystems.GripperSubsystem

class OpenGripperCommand(gripper: GripperSubsystem): CommandBase() {
    val gripper: GripperSubsystem = gripper

    init {
        // Use addRequirements() here to declare subsystem dependencies.
    }

    // Called when the command is initially scheduled.
    override fun initialize() { }

    // Called every time the scheduler runs while the command is scheduled.
    override fun execute() {
        if(gripper.closed){
            gripper.open()
        }
    }

    // Called once the command ends or is interrupted.
    override fun end(interrupted: Boolean) {
        gripper.stop()
     }

    // Returns true when the command should end.
    override fun isFinished(): Boolean {
        return false
    }
}
