package frc.robot

import edu.wpi.first.wpilibj2.command.Command
import edu.wpi.first.wpilibj2.command.button.CommandXboxController
import edu.wpi.first.wpilibj2.command.button.Trigger
import edu.wpi.first.wpilibj2.command.Commands
import edu.wpi.first.wpilibj2.command.InstantCommand
import frc.robot.commands.JoystickArcadeDrive
import frc.robot.subsystems.CameraSubsystem
import frc.robot.subsystems.DriveSubsystem

/**
 * This class is where the bulk of the robot should be declared. Since Command-based is a
 * "declarative" paradigm, very little robot logic should actually be handled in the [Robot]
 * periodic methods (other than the scheduler calls). Instead, the structure of the robot (including
 * subsystems, commands, and trigger mappings) should be declared here.
 */
class RobotContainer {
    // The robot's subsystems and commands are defined here...
    private val driveSubsystem = DriveSubsystem()
    private val Constants = Constants()
    public val cameraSubsystem = CameraSubsystem()
    // private val firstArmSubsystem = ControlledArmSubsystem(Constants.firstArm)
    // private val secondArmSubsystem = ControlledArmSubsystem(Constants.secondArm)
    private val io = IO()

    /** The container for the robot. Contains subsystems, OI devices, and commands. */
    init {
        // Configure the trigger bindings
        configureBindings()
    }

    /**
     * Use this method to define your trigger->command mappings. Triggers can be created via the
     * [Trigger#Trigger(java.util.function.BooleanSupplier)] constructor with an arbitrary
     * predicate, or via the named factories in
     * [edu.wpi.first.wpilibj2.command.button.CommandGenericHID]'s subclasses for
     * [CommandXboxController]/[edu.wpi.first.wpilibj2.command.button.CommandPS4Controller]
     * controllers or [edu.wpi.first.wpilibj2.command.button.CommandJoystick].
     */
    private fun configureBindings() {
        // Schedule ExampleCommand when exampleCondition changes to true
        // Trigger {io.firstButton.asBoolean}.onTrue(ArcadeDriveCommand(driveSubsystem, 1.0,0.0))
        Trigger { io.firstButton.asBoolean }.toggleOnTrue(driveSubsystem.autoBalanceCommand())
    }

    /**
     * Use this to pass the autonomous command to the main [Robot] class.
     *
     * @return the command to run in autonomous
     */
    val autonomousCommand: Command = Commands.run(() -> driveSubsystem.arcadeDrive(1.0, 0.0))

    val teleopCommand: Command = JoystickArcadeDrive(driveSubsystem, io.joystick)
}
