// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.wpilibj.AnalogGyro;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.motorcontrol.MotorControllerGroup;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class DriveSubsystem extends SubsystemBase {
  /** Creates a new DriveSubsystem. */
  public static CANSparkMax driveleftFront;
  public static CANSparkMax driveleftBack;
  public static CANSparkMax driverightFront;
  public static CANSparkMax driverightBack;
  public static MotorControllerGroup driveright;
  public static MotorControllerGroup driveleft;
  public DifferentialDrive drive;
  public AnalogGyro gyro;
  public DriveSubsystem(int plf, int plb, int prf, int prb, int gyrose, int gyrop) {
    driveleftFront = new CANSparkMax(plf, MotorType.kBrushless);
    driveleftBack = new CANSparkMax(plb, MotorType.kBrushless);
    driverightFront = new CANSparkMax(prf, MotorType.kBrushless);
    driverightBack = new CANSparkMax(prb, MotorType.kBrushless);
    driveleft = new MotorControllerGroup(driveleftFront, driveleftBack);
    driveright = new MotorControllerGroup(driveleftFront, driveleftBack);
    drive = new DifferentialDrive(driveleft, driveright);
    driveright.setInverted(true);
    gyro = new AnalogGyro(gyrop);
    gyro.setSensitivity(gyrose);
    //get the "forward" angle on the gyro
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }
}
