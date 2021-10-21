/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
/* import com.ctre.phoenix.motorcontrol.can.WPI_VictorSPX;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMax.IdleMode;
import com.revrobotics.CANSparkMaxLowLevel.MotorType; */

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class Intake extends SubsystemBase {

  private WPI_TalonSRX m_intake;
/*   private WPI_VictorSPX m_intake;
  private CANSparkMax m_intake; */

  public Intake() {

    m_intake = new WPI_TalonSRX(5);
    /* m_intake = new WPI_VictorSPX(5);
    m_intake = new CANSparkMax(5, MotorType.kBrushed); */

    m_intake.setNeutralMode(NeutralMode.Brake);
/*     m_intake.setIdleMode(IdleMode.kBrake); */

  }

  // Runs intake
  public void intake(double speed) {

    m_intake.set(speed);

  }

  @Override
  public void periodic() {
    SmartDashboard.putNumber("Intake", m_intake.get());
  }
}
