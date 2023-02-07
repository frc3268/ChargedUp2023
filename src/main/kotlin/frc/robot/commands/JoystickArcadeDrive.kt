package frc.robot.commands

import edu.wpi.first.wpilibj2.command.CommandBase
import edu.wpi.first.wpilibj.Joystick
import frc.robot.subsystems.DriveSubsystem
import frc.robot.IO

class JoystickArcadeDrive (subsystem: DriveSubsystem, stick:IO): CommandBase() {
    val driveSubsystem:DriveSubsystem = subsystem
    val joystick:Joystick = stick.joystick
    init {
        // Use addRequirements() here to declare subsystem dependencies.
        addRequirements(driveSubsystem)
    }

    // Called when the command is initially scheduled.
    override fun initialize() { }

    // Called every time the scheduler runs while the command is scheduled.
    override fun execute() {
        driveSubsystem.drive.arcadeDrive(joystick.getY(), joystick.getX())

     }

    // Called once the command ends or is interrupted.
    override fun end(interrupted: Boolean) {
        driveSubsystem.drive.stopMotor()
     }

    // Returns true when the command should end.
    override fun isFinished(): Boolean {
        return false
    }
}
