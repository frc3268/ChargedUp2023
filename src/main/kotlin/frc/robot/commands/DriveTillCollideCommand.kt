package frc.robot.commands

import edu.wpi.first.wpilibj2.command.CommandBase
import frc.robot.subsystems.DriveSubsystem
import frc.robot.Constants

class DriveTillCollideCommand (drive:DriveSubsystem): CommandBase() {
    /**
     * Creates a new DriveTillCollideCommand.
     */
    val drive:DriveSubsystem = drive
    init {
        // Use addRequirements() here to declare subsystem dependencies.
        addRequirements(drive)
    }

    // Called when the command is initially scheduled.
    override fun initialize() { }

    // Called every time the scheduler runs while the command is scheduled.
    override fun execute() { 
        if(Math.abs(drive.lastaccel - drive.currentaccel) > Constants.driveConsts.jerkDelta){
            end(false)
        }
        drive.arcadeDrive(Constants.arcadeDriveSpeeds(0.4, 0.0))
    }

    // Called once the command ends or is interrupted.
    override fun end(interrupted: Boolean) { }

    // Returns true when the command should end.
    override fun isFinished(): Boolean {
        return false
    }
}
