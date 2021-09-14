// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class Drivetrain extends SubsystemBase {

  private WPI_TalonSRX m_frontLeft;
  private WPI_TalonSRX m_frontRight;
  private WPI_TalonSRX m_backLeft;
  private WPI_TalonSRX m_backRight;
  
  public Drivetrain() {

    m_frontLeft = new WPI_TalonSRX(1);
    m_frontRight = new WPI_TalonSRX(2);
    m_backLeft = new WPI_TalonSRX(3);
    m_backRight = new WPI_TalonSRX(4);

  }

  public void driveCartesian(double xSpeed, double ySpeed, double zRotation){

    double frontLeft = xSpeed + ySpeed + zRotation;
    double frontRight = -xSpeed + ySpeed - zRotation;
    double backLeft = -xSpeed + ySpeed + zRotation;
    double backright = xSpeed - ySpeed - zRotation;

    m_frontLeft.set(frontLeft);
    m_frontRight.set(frontRight);
    m_backLeft.set(backLeft);
    m_backRight.set(backright);

  }
}
