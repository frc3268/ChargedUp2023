package frc.robot.commands.commandgroups

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup
import frc.robot.subsystems.DriveSubsystem
import frc.robot.subsystems.ControlledArmSubsystem
import frc.robot.subsystems.GripperSubsystem
import frc.robot.commands.HighArmCommand
import frc.robot.commands.CloseGripperCommand
import frc.robot.commands.RetractArmCommand
import frc.robot.commands.OpenGripperCommand
// NOTE:  Consider using this command inline, rather than writing a subclass.  For more
// information, see:
// https://docs.wpilib.org/en/stable/docs/software/commandbased/convenience-features.html
class AutoRoutine(drive:DriveSubsystem, arm:ControlledArmSubsystem, gripper:GripperSubsystem, routine:Int) : SequentialCommandGroup() {
    val drive:DriveSubsystem = drive
    val arm:ControlledArmSubsystem = arm
    val gripper:GripperSubsystem = gripper
    val routine:Int = routine
    /**
     * Creates a new AutoRoutine.
     */
    init {
        when(routine) {
            //balancer: forward, back, balance
            1 -> addCommands(
                drive.arcadeDriveCommand({0.2}, {0.0}).withTimeout(2.0),
                drive.driveBackUntilIncline().withTimeout(5.0),
                drive.autoBalanceCommand()
            )
            //basic: forward, back
            2 -> addCommands(
                drive.arcadeDriveCommand({0.5}, {0.0}).withTimeout(2.0),

                drive.arcadeDriveCommand({-0.5}, {0.0}).withTimeout(3.0),
            )
            //ambitious: score high, back
            3 -> addCommands(
                CloseGripperCommand(gripper),
                HighArmCommand(arm),
                OpenGripperCommand(gripper),
                RetractArmCommand(arm),
                drive.arcadeDriveCommand({-0.5}, {0.0}).withTimeout(3.0)
            )
            //basic if none selected
            else -> {
                addCommands(
                    drive.arcadeDriveCommand({0.5}, {0.0}).withTimeout(2.0),
    
                    drive.arcadeDriveCommand({-0.5}, {0.0}).withTimeout(3.0),
                )
            }
        }
        // Add your commands in the addCommands() call, e.g.
        // addCommands(FooCommand(), BarCommand())
        
    }
}
