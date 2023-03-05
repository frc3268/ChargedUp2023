package frc.robot.commands.commandgroups

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup
import frc.robot.subsystems.DriveSubsystem
import frc.robot.subsystems.ControlledArmSubsystem
import frc.robot.subsystems.GripperSubsystem
import frc.robot.commands.HighArmCommand
import frc.robot.commands.CloseGripperCommand
import frc.robot.commands.RetractArmCommand
// NOTE:  Consider using this command inline, rather than writing a subclass.  For more
// information, see:
// https://docs.wpilib.org/en/stable/docs/software/commandbased/convenience-features.html
class AutoRoutine(drive:DriveSubsystem, arm:ControlledArmSubsystem, gripper:GripperSubsystem) : SequentialCommandGroup() {
    val drive:DriveSubsystem = drive
    val arm:ControlledArmSubsystem = arm
    val gripper:GripperSubsystem = gripper
    /**
     * Creates a new AutoRoutine.
     */
    init {
        // Add your commands in the addCommands() call, e.g.
        // addCommands(FooCommand(), BarCommand())
        addCommands(
            drive.arcadeDriveCommand({0.2}, {0.0}).withTimeout(2.0),
            drive.driveBackUntilIncline().withTimeout(5.0),
            drive.autoBalanceCommand()
        )
    }
}
