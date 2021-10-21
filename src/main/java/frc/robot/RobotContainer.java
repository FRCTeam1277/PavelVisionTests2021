// Copyright (c) FIRST and other WPILib contributors.

// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;


import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.Preferences;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.PrintCommand;
import edu.wpi.first.wpilibj2.command.button.Button;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;
import frc.robot.commands.*;
import frc.robot.commands.facialcommands.DriveTowardsFace;
import frc.robot.subsystems.CameraIO;
import frc.robot.subsystems.Drivetrain;
import frc.robot.subsystems.OnBoardIO;
import frc.robot.subsystems.OnBoardIO.ChannelMode;
import frc.stream.IStreamInput;


/**
 * This class is where the bulk of the robot should be declared. Since Command-based is a
 * "declarative" paradigm, very little robot logic should actually be handled in the {@link Robot}
 * periodic methods (other than the scheduler calls). Instead, the structure of the robot (including
 * subsystems, commands, and button mappings) should be declared here.
 */
public class RobotContainer
{
    // The robot's subsystems and commands are defined here...
    private final Drivetrain drivetrain = new Drivetrain();
    private final OnBoardIO onBoardIO = new OnBoardIO(ChannelMode.INPUT, ChannelMode.INPUT);
    private final CameraIO cameraIO = CameraIO.getInstance();

    // Assumes a gamepad plugged into channel 0
    private final Joystick joystick = new Joystick(0);

    // Create SmartDashboard chooser for autonomous routines
    private final SendableChooser<Command> chooser = new SendableChooser<>();

    // NOTE: The I/O pin functionality of the 5 exposed I/O pins depends on the hardware "overlay"
    // that is specified when launching the wpilib-ws server on the Romi raspberry pi.
    // By default, the following are available (listed in order from inside of the board to outside):
    // - DIO 8 (mapped to Arduino pin 11, closest to the inside of the board)
    // - Analog In 0 (mapped to Analog Channel 6 / Arduino Pin 4)
    // - Analog In 1 (mapped to Analog Channel 2 / Arduino Pin 20)
    // - PWM 2 (mapped to Arduino Pin 21)
    // - PWM 3 (mapped to Arduino Pin 22)
    //
    // Your subsystem configuration should take the overlays into account


    /** The container for the robot. Contains subsystems, OI devices, and commands. */
    public RobotContainer()
    {

        // Configure the button bindings
        configureButtonBindings();
    }


    /**
    * Use this method to define your button->command mappings. Buttons can be created by
    * instantiating a {@link edu.wpi.first.wpilibj.GenericHID GenericHID}, or one of its
    * subclasses ({@link edu.wpi.first.wpilibj.Joystick Joystick} or
    * {@link edu.wpi.first.wpilibj.XboxController XboxController}), and then passing it to a
    * {@link edu.wpi.first.wpilibj2.command.button.JoystickButton JoystickButton}.
    */
    private void configureButtonBindings()
    {
        // Default command is arcade drive. This will run unless another command
        // is scheduled over it.
        drivetrain.setDefaultCommand(getArcadeDriveCommand());

        JoystickButton jb = new JoystickButton(joystick,1);
        jb.whenActive(
//                new DriveHalfHexagonCommandGroup(drivetrain,0.5,12)
                new RunFacialDetection(new RecognizeFace(), new DriveTowardsFace(drivetrain))
//                new PIDRightMotor(drivetrain)
        );

        // Setup SmartDashboard options
        chooser.setDefaultOption("Auto Routine Distance", new AutonomousDistance(drivetrain));
        chooser.addOption("Auto Routine Time", new AutonomousTime(drivetrain));
        //circle auto thing


        SmartDashboard.putData(chooser);
    }


    /**
     * Use this to pass the autonomous command to the main {@link Robot} class.
     *
     * @return the command to run in autonomous
     */
    public Command getAutonomousCommand()
    {
        return chooser.getSelected();
    }


    /**
     * Use this to pass the teleop command to the main {@link Robot} class.
     *
     * @return the command to run in teleop
     */
    public Command getArcadeDriveCommand()
    {
        return new ArcadeDrive(drivetrain, () -> -joystick.getRawAxis(1), () -> joystick.getRawAxis(2));
    }
}
