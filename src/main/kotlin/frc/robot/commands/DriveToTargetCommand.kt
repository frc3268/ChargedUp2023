package frc.robot.commands

import edu.wpi.first.wpilibj2.command.CommandBase
import frc.robot.Constants
import frc.robot.subsystems.CameraSubsystem
import frc.robot.subsystems.DriveSubsystem

/**
 * Creates a new DriveToTargetCommand.
 */
class DriveToTargetCommand(camera: CameraSubsystem, drive: DriveSubsystem, targetHeightM: Double, targetDistM: Double) : CommandBase() {
    val camera: CameraSubsystem = camera
    val drive: DriveSubsystem = drive
    val heightM: Double = targetHeightM
    val distM: Double = targetDistM

    init {
        // Use addRequirements() here to declare subsystem dependencies.
        addRequirements(drive)
    }

    /**
     * Called when the command is initially scheduled.
     */
    override fun initialize() {

    }

    /**
     * Called every time the scheduler runs while the command is scheduled.
     */
    override fun execute() {
        val range: Constants.MovementTarget? = camera.movementToTarget(heightM)
        range ?: return;

        if(range.distanceM < distM) {
            end(false)
        }

        drive.arcadeDrive(
            drive.pidSpeedsCalculate(
                range,
                distM
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
