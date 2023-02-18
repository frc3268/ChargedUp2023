package frc.robot.commands

import edu.wpi.first.wpilibj2.command.CommandBase
import edu.wpi.first.wpilibj.Joystick
import frc.robot.subsystems.DriveSubsystem

class JoystickArcadeDrive (subsystem:DriveSubsystem, stick:Joystick): CommandBase() {
    /**
     * Creates a new JoystickArcadeDrive.
     */
    public val drive:DriveSubsystem = subsystem
    public val stick:Joystick = stick
    init {
        // Use addRequirements() here to declare subsystem dependencies.
        addRequirements(drive)
    }

    // Called when the command is initially scheduled.
    override fun initialize() { }

    // Called every time the scheduler runs while the command is scheduled.
    override fun execute() { 
        
    }

    // Called once the command ends or is interrupted.
    override fun end(interrupted: Boolean) { }

    // Returns true when the command should end.
    override fun isFinished(): Boolean {
        return false
    }
}
