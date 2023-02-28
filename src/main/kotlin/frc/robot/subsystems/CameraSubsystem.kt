package frc.robot.subsystems

import edu.wpi.first.apriltag.AprilTagFieldLayout
import edu.wpi.first.math.geometry.Pose2d
import edu.wpi.first.math.geometry.Transform3d
import edu.wpi.first.math.util.Units
import edu.wpi.first.wpilibj.DriverStation
import edu.wpi.first.wpilibj.Filesystem
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard
import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardTab
import edu.wpi.first.wpilibj2.command.SubsystemBase
import frc.robot.Constants
import java.io.IOException
import org.photonvision.EstimatedRobotPose
import org.photonvision.PhotonCamera
import org.photonvision.PhotonPoseEstimator
import org.photonvision.PhotonPoseEstimator.PoseStrategy
import org.photonvision.targeting.PhotonPipelineResult
import org.photonvision.PhotonUtils

class CameraSubsystem : SubsystemBase() {
    val cam: PhotonCamera = PhotonCamera("CCP BALOON CAMERA")
    var frame: PhotonPipelineResult = PhotonPipelineResult()
    // star out on reflective tape pipeline
    var aprilOn: Boolean = false
    val visiontab: ShuffleboardTab = Shuffleboard.getTab("Vision")
    val distanceLabel = visiontab.add("Distance to Best Target", 0.0).getEntry()
    val pipelineLabel = visiontab.add("Pipeline", "Reflective tape").getEntry()
    var poseEstimator: PhotonPoseEstimator? = null

    init {
        try {
            poseEstimator =
                PhotonPoseEstimator(
                    AprilTagFieldLayout(
                        Filesystem.getDeployDirectory().toString()
                        + "/2023-chargedup.json"
                    ),
                    PoseStrategy.MULTI_TAG_PNP,
                    cam,
                    Transform3d()
                )
        } catch (e: IOException) {
            DriverStation.reportError("AprilTag: Failed to Load", e.getStackTrace())
            // !add some way to lock down apriltage features after this
        }
    }

    /**
     * Called once per scheduler run.
     */
    override fun periodic() {
        frame = cam.getLatestResult()
        val distance: Double? = movementToTarget(Constants.setHeights.poleTapeLow)?.distance
        distance ?: return
        distanceLabel.setDouble(distance)
        pipelineLabel.setString(if(aprilOn) "AprilTag" else "Reflective Tape")
    }

    fun takePicture(processed: Boolean) {
        if(processed)
            cam.takeOutputSnapshot()
        else
            cam.takeInputSnapshot()
    }

    fun movementToTarget(targetHeight: Double): Constants.MovementTarget? {
        if(!frame.hasTargets()) {
            return null
        }
        
        val pitch: Double = Units.degreesToRadians(frame.getBestTarget().getPitch())
        val dist: Double = (Units.inchesToMeters(targetHeight) - Units.inchesToMeters(Constants.setHeights.camera))
            / Math.tan(pitch - 10)
        return Constants.MovementTarget(Math.abs(dist), frame.getBestTarget().getYaw())
    }

    fun getEstimatedPose(prevPose: Pose2d): EstimatedRobotPose? {
        poseEstimator ?: return null
        cam.pipelineIndex = 1
        aprilOn = true
        poseEstimator?.setReferencePose(prevPose)
        return poseEstimator?.update()?.orElse(null)
    }

    fun resetPose(pose: Pose2d) {
        poseEstimator ?: return
        poseEstimator?.setReferencePose(pose)
    }

    override fun simulationPeriodic() {
        // This method will be called once per scheduler run during simulation
    }
}
