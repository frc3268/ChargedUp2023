package frc.robot.subsystems

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import org.photonvision.PhotonCamera;
import org.photonvision.targeting.PhotonPipelineResult


class CameraSubsystem : SubsystemBase() {
    val cam:PhotonCamera = PhotonCamera("CCP SPY BALOON")
    var frame:PhotonPipelineResult? = null
    


    override fun periodic() {
        // This method will be called once per scheduler run
        frame = cam.getLatestResult()
    }

    override fun simulationPeriodic() {
        // This method will be called once per scheduler run during simulation
    }
}
