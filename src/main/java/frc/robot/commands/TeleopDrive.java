// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import java.util.function.BooleanSupplier;
import java.util.function.DoubleSupplier;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Drivetrain;

public class TeleopDrive extends CommandBase {
  
  private final Drivetrain m_drivetrain;
  private final double xSpeed;
  private final double ySpeed;
  private final double zRotation;
  private final boolean fieldRelativeOverride;

  public TeleopDrive(Drivetrain m_drivetrain, DoubleSupplier xSpeed, DoubleSupplier ySpeed, DoubleSupplier zRotation, BooleanSupplier fieldRelativeOverride) {
    
    this.m_drivetrain = m_drivetrain;
    this.xSpeed = xSpeed.getAsDouble();
    this.ySpeed = ySpeed.getAsDouble();
    this.zRotation = zRotation.getAsDouble();
    this.fieldRelativeOverride = fieldRelativeOverride.getAsBoolean();

    addRequirements(m_drivetrain);

  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    System.out.println("Teleop Mecanum Initialized");
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {

    m_drivetrain.drive(xSpeed, ySpeed, zRotation, fieldRelativeOverride);

  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {

    m_drivetrain.drive(0, 0, 0, false);

  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return false;
  }
}
