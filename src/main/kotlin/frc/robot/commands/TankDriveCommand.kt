package frc.robot.commands

import edu.wpi.first.wpilibj2.command.CommandBase
import frc.robot.subsystems.DriveSubsystem

class TankDriveCommand (subsystem:DriveSubsystem, left:Double, right:Double): CommandBase() {
    val driveSubsystem:DriveSubsystem = subsystem
    val left:Double = left
    val right:Double = right
    init {
        // Use addRequirements() here to declare subsystem dependencies.
        addRequirements(driveSubsystem)
    }

    // Called when the command is initially scheduled.
    override fun initialize() { }

    // Called every time the scheduler runs while the command is scheduled.
    override fun execute() { 
        driveSubsystem.drive.tankDrive(left, right)
    }

    // Called once the command ends or is interrupted.
    override fun end(interrupted: Boolean) { }

    // Returns true when the command should end.
    override fun isFinished(): Boolean {
        return false
    }
}
