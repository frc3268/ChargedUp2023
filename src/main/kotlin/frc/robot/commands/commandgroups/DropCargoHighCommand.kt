package frc.robot.commands.commandgroups

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup
import edu.wpi.first.math.util.Units
import frc.robot.commands.FloorArmCommand
import frc.robot.commands.LowerArmCommand
import frc.robot.commands.HighArmCommand
import frc.robot.commands.CloseGripperCommand
import frc.robot.commands.OpenGripperCommand
import frc.robot.commands.RetractArmCommand
import frc.robot.commands.DriveToTargetCommand
import frc.robot.commands.DriveTillCollideCommand
import frc.robot.subsystems.GripperSubsystem
import frc.robot.subsystems.ControlledArmSubsystem
import frc.robot.subsystems.CameraSubsystem
import frc.robot.subsystems.DriveSubsystem
import frc.robot.Constants


// NOTE:  Consider using this command inline, rather than writing a subclass.  For more
// information, see:
// https://docs.wpilib.org/en/stable/docs/software/commandbased/convenience-features.html
class DropCargoHighCommand(gripper: GripperSubsystem, arm: ControlledArmSubsystem, cam: CameraSubsystem, drive: DriveSubsystem) : SequentialCommandGroup() {
    /**
     * Creates a new DropCargoHighCommand.
     */
    init {
        // Add your commands in the addCommands() call, e.g.
        // addCommands(FooCommand(), BarCommand())
        addCommands(
            DriveToTargetCommand(cam, drive, Units.inchesToMeters(Constants.setHeights.poleTapeHighI), Units.inchesToMeters(Constants.setDistances.goaldistHighI)),
            DriveTillCollideCommand(drive),
            CloseGripperCommand(gripper),
            HighArmCommand(arm),
            OpenGripperCommand(gripper)
        )
    }
}
