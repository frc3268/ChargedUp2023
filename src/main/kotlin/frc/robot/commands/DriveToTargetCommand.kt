package frc.robot.commands

import edu.wpi.first.wpilibj2.command.CommandBase
import frc.robot.Constants
import frc.robot.subsystems.CameraSubsystem
import frc.robot.subsystems.DriveSubsystem

/**
 * Creates a new DriveToTargetCommand.
 */
class DriveToTargetCommand(camera: CameraSubsystem, drive: DriveSubsystem, targetHeight: Double, targetDist: Double) : CommandBase() {
    val camera: CameraSubsystem = camera
    val drive: DriveSubsystem = drive
    val height: Double = targetHeight
    val dist: Double = targetDist

    init {
        // Use addRequirements() here to declare subsystem dependencies.
        addRequirements(drive)
    }

    /**
     * Called when the command is initially scheduled.
     */
    override fun initialize() {}

    /**
     * Called every time the scheduler runs while the command is scheduled.
     */
    override fun execute() {
        val range: Constants.MovementTarget? = camera.movementToTarget(height)
        range ?: return;
        if(range.distance < dist){
            end(false)
        }
        drive.arcadeDrive(
            drive.pidSpeedsCalculate(
                range,
                dist
            )
        )
    }

    /**
     * Called once the command ends or is interrupted.
     */
    override fun end(interrupted: Boolean) {
        drive.stopMotor()
    }

    /**
     * Returns true when the command should end.
     */
    override fun isFinished(): Boolean {
        return false
    }
}
