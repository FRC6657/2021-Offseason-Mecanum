// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
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
    double backRight = xSpeed + ySpeed - zRotation;

    m_frontLeft.set(frontLeft);
    m_frontRight.set(-frontRight);
    m_backLeft.set(backLeft);
    m_backRight.set(-backRight);

    SmartDashboard.putNumber("FL", frontLeft);
    SmartDashboard.putNumber("FR", -frontRight);
    SmartDashboard.putNumber("BL", backLeft);
    SmartDashboard.putNumber("BR", -backRight);

  }

  /**
   * @param input    Input in need of a deadband
   * @param deadband Deadband threshold
   * @param weight   Weight of the curve
   * 
   * @author Andrew Card
   */
  private double cubicScaledDeadband(double input, double deadband, double weight){

    //Make the inputs smaller in size to make the equation more 'readable'
    double w = weight;
    double d = deadband;
    double x = input;

    //default output value
    double output = 0;

    //This equation is long an complicated here is a visual representation: https://www.desmos.com/calculator/coy9bhp32l
    if(Math.abs(x) > deadband){
      output = (((w * (Math.pow(x, 3)) + 1*(1 - w) * x) - (Math.abs(x)) / x * (w * (Math.pow(d, 3)) + (1 - w) * d)) / (1 - (w * (Math.pow(d, 3)) + (1 - w) * d)));
    }
    else{
      output=0;
    }
    return output;

  }

}
