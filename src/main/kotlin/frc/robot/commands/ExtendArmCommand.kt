package frc.robot.commands

import edu.wpi.first.wpilibj2.command.CommandBase
import frc.robot.subsystems.ControlledArmSubsystem
import edu.wpi.first.math.util.Units
import frc.robot.Constants

class ExtendArmCommand (arm: ControlledArmSubsystem): CommandBase() {
    val arm: ControlledArmSubsystem = arm

    /**
     * Creates a new ExtendArmCommand.
     */
    init {
        // Use addRequirements() here to declare subsystem dependencies.
    }

    // Called when the command is initially scheduled.
    override fun initialize() { }

    // Called every time the scheduler runs while the command is scheduled.
    override fun execute() {
        arm.moveToGoal(Units.degreesToRadians(Constants.armPositions.extendedD))
    }

    // Called once the command ends or is interrupted.
    override fun end(interrupted: Boolean) { }

    // Returns true when the command should end.
    override fun isFinished(): Boolean {
        return false
    }
}
