package frc.robot

import edu.wpi.first.wpilibj2.command.Command
import edu.wpi.first.wpilibj2.command.button.CommandXboxController
import edu.wpi.first.wpilibj2.command.button.Trigger
import edu.wpi.first.wpilibj2.command.StartEndCommand
import edu.wpi.first.wpilibj2.command.Commands
import frc.robot.subsystems.CameraSubsystem
import frc.robot.subsystems.DriveSubsystem
import frc.robot.subsystems.ControlledArmSubsystem
import frc.robot.subsystems.GripperSubsystem
import frc.robot.commands.DriveToTargetCommand
import frc.robot.commands.PickupArmCommand
import frc.robot.commands.commandgroups.PickUpCargoCommand
import frc.robot.commands.commandgroups.DropCargoFloorCommand
import frc.robot.commands.commandgroups.DropCargoLowCommand
import frc.robot.commands.commandgroups.DropCargoHighCommand
import frc.robot.commands.commandgroups.AutoRoutine
import frc.robot.commands.LowerArmCommand
import frc.robot.commands.CloseGripperCommand
import frc.robot.commands.RetractArmCommand
import frc.robot.commands.OpenGripperCommand
import frc.robot.commands.HighArmCommand
import frc.robot.commands.FloorArmCommand
import frc.robot.Constants

import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard
import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardTab
import edu.wpi.first.wpilibj.shuffleboard.BuiltInWidgets
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser
import edu.wpi.first.math.util.Units

/**
 * This class is where the bulk of the robot should be declared. Since Command-based is a
 * "declarative" paradigm, very little robot logic should actually be handled in the [Robot]
 * periodic methods (other than the scheduler calls). Instead, the structure of the robot (including
 * subsystems, commands, and trigger mappings) should be declared here. 
 */
class RobotContainer {
    // The robot's subsystems and commands are defined here...
    public val driveSubsystem = DriveSubsystem()
    public val cameraSubsystem = CameraSubsystem()
    //Increase kP until the mechanism responds to a sudden change in setpoint by moving sharply to the new position. If the controller oscillates too much around the setpoint, reduce K_p until it stops.
    //Increase kI when the output gets “stuck” before converging to the setpoint.
    //Increase kDto help the system track smoothly-moving setpoints and further reduce oscillation.
    public val armSubsystem = ControlledArmSubsystem(Constants.Arm(5, 0.08, 0.01, 0.0, 0.0, 0.01, 1.0, -1.0, 0.1))
    public val gripperSubsystem = GripperSubsystem()
    public val io = IO()
    private val triggerCommandsMap = mapOf(
        Constants.actionNames.pickup to PickUpCargoCommand(gripperSubsystem, armSubsystem),
        Constants.actionNames.floorDropoff to DropCargoFloorCommand(gripperSubsystem, armSubsystem, cameraSubsystem, driveSubsystem),
        Constants.actionNames.lowDropoff to DropCargoLowCommand(gripperSubsystem, armSubsystem, cameraSubsystem, driveSubsystem),
        Constants.actionNames.highDropoff to DropCargoHighCommand(gripperSubsystem, armSubsystem, cameraSubsystem, driveSubsystem)
    )

    //smart dashboard
    val operatortab: ShuffleboardTab = Shuffleboard.getTab("Operator")
    public var autoselector = SendableChooser<Int>()
    /**
     * The pitch axis moves the robot forward and backward; the roll axis turns it left and right.
     * The throttle can be used to limit the speed of movement or rotation.
     * When the throttle is set to zero (fully down towards the minus sign printed on the joystick), all inputs to the joystick are zeroed and the robot will not move.
     * When the throttle is set to max (all the way up towards the plus sign printed on the joystick), no limits are placed on the magnitude of the input whatsoever.
     * @author Weiju Wang
     */
    val teleopCommand: Command =
        driveSubsystem.arcadeDriveCommand(
            //!work
            { (io.joystick.getY() * (-1 * io.joystick.getThrottle() + 1) / 2 ) * 0.99},
            { (io.joystick.getX() * (-1 * io.joystick.getThrottle() + 1) / 2) * 0.99}
        )

    /** The container for the robot. Contains subsystems, OI devices, and commands. */
    init {
        // Configure the trigger bindings
        autoselector.setDefaultOption("Basic Auto", 1)
        autoselector.addOption("Balance Auto", 2)
        autoselector.addOption("Scoring Auto", 3)
        operatortab.add("Auto Chooser", autoselector)
        configureBindings()
        driveSubsystem.setDefaultCommand(teleopCommand)
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
        //pick up cargo, extend arm(high and floor), retract arm
        Trigger{io.joystick.getRawButton(2)} . onTrue(
            Commands.sequence(
            OpenGripperCommand(gripperSubsystem).withTimeout(1.0),
            PickUpCargoCommand(gripperSubsystem, armSubsystem).withTimeout(0.1),
            CloseGripperCommand(gripperSubsystem).withTimeout(1.0)
            )

        )
        Trigger{io.joystick.getRawButton(3)} . onTrue(
            Commands.sequence(
                
            CloseGripperCommand(gripperSubsystem).withTimeout(1.0),
                HighArmCommand(armSubsystem).withTimeout(0.1)
            )
        )
        Trigger{io.joystick.getRawButton(4)} . onTrue(
            Commands.sequence(
                CloseGripperCommand(gripperSubsystem).withTimeout(1.0),
                FloorArmCommand(armSubsystem).withTimeout(0.1)
            )
        )
        Trigger{io.joystick.getRawButton(5)} . onTrue(
            Commands.sequence(
                OpenGripperCommand(gripperSubsystem).withTimeout(1.0),
                RetractArmCommand(armSubsystem).withTimeout(0.1)
            )
        )


        Trigger{io.joystick.getRawButton(6)} . onTrue(
           driveSubsystem.autoBalanceCommand()
        )
        
        
        
    }

    

}
