package frc.robot.commands;


import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;
import frc.stream.IStreamInput;
import frc.stream.InputDataStream;
import frc.stream.OutputDataStream;
import frc.stream.StreamData;
import org.opencv.core.Rect;

import javax.annotation.Nonnull;

public class RunFacialDetection extends ParallelCommandGroup {

    /**
     * @param recognizeFaceCommand = outputStream (command that writes data)
     * @param <R> Any command that implements the IStreamInput (with type Integer)
     * @param commands array of R-worthy commands
     */
    public <R extends IStreamInput<Integer> & Command> RunFacialDetection(@Nonnull RecognizeFace recognizeFaceCommand, R... commands) {

        StreamData<Integer> faceRects = new StreamData<>();
        recognizeFaceCommand.setOutputStream(new OutputDataStream<Integer>(faceRects));
        for(IStreamInput<Integer> a : commands) {
            a.setInputStream(new InputDataStream<>(faceRects)); //sets inputstream for all commands
        }
        addCommands(recognizeFaceCommand);
        addCommands(commands);
    }

}